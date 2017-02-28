package org.payn.chsm.resources.time;

import org.payn.chsm.processors.ProcessorLong;
import org.payn.chsm.processors.interfaces.InitializerSimple;
import org.payn.chsm.processors.interfaces.UpdaterSimple;

/**
 * Processor for updating the current iteration
 * 
 * @author robpayn
 *
 */
public class Iteration extends ProcessorLong implements InitializerSimple, UpdaterSimple {

   @Override
   public void initialize() throws Exception 
   {
      if (value.isNoValue())
      {
         value.n = 0;
      }
   }

   /**
    * Increment the iteration counter
    */
   @Override
   public void update() 
   {
      value.n += 1;
   }

}
