package org.payn.chsm.processors;

import org.payn.chsm.processors.interfaces.UpdaterMemory;
import org.payn.chsm.values.ValueDouble;

/** 
 * Helper class to implement the memory updater for double precision
 * states that need memory.
 * 
 * @author v78h241
 * @param <PT> 
 *
 */
public class UpdaterMemoryHelper<PT extends ProcessorDouble> implements UpdaterMemory {
   
   /**
    * Processor decorated by this updater
    */
   protected PT processor;
   
   /**
    * Saved value of the processor's value
    */
   protected double savedValue;
   
   /**
    * Value changed by the processor
    */
   protected ValueDouble value;

   /**
    * Construct new instance decorating the provided processor
    * 
    * @param proc
    *       processor to be decorated
    */
   public UpdaterMemoryHelper(PT proc)
   {
      this.processor = proc;
      this.value = proc.getValue();
   }

   @Override
   public void saveNumber() 
   {
      savedValue = value.n;
   }

   @Override
   public void restoreSavedNumber() 
   {
      value.n = savedValue;
   }

}
