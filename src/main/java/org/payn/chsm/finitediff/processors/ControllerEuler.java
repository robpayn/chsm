package org.payn.chsm.finitediff.processors;

import java.util.ArrayList;

import org.payn.chsm.finitediff.sorters.SorterUpdateCore;
import org.payn.chsm.finitediff.sorters.SorterUpdateDelta;
import org.payn.chsm.finitediff.sorters.SorterUpdatePostauxiliary;
import org.payn.chsm.finitediff.sorters.SorterUpdatePreauxiliary;
import org.payn.chsm.processors.interfaces.InitializerSimple;
import org.payn.chsm.processors.interfaces.UpdaterSimpleAuto;
import org.payn.chsm.sorters.SorterInitialize;
import org.payn.chsm.processors.interfaces.UpdaterSimple;

/**
 * A controller for a basic Eulerian single step calculation
 * 
 * @author v78h241
 *
 */
public class ControllerEuler extends ControllerFiniteDiff {

   /**
    * Set up dependencies for simple initializers
    */
   @Override
   public void handleInitializationDependencies() throws Exception 
   {
      SorterInitialize sorterInitialize = new SorterInitialize(initializers);
      this.sorter = sorterInitialize;
      initializers = sorterInitialize.getSortedProcessors();
   }

   /**
    * Initialize the controller
    */
   @Override
   public void initialize() throws Exception 
   {
      for (InitializerSimple initializer: initializers)
      {
         initializer.initialize();
      }
   }

   /**
    * Set up dependencies for simple updaters
    */
   @Override
   public void handleExecutionDependencies() throws Exception 
   {
      // Create sorters for each phase
      SorterUpdatePreauxiliary sorterPreauxiliary = new SorterUpdatePreauxiliary(preauxiliaryUpdaters);
      SorterUpdateDelta sorterDelta = new SorterUpdateDelta(deltaUpdaters);
      SorterUpdateCore sorterCore = new SorterUpdateCore(coreUpdaters);
      SorterUpdatePostauxiliary sorterPostauxiliary = new SorterUpdatePostauxiliary(postauxiliaryUpdaters);
      
      // For each phase, set the sorter to the appropriate phase.
      // Then collect the dependency information and sort for that phase.
      
      this.sorter = sorterPreauxiliary;
      preauxiliaryUpdaters = sorterPreauxiliary.getSortedProcessors();
      
      this.sorter = sorterDelta;
      deltaUpdaters = sorterDelta.getSortedProcessors();
      
      this.sorter = sorterCore;
      coreUpdaters = sorterCore.getSortedProcessors();
      
      this.sorter = sorterPostauxiliary;
      postauxiliaryUpdaters = sorterPostauxiliary.getSortedProcessors();
   }

   /**
    * Perform a single iteration
    * @throws Exception 
    */
   @Override
   public void iterate() throws Exception 
   {
      solve();
   }

   /**
    * Solve the equations for the current iteration
    * 
    * @throws Exception
    */
   protected void solve() throws Exception 
   {
      update(preauxiliaryUpdaters);
      update(deltaUpdaters);
      update(coreUpdaters);
      update(postauxiliaryUpdaters);
   }

   /**
    * Update the provided list of updaters
    * 
    * @param updaters
    *       list of updaters to update
    * @throws Exception
    *       if error in update
    */
   protected void update(ArrayList<UpdaterSimpleAuto> updaters) throws Exception 
   {
      for (UpdaterSimple updater: updaters)
      {
         updater.update();
      }
   }

}
