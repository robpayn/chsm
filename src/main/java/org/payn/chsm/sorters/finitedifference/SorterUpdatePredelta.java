package org.payn.chsm.sorters.finitedifference;

import java.util.ArrayList;

import org.payn.chsm.processors.Processor;
import org.payn.chsm.processors.finitedifference.interfaces.UpdaterPredelta;
import org.payn.chsm.processors.interfaces.UpdaterSimpleAuto;
import org.payn.chsm.sorters.SorterUpdate;

/**
 * Sorter for the "predelta" phase of calculation.
 * 
 * @author robpayn
 *
 */
public class SorterUpdatePredelta extends SorterUpdate {

   /**
    * Create a new instance based on a list of updaters
    * 
    * @param updaters
    *       list of updaters
    */
   public SorterUpdatePredelta(ArrayList<UpdaterSimpleAuto> updaters) 
   {
      super(updaters);
   }

   /**
    * Add a dependency if the needed processor is a trade phase updater
    */
   @Override
   public void addUpdateDependency(Processor processor, Processor neededProcessor) 
   {
      if (UpdaterPredelta.class.isInstance(neededProcessor))
      {
         addToGraph((UpdaterPredelta)processor, (UpdaterPredelta)neededProcessor);
      }
   }

}
