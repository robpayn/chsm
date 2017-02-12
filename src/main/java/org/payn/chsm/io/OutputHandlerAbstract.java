package org.payn.chsm.io;

import org.payn.chsm.Holon;
import org.payn.chsm.OutputHandler;
import org.payn.chsm.State;
import org.payn.chsm.resources.time.BehaviorTime;
import org.payn.chsm.values.ValueDouble;
import org.payn.chsm.values.ValueLong;

/**
 * Handles the output from the state machine
 * 
 * @author robpayn
 * 
 * @param <LT>
 *      output location object type
 */
public abstract class OutputHandlerAbstract<LT> implements OutputHandler {
   
   /**
    * Holon containing the source of output
    */
   protected Holon source;
   
   /**
    * Location to send the output
    */
   protected LT location;
   
   /**
    * Current iteration count
    */
   protected ValueLong iterationValue;

   /**
    * Current simulation time represented by the iteration
    */
   protected ValueDouble timeValue;
   
   /**
    * Factory that created this output handler
    */
   protected OutputHandlerFactory<?,?> factory;
   
   @Override
   public void setFactory(OutputHandlerFactory<?,?> factory)
   {
      this.factory = factory;
   }

   /**
    * Initialize the outputter based on the output element and the source provided
    * 
    * @param element
    *       output element containing configuration information
    * @param source
    *       source holon for output
    * @throws Exception
    *       if error in initialization
    */
   @Override
   public void initialize(Holon source) throws Exception
   {
      State iterationState = 
            source.getState(BehaviorTime.DEFAULT_ITERATION_NAME);
      iterationValue = (ValueLong)iterationState.getValue();
      State timeState = 
            source.getState(BehaviorTime.DEFAULT_TIME_NAME);
      timeValue = (ValueDouble)timeState.getValue();
      this.source = source;
      
      factory.initialize();
   }
   
}
