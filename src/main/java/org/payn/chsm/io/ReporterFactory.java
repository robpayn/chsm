package org.payn.chsm.io;

import org.payn.chsm.LoggerManager;
import org.payn.chsm.Reporter;

/**
 * Factory for a reporter
 * 
 * @author v78h241
 * 
 * @param <CT>
 *      Type of object containing the configuration of the reporter
 * @param <HT> 
 *      Type of reporter configured by this factory
 *
 */
public abstract class ReporterFactory<CT, HT extends Reporter> {
   
   /**
    * Reporter built by this factory
    */
   protected HT reporter;
   
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
    * Create the reporter associated with this factory
    * 
    * @param holon
    *       source holon
    * @return
    *       reference to reporter object
    * @throws Exception
    *       if error in creating reporter object
    */
   public Reporter createReporter() throws Exception
   {
      reporter = newReporter();
      reporter.setFactory(this);
      return reporter;
   }
   
   /**
    * Initialize the associated reporter
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
    * Initialize the associated reporter based on the provided configuration information
    * 
    * @param config
    *       object containing configuration information
    */
   protected abstract void init();

   /**
    * Create a new reporter of the appropriate type for this factory
    * 
    * @return
    *       reference to reporter
    * @throws Exception
    *       if error in creating the new reporter
    */
   protected abstract HT newReporter() throws Exception;

 }
