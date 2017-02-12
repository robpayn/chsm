package org.payn.chsm.processors;

import org.payn.chsm.Holon;
import org.payn.chsm.Processor;
import org.payn.chsm.State;
import org.payn.chsm.Value;
import org.payn.chsm.processors.interfaces.UpdaterSlave;

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
    * Create a dependency on a state variable in the specified holon
    * 
    * @param holon
    *       holon containing the needed state variable
    * @param stateName
    *       name of the needed state variable
    * @return
    *       reference to the needed state variable
    * @throws Exception
    *       if error in finding state variable
    */
   @Override
   public State createDependency(Holon holon, String stateName) throws Exception 
   {
      return masterProcessor.createDependency(holon, stateName);
   }
   
}
