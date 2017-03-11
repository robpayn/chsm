package org.payn.chsm;

import java.sql.Time;

import org.payn.chsm.io.ReporterFactory;
import org.payn.chsm.resources.time.Iteration;
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
public abstract class ReporterAbstract<LT> implements Reporter {
   
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
    * Factory that created this reporter
    */
   protected ReporterFactory<?,?> factory;
   
   @Override
   public void setFactory(ReporterFactory<?,?> factory)
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
            source.getState(Iteration.class.getSimpleName());
      iterationValue = (ValueLong)iterationState.getValue();
      State timeState = 
            source.getState(Time.class.getSimpleName());
      timeValue = (ValueDouble)timeState.getValue();
      this.source = source;
      
      factory.initialize();
   }
   
}
