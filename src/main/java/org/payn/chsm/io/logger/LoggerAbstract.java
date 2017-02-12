package org.payn.chsm.io.logger;

import org.payn.chsm.LoggerManager;

/**
 * An abstract logger
 * 
 * @author robpayn
 *
 */
public abstract class LoggerAbstract implements Logger {

   /**
    * The manager for this logger
    */
   protected LoggerManager loggerManager;
   
   /**
    * Register this logger with the logger manager
    * 
    * @param loggerManager
    */
   @Override
   public void register(LoggerManager loggerManager) 
   {
      this.loggerManager = loggerManager;
      registerListeners();
   }
   
}
