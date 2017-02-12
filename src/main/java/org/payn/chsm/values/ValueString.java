package org.payn.chsm.values;

/**
 * Value for a string
 * 
 * @author robpayn
 *
 */
public class ValueString extends ValueAbstract {
   
   /**
    * The string represented by this value
    */
   public String string;
   
   /**
    * Set to null
    */
   @Override
   public void setToNoValue() 
   {
      string = null;
   }

   /**
    * Returns true if string is a null pointer
    */
   @Override
   public boolean isNoValue() 
   {
      return string == null;
   }

   /**
    * Sets the string equal to the value string
    */
   @Override
   public void setToValueOf(String valueString) throws Exception 
   {
      string = valueString;
   }

   /**
    * Get the string
    */
   @Override
   public String getValueAsString() 
   {
      return string;
   }

}
