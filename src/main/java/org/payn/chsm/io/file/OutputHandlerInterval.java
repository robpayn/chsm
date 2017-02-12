package org.payn.chsm.io.file;


/**
 * An file system output handler where the condition is based on regular iteration intervals
 * 
 * @author robpayn
 *
 */
public abstract class OutputHandlerInterval extends OutputHandlerFileSystem {

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
