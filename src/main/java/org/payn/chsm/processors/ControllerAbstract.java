package org.payn.chsm.processors;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.payn.chsm.State;
import org.payn.chsm.io.inputters.Inputter;
import org.payn.chsm.io.logger.LoggerManager;
import org.payn.chsm.io.reporters.Reporter;
import org.payn.chsm.sorters.Sorter;
import org.payn.chsm.values.Value;

/**
 * A processor that controls other processors in a state map
 * 
 * @author robpayn
 * 
 * @param <VT> 
 *      Type for value
 */
public abstract class ControllerAbstract<VT extends Value> 
extends ProcessorAbstract<VT> 
implements Controller {
   
   /**
    * List of reporters that generate output
    */
   protected LinkedHashMap<String, Reporter> reporters;
   
   @Override
   public LinkedHashMap<String, Reporter> getReporters()
   {
      return reporters;
   }
   
   /**
    * Sorter for managing call order
    */
   protected Sorter<? extends Processor> sorter;
   
   /**
    * List of inputters
    */
   protected ArrayList<Inputter> inputters;
   
   /**
    * Logger manager to use for logging
    */
   protected LoggerManager loggerManager;
   
   @Override
   public void setLoggerManager(LoggerManager loggerManager)
   {
      this.loggerManager = loggerManager;
   }

   /**
    * Construct a new instance
    */
   public ControllerAbstract()
   {
      reporters = new LinkedHashMap<String, Reporter>();
      inputters = new ArrayList<Inputter>();
   }
   
   /**
    * Add a reporter to the controller
    * 
    * @param reporter
    *       reporter to add
    */
   public void addReporter(String reporterName, Reporter reporter)
   {
      reporters.put(reporterName, reporter);
   }

   /**
    * Get the sorter
    * 
    * @return
    *       sorter
    */
   public Sorter<? extends Processor> getSorter() 
   {
      return sorter;
   }
   

   /**
    * Create a dependency between processors
    * 
    * @param processor
    *       dependent processor
    * @param holon
    *       holon containing the needed state
    * @param stateName
    *       name of the needed state
    * @throws Exception
    *       if error in finding needed state
    */
   @Override
   public void createDependency(Processor processor, State state) throws Exception 
   {
      if (!state.isStatic())
      {
         Processor neededProc = state.getProcessor();
         sorter.addDependency(processor, neededProc);
      }
   }
   
   @Override
   public void addInputter(Inputter inputter)
   {
      inputters.add(inputter);
   }
   
}
