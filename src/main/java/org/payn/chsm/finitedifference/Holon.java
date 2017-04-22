package org.payn.chsm.finitedifference;

import org.payn.chsm.Resource;
import org.payn.chsm.State;

/**
 * A holon with a base state for finite difference calculations
 * 
 * @author robpayn
 *
 */
public interface Holon extends org.payn.chsm.Holon {

   /**
    * Get the base state for the provided resource
    * 
    * @param resource
    * 
    * @return
    *       root state
    */
   State getBaseState(Resource resource);

}
