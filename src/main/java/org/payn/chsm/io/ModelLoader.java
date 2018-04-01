package org.payn.chsm.io;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.payn.chsm.io.logger.Logger;
import org.payn.chsm.io.logger.LoggerManager;
import org.payn.chsm.io.reporters.Reporter;
import org.payn.chsm.io.reporters.ReporterFactory;
import org.payn.chsm.processors.ControllerHolon;
import org.payn.chsm.resources.Resource;
import org.payn.chsm.resources.time.ResourceTime;

/**
 * Abstract definition of loader for helping in creating loaders for model builders
 * 
 * @author v78h241
 *
 */
public abstract class ModelLoader {

   /**
    * Key name in command line argument to the file path for the builder
    */
   public static final Object ARG_FILE_PATH = "loaderPath";

   /**
    * Key name in command line argument to the class path for the builder
    */
   public static final Object ARG_CLASS_PATH = "loaderClassPath";

   /**
    * Static method to load the builder for the model,
    * as configured by command line arguments.
    * 
    * <p>Command line arguments must be formated by space-delimited
    * &ltkey&gt=&ltvalue&gt format</p>
    * 
    * 
    * @param args
    *       command line arguments
    * @param workingDir
    *       working directory for the model
    * @return
    *       the builder
    * @throws Exception
    *       if error in creating cell network
    */
   public static ModelBuilder loadModel(File workingDir, String[] args) 
         throws Exception
   {
      HashMap<String,String> argMap = ModelLoader.createArgMap(args);
      ModelLoader loader = (ModelLoader)ModelLoader.createObjectInstance(
            new File(argMap.get(ModelLoader.ARG_FILE_PATH)),
            argMap.get(ModelLoader.ARG_CLASS_PATH),
            "Model loader"
            );
      return loader.load(workingDir, argMap);
   }
   
   /**
    * Create a hashmap of key value pairs from the command line arguments.
    * Delimiter between the key and value is expected to be an equals sign.
    * 
    * @param args
    *       Array of arguments
    * @return
    *       Hashmap of key value pairs
    * @throws Exception
    *       If error in key value format
    */
   public static HashMap<String,String> createArgMap(String[] args) throws Exception
   {
      // Build the argument map
      HashMap<String,String> argMap = new HashMap<String,String>();
      for (int i = 0; i < args.length; i++)
      {
         String[] arg = args[i].split("=");
         if (arg.length != 2)
         {
            throw new Exception(String.format(
                  "'%s' is a malformed command line argument.",
                  args[i]
                  ));
         }
         argMap.put(arg[0], arg[1]);
      }
      return argMap;
   }

   /**
    * Create an object from a provided file system path and class path
    * 
    * @param classLoader 
    *       class loader to use
    * @param path
    *       File system path
    * @param classPath
    *       class path
    * @param errorMessage
    *       error preamble if problem with loading
    * @return
    *       instantiated object
    * @throws Exception
    *       if error in loading object
    */
   public static Object createObjectInstance(File path, 
         String classPath, String errorMessage) throws Exception
   {
      if (!path.exists())
      {
         throw new Exception(String.format(
               "%s: class location %s does not exist.",
               errorMessage,
               path.getAbsolutePath()
               ));
      }
      Class<?> loadedClass = loadClass(path, classPath);
      return loadedClass.getConstructor().newInstance();
   }
   
   /**
    * Load a class
    * 
    * @param classLoader
    * @param path
    * @param classPath
    * @return
    *       loaded class
    * @throws Exception
    */
   public static Class<?> loadClass(File path, String classPath) throws Exception
   {
      URLClassLoader outCL = new URLClassLoader(
            new URL[] {path.toURI().toURL()},
            ModelLoader.class.getClassLoader()
            );
      Class<?> loadedClass = Class.forName(classPath, false, outCL);
      return loadedClass;
   }

   /**
    * The logger manager for all logging
    */
   protected LoggerManager loggerManager;

   /**
    * Argument map
    */
   protected HashMap<String, String> argMap;

   /**
    * Working directory
    */
   protected File workingDir;
   
   /**
    * List of logger classes to install
    */
   protected ArrayList<Class<?>> loggerList;

   
   /**
    * Raw constructor
    */
   public ModelLoader()
   {
      this.loggerManager = new LoggerManager();
      this.loggerList = new ArrayList<Class<?>>();
   }
   
