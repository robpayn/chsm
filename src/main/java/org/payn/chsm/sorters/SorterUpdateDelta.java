package org.payn.chsm.sorters;

import java.util.ArrayList;

import org.payn.chsm.Processor;
import org.payn.chsm.finitedifference.processors.interfaces.UpdaterDelta;
import org.payn.chsm.processors.interfaces.UpdaterSimpleAuto;

/**
 * Sorter for ordering calls to storage phase updaters
 * 
 * @author robpayn
 *
 */
public class SorterUpdateDelta extends SorterUpdate {

   /**
    * Create a new sorter based on a list of updaters
    * 
    * @param updaters
    *       list of updaters
    */
   public SorterUpdateDelta(ArrayList<UpdaterSimpleAuto> updaters) 
   {
      super(updaters);
   }

   /**
    * Add a dependency if the needed processor is a storage phase updaters
    */
   @Override
   public void addUpdateDependency(Processor processor, Processor neededProcessor) 
   {
      if (UpdaterDelta.class.isInstance(neededProcessor))
      {
         addToGraph((UpdaterDelta)processor, (UpdaterDelta)neededProcessor);
      }
   }

}
