package org.payn.chsm.io.file;

import java.io.File;

import org.payn.chsm.io.OutputHandlerFactoryXML;
import org.payn.chsm.io.xml.ElementOutput;
import org.w3c.dom.Element;

/**
 * Factory for a file system output handler
 * @author v78h241
 *
 */
public class OutputHandlerFileSystemFactoryXML extends OutputHandlerFactoryXML<OutputHandlerFileSystem> {
   
   /**
    * Construct the factory with the provided output handler
    * 
    * @param outputHandler
    * @param config 
    */
   public OutputHandlerFileSystemFactoryXML(OutputHandlerFileSystem outputHandler, ElementOutput config)
   {
      this.outputHandler = outputHandler;
      this.config = config;
   }

   @Override
   public void init() 
   {
      OutputHandlerFileSystem handler = 
            ((OutputHandlerFileSystem)outputHandler);
      Element fileElement = config.getFirstChildElement("file");
      File workingDir = new File(System.getProperty("user.dir"));
      handler.setWorkingDir(workingDir);
      File outputDir;
      if(Boolean.valueOf(fileElement.getAttribute("fromWorkingDir")))
      {
         outputDir = new File(
               workingDir.getAbsolutePath() + File.separator + fileElement.getAttribute("outputpath")
               );
      }
      else
      {         
         outputDir = new File(
               fileElement.getAttribute("outputpath")
               );
      }
      if(!outputDir.exists())
      {
         outputDir.mkdirs();
      }
      handler.setOutputDir(outputDir);
   }

   @Override
   public OutputHandlerFileSystem newOutputHandler() throws Exception 
   {
      throw new Exception("Cannot create an abstract file system output handler");
   }

}
