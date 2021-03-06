package org.payn.chsm.processors.finitedifference;

import java.util.ArrayList;

import org.payn.chsm.Holon;
import org.payn.chsm.processors.Processor;
import org.payn.chsm.processors.ProcessorDouble;
import org.payn.chsm.processors.UpdaterMemoryHelper;
import org.payn.chsm.processors.finitedifference.interfaces.UpdaterStore;
import org.payn.chsm.processors.finitedifference.interfaces.UpdaterInfo;
import org.payn.chsm.processors.interfaces.UpdaterMemory;
import org.payn.chsm.resources.time.BehaviorTime;
import org.payn.chsm.values.ValueDouble;

/**
 * A controller for a basic Eulerian single step calculation
 * 
 * @author v78h241
 *
 */
public class ControllerRungeKuttaTwo extends ControllerEuler {

   /**
    * Value for the time interval
    */
   private ValueDouble timeIntervalValue;
   
   /**
    * List of memory updaters for the processors in the store phase
    */
   private ArrayList<UpdaterMemory> storeProcessorMemoryUpdaters;
   
   /**
    * Constructor
    */
   public ControllerRungeKuttaTwo()
   {
      super();
      storeProcessorMemoryUpdaters = new ArrayList<UpdaterMemory>();
   }

   /**
    * Overrides initialization from ControllerNEOEuler to set the value for the time interval
    */
   @Override
   public void initialize() throws Exception 
   {
      timeIntervalValue = 
            (ValueDouble)((Holon)state).getState(BehaviorTime.ITERATION_INTERVAL).getValue();
      super.initialize();
   }

   /**
    * Overrides iterate from ControllerNEOEuler to perform the extra calculations for 
    * Runge Kutta (second order midpoint method)
    * @throws Exception 
    */
   @Override
   public void solve() throws Exception 
   {
      rungeKuttaTwoSolver();
   }

   /**
    * Perform a runge kutta iteration
    * 
    * @throws Exception 
    */
   protected void rungeKuttaTwoSolver() throws Exception 
   {
      double fullTimeInterval = timeIntervalValue.n;
      double halfTimeInterval = timeIntervalValue.n / 2;
      
      // Save the initial store values
      saveRootState();
      
      // Calculate trade values at beginning of time step
      update(changeUpdaters);
      update(deltaUpdaters);
      
      // Calculate store and trade values after half the time step
      timeIntervalValue.n = halfTimeInterval;
      update(storeUpdaters);
      update(infoUpdaters);
      
      update(changeUpdaters);
      update(deltaUpdaters);
      
      // Restore original store values
      restoreState();
      
      // Calculate the store values over the full time step
      // using the Runge Kutta estimates of the loads
      timeIntervalValue.n = fullTimeInterval;
      update(storeUpdaters);
      update(infoUpdaters);
   }

   /**
    * Restore the saved value of states with store updaters
    */
   protected void restoreState() 
   {
      for (UpdaterMemory updater: storeProcessorMemoryUpdaters)
      {
         updater.restoreSavedNumber();
      }
   }

   /**
    * Save the current value of the states with store updaters
    */
   protected void saveRootState() 
   {
      for (UpdaterMemory updater: storeProcessorMemoryUpdaters)
      {
         updater.saveNumber();
      }
   }
   
   @Override
   public void addProcessor(Processor processor) throws Exception
   {
      super.addProcessor(processor);
      addStoreMemoryUpdater(processor);
   }

   /**
    * Add a memory updater to a processor if it is a store updater
    * 
    * @param processor
    */
   protected void addStoreMemoryUpdater(Processor processor) 
   {
      if ((UpdaterStore.class.isInstance(processor) || UpdaterInfo.class.isInstance(processor)) 
            && UpdaterMemory.class.isInstance(processor))
      {
         storeProcessorMemoryUpdaters.add((UpdaterMemory)processor);
      }
      else if (ProcessorDoubleStore.class.isInstance(processor) || 
            ProcessorDoubleInfo.class.isInstance(processor))
      {
         storeProcessorMemoryUpdaters.add(
               new UpdaterMemoryHelper<ProcessorDouble>((ProcessorDouble)processor)
               );
      }
   }

}
