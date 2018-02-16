package org.payn.chsm.finitediff;

import java.util.HashMap;

import org.payn.chsm.Holon;
import org.payn.chsm.HolonStateValue;
import org.payn.chsm.State;
import org.payn.chsm.finitediff.processors.interfaces.UpdaterCore;
import org.payn.chsm.io.exceptions.ExceptionStorageCollision;
import org.payn.chsm.resources.Resource;

/**
 * Extension of a basic Holon for finite difference
 * state machines.  Provides additional function to 
 * track how the core state of the holon is
 * stored.
 * 
 * @author robpayn
 *
 */
public class HolonFiniteDiff extends HolonStateValue {

   /**
    * Map of states that represent the core state of the cell
    */
   protected HashMap<Resource, State> coreMap;
   
   /**
    * Constructs a new instance with the provided name and
    * parent holon
    * 
    * @param name
    * @param parentHolon
    * @throws Exception
    */
   public HolonFiniteDiff(String name, Holon parentHolon)
         throws Exception 
   {
      super(name, parentHolon);
      this.coreMap = new HashMap<Resource, State>();
   }

   /**
    * Get the core state associated with the provided resource
    * 
    * @param resource
    *       the resource described by the state
    * @return
    *       storage state associated with the resource
    */
   public State getCore(Resource resource) 
   {
      return coreMap.get(resource);
   }
   
   /**
    * A finite difference holon needs to track any core states
    * being installed for a resource
    * 
    * @throws ExceptionStorageCollision
    *       if a core state is installed for a resource for which 
    *       there is already a core state installed
    */
   @Override
   public void trackProcessor(State state) 
         throws ExceptionStorageCollision 
   {
      if (state.isProcessorType(UpdaterCore.class))
      {
         Resource resource = state.getBehavior().getResource();
         if (coreMap.containsKey(resource))
         {
            throw new ExceptionStorageCollision(
                  state, this, resource);
         }
         coreMap.put(resource, state);
      }
   }

}
