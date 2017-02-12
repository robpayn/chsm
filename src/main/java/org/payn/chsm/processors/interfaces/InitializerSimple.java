package org.payn.chsm.processors.interfaces;

/**
 * Interface to an simple one step initializer
 * 
 * @author robpayn
 *
 */
public interface InitializerSimple extends Initializer {

   /**
    * Initialize the value
    * @throws Exception 
    */
   public void initialize() throws Exception;

}
