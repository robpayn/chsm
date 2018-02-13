package org.payn.chsm.io.exceptions;

import org.payn.chsm.Holon;
import org.payn.chsm.State;

/**
 * Exception for a state name collision in a holon
 * 
 * @author v78h241
 *
 */
@SuppressWarnings("serial")
public class ExceptionStateCollision extends Exception {
   
   /**
    * Message for error
    */
   public static String MESSAGE = "State name collision:"
         + "state name %s in holon %s between behaviors %s and %s.";
   
   /**
    * Construct a new state name collision exception
    * 
    * @param state
    *       existing state with which collision is occurring
    * @param holon
    *       holon where collision is occurring
    */
   public ExceptionStateCollision(State state, Holon holon)
   {
      super(String.format(
            MESSAGE, 
            state.getName(), 
            holon.toString(), 
            holon.getValue().getState(state.getName()).getBehavior().getName(), 
            state.getBehavior().getName()
            ));
   }

}
