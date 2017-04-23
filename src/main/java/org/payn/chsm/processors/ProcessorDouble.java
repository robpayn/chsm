package org.payn.chsm.processors;

import org.payn.chsm.values.Value;
import org.payn.chsm.values.ValueDouble;

/**
 * A processor that changes a double precision floating point value
 * 
 * @author robpayn
 *
 */
public class ProcessorDouble
extends ProcessorAbstract<ValueDouble> {

   /**
    * Get the value class for the processor
    * 
    * @return
    *       value class 
    */
   public static Class<? extends Value> getValueClass()
   {
      return ValueDouble.class;
   }

   /**
    * Create the double precision floating point value object
    */
   @Override
   public void createValue() throws Exception 
   {
      setValue(new ValueDouble());
   }
   
   @Override
   public void setValueFromState()
   {
      setValue((ValueDouble)state.getValue());
   }

}
