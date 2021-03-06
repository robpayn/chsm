package org.payn.chsm.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.payn.chsm.io.reporters.ReporterFactory;
import org.payn.chsm.io.reporters.ReporterFactoryXML;
import org.payn.chsm.io.xmltools.ElementBuilder;
import org.payn.chsm.io.xmltools.ElementLogger;
import org.payn.chsm.io.xmltools.ElementProcessor;
import org.payn.chsm.io.xmltools.ElementReporter;
import org.payn.chsm.io.xmltools.ElementResource;
import org.payn.chsm.io.xmltools.XMLDocumentModelConfig;
import org.payn.chsm.processors.ControllerHolon;
import org.payn.chsm.resources.Resource;

/**
 * Model loader that is configured by an XML document
 * 
 * @author robpayn
 */
public class ModelLoaderXML extends ModelLoader {

   /**
    * XML document with configuration information
    */
   protected XMLDocumentModelConfig documentConfig;

   /**
    * Get the XML configuration document
    * 
    * @return
    *       XML document
    */
   public XMLDocumentModelConfig getDocument() 
   {
      return documentConfig;
   }

   /**
    * Root path for loading
    */
   protected String pathRoot;

   @Override
   protected void loadConfiguration() throws Exception
   {
      // Check for configuration file in file system
      if (!argMap.containsKey("config"))
      {
         throw new Exception(
               "Must provide an argument for configuration file relative to working directory " +
                     "(e.g. 'config=./config/config.xml')"
               );
      }
      File configFile = new File(
            workingDir.getAbsolutePath() + argMap.get("config")
            );
      if (!configFile.exists() || configFile.isDirectory()) 
      {
         throw new Exception(String.format(
               "%s is an invalid configuration file.", 
               configFile.getAbsolutePath()
               ));
      }
      
      // Parse the XML configuration file
      documentConfig = new XMLDocumentModelConfig(configFile);
      pathRoot = documentConfig.getPathRoot();
   }

   @Override
   protected void loadLoggers() throws Exception {
      Iterator<ElementLogger> loggerIter = documentConfig.getLoggerIterator();
      if (loggerIter != null)
      {
         loggerList.clear();
         while (loggerIter.hasNext())
         {
            ElementLogger loggerElem = loggerIter.next();
            File loggerFile = loggerElem.getFile(pathRoot);
            String classPath = loggerElem.getClassPath();
            loggerList.add(ModelLoader.loadClass(
                  loggerFile, 
                  classPath
                  ));
         }
      }
   }

   @Override
   protected ControllerHolon loadController() throws Exception 
   {
      ElementProcessor procElem = documentConfig.getProcessorElement();
      if (procElem != null)
      {
         String classPath = procElem.getClassPath();
         if (!classPath.equals(""))
         {
            return (ControllerHolon)ModelLoader.createObjectInstance(
                  procElem.getFile(pathRoot), 
                  classPath,
                  String.format("Controller %s", classPath)
                  );
         }
      }
      return null;
   }

   @Override
   protected ArrayList<Resource> loadResources() throws Exception 
   {
      Iterator<ElementResource> resourceIter = documentConfig.getResourceIterator();
      ArrayList<Resource> list = new ArrayList<Resource>();
      while (resourceIter.hasNext())
      {
         ElementResource resourceElem = resourceIter.next();
         if (resourceElem.isActive())
         {
            Resource resource = getResource(resourceElem);
            if (resource == null)
            {
               throw new Exception(String.format(
                     "Configured resource %s could not be loaded.", 
                     resourceElem.getName()
                     ));
            }
            list.add(resource);
         }
      }
      return list;
   }

   /**
    * Get the resource object appropriate for the element
    * 
    * @param resourceElem
    * @return
    * @throws Exception
    */
   protected Resource getResource(ElementResource resourceElem) throws Exception 
   {
      String classPath = resourceElem.getClassPath();
      if (!classPath.equals(""))
      {
         Resource resource = (Resource)ModelLoader.createObjectInstance(
               resourceElem.getFile(pathRoot), 
               classPath, 
               String.format("Resource %s", classPath)
               );
         resource.initialize(resourceElem.getName());
         return resource;
      }
      else
      {
         return null;
      }
   }

   @Override
   protected ArrayList<ReporterFactory<?,?>> loadReporterFactories() throws Exception 
   {
      // Create the reporters and set their configurations
      Iterator<ElementReporter> outputIter = documentConfig.getReporterIterator();
      ArrayList<ReporterFactory<?,?>> list = new ArrayList<ReporterFactory<?,?>>();
      while (outputIter.hasNext())
      {
         ElementReporter outputElem = outputIter.next();
         if (outputElem.isActive())
         {
            ReporterFactoryXML<?> reporterFactory = getReporterFactory(outputElem);
            reporterFactory.setConfig(outputElem);
            reporterFactory.setLogger(loggerManager);
            list.add(reporterFactory);
         }
      }
      return list;
   }

   /**
    * Get a configured reporter factory
    * 
    * @param outputElem
    * @return
    * @throws Exception
    */
   protected ReporterFactoryXML<?> getReporterFactory(ElementReporter outputElem) throws Exception 
   {
      String classPath = outputElem.getClassPath();
      if (!classPath.equals(""))
      {
         return (ReporterFactoryXML<?>)createObjectInstance(
            outputElem.getFile(pathRoot), 
            classPath, 
            String.format("Reporter factory %s", classPath)
            );
      }
      else
      {
         return null;
      }
   }

   @Override
   protected ModelBuilder loadBuilder() throws Exception 
   {
      ElementBuilder builderElem = 
            documentConfig.getBuilderElement();
      String classPath = builderElem.getClassPath();
      if (!classPath.equals(""))
      {
         return (ModelBuilder)ModelLoader.createObjectInstance(
               builderElem.getFile(pathRoot), 
               classPath,
               String.format("Builder %s", classPath)
               );
      }
      else
      {
         return null;
      }
   }

}
