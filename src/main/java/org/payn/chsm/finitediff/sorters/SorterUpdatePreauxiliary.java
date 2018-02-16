package org.payn.chsm.finitediff.sorters;

import java.util.ArrayList;

import org.payn.chsm.finitediff.processors.interfaces.UpdaterPreauxiliary;
import org.payn.chsm.processors.Processor;
import org.payn.chsm.processors.interfaces.UpdaterSimpleAuto;
import org.payn.chsm.sorters.SorterUpdate;

/**
 * Sorter for the preauxiliary phase of calculation.
 * 
 * @author robpayn
 *
 */
public class SorterUpdatePreauxiliary extends SorterUpdate {

   /**
    * Create a new instance based on a list of updaters
    * 
    * @param updaters
    *       list of updaters
    */
   public SorterUpdatePreauxiliary(ArrayList<UpdaterSimpleAuto> updaters) 
   {
      super(updaters);
   }

   /**
    * Add a dependency if the needed processor is a preauxiliary phase updater
    */
   @Override
   public void addUpdateDependency(Processor processor, Processor neededProcessor) 
   {
      if (UpdaterPreauxiliary.class.isInstance(neededProcessor))
      {
         addToGraph((UpdaterPreauxiliary)processor, (UpdaterPreauxiliary)neededProcessor);
      }
   }

}
