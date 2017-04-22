package org.payn.chsm.finitedifference.processors;

import org.payn.chsm.State;
import org.payn.chsm.finitedifference.Holon;
import org.payn.chsm.finitedifference.processors.interfaces.UpdaterDelta;
import org.payn.chsm.processors.ProcessorDouble;

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
   protected ProcessorDoubleBaseState rootStateProcessor;

   @Override
   public void setUpdateDependencies() throws Exception
   {
      setUpdateDependenciesDelta();
      State storage = ((Holon)state.getParentHolon()).getBaseState(
            state.getBehavior().getResource()
            );
      rootStateProcessor = (ProcessorDoubleBaseState)storage.getProcessor();
   }
   
   @Override
   public void update() throws Exception
   {
      updateDelta();
      updateRootStateProcessor();
   }

   /**
    * Update the storage processor net load
    * 
    * @throws Exception
    */
   @Override
   public void updateRootStateProcessor() throws Exception 
   {
      rootStateProcessor.incrementNetChange(value.n);
   }

}
