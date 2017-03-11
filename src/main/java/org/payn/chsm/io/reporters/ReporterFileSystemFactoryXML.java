package org.payn.chsm.io.reporters;

import java.io.File;
import java.util.HashMap;

import org.payn.chsm.ReporterFactoryXML;
import org.payn.chsm.io.xmltools.ElementReporter;
import org.w3c.dom.Element;

/**
 * Factory for a file system reporter
 * 
 * @author v78h241
 *
 */
public class ReporterFileSystemFactoryXML extends ReporterFactoryXML<ReporterFileSystem> {
   
   /**
    * Construct the factory with the provided reporter
    * 
    * @param reporter
    * @param config 
    */
   public ReporterFileSystemFactoryXML(ReporterFileSystem reporter, ElementReporter config)
   {
      this.reporter = reporter;
      this.config = config;
   }

   @Override
   public void init() throws Exception 
   {
      Element fileElement = config.getFirstChildElement("file");
      File outputDir;
      if(Boolean.valueOf(fileElement.getAttribute("fromWorkingDir")))
      {
         outputDir = new File(
               reporter.getWorkingDir().getAbsolutePath() + File.separator 
                  + fileElement.getAttribute("outputpath")
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
      reporter.setOutputDir(outputDir);
   }

   @Override
   public ReporterFileSystem newReporter(File workingDir, HashMap<String, String> argMap) throws Exception 
   {
      throw new Exception("Cannot create an abstract file system reporter");
   }

}
