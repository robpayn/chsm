package org.payn.chsm.sorters.finitedifference;

import java.util.ArrayList;

import org.payn.chsm.processors.Processor;
import org.payn.chsm.processors.finitedifference.interfaces.UpdaterStore;
import org.payn.chsm.processors.interfaces.UpdaterSimpleAuto;
import org.payn.chsm.sorters.SorterUpdate;

/**
 * Sorter for ordering calls to storage phase updaters
 * 
 * @author robpayn
 *
 */
public class SorterUpdateStore extends SorterUpdate {

   /**
    * Create a new sorter based on a list of updaters
    * 
    * @param updaters
    *       list of updaters
    */
   public SorterUpdateStore(ArrayList<UpdaterSimpleAuto> updaters) 
   {
      super(updaters);
   }

   /**
    * Add a dependency if the needed processor is a storage phase updaters
    */
   @Override
   public void addUpdateDependency(Processor processor, Processor neededProcessor) 
   {
      if (UpdaterStore.class.isInstance(neededProcessor))
      {
         addToGraph((UpdaterStore)processor, (UpdaterStore)neededProcessor);
      }
   }

}
