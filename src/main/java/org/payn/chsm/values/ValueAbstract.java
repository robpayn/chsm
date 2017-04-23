package org.payn.chsm.values;

/**
 * Abstract implementation of a value
 * 
 * @author robpayn
 *
 */
public abstract class ValueAbstract implements Value {
   
   /**
    * Raw constructor
    */
   public ValueAbstract()
   {
   }

   /**
    * Constructor which sets the value from the string value
    * 
    * @param valueString
    * @throws Exception
    */
   public ValueAbstract(String valueString) throws Exception 
   {
      setToValueOf(valueString);
   }

   /**
    * Get the string representation of the number
    */
   @Override
   public String toString() 
   {
      if (isNoValue())
      {
          return "NoValue";
      }
      else
      {
          return getValueAsString();
      }
   }

}
