package org.payn.chsm;

import java.util.HashMap;

import org.payn.chsm.processors.ControllerHolon;
import org.payn.chsm.resources.Behavior;
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
   void addState(State state) throws Exception;

   /**
    * Get a reference to the state variable with the provided name
    * 
    * @param stateName
    *       name of the state variable to find
    * @return
    *       state variable with the provided name, 
    *       null if state variable with provided name does not exist in holon
    */
   State getState(String stateName);
   
   /**
    * Get the behavior map for the holon
    * 
    * @return
    *       map of behaviors tracked by the holon
    */
   HashMap<String, Behavior> getBehaviorMap();
   
   /**
    * Add an installed behavior
    * 
    * @param behavior
    */
   void addInstalledBehavior(Behavior behavior);

   /**
    * Determine if the behavior is installed in the holon
    * 
    * @param behavior 
    *       behavior to check for installation
    * @return
    *       true if behavior is installed, false otherwise
    */
   boolean isBehahiorInstalled(Behavior behavior);
   
   /**
    * Track the processor for a given state
    * 
    * @param state
    * @throws Exception
    */
   void trackProcessor(State state) throws Exception;

   @Override
   ValueStateMap getValue();
   
   @Override
   ControllerHolon getProcessor();

}
