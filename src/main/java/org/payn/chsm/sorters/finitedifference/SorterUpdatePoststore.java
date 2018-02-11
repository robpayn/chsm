package org.payn.chsm.sorters.finitedifference;

import java.util.ArrayList;

import org.payn.chsm.processors.Processor;
import org.payn.chsm.processors.finitedifference.interfaces.UpdaterPoststore;
import org.payn.chsm.processors.interfaces.UpdaterSimpleAuto;
import org.payn.chsm.sorters.SorterUpdate;

/**
 * Sorter for ordering calls to trade phase updating processors
 * 
 * @author robpayn
 *
 */
public class SorterUpdatePoststore extends SorterUpdate {

   /**
    * Create a new instance based on a list of updaters
    * 
    * @param updaters
    *       list of updaters
    */
   public SorterUpdatePoststore(ArrayList<UpdaterSimpleAuto> updaters) 
   {
      super(updaters);
   }

   /**
    * Add a dependency if the needed processor is a trade phase updater
    */
   @Override
   public void addUpdateDependency(Processor processor, Processor neededProcessor) 
   {
      if (UpdaterPoststore.class.isInstance(neededProcessor))
      {
         addToGraph((UpdaterPoststore)processor, (UpdaterPoststore)neededProcessor);
      }
   }

}
