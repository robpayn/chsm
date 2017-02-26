package org.payn.chsm;

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
    * Set the state variable containing the value to be changed
    * 
    * @param state
    *       state variable with the value to be changed
    * @throws Exception 
    */
   public abstract void setState(State state) throws Exception;

   /**
    * Get the state variable with the value to be changed
    * 
    * @return
    *       reference to the state variable
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
    * Create a dependency on the processor associated with the named state variable
    * 
    * @param stateName
    *       name of the state variable on which this processor is dependent
    * @return
    *       reference to the needed state 
    * @throws Exception
    *       if error in finding the state 
    */
   State createDependency(String stateName) throws Exception;

   /**
    * Create a dependency on the processor associated with the named state variable
    * 
    * @param stateName
    *       name of the state variable on which this processor is dependent
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
    *       reference to the needed state variable
    * @throws Exception 
    *       if error in finding the needed state
    */
   public abstract State createAbstractDependency(String stateName) throws Exception;

   /**
    * Create a dependency on the processor associated with the named state variable in the named holon
    * 
    * @param holon 
    *       holon containing the needed processor
    * @param stateName
    *       name of the state variable on which this processor is dependent
    * @return
    *       reference to the needed state variable
    * @throws Exception
    *       if error in finding the state variable
    */
   State createDependency(Holon holon, String stateName)
         throws Exception;

   /**
    * Create a dependency on the processor associated with the named state variable in the named holon
    * 
    * @param holon 
    *       holon containing the needed processor
    * @param stateName
    *       name of the state variable on which this processor is dependent
    * @return
    *       reference to the value of the needed state variable
    * @throws Exception
    *       if error in finding the state variable
    */
   Value createDependencyOnValue(Holon holon, String stateName)
         throws Exception;

   /**
    * Create a dependency on the named state variable in the same resource
    * and in the named holon
    * 
    * @param holon 
    *       holon containing the needed processor
    * @param stateName
    *       name of the state variable on which this processor is dependent
    * @return
    *       reference to the needed state variable
    * @throws Exception
    *       if error in finding the state variable
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

}
