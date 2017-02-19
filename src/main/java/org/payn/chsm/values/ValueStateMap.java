package org.payn.chsm.values;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.payn.chsm.State;
import org.payn.chsm.ValueAbstract;

/**
 * State variable that controls a hash map collection of states
 * 
 * @author robpayn
 *
 */
public class ValueStateMap extends ValueAbstract {

   /**
    * Map of state variables
    */
   private HashMap<String, State> stateMap;
   
   /**
    * Raw constructor
    */
   public ValueStateMap() 
   {
      setMap(new LinkedHashMap<String, State>());
   }

   /**
    * Getter for map
    * 
    * @return
    *       map
    */
   public HashMap<String, State> getMap() 
   {
      return stateMap;
   }

   /**
    * Setter for map
    * 
    * @param map
    *       map to be associated with the value
    */
   private void setMap(HashMap<String, State> map) 
   {
      this.stateMap = map;
   }

   /**
    * Cannot be set from a string representation
    */
   @Override
   public void setToValueOf(String valueString) throws Exception 
   {
      throw new Exception("Cannot be created from a string.");
   }

   /**
    * Add a state variable to the map
    * 
    * @param stateVariable
    *       state variable to be added
    */
   public void addState(State stateVariable) 
   {
      stateMap.put(stateVariable.getName(), stateVariable);
   }

   /**
    * Get a state variable from the map
    * 
    * @param stateName
    *       name of the state variable to retrieve
    * @return
    *       reference to the named state variable
    */
   public State getState(String stateName) 
   {
      return stateMap.get(stateName);
   }

   /**
    * Clears the map
    */
   @Override
   public void setToNoValue() 
   {
      stateMap.clear();
   }

   /**
    * Returns true if map is empty
    */
   @Override
   public boolean isNoValue() 
   {
      return stateMap.isEmpty();
   }

   /**
    * Get the string representation of the map
    */
   @Override
   public String getValueAsString() 
   {
      return stateMap.toString();
   }

   /**
    * Check if a state name exists
    * 
    * @param name
    *       name to check
    * @return
    *       true if name exists in state map, false otherwise
    */
   public boolean hasStateName(String name) 
   {
      return stateMap.containsKey(name);
   }
   
}
