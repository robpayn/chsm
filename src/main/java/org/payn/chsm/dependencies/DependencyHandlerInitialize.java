package org.payn.chsm.dependencies;

import java.util.ArrayList;

import org.payn.chsm.DependencyHandler;
import org.payn.chsm.Processor;
import org.payn.chsm.processors.interfaces.InitializerAuto;
import org.payn.chsm.processors.interfaces.InitializerAutoSimple;

/**
 * Handles dependencies during the initialization phase of a NEO lite simulation
 * 
 * @author robpayn
 *
 */
public class DependencyHandlerInitialize extends DependencyHandler<InitializerAutoSimple> {

   /**
    * Constructs a new instance based on an array of initializers
    * 
    * @param initializers
    */
   public DependencyHandlerInitialize(ArrayList<InitializerAutoSimple> initializers) 
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
      if (!InitializerAutoSimple.class.isInstance(processor))
      {
         throw new Exception("Processor not compatible with InitilzerAutoSimple dependency handler");
      }
      if (InitializerAuto.class.isInstance(neededProcessor))
      {
         addToGraph((InitializerAutoSimple)processor, (InitializerAutoSimple)neededProcessor);
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
