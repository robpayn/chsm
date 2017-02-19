package org.payn.chsm.processors;

import org.payn.chsm.ProcessorAbstract;
import org.payn.chsm.Value;
import org.payn.chsm.values.ValueLong;

/**
 * A processor for a long integer value
 * 
 * @author robpayn
 *
 */
public class ProcessorLong extends ProcessorAbstract<ValueLong> {
   
   /**
    * Get the value class for this processor
    * 
    * @return
    *       value class
    */
   public static Class<? extends Value> getValueClass()
   {
      return ValueLong.class;
   }

   /**
    * Create the new long integer value object to be changed
    */
   @Override
   public void createValue() throws Exception 
   {
      setValue(new ValueLong());
   }
   
   @Override
   public void setValueFromState()
   {
      setValue((ValueLong)state.getValue());
   }

}
