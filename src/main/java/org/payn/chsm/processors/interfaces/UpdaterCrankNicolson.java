package org.payn.chsm.processors.interfaces;

/**
 * Updater for a Crank Nicolson estimate
 * 
 * @author v78h241
 *
 */
public interface UpdaterCrankNicolson extends UpdaterMemory {

   /**
    * Update the value based on a Crank Nicolson estimate
    * 
    * @throws Exception
    */
   public abstract void updateCrankNicolson() throws Exception;

}
