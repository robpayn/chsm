package org.payn.chsm.finitedifference;

import java.util.HashMap;

import org.payn.chsm.Holon;
import org.payn.chsm.HolonBasic;
import org.payn.chsm.Resource;
import org.payn.chsm.State;
import org.payn.chsm.finitedifference.processors.interfaces.UpdaterBaseState;

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
   protected HashMap<Resource, State> baseStateMap;
   
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
      this.baseStateMap = new HashMap<Resource, State>();
   }

   /**
    * Get the base state associated with the provided resource
    * 
    * @param resource
    *       resource
    * @return
    *       base state associated with the resource
    */
   public State getBaseState(Resource resource) 
   {
      return baseStateMap.get(resource);
   }
   
   @Override
   public void trackProcessor(State state) throws Exception 
   {
      if (UpdaterBaseState.class.isInstance(state.getProcessor()))
      {
         Resource resource = state.getBehavior().getResource();
         if (baseStateMap.containsKey(resource))
         {
            throw new Exception(String.format(
                  "Cannot add base state %s: base state %s already configured for holon %s.",
                  state.toString(),
                  baseStateMap.get(resource).toString(),
                  toString()
                  ));
         }
         baseStateMap.put(resource, state);
      }
   }

}
