package org.payn.chsm.resources.time;

import org.payn.chsm.processors.ProcessorDouble;
import org.payn.chsm.processors.interfaces.UpdaterSimple;
import org.payn.chsm.values.ValueDouble;

/**
 * Updater processor for time
 * 
 * @author robpayn
 *
 */
public class Time extends ProcessorDouble implements UpdaterSimple {

   /**
    * Time interval value
    */
   private ValueDouble interval;

   /**
    * Define the state variables necessary to calculate time
    */
   public void setDependencies() 
   {  
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
