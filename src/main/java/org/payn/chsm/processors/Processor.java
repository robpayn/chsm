package org.payn.chsm.processors;

import org.payn.chsm.Holon;
import org.payn.chsm.State;
import org.payn.chsm.values.Value;

/**
 * Interface for a processor that changes a Value for a State
 * 
 * @author robpayn
 * @see State
 * @see Value
 * 
 */
public interface Processor {

   /**
    * Set the state containing the value to be changed
    * 
    * @param state
    *       state with the value to be changed
    * @throws Exception 
    */
   public abstract void setState(State state) throws Exception;

   /**
    * Get the state with the value to be changed
    * 
    * @return
    *       reference to the state
    */
   public abstract State getState();

   /**
    * Set the controller that controls this processor
    * 
    * @param controller
    *       controlling processor
    */
   public abstract void setController(Controller controller);
   
   /**
    * Getter
    * 
    * @return 
    *       controller
    */
   public abstract Controller getController();

   /**
    * Create the value to be changed by the processor
    * 
    * @throws Exception
    *       if error in instantiating value
    */
   public abstract void createValue() throws Exception;

   /**
    * Set the value based on the value for the associated state
    * 
    * @throws Exception
    *       if error in setting the value
    */
   public abstract void setValueFromState() throws Exception;
   
   /**
    * Get a unique name for the processor
    * 
    * @return
    *       name of processor
    */
   @Override
   public abstract String toString();

   /**
    * Create a dependency on the processor associated with the named state
    * 
    * @param stateName
    *       name of the state on which this processor is dependent
    * @return
    *       reference to the needed state 
    * @throws Exception
    *       if error in finding the state 
    */
   State createDependency(String stateName) throws Exception;

   /**
    * Create a dependency on the processor associated with the named state
    * 
    * @param stateName
    *       name of the state on which this processor is dependent
    * @return
    *       reference to the value of the needed state 
    * @throws Exception
    *       if error in finding the state 
    */
   Value createDependencyOnValue(String stateName) throws Exception;

   /**
    * Create a dependency on a state in the same resource
    * 
    * @param stateName
    *       name of state upon which this processor is dependent
    * @return
    *       reference to the needed state
    * @throws Exception 
    *       if error in finding the needed state
    */
   public abstract State createAbstractDependency(String stateName) throws Exception;

   /**
    * Create a dependency on the processor associated with the named state in the named holon
    * 
    * @param holon 
    *       holon containing the needed processor
    * @param stateName
    *       name of the state on which this processor is dependent
    * @return
    *       reference to the needed state
    * @throws Exception
    *       if error in finding the state
    */
   State createDependency(Holon holon, String stateName)
         throws Exception;

   /**
    * Create a dependency on the processor associated with the named state in the named holon
    * 
    * @param holon 
    *       holon containing the needed processor
    * @param stateName
    *       name of the state on which this processor is dependent
    * @return
    *       reference to the value of the needed state
    * @throws Exception
    *       if error in finding the state
    */
   Value createDependencyOnValue(Holon holon, String stateName)
         throws Exception;

   /**
    * Create a dependency on the named state in the same resource
    * and in the named holon
    * 
    * @param holon 
    *       holon containing the needed processor
    * @param stateName
    *       name of the state on which this processor is dependent
    * @return
    *       reference to the needed state
    * @throws Exception
    *       if error in finding the state
    */
   public abstract State createAbstractDependency(Holon holon, String stateName)
         throws Exception;

   /**
    * Get a reference to the value being changed
    * 
    * @return
    *       reference to value
    */
   public abstract Value getValue();

   /**
    * Get the name of the resource for the behavior of the associated state
    * 
    * @return
    *       resource name
    */
   public abstract String getResourceName();

   /**
    * Get the state name for the resource for the processor 
    * 
    * @param stateName
    * @return
    *       state name
    */
   String getStateName(String stateName);

   /**
    * Get a state in the holon containing the processor
    * 
    * @param stateName
    * @return
    *       state
    * @throws Exception 
    */
   State getState(String stateName) throws Exception;

   /**
    * Get a state in the provided holon
    * 
    * @param holon
    * @param stateName
    * @return
    *       state
    * @throws Exception 
    */
   State getState(Holon holon, String stateName) throws Exception;
   
}
