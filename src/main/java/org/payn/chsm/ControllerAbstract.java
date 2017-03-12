package org.payn.chsm;

import java.util.ArrayList;

/**
 * A processor that controls other processors in a state variable map
 * 
 * @author robpayn
 * 
 * @param <VT> 
 *      Type for value
 */
public abstract class ControllerAbstract<VT extends Value> extends ProcessorAbstract<VT> implements Controller {
   
   /**
    * List of reporters that generate output
    */
   protected ArrayList<Reporter> reporters;
   
   @Override
   public ArrayList<Reporter> getReporters()
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
      reporters = new ArrayList<Reporter>();
      inputters = new ArrayList<Inputter>();
   }
   
   /**
    * Add a reporter to the controller
    * 
    * @param reporter
    *       reporter to add
    */
   public void addReporter(Reporter reporter)
   {
      reporters.add(reporter);
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
    *       holon containing the needed state variable
    * @param stateName
    *       name of the needed state variable
    * @throws Exception
    *       if error in finding needed state variable
    */
   @Override
   public void createDependency(Processor processor, State state) throws Exception 
   {
      if (state.isDynamic())
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
