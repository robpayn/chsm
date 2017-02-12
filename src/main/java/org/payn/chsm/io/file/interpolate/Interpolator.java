package org.payn.chsm.io.file.interpolate;

import org.payn.chsm.InputHandler;
import org.payn.chsm.values.ValueDouble;

/**
 * An abstract interpolator implementing the selection of the calculator
 * 
 * @author robpayn
 *
 */
public abstract class Interpolator implements InputHandler {
   
   /**
    * Name for linear calculation option
    */
   public static final String OPTION_LINEAR = "linear";
   
   /**
    * Name for step calculation option
    */
   public static final String OPTION_STEP = "step";
   
   /**
    * Calculator
    */
   protected InterpolatorCalculator calculator;
   
   /**
    * Getter 
    * 
    * @return
    *       calculator
    */
   public InterpolatorCalculator getCalculator() 
   {
      return calculator;
   }

   /**
    * Set the calculator based on the option
    * 
    * @param option
    *       option name for calculator type          
    * @param time
    *       time value
    */
   protected void setCalculator(String option, ValueDouble time)
   {
      switch(option)
      {
         case OPTION_LINEAR:
            calculator = new InterpolatorCalculator(OPTION_LINEAR) {
               @Override
               public double calculate() {
                  return lastValue + (nextValue - lastValue) * ((time.n - lastTime) / (nextTime - lastTime));
               }
            };
            break;
         case OPTION_STEP:
            calculator = new InterpolatorCalculator(OPTION_STEP) {
               @Override
               public double calculate()
               {
                  return lastValue;
               }
            };
            break;
      }
      calculator.setTime(time);
   }

}
