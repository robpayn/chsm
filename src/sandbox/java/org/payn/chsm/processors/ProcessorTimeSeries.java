package org.payn.chsm.processors;

import org.payn.chsm.Value;
import org.payn.chsm.processors.ProcessorAbstract;
import org.payn.chsm.values.ValueTimeSeries;

public class ProcessorTimeSeries extends ProcessorAbstract<ValueTimeSeries> {
   
   /**
    * Get the value class for this processor
    * 
    * @return
    *       value class
    */
   public static Class<? extends Value> getValueClass()
   {
      return ValueTimeSeries.class;
   }

   @Override
   public void createValue() throws Exception 
   {
      setValue(new ValueTimeSeries());
   }

   @Override
   public void setValueFromState() throws Exception 
   {
      setValue((ValueTimeSeries)state.getValue());
   }

}
