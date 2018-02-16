package org.payn.chsm.processors;

import org.payn.chsm.Holon;
import org.payn.chsm.State;
import org.payn.chsm.values.Value;

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
    * State containing the value to be changed
    */
   protected State state;
   
   /**
    * Set the state associated with this processor
    * 
    * @param state
    *       state associated with this processor 
    * @throws Exception 
    */
   @Override
   public void setState(State state) throws Exception
   {
      this.state = state;
      if (state.getValue() == null)
      {
         createValue();
         value.setToNoValue();
         state.setValue(value);
      }
      else
      {
         setValueFromState();
      }
   }
   
   /**
    * Get the state associated with this processor
    * 
    * @return
    *       state
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
    * Create a dependency on a state in the same holon
    * 
    * @param stateName
    *       name of the needed state
    * @return
    *       reference to the needed state
    * @throws Exception
    *       if error in locating state
    */
   @Override
   public State createDependency(String stateName) throws Exception 
   {
      return createDependency(state.getParentHolon(), stateName);
   }

   @Override
   public State getState(String stateName) throws Exception 
   {
      return getState(state.getParentHolon(), stateName);
   }

   @Override
   public Value createDependencyOnValue(String stateName) throws Exception 
   {
      return createDependency(stateName).getValue();
   }

   @Override
   public State createAbstractDependency(String stateName) throws Exception 
   {
      return createDependency(getResourceName() + stateName);
   }

   @Override
   public State createDependency(Holon holon, String stateName) throws Exception 
   {
      State neededState = getState(holon, stateName);
      controller.createDependency(this, neededState);
      return neededState;
   }

   @Override
   public State getState(Holon holon, String stateName) throws Exception 
   {
      State neededState = holon.getState(
            state.getBehavior().getResource().getStateName(stateName)
            );
      if (neededState == null)
      {
         throw new Exception(String.format("Processor %s requesting dependency on invalid " +
                  "state %s in holon %s.",
               toString(),
               stateName,
               holon.getName()
               ));
      }
      return neededState;
   }

   @Override
   public Value createDependencyOnValue(Holon holon, String stateName) throws Exception
   {
      return createDependency(holon, stateName).getValue();
   }

   @Override
   public State createAbstractDependency(Holon holon, String stateName) throws Exception 
   {
      return createDependency(holon, getResourceName() + stateName);
   }

   /**
    * Get the processor name, based on the associated state name
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

   @Override
   public String getStateName(String stateName) 
   {
      return state.getBehavior().getResource().getStateName(stateName);
   }

}
