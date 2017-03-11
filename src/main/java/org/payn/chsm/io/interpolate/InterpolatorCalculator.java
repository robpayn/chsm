package org.payn.chsm.io.interpolate;

import org.payn.chsm.values.ValueDouble;

/**
 * Abstract implementation of an interpolator.
 * 
 * @author robpayn
 *
 */
public abstract class InterpolatorCalculator {
   
   /**
    * Name of the type of the interpolator
    */
   protected String typeName;
   
   /**
    * Getter 
    * 
    * @return
    *       name of type
    */
   public String getTypeName()
   {
      return typeName;
   }
   
   /**
    * Value for time
    */
   protected ValueDouble time;

   /**
    * Setter for time value
    * 
    * @param time
    *       time value
    */
   public void setTime(ValueDouble time)
   {
      this.time = time;
   }
   
   /**
    * Last value used in interpolation
    */
   protected double lastValue;
   
   /**
    * Setter
    * 
    * @param lastValue
    *       last value
    */
   public void setLastValue(double lastValue)
   {
      this.lastValue = lastValue;
   }

   /**
    * Next value used in interpolation
    */
   protected double nextValue;

   /**
    * Setter
    * 
    * @param nextValue
    *       next value
    */
   public void setNextValue(double nextValue)
   {
      this.nextValue = nextValue;
   }

   /**
    * Last time
    */
   protected double lastTime;

   /**
    * Next time
    */
   protected double nextTime;

   /**
    * Constructor
    * 
    * @param typeName
    *       type name of the calculator
    */
   public InterpolatorCalculator(String typeName)
   {
      this.typeName = typeName;
   }

   /**
    * Updates the time
    * 
    * @param nextTime
    *       new next time
    */
   public void updateTime(double nextTime)
   {
      lastTime = this.nextTime;
      this.nextTime = nextTime;
   }

   /**
    * Calculate the interpolation
    * 
    * @return
    *       interpolated value
    */
   public abstract double calculate();

}
