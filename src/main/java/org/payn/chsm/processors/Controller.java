package org.payn.chsm.processors;

import java.util.LinkedHashMap;

import org.payn.chsm.State;
import org.payn.chsm.io.inputters.Inputter;
import org.payn.chsm.io.logger.LoggerManager;
import org.payn.chsm.io.reporters.Reporter;

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
   public abstract void setLoggerManager(LoggerManager loggerManager);

   /**
    * Get the reporter list
    * 
    * @return
    *       list of reporters
    */
   public abstract LinkedHashMap<String, Reporter> getReporters();

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
    * Add an inputter
    * 
    * @param inputter
    *       inputter to be added
    */
   public abstract void addInputter(Inputter inputter);
   
}
