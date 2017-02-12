package org.payn.chsm.io;

import org.payn.chsm.LoggerManager;
import org.payn.chsm.OutputHandler;

/**
 * Factory for an output handler
 * 
 * @author v78h241
 * 
 * @param <CT>
 *      Type of object containing the configuration information 
 * @param <HT> 
 *      Type of output handler
 *
 */
public abstract class OutputHandlerFactory<CT, HT extends OutputHandler> {
   
   /**
    * Output handler built by this factory
    */
   protected HT outputHandler;
   
   /**
    * Configuration information
    */
   protected CT config;
   
   /**
    * Setter
    * 
    * @param config
    *       object containing configuration information
    */
   public void setConfig(CT config)
   {
      this.config = config;
   }
   
   /**
    * The logger manager to use for logging
    */
   protected LoggerManager loggerManager;
   
   /**
    * Set the logger manager
    * 
    * @param loggerManager
    */
   public void setLogger(LoggerManager loggerManager)
   {
      this.loggerManager = loggerManager;
   }
   

   /**
    * Create the output handler associated with this factory
    * 
    * @param holon
    *       source holon
    * @return
    *       output handler
    * @throws Exception
    *       if error in creating factory
    */
   public OutputHandler createOutputHandler() throws Exception
   {
      outputHandler = newOutputHandler();
      outputHandler.setFactory(this);
      return outputHandler;
   }
   
   /**
    * Initialize the associated output handler
    * 
    * @throws Exception
    *       if error in initialization
    */
   public void initialize() throws Exception
   {
      if (config == null)
      {
         throw new Exception(String.format(
               "%s must have a configuration set before it can be initialized.", getClass().getName()
               ));
      }
      init();
   }
   
   /**
    * Initialize the associated output handler based on the provided configuration information
    * 
    * @param config
    *       object containing configuration information
    */
   protected abstract void init();

   /**
    * Create a new output handler of the appropriate type for this factory
    * 
    * @return
    *       output handler
    * @throws Exception
    *       if error in creating the new output handler 
    */
   protected abstract HT newOutputHandler() throws Exception;

 }
