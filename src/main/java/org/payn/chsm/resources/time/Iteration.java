package org.payn.chsm.resources.time;

import org.payn.chsm.processors.ProcessorLong;
import org.payn.chsm.processors.interfaces.UpdaterSimple;

/**
 * Processor for updating the current iteration
 * 
 * @author robpayn
 *
 */
public class Iteration extends ProcessorLong implements UpdaterSimple {

   /**
    * Increment the iteration counter
    */
   @Override
   public void update() 
   {
      value.n = value.n + 1;
   }

}
