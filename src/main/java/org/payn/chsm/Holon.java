package org.payn.chsm;

import java.util.HashMap;

import org.payn.chsm.values.ValueStateMap;

/**
 * A collection of states representing an compositional layer
 * of a hierarchical state machine
 * 
 * @author robpayn
 *
 */
public interface Holon extends State {
   
   /**
    * Add a state to the holon composition
    * 
    * @param state
    *       state object
    * @throws Exception
    */
   public abstract void addState(State state) throws Exception;

   /**
    * Get a reference to the state variable with the provided name
    * 
    * @param stateName
    *       name of the state variable to find
    * @return
    *       state variable with the provided name, 
    *       null if state variable with provided name does not exist in holon
    */
   public abstract State getState(String stateName);
   
   /**
    * Get the behavior map for the holon
    * 
    * @return
    *       map of behaviors tracked by the holon
    */
   public abstract HashMap<String, Behavior> getBehaviorMap();
   
   /**
    * Add an installed behavior
    * 
    * @param behavior
    */
   public abstract void addInstalledBehavior(Behavior behavior);

   /**
    * Determine if the behavior is installed in the holon
    * 
    * @param behavior 
    *       beavhior to check for installation
    * @return
    *       true if behavior is installed, false otherwise
    */
   public abstract boolean isBehahiorInstalled(Behavior behavior);

   /**
    * Track the processor for a given state
    * 
    * @param state
    * @throws Exception
    */
   public abstract void trackProcessor(State state) throws Exception;

   @Override
   public abstract ValueStateMap getValue();

}
