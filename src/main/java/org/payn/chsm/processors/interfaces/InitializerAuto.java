package org.payn.chsm.processors.interfaces;

/**
 * Interface to an initializer that is automatically called by a controller
 * 
 * @author robpayn
 *
 */
public interface InitializerAuto extends Initializer {

   /**
    * Define the state variables needed to initialize
    * 
    * @throws Exception
    *       if error in finding state variables
    */
   public abstract void setInitDependencies() throws Exception;
   
}
