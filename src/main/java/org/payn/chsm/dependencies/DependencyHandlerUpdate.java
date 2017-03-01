package org.payn.chsm.dependencies;

import java.util.ArrayList;

import org.payn.chsm.Processor;
import org.payn.chsm.processors.interfaces.UpdaterSimpleAuto;

/**
 * Dependency handler for updating processors
 * 
 * @author robpayn
 * 
 */
public class DependencyHandlerUpdate extends DependencyHandlerUpdateAbstract<UpdaterSimpleAuto> {

   /**
    * Constructor
    * 
    * @param processors
    */
   public DependencyHandlerUpdate(ArrayList<UpdaterSimpleAuto> processors) 
   {
      super(processors);
   }

   @Override
   public void addUpdateDependency(Processor processor, Processor neededProcessor) throws Exception 
   {
      if (!UpdaterSimpleAuto.class.isInstance(processor))
      {
         throw new Exception("Processor not compatible with UpdaterAutoSimple dependency handler");
      }
      if (UpdaterSimpleAuto.class.isInstance(neededProcessor))
      {
         addToGraph((UpdaterSimpleAuto)processor, (UpdaterSimpleAuto)neededProcessor);
      }
   }

}
