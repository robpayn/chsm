package org.payn.chsm.processors.interfaces;

/**
 * Interface to an updater that is automatically called by a controller
 * 
 * @author robpayn
 *
 */
public interface UpdaterAuto extends Updater {

   /**
    * Define the state variables needed to update
    * 
    * @throws Exception
    *       if error in finding state variables
    */
   public abstract void setUpdateDependencies() throws Exception;
   
}
