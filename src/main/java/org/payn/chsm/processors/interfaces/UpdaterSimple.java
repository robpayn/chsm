package org.payn.chsm.processors.interfaces;

/**
 * Interface to an simple one step updater
 * 
 * @author robpayn
 *
 */
public interface UpdaterSimple extends Updater {

   /**
    * Update the value
    * @throws Exception 
    */
   public abstract void update() throws Exception;

}
