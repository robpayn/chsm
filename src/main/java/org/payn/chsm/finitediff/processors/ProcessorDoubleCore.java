package org.payn.chsm.finitediff.processors;

import org.payn.chsm.Holon;
import org.payn.chsm.finitediff.processors.interfaces.UpdaterCore;
import org.payn.chsm.processors.ProcessorDouble;
import org.payn.chsm.resources.time.BehaviorTime;
import org.payn.chsm.values.ValueDouble;

/**
 * A double precision floating point core processor that uses a simple one
 * step mass balance algorithm
 * 
 * @author v78h241
 *
 */
public class ProcessorDoubleCore extends ProcessorDouble implements UpdaterCore {
   
   /**
    * Time interval of the model
    */
   private ValueDouble timeInterval;

   /**
    * Net delta over a time step
    */
   private double netDelta;
   
   /**
    * Raw constructor
    */
   public ProcessorDoubleCore()
   {
      netDelta = 0.0;
   }

   /**
    * Define the states necessary to calculate the core state value
    */
   @Override
   public void setUpdateDependencies() 
   {
      Holon matrixHolon = (Holon)getController().getState();
      timeInterval = (ValueDouble)matrixHolon.getState(BehaviorTime.ITERATION_INTERVAL).getValue();
   }

   /**
    * Update the value based on the net delta
    */
   @Override
   public void update() 
   {
      value.n = value.n + netDelta * timeInterval.n;
      netDelta = 0.0;
   }

   /**
    * Increment the net delta by the provided value
    * 
    * @param value
    */
   public void incrementNetDelta(double value) 
   {
      netDelta += value;
   }

   /**
    * Decrement the net delta by the provided value
    * 
    * @param value
    */
   public void decrementNetDelta(double value) 
   {
      netDelta -= value;
   }

}
