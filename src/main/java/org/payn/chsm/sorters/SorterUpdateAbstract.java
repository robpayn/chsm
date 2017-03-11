package org.payn.chsm.sorters;

import java.util.ArrayList;

import org.payn.chsm.SorterAbstract;
import org.payn.chsm.Processor;
import org.payn.chsm.processors.interfaces.UpdaterAuto;
import org.payn.chsm.processors.interfaces.UpdaterSlave;

/**
 * Abstract sorter for ordering updating of processors
 * 
 * @author robpayn
 * @param <PT> 
 *
 */
public abstract class SorterUpdateAbstract<PT extends UpdaterAuto> extends SorterAbstract<PT> {

   /**
    * Constructor
    * 
    * @param processors
    */
   public SorterUpdateAbstract(ArrayList<PT> processors) 
   {
      super(processors);
   }

   /**
    * Add dependencies for all processors
    * 
    * @throws Exception
    *       if cycle in dependencies
    */
   @Override
   public void addDependencies() throws Exception 
   {
      for (UpdaterAuto updater: processors)
      {
         updater.setUpdateDependencies();
      }
   }

   /**
    * Navigate to the controlling non-slave updater and add the dependency
    * @throws Exception 
    */
   @Override
   public final void addDependency(Processor processor, Processor neededProcessor) throws Exception 
   {
      while (UpdaterSlave.class.isInstance(neededProcessor))
      {
         neededProcessor = ((UpdaterSlave)neededProcessor).getMasterProcessor();
      }
      addUpdateDependency(processor, neededProcessor);
   }

   /**
    * Add an update dependency
    * 
    * @param processor
    *       dependent processor
    * @param neededProcessor
    *       needed processor
    * @throws Exception 
    */
   public abstract void addUpdateDependency(Processor processor, Processor neededProcessor) throws Exception;
   
}
