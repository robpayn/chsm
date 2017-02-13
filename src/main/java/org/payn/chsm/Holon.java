package org.payn.chsm;

import java.util.HashMap;
import java.util.HashSet;

import org.payn.chsm.processors.ControllerHolon;
import org.payn.chsm.resources.statespace.ResourceStateSpace;
import org.payn.chsm.values.ValueStateMap;

/**
 * A collection of states representing an compositional layer
 * of a hierarchical state machine
 * 
 * @author robpayn
 *
 */
public abstract class Holon extends StateVariable {
   
   /**
    * Map of all behaviors tracked by the holon
    */
   protected HashMap<String, Behavior> behaviorMap;
   
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
   public Holon(String name, Holon parentHolon) throws Exception 
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
   
   /**
    * Add a state to the holon composition
    * 
    * @param state
    *       state object
    * @throws Exception
    */
   public void addState(State state) throws Exception
   {
      if (getValue() == null)
      {
         setValue(new ValueStateMap());
      }
      if (getValue().hasStateName(state.getName()))
      {
         throw new Exception(String.format(
               "State name collision for %s in holon %s between behaviors %s and %s.", 
               state.getName(), 
               name,
               getValue().getState(state.getName()).getBehavior().getName(),
               state.getBehavior().getName()
               ));
      }
      getValue().addState(state);
      state.setParentHolon(this);
      
      Behavior behavior = state.getBehavior();
      if (!behaviorMap.containsKey(behavior.getName()))
      {
         behaviorMap.put(behavior.getName(), behavior);
      }
      
      if (state.isDynamic())
      {
         trackProcessor(state);
      }
   }

   /**
    * Get a reference to the state variable with the provided name
    * 
    * @param stateName
    *       name of the state variable to find
    * @return
    *       state variable with the provided name, 
    *       null if state variable with provided name does not exist in holon
    */
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
   
   /**
    * Get the value as a state variable map
    */
   @Override
   public ValueStateMap getValue()
   {
      return (ValueStateMap)value;
   }

   /**
    * Get the behavior map for the holon
    * 
    * @return
    *       map of behaviors tracked by the holon
    */
   public HashMap<String, Behavior> getBehaviorMap()
   {
      return behaviorMap;
   }
   
   /**
    * Add an installed behavior
    * 
    * @param behavior
    */
   public void addInstalledBehavior(Behavior behavior) 
   {
      installedBehaviorMap.add(behavior.getName());
   }

   /**
    * Determine if the behavior is installed in the holon
    * 
    * @param behavior 
    *       beavhior to check for installation
    * @return
    *       true if behavior is installed, false otherwise
    */
   public boolean isBehahiorInstalled(Behavior behavior) 
   {
      return installedBehaviorMap.contains(behavior.getName());
   }

   /**
    * Track the processor for a given state
    * 
    * @param state
    * @throws Exception
    */
   public abstract void trackProcessor(State state) throws Exception;

}
