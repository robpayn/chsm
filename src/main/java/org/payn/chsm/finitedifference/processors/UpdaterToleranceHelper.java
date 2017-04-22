package org.payn.chsm.finitedifference.processors;

import org.payn.chsm.finitedifference.processors.interfaces.UpdaterTolerance;
import org.payn.chsm.values.ValueDouble;

/**
 * Helper for a processor implementing the tolerance updater
 * 
 * @author v78h241
 *
 */
public class UpdaterToleranceHelper implements UpdaterTolerance {

   /**
    * The last estimate for checking tolerance of the current estimate
    */
   protected double lastEstimate;
   
   /**
    * Value containing the current estimate
    */
   protected ValueDouble value;
   
   /**
    * Controller tracking the results of the tolerance check
    */
   protected ControllerBackEuler controller;
   
   /**
    * Decorated processor
    */
   protected ProcessorDoubleBaseState proc;

   /**
    * Construct a new instance decorating the provided processor
    * 
    * @param proc
    *       processor to be decorated
    */
   public UpdaterToleranceHelper(ProcessorDoubleBaseState proc) 
   {
      this.proc = proc;
      value = proc.getValue();
      controller = (ControllerBackEuler)proc.getController();
   }

   @Override
   public void setLastEstimate()
   {
      lastEstimate = value.n;
   }

   @Override
   public void checkTolerance()
   {
      double error = Math.abs(value.n - lastEstimate);
      if (error > controller.getDefaultTolerance())
      {
         ((ControllerBackEuler)controller).convergenceFailed();
      }
   }

}
