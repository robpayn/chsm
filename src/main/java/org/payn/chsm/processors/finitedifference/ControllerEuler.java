package org.payn.chsm.processors.finitedifference;

import java.util.ArrayList;

import org.payn.chsm.processors.interfaces.InitializerSimple;
import org.payn.chsm.processors.interfaces.UpdaterSimpleAuto;
import org.payn.chsm.sorters.SorterInitialize;
import org.payn.chsm.sorters.finitedifference.SorterUpdateChange;
import org.payn.chsm.sorters.finitedifference.SorterUpdateDelta;
import org.payn.chsm.sorters.finitedifference.SorterUpdateInfo;
import org.payn.chsm.sorters.finitedifference.SorterUpdateState;
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
      SorterUpdateChange sorterChange = new SorterUpdateChange(changeUpdaters);
      SorterUpdateDelta sorterDelta = new SorterUpdateDelta(deltaUpdaters);
      SorterUpdateState sorterState = new SorterUpdateState(stateUpdaters);
      SorterUpdateInfo sorterInfo = new SorterUpdateInfo(infoUpdaters);
      
      this.sorter = sorterChange;
      changeUpdaters = sorterChange.getSortedProcessors();
      
      this.sorter = sorterDelta;
      deltaUpdaters = sorterDelta.getSortedProcessors();
      
      this.sorter = sorterState;
      stateUpdaters = sorterState.getSortedProcessors();
      
      this.sorter = sorterInfo;
      infoUpdaters = sorterInfo.getSortedProcessors();
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
      update(changeUpdaters);
      update(deltaUpdaters);
      update(stateUpdaters);
      update(infoUpdaters);
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
