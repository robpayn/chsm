package org.payn.chsm.sorters;

import java.util.ArrayList;

import org.payn.chsm.Processor;
import org.payn.chsm.finitedifference.processors.interfaces.UpdaterInfo;
import org.payn.chsm.processors.interfaces.UpdaterSimpleAuto;

/**
 * Sorter for ordering calls to trade phase updating processors
 * 
 * @author robpayn
 *
 */
public class SorterUpdateInfo extends SorterUpdate {

   /**
    * Create a new instance based on a list of updaters
    * 
    * @param updaters
    *       list of updaters
    */
   public SorterUpdateInfo(ArrayList<UpdaterSimpleAuto> updaters) 
   {
      super(updaters);
   }

   /**
    * Add a dependency if the needed processor is a trade phase updater
    */
   @Override
   public void addUpdateDependency(Processor processor, Processor neededProcessor) 
   {
      if (UpdaterInfo.class.isInstance(neededProcessor))
      {
         addToGraph((UpdaterInfo)processor, (UpdaterInfo)neededProcessor);
      }
   }

}
