package org.payn.chsm.dependencies;

import java.util.ArrayList;

import org.payn.chsm.DependencyHandlerAbstract;
import org.payn.chsm.Processor;
import org.payn.chsm.processors.interfaces.InitializerAuto;
import org.payn.chsm.processors.interfaces.InitializerSimpleAuto;

/**
 * Handles dependencies during the initialization phase of a NEO lite simulation
 * 
 * @author robpayn
 *
 */
public class DependencyHandlerInitialize extends DependencyHandlerAbstract<InitializerSimpleAuto> {

   /**
    * Constructs a new instance based on an array of initializers
    * 
    * @param initializers
    */
   public DependencyHandlerInitialize(ArrayList<InitializerSimpleAuto> initializers) 
   {
      super(initializers);
   }

   /**
    * Adds a dependency if the needed processor is an initializer
    * @throws Exception 
    */
   @Override
   public void addDependency(Processor processor, Processor neededProcessor) throws Exception 
   {
      if (!InitializerSimpleAuto.class.isInstance(processor))
      {
         throw new Exception("Processor not compatible with InitilzerAutoSimple dependency handler");
      }
      if (InitializerAuto.class.isInstance(neededProcessor))
      {
         addToGraph((InitializerSimpleAuto)processor, (InitializerSimpleAuto)neededProcessor);
      }
   }

   @Override
   public void addDependencies() throws Exception 
   {
      for (InitializerAuto initializer: processors)
      {
         initializer.setInitDependencies();
      }
   }

}
