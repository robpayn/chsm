package org.payn.chsm.resources.time;

import org.payn.chsm.processors.ProcessorDouble;
import org.payn.chsm.processors.interfaces.InitializerSimple;
import org.payn.chsm.processors.interfaces.UpdaterSimple;
import org.payn.chsm.values.ValueDouble;

/**
 * Updater processor for time
 * 
 * @author robpayn
 *
 */
public class Time extends ProcessorDouble implements InitializerSimple, UpdaterSimple {

   /**
    * Time interval value
    */
   private ValueDouble interval;

   @Override
   public void initialize() throws Exception 
   {
      if (value.isNoValue())
      {
         value.n = 0;
      }
      interval = (ValueDouble)state.getParentHolon().getState(BehaviorTime.ITERATION_INTERVAL).getValue();
   }

   /**
    * Update the value of time
    */
   @Override
   public void update() 
   {
      value.n = value.n + interval.n;
   }

}
