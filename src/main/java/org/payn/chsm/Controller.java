package org.payn.chsm;

import java.util.ArrayList;

/**
 * Interface to a processor that controls other processors
 * 
 * @author robpayn
 *
 */
public interface Controller extends Processor {
   
   /**
    * Set the logger manager for the controller
    * 
    * @param loggerManager
    *       logger manager
    */
   public abstract void setLogger(LoggerManager loggerManager);

   /**
    * Getter
    * 
    * @return
    *       list of output handlers
    */
   public abstract ArrayList<OutputHandler> getOutputHandlers();

   /**
    * Add a processor to be controlled
    * 
    * @param processor
    *       processor controlled by this controller
    * @throws Exception
    *       if error in adding processor
    */
   public abstract void addProcessor(Processor processor) throws Exception;
   
   /**
    * Create a dependency from a processor to a necessary state for calculation of that processor
    * 
    * @param processor
    *       dependent processor
    * @param state
    *       needed state
    * @throws Exception
    *       if error in setting dependency
    */
   public abstract void createDependency(Processor processor, State state) throws Exception;

   /**
    * Add an input handler
    * 
    * @param inputHandler
    *       input handler to be added
    */
   public abstract void addInputHandler(InputHandler inputHandler);

}
