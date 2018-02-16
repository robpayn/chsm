package org.payn.chsm.finitediff.processors;

import org.payn.chsm.State;
import org.payn.chsm.finitediff.HolonFiniteDiff;
import org.payn.chsm.finitediff.processors.interfaces.UpdaterDelta;
import org.payn.chsm.processors.ProcessorDouble;

/**
 * A processor for a double precision floating point delta value
 * 
 * @author robpayn
 *
 */
public abstract class ProcessorDoubleDelta extends ProcessorDouble 
implements UpdaterDelta {
   
   /**
    * The core processor to increment with this delta
    */
   protected ProcessorDoubleCore coreProcessor;

   @Override
   public void setUpdateDependencies() throws Exception
   {
      setUpdateDependenciesDelta();
      State core = ((HolonFiniteDiff)state.getParentHolon()).getCore(
            state.getBehavior().getResource()
            );
      coreProcessor = (ProcessorDoubleCore)core.getProcessor();
   }
   
   @Override
   public void update() throws Exception
   {
      updateDelta();
      updateCoreProcessor();
   }

   /**
    * Update the core processor net delta
    * 
    * @throws Exception
    */
   @Override
   public void updateCoreProcessor() throws Exception 
   {
      coreProcessor.incrementNetDelta(value.n);
   }

}
