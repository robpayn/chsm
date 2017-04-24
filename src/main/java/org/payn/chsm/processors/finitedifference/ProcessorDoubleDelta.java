package org.payn.chsm.processors.finitedifference;

import org.payn.chsm.HolonFiniteDiff;
import org.payn.chsm.State;
import org.payn.chsm.processors.ProcessorDouble;
import org.payn.chsm.processors.finitedifference.interfaces.UpdaterDelta;

/**
 * A processor for a load
 * 
 * @author robpayn
 *
 */
public abstract class ProcessorDoubleDelta extends ProcessorDouble 
implements UpdaterDelta {
   
   /**
    * The storage processor to increment with this load
    */
   protected ProcessorDoubleStore storeProcessor;

   @Override
   public void setUpdateDependencies() throws Exception
   {
      setUpdateDependenciesDelta();
      State storage = ((HolonFiniteDiff)state.getParentHolon()).getStore(
            state.getBehavior().getResource()
            );
      storeProcessor = (ProcessorDoubleStore)storage.getProcessor();
   }
   
   @Override
   public void update() throws Exception
   {
      updateDelta();
      updateStoreProcessor();
   }

   /**
    * Update the storage processor net load
    * 
    * @throws Exception
    */
   @Override
   public void updateStoreProcessor() throws Exception 
   {
      storeProcessor.incrementNetChange(value.n);
   }

}
