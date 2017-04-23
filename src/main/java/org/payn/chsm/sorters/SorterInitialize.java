package org.payn.chsm.sorters;

import java.util.ArrayList;

import org.payn.chsm.processors.Processor;
import org.payn.chsm.processors.interfaces.InitializerAuto;
import org.payn.chsm.processors.interfaces.InitializerSimpleAuto;

/**
 * Handles dependencies during the initialization phase of a NEO lite simulation
 * 
 * @author robpayn
 *
 */
public class SorterInitialize extends SorterAbstract<InitializerSimpleAuto> {

   /**
    * Constructs a new instance based on an array of initializers
    * 
    * @param initializers
    */
   public SorterInitialize(ArrayList<InitializerSimpleAuto> initializers) 
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
         throw new Exception("Processor not compatible with InitilzerAutoSimple sorter");
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
