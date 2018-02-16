package org.payn.chsm.finitediff.sorters;

import java.util.ArrayList;

import org.payn.chsm.finitediff.processors.interfaces.UpdaterPostauxiliary;
import org.payn.chsm.processors.Processor;
import org.payn.chsm.processors.interfaces.UpdaterSimpleAuto;
import org.payn.chsm.sorters.SorterUpdate;

/**
 * Sorter for ordering calls to postauxiliary phase updating processors
 * 
 * @author robpayn
 *
 */
public class SorterUpdatePostauxiliary extends SorterUpdate {

   /**
    * Create a new instance based on a list of updaters
    * 
    * @param updaters
    *       list of updaters
    */
   public SorterUpdatePostauxiliary(ArrayList<UpdaterSimpleAuto> updaters) 
   {
      super(updaters);
   }

   /**
    * Add a dependency if the needed processor is a postauxiliary phase updater
    */
   @Override
   public void addUpdateDependency(Processor processor, Processor neededProcessor) 
   {
      if (UpdaterPostauxiliary.class.isInstance(neededProcessor))
      {
         addToGraph((UpdaterPostauxiliary)processor, (UpdaterPostauxiliary)neededProcessor);
      }
   }

}