   /**
    * Load model components
    * 
    * @param workingDir
    *       working directory
    * @param argMap
    *       map of key-value pair arguments
    * @return 
    *       reference to the loaded model builder
    * @throws Exception
    *       if errors in loading the builder, processor, reporters, or matrix
    */
   public ModelBuilder load(File workingDir, HashMap<String, String> argMap) 
         throws Exception
   {
      // Check for valid working directory
      if (!workingDir.exists()) 
      {
         throw new Exception(String.format(
               "Specified working directory does not exist.",
               workingDir.getAbsolutePath()
               ));
      }
      else if (workingDir.isFile()) 
      {
         throw new Exception(String.format(
               "Working directory %s cannot be a file.",
               workingDir.getAbsolutePath()
               ));
      }
      
      this.argMap = argMap;
      this.workingDir = workingDir;
      
      System.out.println();
      System.out.println(String.format(
            "Loading with %s ...",
            this.getClass().getCanonicalName()
            ));

      loadConfiguration();
      
      System.out.println("Loading the loggers...");
      loadLoggers();
      if (loggerList.isEmpty())
      {
         System.out.println("No loggers are selected, model status is invisible...");
      }
      for (Class<?> logger: loggerList)
      {
         loggerManager.addLogger((Logger)logger.getConstructor().newInstance());
         System.out.println(String.format(
            "   Loaded logger %s ...",
            logger.getCanonicalName()
            ));
      }

      loggerManager.statusUpdate("Loading the model builder...");
      ModelBuilder builder = loadBuilder();
      loggerManager.statusUpdate(String.format(
            "   Loaded the model builder %s...",
            builder.getClass().getCanonicalName()
            ));
      builder.setWorkingDirectory(workingDir);
      builder.setArgMap(argMap);
      builder.setLoader(this);
      builder.setLogger(loggerManager);

      loggerManager.statusUpdate("Loading the controller...");
      ControllerHolon controller = loadController();
      loggerManager.statusUpdate(String.format(
            "   Loaded the controller %s ...",
            controller.getClass().getCanonicalName()
            ));
      controller.setBuilder(builder);
      controller.setLoggerManager(loggerManager);
      
      loggerManager.statusUpdate("Loading the configured resources...");
      ArrayList<Resource> resources = loadResources();
      boolean isTimeResourceLoaded = false;
      for (Resource resource: resources)
      {
         if (resource.getName().equals(ResourceTime.DEFAULT_NAME))
         {
            isTimeResourceLoaded = true;
         }
         builder.addResource(resource);
         loggerManager.statusUpdate(String.format(
               "   Resource %s loaded with name \"%s\" ...", 
               resource.getClass().getCanonicalName(),
               resource.getName()
               ));
      }

      if (!isTimeResourceLoaded)
      {
         loggerManager.statusUpdate("Loading the default time resource...");
         Resource resourceTime = new ResourceTime();
         resourceTime.initialize(ResourceTime.DEFAULT_NAME);
         builder.addResource(resourceTime);
      }
      
      loggerManager.statusUpdate("Loading the reporters...");
      LinkedHashMap<String, ReporterFactory<?,?>> factories = loadReporterFactories();
      for (Entry<String, ReporterFactory<?,?>> entry: factories.entrySet())
      {
         Reporter reporter = entry.getValue().createReporter(workingDir, argMap);
         builder.getController().addReporter(entry.getKey(), reporter);
         loggerManager.statusUpdate(String.format(
               "   Loaded reporter %s ...", 
               reporter.getClass().getCanonicalName()
               ));
      }
      
      return builder;
   }

   /**
    * Initialize the loader
    * 
    * @throws Exception
    */
   protected abstract void loadConfiguration() throws Exception;
   
   /**
    * Initialize the loggers
    * 
    * @throws Exception
    */
   protected abstract void loadLoggers() throws Exception;

   /**
    * Get the appropriate loaded builder class
    * 
    * @return 
    *       builder
    * @throws Exception
    */
   protected abstract ModelBuilder loadBuilder() throws Exception;
   
   /**
    * Get the list of reporter factories
    * 
    * @return
    *       Array list of reporter factories
    * @throws Exception 
    */
   protected abstract LinkedHashMap<String, ReporterFactory<?,?>> loadReporterFactories() 
         throws Exception;

   /**
    * Get the configured controller class
    * 
    * @return
    *       controller class
    * @throws Exception
    */
   protected abstract ControllerHolon loadController() throws Exception;

   /**
    * Get the list of configured resources
    * 
    * @return
    *       resource list
    * @throws Exception
    */
   protected abstract ArrayList<Resource> loadResources() throws Exception;

}
