package org.payn.chsm;

/**
 * Fundamental implementation of a processor that changes a value of the provided type
 * 
 * @author robpayn
 *
 * @param <VT>
 *      Type of the value
 */
public abstract class ProcessorAbstract<VT extends Value> implements Processor {
   
   /**
    * State variable containing the value to be changed
    */
   protected State state;
   
   /**
    * Set the state variable associated with this processor
    * 
    * @param state
    *       state variable composed 
    * @throws Exception 
    */
   @Override
   public void setState(State state) throws Exception
   {
      this.state = state;
      if (state.getValue() == null)
      {
         createValue();
         state.setValue(value);
      }
      else
      {
         setValueFromState();
      }
   }
   
   /**
    * Get the state variable associated with this processor
    * 
    * @return
    *       state variable
    */
   @Override
   public State getState()
   {
      return state;
   }
   
   /**
    * Value to be changed
    */
   protected VT value;
   
   /**
    * Setter 
    * 
    * @param value
    *       value to be set
    */
   public void setValue(VT value)
   {
      this.value = value;
   }
   
   /**
    * Get the value changed by this processor
    * 
    * @return
    *       value changed by processor
    */
   @Override
   public VT getValue()
   {
      return value;
   }
   
   /**
    * Controller for this processor
    */
   protected Controller controller = null;
   
   /**
    * Set the controller that uses this processor
    * 
    * @param controller
    *       controller for this processor
    */
   @Override
   public void setController(Controller controller)
   {
      this.controller = controller;
   }
   
   /**
    * Getter
    * 
    * @return
    *       controller for this processor
    */
   @Override
   public Controller getController()
   {
      return controller;
   }
   
   /**
    * Create a dependency on a state variable in the same holon
    * 
    * @param stateName
    *       name of the needed state variable
    * @return
    *       reference to the needed state variable
    * @throws Exception
    *       if error in locating state variable
    */
   @Override
   public State createDependency(String stateName) throws Exception 
   {
      return createDependency(state.getParentHolon(), stateName);
   }

   @Override
   public State createAbstractDependency(String stateName) throws Exception 
   {
      return createDependency(getResourceName() + stateName);
   }

   @Override
   public State createDependency(Holon holon, String stateName) throws Exception 
   {
      State state = holon.getState(stateName);
      if (state == null)
      {
         throw new Exception(String.format("Processor %s requesting dependency on invalid " +
                  "state variable %s in holon %s.",
               toString(),
               stateName,
               holon.getName()
               ));
      }
      controller.createDependency(this, state);
      return state;
   }
   
   @Override
   public State createAbstractDependency(Holon holon, String stateName) throws Exception 
   {
      State state = holon.getState(getResourceName() + stateName);
      if (state == null)
      {
         throw new Exception(String.format("Processor %s requesting dependency on invalid " +
                  "state variable %s in holon %s.",
               toString(),
               stateName,
               holon.getName()
               ));
      }
      controller.createDependency(this, state);
      return state;
   }

   /**
    * Get the processor name, based on the associated state variable name
    */
   @Override
   public String toString()
   {
      return "proc_" + state.toString();
   }
   
   @Override
   public String getResourceName()
   {
      return getState().getBehavior().getResource().getName();
   }
   
}
