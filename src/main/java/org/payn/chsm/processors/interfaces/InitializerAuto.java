package org.payn.chsm.processors.interfaces;

/**
 * Interface to an initializer that is automatically called by a controller
 * 
 * @author robpayn
 *
 */
public interface InitializerAuto extends Initializer {

   /**
    * Define the states needed to initialize
    * 
    * @throws Exception
    *       if error in finding states
    */
   public abstract void setInitDependencies() throws Exception;
   
}
