package org.payn.chsm.values;

import org.payn.chsm.State;

/**
 * Interface for a value of a state variable
 * 
 * @author robert.payn
 * 
 * @see State
 *
 */
public interface Value {
   
   /**
    * Get the string representation of the value
    */
   @Override
   public String toString();

   /**
    * Set the value to a no value state
    */
   public abstract void setToNoValue();
   
   /**
    * Get the validity of the current value state
    * 
    * @return
    *       true - if valid value, false - otherwise
    */
   public abstract boolean isNoValue();
   
   /**
    * Get a string representation of the value
    * 
    * @return 
    *       string representation
    */
   public abstract String getValueAsString();
   
   /**
    * Set the value according to the string representation
    * 
    * @param valueString
    *       string representing the value
    * @throws Exception
    *       if string cannot be interpreted for the implemented value
    */
   public abstract void setToValueOf(String valueString) throws Exception;

}
