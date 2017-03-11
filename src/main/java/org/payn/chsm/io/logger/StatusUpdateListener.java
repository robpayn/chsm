package org.payn.chsm.io.logger;

import org.payn.chsm.Logger;

/**
 * Interface for a status update listener
 * 
 * @author robpayn
 *
 */
public interface StatusUpdateListener extends Logger {
   
   /**
    * Method to be called for a status update event
    * 
    * @param message
    */
   public abstract void statusUpdate(String message);

}
