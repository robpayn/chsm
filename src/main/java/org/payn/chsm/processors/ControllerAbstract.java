package org.payn.chsm.processors;

import java.util.ArrayList;

import org.payn.chsm.Controller;
import org.payn.chsm.DependencyHandler;
import org.payn.chsm.InputHandler;
import org.payn.chsm.LoggerManager;
import org.payn.chsm.OutputHandler;
import org.payn.chsm.Processor;
import org.payn.chsm.State;
import org.payn.chsm.Value;

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
    * Output handlers for generating output
    */
   protected ArrayList<OutputHandler> outputHandlers;
   
   @Override
   public ArrayList<OutputHandler> getOutputHandlers()
   {
      return outputHandlers;
   }
   
   /**
    * Dependency handler for managing call order
    */
   protected DependencyHandler<? extends Processor> dependencyHandler;
   
   /**
    * List of input handlers
    */
   protected ArrayList<InputHandler> inputHandlers;
   
   /**
    * Logger manager to use for logging
    */
   protected LoggerManager loggerManager;
   
   @Override
   public void setLogger(LoggerManager loggerManager)
   {
      this.loggerManager = loggerManager;
   }

   /**
    * Construct a new instance
    */
   public ControllerAbstract()
   {
      outputHandlers = new ArrayList<OutputHandler>();
      inputHandlers = new ArrayList<InputHandler>();
   }
   
   /**
    * Add an output handler
    * 
    * @param outputHandler
    *       new output handler to add
    */
   public void addOutputHandler(OutputHandler outputHandler)
   {
      outputHandlers.add(outputHandler);
   }

   /**
    * Get the dependency handler
    * 
    * @return
    *       dependency handler
    */
   public DependencyHandler<? extends Processor> getDependencyHandler() 
   {
      return dependencyHandler;
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
         dependencyHandler.addDependency(processor, neededProc);
      }
   }
   
   @Override
   public void addInputHandler(InputHandler inputHandler)
   {
      inputHandlers.add(inputHandler);
   }
   
}
