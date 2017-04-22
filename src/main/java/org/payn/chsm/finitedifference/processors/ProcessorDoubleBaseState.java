package org.payn.chsm.finitedifference.processors;

import org.payn.chsm.Holon;
import org.payn.chsm.finitedifference.processors.interfaces.UpdaterBaseState;
import org.payn.chsm.processors.ProcessorDouble;
import org.payn.chsm.resources.time.BehaviorTime;
import org.payn.chsm.values.ValueDouble;

/**
 * A double precision floating point storage processor that uses a simple one
 * step mass balance algorithm
 * 
 * @author v78h241
 *
 */
public class ProcessorDoubleBaseState extends ProcessorDouble implements UpdaterBaseState {
   
   /**
    * Time interval of the model
    */
   private ValueDouble timeInterval;

   /**
    * Net load over a time step
    */
   private double netChange;
   
   /**
    * Raw constructor
    */
   public ProcessorDoubleBaseState()
   {
      netChange = 0.0;
   }

   /**
    * Define the state variables necessary to calculate the storage
    */
   @Override
   public void setUpdateDependencies() 
   {
      Holon matrixHolon = (Holon)getController().getState();
      timeInterval = (ValueDouble)matrixHolon.getState(BehaviorTime.ITERATION_INTERVAL).getValue();
   }

   /**
    * Update the value by calculating the net load and adding
    */
   @Override
   public void update() 
   {
      value.n = value.n + netChange * timeInterval.n;
      netChange = 0.0;
   }

   /**
    * Increment the net load by the provided value
    * 
    * @param value
    */
   public void incrementNetChange(double value) 
   {
      netChange += value;
   }

   /**
    * Decrement the net load by the provided value
    * 
    * @param value
    */
   public void decrementNetChange(double value) 
   {
      netChange -= value;
   }

}
