package org.payn.chsm.io.reporters;

import java.io.File;
import java.util.HashMap;

/**
 * A file system reporter where the condition is based on regular iteration intervals
 * 
 * @author robpayn
 *
 */
public abstract class ReporterInterval extends ReporterFileSystem {

   /**
    * Iteration interval between output events
    */
   protected long interval;

   /**
    * Setter for the interval
    * 
    * @param interval
    *       value for the interval
    */
   public void setInterval(long interval) 
   {
      this.interval = interval;
   }

   /**
    * Construct a new instance with the provided working directory and
    * argument map
    * 
    * @param workingDir
    * @param argMap
    */
   public ReporterInterval(File workingDir, HashMap<String, String> argMap) 
   {
      super(workingDir, argMap);
   }

   /**
    * Write if iteration is on the specified iteration interval
    */
   @Override
   public void conditionalWrite() throws Exception
   {
      if (iterationValue.n % interval == 0)
      {
         write();
      }
   }

}
