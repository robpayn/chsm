package org.payn.chsm.processors.finitedifference.interfaces;

import org.payn.chsm.processors.interfaces.UpdaterSimpleAuto;

/**
 * Updater for a load.  Used to distinguish loads from other trade processors.
 * 
 * @author v78h241
 *
 */
public interface UpdaterDelta extends UpdaterSimpleAuto {
   
   /**
    * Set the update dependencies for the load
    * 
    * @throws Exception 
    */
   void setUpdateDependenciesDelta() throws Exception;

   /**
    * Update the load value
    * 
    * @throws Exception
    */
   void updateDelta() throws Exception;

   /**
    * Update the storage processor net load
    * 
    * @throws Exception
    */
   void updateStoreProcessor() throws Exception;

}
