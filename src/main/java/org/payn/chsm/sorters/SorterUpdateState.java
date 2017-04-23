package org.payn.chsm.sorters;

import java.util.ArrayList;

import org.payn.chsm.processors.Processor;
import org.payn.chsm.processors.finitedifference.interfaces.UpdaterBaseState;
import org.payn.chsm.processors.interfaces.UpdaterSimpleAuto;

/**
 * Sorter for ordering calls to storage phase updaters
 * 
 * @author robpayn
 *
 */
public class SorterUpdateState extends SorterUpdate {

   /**
    * Create a new sorter based on a list of updaters
    * 
    * @param updaters
    *       list of updaters
    */
   public SorterUpdateState(ArrayList<UpdaterSimpleAuto> updaters) 
   {
      super(updaters);
   }

   /**
    * Add a dependency if the needed processor is a storage phase updaters
    */
   @Override
   public void addUpdateDependency(Processor processor, Processor neededProcessor) 
   {
      if (UpdaterBaseState.class.isInstance(neededProcessor))
      {
         addToGraph((UpdaterBaseState)processor, (UpdaterBaseState)neededProcessor);
      }
   }

}
