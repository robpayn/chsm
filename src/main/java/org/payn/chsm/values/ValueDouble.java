package org.payn.chsm.values;

import java.text.DecimalFormat;

import org.payn.chsm.ValueAbstract;

/**
 * A double precision floating point value
 * 
 * @author robert.payn
 *
 */
public class ValueDouble extends ValueAbstract {
   
   /**
    * Default format
    */
   public static final String FORMAT_DEFAULT = "0.000000E0";
   
   /**
    * Alias name
    */
   public static final String ALIAS = ValueDouble.class.getSimpleName();
   
   /**
    * The number representing the value
    */
   public double n;

   /**
    * Formatter for the string version of the number
    */
   private DecimalFormat formatter;
   
   
   /**
    * Constructor
    */
   public ValueDouble()
   {
      formatter = new DecimalFormat(FORMAT_DEFAULT);
   }

   /**
    * Constructor that sets the value based on the provided string
    * 
    * @param valueString
    * @throws Exception
    */
   public ValueDouble(String valueString) throws Exception 
   {
      super(valueString);
      formatter = new DecimalFormat(FORMAT_DEFAULT);
   }

   /**
    * Construct a new instance based on the provided number
    * 
    * @param number
    *       numerical value for the new value
    */
   public ValueDouble(double number) 
   {
      this();
      this.n = number;
   }

   /**
    * Set the number from its string representation
    */
   @Override
   public void setToValueOf(String valueString) throws Exception 
   {
      n = Double.valueOf(valueString);
   }

   /**
    * Sets the value to a NaN state (not a number)
    */
   @Override
   public void setToNoValue() 
   {
      n = Double.NaN;
   }

   /**
    * Returns true if value is NaN (not a number).
    */
   @Override
   public boolean isNoValue() 
   {
      return Double.isNaN(n);
   }

   /**
    * Get the number as a string
    */
   @Override
   public String getValueAsString() 
   {
      return formatter.format(n);
   }

}
