package org.payn.chsm.finitediff.processors;

import java.util.ArrayList;

import org.payn.chsm.finitediff.processors.interfaces.UpdaterCrankNicolson;
import org.payn.chsm.finitediff.processors.interfaces.UpdaterDelta;
import org.payn.chsm.processors.Processor;

/**
 * NEO controller that used a Crank Nicolson solver based on fixed point iteration
 * 
 * @author robpayn
 *
 */
public class ControllerCrankNicolson extends ControllerBackEuler {

   /**
    * List of trade processors to be updated by the crank nicolson updaters
    */
   protected ArrayList<UpdaterCrankNicolson> deltaProcessorCrankNicolsonUpdaters;
   
   /**
    * Constructor
    */
   public ControllerCrankNicolson()
   {
      super();
      deltaProcessorCrankNicolsonUpdaters = new ArrayList<UpdaterCrankNicolson>();
   }
   
   @Override
   public void solve() throws Exception 
   {
      crankNicolsonSolver();
   }

   @Override
   protected void setIterationConfig() 
   {
      defaultTolerance = 1e-7;
      maxIterations = 80;
      iterationTotal = 0;
      totalSteps = 0;
      countMaxIterationsReached = 0;
   }

   /**
    * Perform a Crank Nicolson iteration
    * 
    * @throws Exception
    */
   private void crankNicolsonSolver() throws Exception 
   {
      // save the original state
      saveRootState();
      // use the original state as the first state estimate
      setLastStateEstimates();
      
      // update the deltas
      update(preauxiliaryUpdaters);
      update(deltaUpdaters);
      
      // save the current deltas at the beginning of the time step
      saveDeltas();
      
      // get a first estimate of the state at the end of the time step (forward Euler)
      update(coreUpdaters);
      update(postauxiliaryUpdaters);
      
      // check for convergence (only converges if new state does not change)
      converged = true;
      checkTolerances();
      
      if (!converged)
      {
         // Fixed point iteration until converged or until maximum iterations are met
         for (int iterCount = 2; iterCount <= maxIterations; iterCount++)
         {
            converged = true;
            
            // estimate Crank Nicolson deltas based on new state estimate
            update(preauxiliaryUpdaters);
            updateDeltasCrankNicolson();
            
            // restore initial state
            restoreState();
            
            // get new state estimate
            update(coreUpdaters);
            update(postauxiliaryUpdaters);
            
            // check if converged on a consistent estimate of state
            checkTolerances();
            if (converged || iterCount == maxIterations)
            {
               iterationTotal += iterCount;
               totalSteps += 1;
               if (iterCount == maxIterations)
               {
                  countMaxIterationsReached += 1;
               }
               break;
            }
         }
      }
      else
      {
         iterationTotal += 1;
         totalSteps += 1;
      }
      averageIterations = (double)iterationTotal / (double)totalSteps;
   }

   /**
    * Calculate the Crank Nicolson estimate of all the trade values
    * 
    * @throws Exception
    */
   protected void updateDeltasCrankNicolson() throws Exception 
   {
      for (UpdaterCrankNicolson updater: deltaProcessorCrankNicolsonUpdaters)
      {
         updater.updateCrankNicolson();
      }      
   }

   /**
    * Save all the current trade values to memory
    */
   protected void saveDeltas() 
   {
      for (UpdaterCrankNicolson updater: deltaProcessorCrankNicolsonUpdaters)
      {
         updater.saveNumber();
      }      
   }
   
   @Override
   public void addProcessor(Processor proc) throws Exception
   {
      super.addProcessor(proc);
      addDeltaProcessorCrankNicolson(proc);
   }

   /**
    * Add to the list of crank nicolson trade processor if the appropriate type
    * 
    * @param proc
    */
   protected void addDeltaProcessorCrankNicolson(Processor proc) 
   {
      if (UpdaterDelta.class.isInstance(proc) && UpdaterCrankNicolson.class.isInstance(proc))
      {
         deltaProcessorCrankNicolsonUpdaters.add((UpdaterCrankNicolson)proc);
      }
      else if (ProcessorDoubleDelta.class.isInstance(proc))
      {
         deltaProcessorCrankNicolsonUpdaters.add(new UpdaterCrankNicolsonHelper((ProcessorDoubleDelta)proc));
      }
   }
   
}
