package org.payn.chsm.io.reporters;

import java.io.File;
import java.util.HashMap;

/**
 * Reporter that writes reports to a directory in a file system
 * 
 * @author robpayn
 * 
 */
public abstract class ReporterFileSystem extends ReporterAbstract<File> {

   /**
    * Output directory
    */
   protected File outputDir;
   
   /**
    * Setter
    * 
    * @param outputDir
    *       File object representing the output directory
    */
   public void setOutputDir(File outputDir)
   {
      this.outputDir = outputDir;
   }
   
   /**
    * Construct a new instance with the provided working directory and 
    * command line arguments
    * 
    * @param workingDir
    * @param argMap
    */
   public ReporterFileSystem(File workingDir, HashMap<String, String> argMap) 
   {
      super(workingDir, argMap);
   }

}
