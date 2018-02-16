package org.payn.chsm.finitediff.processors;

import java.util.ArrayList;

import org.payn.chsm.finitediff.processors.interfaces.UpdaterCore;
import org.payn.chsm.finitediff.processors.interfaces.UpdaterDelta;
import org.payn.chsm.finitediff.processors.interfaces.UpdaterPostauxiliary;
import org.payn.chsm.finitediff.processors.interfaces.UpdaterPreauxiliary;
import org.payn.chsm.processors.ControllerTimeStep;
import org.payn.chsm.processors.Processor;
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
    * List of updaters for the preauxiliary phase
    */
   protected ArrayList<UpdaterSimpleAuto> preauxiliaryUpdaters;
   
   /**
    * Getter
    * 
    * @return
    *       list of trade updaters
    */
   public ArrayList<UpdaterSimpleAuto> getPreauxiliaryUpdaters() 
   {
      return preauxiliaryUpdaters;
   }

   /**
    * List of updaters for the delta phase
    */
   protected ArrayList<UpdaterSimpleAuto> deltaUpdaters;
   
   /**
    * Getter
    * 
    * @return
    *       array list of delta updaters
    */
   public ArrayList<UpdaterSimpleAuto> getDeltaUpdaters() 
   {
      return deltaUpdaters;
   }

   /**
    * List of updaters for the core phase
    */
   protected ArrayList<UpdaterSimpleAuto> coreUpdaters;

   /**
    * Getter
    * 
    * @return
    *       array list of core updaters
    */
   public ArrayList<UpdaterSimpleAuto> getCoreUpdaters() 
   {
      return coreUpdaters;
   }

   /**
    * List of updaters for the postauxiliary phase
    */
   protected ArrayList<UpdaterSimpleAuto> postauxiliaryUpdaters;
   
   /**
    * Getter
    * 
    * @return
    *       list of postauxiliary updaters
    */
   public ArrayList<UpdaterSimpleAuto> getPostauxiliaryUpdaters() 
   {
      return postauxiliaryUpdaters;
   }

   /**
    * Constructor
    */
   public ControllerFiniteDiff()
   {
      initializers = new ArrayList<InitializerSimpleAuto>();
      preauxiliaryUpdaters = new ArrayList<UpdaterSimpleAuto>();
      deltaUpdaters = new ArrayList<UpdaterSimpleAuto>();
      coreUpdaters = new ArrayList<UpdaterSimpleAuto>();
      postauxiliaryUpdaters = new ArrayList<UpdaterSimpleAuto>();
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
      
      if (UpdaterPreauxiliary.class.isInstance(processor))
      {
         preauxiliaryUpdaters.add((UpdaterPreauxiliary)processor);
      }
      else if (UpdaterDelta.class.isInstance(processor))
      {
         deltaUpdaters.add((UpdaterDelta)processor);
      }
      else if (UpdaterCore.class.isInstance(processor))
      {
         coreUpdaters.add((UpdaterCore)processor);
      }
      else if (UpdaterPostauxiliary.class.isInstance(processor))
      {
         postauxiliaryUpdaters.add((UpdaterSimpleAuto)processor);
      }
   }
   
}
