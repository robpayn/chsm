package org.payn.chsm.processors.finitedifference;

import org.payn.chsm.processors.UpdaterMemoryHelper;
import org.payn.chsm.processors.finitedifference.interfaces.UpdaterCrankNicolson;

/**
 * Helper for a processor for performing Crank Nicolson updates
 * 
 * @author v78h241
 *
 */
public class UpdaterCrankNicolsonHelper extends UpdaterMemoryHelper<ProcessorDoubleDelta> 
implements UpdaterCrankNicolson {

   /**
    * Construct a new instance decorating the provided processor
    * 
    * @param proc
    *       processor to be decorated
    */
   public UpdaterCrankNicolsonHelper(ProcessorDoubleDelta proc) 
   {
      super(proc);
   }

   @Override
   public void updateCrankNicolson() throws Exception 
   {
      processor.updateDelta();
      value.n = 0.5 * (value.n + savedValue);
      processor.updateStoreProcessor();
   }

}
