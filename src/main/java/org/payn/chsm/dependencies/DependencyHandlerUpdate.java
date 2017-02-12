package org.payn.chsm.dependencies;

import java.util.ArrayList;

import org.payn.chsm.Processor;
import org.payn.chsm.processors.interfaces.UpdaterAutoSimple;

/**
 * Dependency handler for updating processors
 * 
 * @author robpayn
 * 
 */
public class DependencyHandlerUpdate extends DependencyHandlerUpdateAbstract<UpdaterAutoSimple> {

   /**
    * Constructor
    * 
    * @param processors
    */
   public DependencyHandlerUpdate(ArrayList<UpdaterAutoSimple> processors) 
   {
      super(processors);
   }

   @Override
   public void addUpdateDependency(Processor processor, Processor neededProcessor) throws Exception 
   {
      if (!UpdaterAutoSimple.class.isInstance(processor))
      {
         throw new Exception("Processor not compatible with UpdaterAutoSimple dependency handler");
      }
      if (UpdaterAutoSimple.class.isInstance(neededProcessor))
      {
         addToGraph((UpdaterAutoSimple)processor, (UpdaterAutoSimple)neededProcessor);
      }
   }

}
