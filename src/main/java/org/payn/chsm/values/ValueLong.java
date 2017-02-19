package org.payn.chsm.values;

import org.payn.chsm.ValueAbstract;

/**
 * A long integer value
 * 
 * @author robert.payn
 *
 */
public class ValueLong extends ValueAbstract {
   
   /**
    * The number representing the value
    */
   public long n;
   
   /**
    * Set the number based on a string representation
    */
   @Override
   public void setToValueOf(String valueString) throws Exception 
   {
      n = Long.valueOf(valueString);
   }

   /**
    * Set the number to the minimum value for a long integer 
    * (minimum value is considered an invalid state)
    */
   @Override
   public void setToNoValue() 
   {
      n = Long.MIN_VALUE;
   }

   /**
    * Returns true if the value is the minumum value for a long integer
    * (minimum value is considered an invalid state)
    */
   @Override
   public boolean isNoValue() 
   {
      return n == Long.MIN_VALUE;
   }

   /**
    * Get the number as a string
    */
   @Override
   public String getValueAsString() 
   {
      return Long.toString(n);
   }

}
