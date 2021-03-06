package org.payn.chsm.io.reporters;

import java.io.File;
import java.sql.Time;
import java.util.HashMap;

import org.payn.chsm.Holon;
import org.payn.chsm.State;
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
    * Working directory
    */
   protected File workingDir;
   
   @Override
   public File getWorkingDir()
   {
      return workingDir;
   }

   /**
    * Map of command line arguments
    */
   protected HashMap<String, String> argMap;
   
   /**
    * Construct a new instance based on the provided working directory
    * and argument map
    * 
    * @param workingDir
    * @param argMap
    */
   public ReporterAbstract(File workingDir, HashMap<String, String> argMap)
   {
      this.workingDir = workingDir;
      this.argMap = argMap;
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
