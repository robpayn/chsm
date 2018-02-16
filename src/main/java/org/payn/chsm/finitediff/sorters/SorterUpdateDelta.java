package org.payn.chsm.finitediff.sorters;

import java.util.ArrayList;

import org.payn.chsm.finitediff.processors.interfaces.UpdaterDelta;
import org.payn.chsm.processors.Processor;
import org.payn.chsm.processors.interfaces.UpdaterSimpleAuto;
import org.payn.chsm.sorters.SorterUpdate;

/**
 * Sorter for ordering calls to delta phase updaters
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
    * Add a dependency if the needed processor is a delta phase updater
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
