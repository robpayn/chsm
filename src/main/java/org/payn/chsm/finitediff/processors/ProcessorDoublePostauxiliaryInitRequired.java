package org.payn.chsm.finitediff.processors;

import org.payn.chsm.io.exceptions.ExceptionMissingInitialValue;

/**
 * A processor for the postauxiliary phase that requires initialization
 * 
 * @author v78h241
 *
 */
public abstract class ProcessorDoublePostauxiliaryInitRequired extends ProcessorDoublePostauxiliaryInit {

   @Override
   public void setInitDependencies() 
         throws Exception 
   {
      // Default is no initialization dependencies
      // Override to add dependencies
   }

   @Override
   public void initialize() 
         throws ExceptionMissingInitialValue 
   {
      if (value.isNoValue())
      {
         throw new ExceptionMissingInitialValue(state);
      }
   }

}
