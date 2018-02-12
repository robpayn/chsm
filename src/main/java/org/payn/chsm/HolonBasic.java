package org.payn.chsm;

import java.util.HashMap;
import java.util.HashSet;

import org.payn.chsm.io.exceptions.ExceptionStateCollision;
import org.payn.chsm.processors.ControllerHolon;
import org.payn.chsm.resources.Behavior;
import org.payn.chsm.resources.statespace.ResourceStateSpace;
import org.payn.chsm.values.ValueStateMap;

/**
 * Basic implementation of a holon, which is a state variable with a value 
 * composed of states at a lower tier in the hierarchy.  
 * This relationship provides the foundation of the composition hierarchy.
 * 
 * @author robpayn
 *
 */
public class HolonBasic extends StateVariable implements Holon {

   /**
    * Map of all behaviors tracked by the holon
    */
   protected HashMap<String, Behavior> behaviorMap;
   
   @Override
   public HashMap<String, Behavior> getBehaviorMap()
   {
      return behaviorMap;
   }

   /**
    * Map of behaviors installed in the holon
    */
   protected HashSet<String> installedBehaviorMap;
   
   /**
    * Create a new holon with the provided name and parent holon
    * 
    * @param name
    *       name string for the new holon
    * @param parentHolon 
    *       parent holon that is composed by the new holon
    * @throws Exception 
    */
   public HolonBasic(String name, Holon parentHolon) throws Exception 
   {
      super(name);
      if (parentHolon != null)
      {
         behavior = parentHolon.getBehavior();
         parentHolon.addState(this);
      }
      else
      {
         behavior = ResourceStateSpace.createBehaviorHierarchy(name);
      }
      this.behaviorMap = new HashMap<String, Behavior>();
      this.installedBehaviorMap = new HashSet<String>();
   }
   
   @Override
   public ControllerHolon getProcessor()
   {
      return (ControllerHolon)processor;
   }
   
   @Override
   public ValueStateMap getValue()
   {
      return (ValueStateMap)value;
   }

   @Override
   public void addState(State state) throws Exception
   {
      if (getValue() == null)
      {
         setValue(new ValueStateMap());
      }
      if (getValue().hasStateName(state.getName()))
      {
         throw new ExceptionStateCollision(state, this);
      }
      getValue().addState(state);
      state.setParentHolon(this);
      
      Behavior behavior = state.getBehavior();
      if (!behaviorMap.containsKey(behavior.getName()))
      {
         behaviorMap.put(behavior.getName(), behavior);
      }
      
      if (!state.isStatic())
      {
         trackProcessor(state);
      }
   }

   @Override
   public State getState(String stateName) 
   {
      if (getValue() == null)
      {
         return null;
      }
      else
      {
         return getValue().getState(stateName);
      }
   }

   @Override
   public void addInstalledBehavior(Behavior behavior) 
   {
      installedBehaviorMap.add(behavior.getName());
   }

   @Override
   public boolean isBehahiorInstalled(Behavior behavior) 
   {
      return installedBehaviorMap.contains(behavior.getName());
   }

   @Override
   public void trackProcessor(State state) throws Exception 
   {
      // Processors are not tracked in a basic holon
   }

}
