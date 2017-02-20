package org.payn.chsm;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

import org.payn.chsm.io.OutputHandlerFactory;
import org.payn.chsm.io.logger.Logger;
import org.payn.chsm.processors.ControllerHolon;
import org.payn.chsm.resources.time.ResourceTime;

/**
 * Abstract definition of loader for helping in creating loaders for model builders
 * 
 * @author v78h241
 *
 * @param <MBT>
 *      model builder type
 */
public abstract class ModelLoader<MBT extends ModelBuilder<?>> {

   /**
    * Key name in command line argument to the file path for the builder
    */
   public static final Object ARG_FILE_PATH = "loaderPath";

   /**
    * Key name in command line argument to the class path for the builder
    */
   public static final Object ARG_CLASS_PATH = "loaderClassPath";

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
   public static Object createObjectInstance(ClassLoader classLoader, File path, String classPath, String errorMessage) throws Exception
   {
      if (!path.exists())
      {
         throw new Exception(String.format(
               "%s: class location %s does not exist.",
               errorMessage,
               path.getAbsolutePath()
               ));
      }
      Object object = loadClass(classLoader, path, classPath).newInstance();
      return object;
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
   public static Class<?> loadClass(ClassLoader classLoader, File path, String classPath) throws Exception
   {
      URLClassLoader outCL = new URLClassLoader(
            new URL[] {path.toURI().toURL()},
            classLoader
            );
      Class<?> loadedClass = outCL.loadClass(classPath);
      outCL.close();
      return loadedClass;
   }

   /**
    * Matrix builder
    */
   protected MBT builder;
   
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
    * Create the matrix and load the controller
    * 
    * @param path
    *       file system path
    * @param classPath
    *       class path
    * @param matrixName
    *       name of the matrix holon
    * @throws Exception
    *       if error in loading controller class
    */
   protected void installController(ControllerHolon controller) throws Exception 
   {
      controller.setBuilder(builder);
      controller.setLogger(loggerManager);
   }

   /**
    * Create the matrix with loaded builder
    * 
    * @param argMap
    *       map of arguments
    * @param workingDir
    *       working directory
    * @return
    *       matrix
    * @throws Exception
    *       if errors in loading the builder, processor, outputters, or matrix
    */
   public MBT load(HashMap<String, String> argMap, File workingDir) 
         throws Exception
   {
      this.argMap = argMap;
      this.workingDir = workingDir;
      
      initializeInputs();
      initializeLoggers();
      
      System.out.println("Loading the loggers...");
      if (loggerList.isEmpty())
      {
         System.out.println("No loggers are selected, model status is invisible...");
      }
      for (Class<?> logger: loggerList)
      {
         loggerManager.addLogger((Logger)logger.newInstance());
         System.out.println(String.format(
            "   Loaded logger %s ...",
            logger.getCanonicalName()
            ));
      }

      loggerManager.statusUpdate("Loading the model builder...");
      builder = createBuilder();
      loggerManager.statusUpdate(String.format(
            "   Loaded the model builder %s...",
            builder.getClass().getCanonicalName()
            ));
      builder.setWorkingDirectory(workingDir);
      builder.setArgMap(argMap);
      builder.setLoader(this);
      builder.setLogger(loggerManager);

      loggerManager.statusUpdate("Loading the controller...");
      ControllerHolon controller = getController();
      loggerManager.statusUpdate(String.format(
            "   Loaded the controller %s ...",
            controller.getClass().getCanonicalName()
            ));
      installController(controller);
      
      loggerManager.statusUpdate("Loading the configured resources...");
      ArrayList<Resource> resources = getResources();
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
      
      loggerManager.statusUpdate("Loading the output handlers...");
      ArrayList<OutputHandlerFactory<?,?>> factories = getOutputHandlerFactories();
      for (OutputHandlerFactory<?,?> factory: factories)
      {
         OutputHandler outputHandler = factory.createOutputHandler();
         builder.getController().addOutputHandler(outputHandler);
         loggerManager.statusUpdate(String.format(
               "   Loaded output handler %s ...", 
               outputHandler.getClass().getCanonicalName()
               ));
      }

      return builder;
   }

   /**
    * Initialize the loader
    * 
    * @throws Exception
    */
   protected abstract void initializeInputs() throws Exception;
   
   /**
    * Initialize the loggers
    * 
    * @throws Exception
    */
   protected abstract void initializeLoggers() throws Exception;

   /**
    * Get the appropriate loaded builder class
    * 
    * @return
    *       builder class
    * @throws Exception
    */
   protected abstract MBT createBuilder() throws Exception;
   
   /**
    * Get the list of output handler factories
    * 
    * @return
    * @throws Exception 
    */
   protected abstract ArrayList<OutputHandlerFactory<?,?>> getOutputHandlerFactories() throws Exception;

   /**
    * Get the configured controller class
    * 
    * @return
    *       controller class
    * @throws Exception
    */
   protected abstract ControllerHolon getController() throws Exception;

   /**
    * Get the list of configured resources
    * 
    * @return
    *       resource list
    * @throws Exception
    */
   protected abstract ArrayList<Resource> getResources() throws Exception;

}
