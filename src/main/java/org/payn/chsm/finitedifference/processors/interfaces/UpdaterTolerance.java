package org.payn.chsm.finitedifference.processors.interfaces;

/**
 * Interface for a tolerance updater
 * 
 * @author v78h241
 *
 */
public interface UpdaterTolerance {

   /**
    * Check the tolerance
    */
   public abstract void checkTolerance();

   /**
    * Set the last estimate for the next tolerance check
    */
   public abstract void setLastEstimate();

}
