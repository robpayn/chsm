package org.payn.chsm.sorters;

import java.util.ArrayList;

import org.payn.chsm.processors.Processor;
import org.payn.chsm.processors.interfaces.UpdaterSimpleAuto;

/**
 * Sorter for ordering updating of processors
 * 
 * @author robpayn
 * 
 */
public class SorterUpdate extends SorterUpdateAbstract<UpdaterSimpleAuto> {

   /**
    * Constructor
    * 
    * @param processors
    */
   public SorterUpdate(ArrayList<UpdaterSimpleAuto> processors) 
   {
      super(processors);
   }

   @Override
   public void addUpdateDependency(Processor processor, Processor neededProcessor) throws Exception 
   {
      if (!UpdaterSimpleAuto.class.isInstance(processor))
      {
         throw new Exception("Processor not compatible with UpdaterAutoSimple sorter");
      }
      if (UpdaterSimpleAuto.class.isInstance(neededProcessor))
      {
         addToGraph((UpdaterSimpleAuto)processor, (UpdaterSimpleAuto)neededProcessor);
      }
   }

}
