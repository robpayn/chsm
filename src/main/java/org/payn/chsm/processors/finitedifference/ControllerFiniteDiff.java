package org.payn.chsm.processors.finitedifference;

import java.util.ArrayList;

import org.payn.chsm.processors.ControllerTimeStep;
import org.payn.chsm.processors.Processor;
import org.payn.chsm.processors.finitedifference.interfaces.UpdaterStore;
import org.payn.chsm.processors.finitedifference.interfaces.UpdaterPredelta;
import org.payn.chsm.processors.finitedifference.interfaces.UpdaterDelta;
import org.payn.chsm.processors.finitedifference.interfaces.UpdaterPoststore;
import org.payn.chsm.processors.interfaces.InitializerSimpleAuto;
import org.payn.chsm.processors.interfaces.UpdaterSimpleAuto;

/**
 * Controller for a NEO-CH simulation
 * 
 * @author robpayn
 *
 */
public abstract class ControllerFiniteDiff extends ControllerTimeStep {

   /**
    * List of initializing processors
    */
   protected ArrayList<InitializerSimpleAuto> initializers;
   
   /**
    * Getter
    * 
    * @return
    *       unsorted list of initializing processors
    */
   public ArrayList<InitializerSimpleAuto> getInitializers() 
   {
      return initializers;
   }

   /**
    * List of updaters for the trade phase
    */
   protected ArrayList<UpdaterSimpleAuto> predeltaUpdaters;
   
   /**
    * Getter
    * 
    * @return
    *       list of trade updaters
    */
   public ArrayList<UpdaterSimpleAuto> getPredeltaUpdaters() 
   {
      return predeltaUpdaters;
   }

   /**
    * List of updaters for the load phase
    */
   protected ArrayList<UpdaterSimpleAuto> deltaUpdaters;
   
   /**
    * Getter
    * 
    * @return
    *       list of load updaters
    */
   public ArrayList<UpdaterSimpleAuto> getDeltaUpdaters() 
   {
      return deltaUpdaters;
   }

   /**
    * List of updaters for the storage phase
    */
   protected ArrayList<UpdaterSimpleAuto> storeUpdaters;

   /**
    * Getter
    * 
    * @return
    *       list of storage updaters
    */
   public ArrayList<UpdaterSimpleAuto> getStoreUpdaters() 
   {
      return storeUpdaters;
   }

   /**
    * List of updaters for the update phase
    */
   protected ArrayList<UpdaterSimpleAuto> poststoreUpdaters;
   
   /**
    * Getter
    * 
    * @return
    *       list of storage updaters
    */
   public ArrayList<UpdaterSimpleAuto> getPoststoreUpdaters() 
   {
      return poststoreUpdaters;
   }

   /**
    * Constructor
    */
   public ControllerFiniteDiff()
   {
      initializers = new ArrayList<InitializerSimpleAuto>();
      predeltaUpdaters = new ArrayList<UpdaterSimpleAuto>();
      deltaUpdaters = new ArrayList<UpdaterSimpleAuto>();
      storeUpdaters = new ArrayList<UpdaterSimpleAuto>();
      poststoreUpdaters = new ArrayList<UpdaterSimpleAuto>();
   }
   
   /**
    * Get the class loader
    */
   @Override 
   public ClassLoader getClassLoader()
   {
      return classLoader;
   }

   /**
    * Add a processor to the controller
    */
   @Override
   public void addProcessor(Processor processor) throws Exception 
   {
      if (InitializerSimpleAuto.class.isInstance(processor))
      {
         initializers.add((InitializerSimpleAuto)processor);
      }
      
      if (UpdaterPredelta.class.isInstance(processor))
      {
         predeltaUpdaters.add((UpdaterPredelta)processor);
      }
      else if (UpdaterDelta.class.isInstance(processor))
      {
         deltaUpdaters.add((UpdaterDelta)processor);
      }
      else if (UpdaterStore.class.isInstance(processor))
      {
         storeUpdaters.add((UpdaterStore)processor);
      }
      else if (UpdaterPoststore.class.isInstance(processor))
      {
         poststoreUpdaters.add((UpdaterSimpleAuto)processor);
      }
   }
   
}
