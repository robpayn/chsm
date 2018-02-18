package org.payn.chsm.io.reporters;

import java.io.File;
import java.util.HashMap;

import org.payn.chsm.io.logger.LoggerManager;

/**
 * Factory for a reporter
 * 
 * @author v78h241
 * 
 * @param <CT>
 *      Type of object containing the configuration of the reporter
 * @param <RT> 
 *      Type of reporter configured by this factory
 *
 */
public abstract class ReporterFactory<CT, RT extends Reporter> {
   
   /**
    * Reporter built by this factory
    */
   protected RT reporter;
   
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
    * @param workingDir
    *       File representation of the model working directory 
    * @param argMap 
    *       map of command line arguments (key/value pairs)
    * @return
    *       reference to reporter object
    * @throws Exception
    *       if error in creating reporter object
    */
   public Reporter createReporter(File workingDir, HashMap<String, String> argMap) 
         throws Exception
   {
      reporter = newReporter(workingDir, argMap);
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
    * @throws Exception 
    */
   protected abstract void init() throws Exception;

   /**
    * Create a new reporter of the appropriate type for this factory
    * 
    * @param workingDir 
    *       working directory for the reporter
    * @param argMap 
    *       command line argument map
    * @return
    *       reference to reporter
    * @throws Exception
    *       if error in creating the new reporter
    */
   protected abstract RT newReporter(File workingDir, HashMap<String, String> argMap) 
         throws Exception;

 }
