package org.payn.chsm.finitediff.sorters;

import java.util.ArrayList;

import org.payn.chsm.finitediff.processors.interfaces.UpdaterCore;
import org.payn.chsm.processors.Processor;
import org.payn.chsm.processors.interfaces.UpdaterSimpleAuto;
import org.payn.chsm.sorters.SorterUpdate;

/**
 * Sorter for ordering calls to core phase updaters
 * 
 * @author robpayn
 *
 */
public class SorterUpdateCore extends SorterUpdate {

   /**
    * Create a new sorter based on a list of updaters
    * 
    * @param updaters
    *       list of updaters
    */
   public SorterUpdateCore(ArrayList<UpdaterSimpleAuto> updaters) 
   {
      super(updaters);
   }

   /**
    * Add a dependency if the needed processor is a core phase updater
    */
   @Override
   public void addUpdateDependency(Processor processor, Processor neededProcessor) 
   {
      if (UpdaterCore.class.isInstance(neededProcessor))
      {
         addToGraph((UpdaterCore)processor, (UpdaterCore)neededProcessor);
      }
   }

}
