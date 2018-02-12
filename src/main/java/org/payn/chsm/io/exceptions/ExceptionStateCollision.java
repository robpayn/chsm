package org.payn.chsm.io.exceptions;

import org.payn.chsm.Holon;
import org.payn.chsm.State;

@SuppressWarnings("serial")
public class ExceptionStateCollision extends Exception {
   
   public static String MESSAGE = "State name collision:"
         + "state name %s in holon %s between behaviors %s and %s.";
   
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
