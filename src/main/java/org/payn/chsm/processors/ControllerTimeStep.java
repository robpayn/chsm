package org.payn.chsm.processors;

import org.payn.chsm.Holon;
import org.payn.chsm.InputHandler;
import org.payn.chsm.Reporter;
import org.payn.chsm.State;
import org.payn.chsm.processors.interfaces.InitializerSimple;
import org.payn.chsm.processors.interfaces.UpdaterSimple;
import org.payn.chsm.resources.time.BehaviorTime;
import org.payn.chsm.resources.time.Iteration;
import org.payn.chsm.resources.time.Time;
import org.payn.chsm.values.ValueLong;

/**
 * Controller that operates on a time step
 * 
 * @author robpayn
 *
 */
public abstract class ControllerTimeStep extends ControllerHolon {
   
   /**
    * Updater for the iteration count
    */
   private UpdaterSimple iterationUpdater;

   /**
    * Value of iteration counter
    */
   protected ValueLong iterationValue;

   /**
    * Updater for the time
    */
   private UpdaterSimple timeUpdater;

   /**
    * Initialize the time fields
    * 
    * @throws Exception
    *       if error in finding time state variables
    */
   public void initializeController() throws Exception
   {
      Holon holon = (Holon)state;

      // Initialize reporters
      loggerManager.statusUpdate("Initializing reporters...");
      for (Reporter reporter: getReporters())
      {
         reporter.initialize(holon);
      }
      
      // Initialize time
      State iterationState = 
            holon.getState(Iteration.class.getSimpleName());
      iterationValue = (ValueLong)iterationState.getValue();
      iterationUpdater = (UpdaterSimple)iterationState.getProcessor();
      ((InitializerSimple)iterationUpdater).initialize();
      
      State timeState = 
            holon.getState(Time.class.getSimpleName());
      timeUpdater = (UpdaterSimple)timeState.getProcessor();
      ((InitializerSimple)timeUpdater).initialize();
      
      loggerManager.statusUpdate("Sorting initialization dependencies...");
      handleInitializationDependencies();
      loggerManager.statusUpdate("Initializing processors...");
      initialize();
      
      loggerManager.statusUpdate("Outputting initialized model...");
      for (Reporter reporter: reporters)
      {
         reporter.openLocation();
      }
      for (Reporter reporter: reporters)
      {
         reporter.write();
      }
      for (Reporter reporter: reporters)
      {
         reporter.closeLocation();
      }
   }

   /**
    * Execute the controller
    * 
    * @throws Exception
    *       if error in execution
    */
   public void executeController() throws Exception
   {
      loggerManager.statusUpdate("Sorting update dependencies...");
      handleExecutionDependencies();
      
      ValueLong lastIter = (ValueLong)((Holon)state).getState(BehaviorTime.LAST_ITERATION).getValue();
      
      long startTime = System.currentTimeMillis();
      loggerManager.statusUpdate("Executing update loop...");
      executeIteration();
      
      for (Reporter reporter: reporters)
      {
         reporter.openLocation();
         reporter.conditionalWrite();
      }
      while(iterationValue.n < lastIter.n)
      {
         executeIteration();
         for (Reporter reporter: reporters)
         {
            reporter.conditionalWrite();
         }
      }
      long time = System.currentTimeMillis() - startTime;
      loggerManager.statusUpdate(String.format(
            "Execution loop time = %s...", 
            BehaviorTime.parseTimeInMillis(time)
            ));
      loggerManager.statusUpdate("");
      loggerManager.statusUpdate("Closing input handlers...");
      for (InputHandler inputHandler: inputHandlers)
      {
         inputHandler.close();
      }
      loggerManager.statusUpdate("Closing reporters...");
      for (Reporter reporter: reporters)
      {
         reporter.closeLocation();
      }
      
      loggerManager.statusUpdate("Execution complete...");
   }

   /**
    * Update time and perform a single time iteration of the controlled processors
    * @throws Exception 
    */
   public void executeIteration() throws Exception
   {
      iterationUpdater.update();
      timeUpdater.update();
      iterate();
   }

   /**
    * Get the appropriate class loader for loading the time behavior
    * 
    * @return
    *       class loader
    */
   public abstract ClassLoader getClassLoader();

   /**
    * Set up the initialization dependencies before initializing the processors
    * 
    * @throws Exception 
    *       if error in setting up initialization dependencies
    */
   public abstract void handleInitializationDependencies() throws Exception;

   /**
    * Initialize the controller
    * 
    * @throws Exception 
    *       if error in initialization
    */
   public abstract void initialize() throws Exception;

   /**
    * Set up the execution dependencies for the execution loop
    * 
    * @throws Exception 
    *       if error in setting up dependencies
    */
   public abstract void handleExecutionDependencies() throws Exception;

   /**
    * Perform a single time step iteration
    * @throws Exception 
    */
   public abstract void iterate() throws Exception;

}
