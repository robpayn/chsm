package org.payn.chsm.io.exceptions;

import org.payn.chsm.State;

/**
 * Exception for a missing initial value
 * 
 * @author v78h241
 *
 */
@SuppressWarnings("serial")
public class ExceptionMissingInitialValue extends Exception {

   /**
    * Error message
    */
   public static final String MESSAGE = "Missing initial value: "
         + "%s must be assigned an initial value in holon %s";
   
   /**
    * Constructor for a missing initial value exception.
    * 
    * @param state
    *       State that is missing an initial value
    */
   public ExceptionMissingInitialValue (State state) 
   {
      super(String.format(
            MESSAGE,
            state.getName(),
            state.getParentHolon().getName()
            ));   
   }
   
}
