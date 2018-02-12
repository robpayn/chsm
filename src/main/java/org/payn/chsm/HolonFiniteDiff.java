package org.payn.chsm;

import java.util.HashMap;

import org.payn.chsm.io.exceptions.ExceptionStorageCollision;
import org.payn.chsm.processors.finitedifference.interfaces.UpdaterStore;
import org.payn.chsm.resources.Resource;

/**
 * Extension of a basic Holon for finite difference
 * state machines.  Provides additional function to 
 * track how the fundamental state of the holon is
 * stored.
 * 
 * @author robpayn
 *
 */
public class HolonFiniteDiff extends HolonBasic {

   /**
    * Map of state variables that store the state of the cell
    */
   protected HashMap<Resource, State> storeMap;
   
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
      this.storeMap = new HashMap<Resource, State>();
   }

   /**
    * Get the storage state associated with the provided resource
    * 
    * @param resource
    *       the resource described by the state
    * @return
    *       storage state associated with the resource
    */
   public State getStore(Resource resource) 
   {
      return storeMap.get(resource);
   }
   
   @Override
   public void trackProcessor(State state) throws Exception 
   {
      if (UpdaterStore.class.isInstance(state.getProcessor()))
      {
         Resource resource = state.getBehavior().getResource();
         if (storeMap.containsKey(resource))
         {
            throw new ExceptionStorageCollision(
                  state, this, resource);
         }
         storeMap.put(resource, state);
      }
   }

}
