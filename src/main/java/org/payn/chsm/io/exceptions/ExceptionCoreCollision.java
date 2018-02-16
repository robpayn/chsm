package org.payn.chsm.io.exceptions;

import org.payn.chsm.State;
import org.payn.chsm.finitediff.HolonFiniteDiff;
import org.payn.chsm.resources.Resource;

/**
 * Exception for a collision of more than one core state being applied
 * for a given resource in a given holon
 * 
 * @author v78h241
 *
 */
@SuppressWarnings("serial")
public class ExceptionCoreCollision extends Exception {
   
   /**
    * Error message
    */
   public static String MESSAGE = "Cannot add core state %s:"
         + " core state %s for resource %s"
         + " already configured for holon %s.";
   
   /**
    * Create a new core state collision exception
    * 
    * @param state
    *       state creating the collision
    * @param holon
    *       holon in which the collision is occurring
    * @param resource
    *       resource creating the collision
    */
   public ExceptionCoreCollision(State state,  
         HolonFiniteDiff holon, Resource resource)
   {
      super(String.format(
            MESSAGE, 
            state.getName(), 
            holon.getCore(resource).toString(), 
            resource.getName(), 
            holon.getName()
            ));
   }

}
