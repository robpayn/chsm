package org.payn.chsm.processors.interfaces;

import org.payn.chsm.Processor;

/**
 * A slave processor that updates a value based on
 * calls from a master processor.
 * 
 * @author robpayn
 *
 */
public interface UpdaterSlave extends Updater {
   
   /**
    * Set the slaves dependencies on other states.
    */
   public abstract void setSlaveDependencies();

   /**
    * Get the master processor for the slave updater
    * 
    * @return
    *       master processor
    */
   public abstract Processor getMasterProcessor();

}
