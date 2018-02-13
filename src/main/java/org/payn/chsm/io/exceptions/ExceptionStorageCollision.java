package org.payn.chsm.io.exceptions;

import org.payn.chsm.HolonFiniteDiff;
import org.payn.chsm.State;
import org.payn.chsm.resources.Resource;

/**
 * Exception for a collision of more than one storage being applied
 * for a given resource in a given holon
 * 
 * @author v78h241
 *
 */
@SuppressWarnings("serial")
public class ExceptionStorageCollision extends Exception {
   
   /**
    * Error message
    */
   public static String MESSAGE = "Cannot add storage state %s:"
         + " storage state %s for resource %s"
         + " already configured for holon %s.";
   
   /**
    * Create a new storage collision exception
    * 
    * @param state
    *       state creating the collision
    * @param holon
    *       holon in which the collision is occuring
    * @param resource
    *       resource creating the collision
    */
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
