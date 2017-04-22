package org.payn.chsm.sorters;

import java.util.ArrayList;

import org.payn.chsm.Processor;
import org.payn.chsm.finitedifference.processors.interfaces.UpdaterChange;
import org.payn.chsm.processors.interfaces.UpdaterSimpleAuto;

/**
 * Sorter for ordering calls to trade phase updating processors
 * 
 * @author robpayn
 *
 */
public class SorterUpdateChange extends SorterUpdate {

   /**
    * Create a new instance based on a list of updaters
    * 
    * @param updaters
    *       list of updaters
    */
   public SorterUpdateChange(ArrayList<UpdaterSimpleAuto> updaters) 
   {
      super(updaters);
   }

   /**
    * Add a dependency if the needed processor is a trade phase updater
    */
   @Override
   public void addUpdateDependency(Processor processor, Processor neededProcessor) 
   {
      if (UpdaterChange.class.isInstance(neededProcessor))
      {
         addToGraph((UpdaterChange)processor, (UpdaterChange)neededProcessor);
      }
   }

}
