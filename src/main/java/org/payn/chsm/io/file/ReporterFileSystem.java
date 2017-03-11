package org.payn.chsm.io.file;

import java.io.File;

import org.payn.chsm.ReporterAbstract;

/**
 * Reporter that writes reports to a directory in a file system
 * 
 * @author robpayn
 * 
 */
public abstract class ReporterFileSystem extends ReporterAbstract<File> {

   /**
    * Working directory
    */
   protected File workingDir;
   
   /**
    * Setter 
    * 
    * @param workingDir
    *       File object for working directory
    */
   public void setWorkingDir(File workingDir) 
   {
      this.workingDir = workingDir;
   }
   
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
   
}
