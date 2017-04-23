package org.payn.chsm.io.inputters;

import org.payn.chsm.values.ValueDouble;

/**
 * An abstract interpolator implementing the selection of the calculator
 * 
 * @author robpayn
 *
 */
public class Interpolator {
   
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
    * Table with the column being processed by this interpolator
    */
   private InterpolatorSnapshotTable table;
   
   /**
    * Construct a new interpolator for the table and column number provided
    * 
    * @param table
    * @param columnNumber
    * @param interpolationType
    * @param time
    */
   public Interpolator(InterpolatorSnapshotTable table, String interpolationType, 
         ValueDouble time)
   {
      this.table = table;
      setCalculator(interpolationType, time);
   }
   
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

   /**
    * Interpolate the value for the column
    * 
    * @return
    *       interpolated value
    * @throws Exception
    */
   public double interpolate() throws Exception 
   {
      table.checkTime();
      return calculator.calculate();
   }

}
