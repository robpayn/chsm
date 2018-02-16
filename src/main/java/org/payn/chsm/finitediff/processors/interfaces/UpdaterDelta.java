package org.payn.chsm.finitediff.processors.interfaces;

import org.payn.chsm.processors.interfaces.UpdaterSimpleAuto;

/**
 * Updater for a delta state.  
 * Used to distinguish delta processors from other processors.
 * 
 * @author v78h241
 *
 */
public interface UpdaterDelta extends UpdaterSimpleAuto {
   
   /**
    * Set the update dependencies for the delta processor
    * 
    * @throws Exception 
    */
   void setUpdateDependenciesDelta() throws Exception;

   /**
    * Update the delta value
    * 
    * @throws Exception
    */
   void updateDelta() throws Exception;

   /**
    * Update the core processor net delta
    * 
    * @throws Exception
    */
   void updateCoreProcessor() throws Exception;

}
