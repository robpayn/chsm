package org.payn.chsm.io.exceptions;

import org.payn.chsm.HolonFiniteDiff;
import org.payn.chsm.State;
import org.payn.chsm.resources.Resource;

@SuppressWarnings("serial")
public class ExceptionStorageCollision extends Exception {
   
   public static String MESSAGE = "Cannot add storage state %s:"
         + " storage state %s for resource %s"
         + " already configured for holon %s.";
   
   public ExceptionStorageCollision(State state,  
         HolonFiniteDiff holon, Resource resource)
   {
      super(String.format(
            MESSAGE, 
            state.getName(), 
            holon.getStore(resource).toString(), 
            resource.getName(), 
            holon.getName()
            ));
   }

}
