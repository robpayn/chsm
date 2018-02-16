package org.payn.chsm.processors;

import org.payn.chsm.Holon;
import org.payn.chsm.State;
import org.payn.chsm.processors.interfaces.UpdaterSlave;
import org.payn.chsm.values.Value;

/**
 * A processor that is a slave to another processor.  
 * 
 * @author robpayn
 * 
 * @param <VT> 
 *      type for the value changed by the processor
 */
public abstract class ProcessorSlave<VT extends Value> extends ProcessorAbstract<VT> implements UpdaterSlave {
   
   /**
    * The master processor to this slave processor
    */
   private Processor masterProcessor;
   
   /**
    * Get the master processor
    */
   @Override
   public Processor getMasterProcessor()
   {
      return masterProcessor;
   }
   
   /**
    * Construct the slave processor as controlled by the provided master processor
    * 
    * @param masterProcessor
    *       master processor that controls this slave
    */
   public ProcessorSlave(Processor masterProcessor)
   {
      this.masterProcessor = masterProcessor;
   }

   /**
    * Create a dependency on a state in the specified holon
    * 
    * @param holon
    *       holon containing the needed state
    * @param stateName
    *       name of the needed state
    * @return
    *       reference to the needed state
    * @throws Exception
    *       if error in finding state
    */
   @Override
   public State createDependency(Holon holon, String stateName) throws Exception 
   {
      return masterProcessor.createDependency(holon, stateName);
   }
   
}
