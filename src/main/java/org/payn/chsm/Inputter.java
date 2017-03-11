package org.payn.chsm;

/**
 * Interface for an inputter.
 * 
 * <p>Inputters can be registered with the controller, so they
 * can be closed cleanly at the end of an execution.</p>
 * 
 * @author robpayn
 *
 */
public interface Inputter {
   
   /**
    * Close the inputter
    * 
    * @throws Exception
    *       if error in closing the inputter
    */
   public abstract void close() throws Exception;

}
