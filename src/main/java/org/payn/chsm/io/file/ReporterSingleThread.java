package org.payn.chsm.io.file;

/**
 * Outputter that runs IO operations in a single separate thread
 * 
 * @author robpayn
 *
 */
public abstract class ReporterSingleThread extends ReporterInterval implements Runnable {
   
   /**
    * Thread where IO operations are running
    */
   protected Thread thread;
   
   /**
    * Write the output
    * 
    * @throws Exception 
    *       if error in IO
    */
   @Override
   public void write() throws Exception
   {
      if (thread != null && thread.isAlive())
      {
         thread.join();
      }
      bufferOutput();
      thread = new Thread(this);
      thread.start();
   }
   
   /**
    * Runnable method for operation in separate thread
    */
   @Override
   public void run()
   {
      try 
      {
         backgroundWrite();
      } 
      catch (Exception e) 
      {
         e.printStackTrace();
      }
   }
   
   /**
    * Close the output location when thread is finished
    */
   @Override
   public void closeLocation() throws Exception
   {
      if (thread != null && thread.isAlive())
      {
         thread.join();
      }
      closeWhenFinished();
   }
   
   /**
    * Buffer the output information to memory
    * 
    * @throws Exception
    *       if error in buffering
    */
   protected abstract void bufferOutput() throws Exception;

   /**
    * Write the output
    * 
    * @throws Exception
    *       if error in writing
    */
   protected abstract void backgroundWrite() throws Exception;

   /**
    * Close the location 
    * 
    * @throws Exception
    *       if error in closing output location
    */
   protected abstract void closeWhenFinished() throws Exception;

}
