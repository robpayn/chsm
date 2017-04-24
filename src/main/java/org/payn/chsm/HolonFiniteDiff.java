package org.payn.chsm;

import java.util.HashMap;

import org.payn.chsm.processors.finitedifference.interfaces.UpdaterStore;
import org.payn.chsm.resources.Resource;

/**
 * Implementation of the Holon interface for finite difference
 * state machines
 * 
 * @author robpayn
 *
 */
public class HolonFiniteDiff extends HolonBasic implements Holon {

   /**
    * Storage state variables associated with the cell
    */
   protected HashMap<Resource, State> storeMap;
   
   /**
    * Constructs a new instance wtih the provided name and
    * parent holon
    * 
    * @param name
    * @param parentHolon
    * @throws Exception
    */
   public HolonFiniteDiff(String name, org.payn.chsm.Holon parentHolon)
         throws Exception 
   {
      super(name, parentHolon);
      this.storeMap = new HashMap<Resource, State>();
   }

   /**
    * Get the base state associated with the provided resource
    * 
    * @param resource
    *       resource
    * @return
    *       base state associated with the resource
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
            throw new Exception(String.format(
                  "Cannot add base state %s: base state %s already configured for holon %s.",
                  state.toString(),
                  storeMap.get(resource).toString(),
                  toString()
                  ));
         }
         storeMap.put(resource, state);
      }
   }

}
