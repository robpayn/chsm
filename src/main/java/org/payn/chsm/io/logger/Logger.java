package org.payn.chsm.io.logger;

/**
 * Interface for a logger
 * 
 * @author robpayn
 *
 */
public interface Logger {

   /**
    * Register listeners
    */
   public abstract void registerListeners();

   /**
    * Register with the logger manager
    * 
    * @param loggerManager
    */
   public abstract void register(LoggerManager loggerManager);

}
