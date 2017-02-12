package org.payn.chsm;

import java.util.ArrayList;

import org.payn.chsm.io.logger.Logger;
import org.payn.chsm.io.logger.StatusUpdateListener;

/**
 * Manages all logging for the composite hierarchy state machine
 * 
 * @author robpayn
 *
 */
public class LoggerManager {
   
   /**
    * List of loggers listening for status updates
    */
   private ArrayList<StatusUpdateListener> statusUpdateListeners;
   
   /**
    * Get the list of status update listeners
    * 
    * @return
    *       list of status update listeners
    */
   public ArrayList<StatusUpdateListener> getStatusUpdateListeners()
   {
      return statusUpdateListeners;
   }
   
   
   /**
    * Simple constructor
    */
   public LoggerManager()
   {
      statusUpdateListeners = new ArrayList<StatusUpdateListener>();
   }
   
   /**
    * Add a logger to be managed
    * 
    * @param logger
    */
   public void addLogger(Logger logger)
   {
      // ask the logger to register its listeners
      logger.register(this);
   }

   /**
    * Add a status update listener
    * 
    * @param listener
    */
   public void addStatusUpdateListener(StatusUpdateListener listener) 
   {
      statusUpdateListeners.add(listener);
   }

   /**
    * Generate a status update message
    * 
    * @param message
    *       message to be sent to status update listeners
    */
   public void statusUpdate(String message) 
   {
      for (StatusUpdateListener logger: statusUpdateListeners)
      {
         logger.statusUpdate(message);
      }
   }

}
