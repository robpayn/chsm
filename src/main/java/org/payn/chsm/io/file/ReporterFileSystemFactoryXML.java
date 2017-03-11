package org.payn.chsm.io.file;

import java.io.File;

import org.payn.chsm.io.ReporterFactoryXML;
import org.payn.chsm.io.xml.ElementReporter;
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
   public void init() 
   {
      ReporterFileSystem handler = 
            ((ReporterFileSystem)reporter);
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
   public ReporterFileSystem newReporter() throws Exception 
   {
      throw new Exception("Cannot create an abstract file system reporter");
   }

}
