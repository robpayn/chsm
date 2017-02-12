package org.payn.chsm;

/**
 * Interface for an input handler.
 * 
 * Input handlers can be registered with the controller, so they
 * can be closed cleanly at the end of an execution.
 * 
 * @author robpayn
 *
 */
public interface InputHandler {
   
   /**
    * Close the input handler
    * 
    * @throws Exception
    *       if error in closing the handler
    */
   public abstract void close() throws Exception;

}
