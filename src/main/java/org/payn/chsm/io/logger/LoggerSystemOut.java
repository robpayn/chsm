package org.payn.chsm.io.logger;

import org.payn.chsm.LoggerAbstract;

/**
 * A logger that will send status updates to the System.out
 * 
 * @author robpayn
 *
 */
public class LoggerSystemOut extends LoggerAbstract implements StatusUpdateListener {

   @Override
   public void registerListeners() 
   {
      loggerManager.addStatusUpdateListener(this);
   }

   @Override
   public void statusUpdate(String message) 
   {
      System.out.println(message);
   }

}
