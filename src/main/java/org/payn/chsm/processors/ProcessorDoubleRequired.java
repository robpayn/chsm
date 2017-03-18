package org.payn.chsm.processors;

import org.payn.chsm.processors.interfaces.InitializerSimple;

/**
 * A processor that requires initialization
 * 
 * @author v78h241
 *
 */
public abstract class ProcessorDoubleRequired extends ProcessorDouble implements InitializerSimple {

   @Override
   public void initialize() throws Exception 
   {
      if (value.isNoValue())
      {
         throw new Exception(String.format(
               "%s must be assigned an initial value in holon %s",
               state.getName(),
               state.getParentHolon().getName()
               ));
      }
   }

}
