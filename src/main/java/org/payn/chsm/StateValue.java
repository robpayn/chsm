package org.payn.chsm;

import org.payn.chsm.processors.Processor;
import org.payn.chsm.resources.Behavior;
import org.payn.chsm.values.Value;

/**
 * <p>Implementation of a state in a composite hierarchy state machine.</p>
 * 
 * <p>A state is a named unit of information in the state machine.
 * This unit of information is considered atomic at its respective
 * hierarchical tier, as defined by its parent holon.</p>
 * 
 * @see org.payn.chsm.Holon
 * 
 * @author robpayn
 *
 */
public class StateValue implements State {

   /**
    * Name of the state
    */
   protected String name;
   
   @Override
   public String getName() 
   {
      return name;
   }
   
   /**
    * Behavior tracked by the state
    */
   protected Behavior behavior = null;

   @Override
   public Behavior getBehavior() 
   {
      return behavior;
   }

   /**
    * Value containing the data for the state
    */
   protected Value value = null;
   
   @Override
   public void setValue(Value value) 
   {
      this.value = value;
   }

   @Override
   public Value getValue() 
   {
      return value;
   }

   /**
    * Holon that is composed of the state
    */
   protected Holon parentHolon;
   
   @Override
   public void setParentHolon(Holon parentHolon)
   {
      this.parentHolon = parentHolon;
   }

   @Override
   public Holon getParentHolon() 
   {
      return parentHolon;
   }
   
   /**
    * Processor that has potential to change the value of the state 
    * (null if state is not dynamic after initialization)
    */
   protected Processor processor = null;
   
   @Override
   public void setProcessor(Processor processor) throws Exception
   {
      this.processor = processor;
      if (processor != null)
      {
         processor.setState(this);
      }
   }
   
   @Override
   public Processor getProcessor()
   {
      return processor;
   }
   
   /**
    * Constructs a new state value with the given name
    * 
    * @param name
    *       Name of the state
    */
   protected StateValue(String name)
   {
      this.name = name;
   }
   
   /**
    * Constructs a new state value with the given name and behavior
    * 
    * @param name
    *       name of the state
    * @param behavior
    *       behavior tracked by the state
    */
   protected StateValue(String name, Behavior behavior)
   {
      this(name);
      this.behavior = behavior;
   }
   
   /**
    * Construct a new state value composing the provided holon
    * 
    * @param name
    *       name of the state value
    * @param behavior
    *       behavior tracked by the state
    * @param holon
    *       holon to contain the state
    * @throws Exception
    *       if error in constructing state value
    */
   public StateValue(String name, Behavior behavior, Holon holon) throws Exception
   {
      this(name, behavior, null, holon);
   }
   
   /**
    * Construct a new state value with the provided processor
    * 
    * @param name
    *       name of the state value
    * @param behavior
    *       behavior of the state
    * @param processor 
    *       processor for this state
    * @throws Exception 
    *       if error in assigning processor
    */
   protected StateValue(String name, Behavior behavior, Processor processor) throws Exception
   {
      this(name, behavior);
      setProcessor(processor);
   }
   
   /**
    * Construct a new state with the provided processor and composing the provided holon
    * 
    * @param name
    *       name of the state
    * @param behavior
    *       behavior of the state
    * @param processor 
    *       processor for this state
    * @param holon 
    *       holon to contain the state value
    * @throws Exception 
    *       if error in assigning processor
    */
   public StateValue(String name, Behavior behavior, Processor processor, Holon holon) throws Exception
   {
      this(name, behavior, processor);
      holon.addState(this);
   }
   
   /**
    * Unique string representing the full state composition hierarchy
    */
   @Override
   public String toString()
   {
      if (parentHolon != null)
      {
         return parentHolon.toString() + "." + name;
      }
      else
      {
         return name;
      }
   }

   @Override
   public boolean isRegistered()
   {
      return (behavior.isStateRegistered(name));
   }
   
   @Override
   public boolean isStatic()
   {
      return processor == null;
   }

   @Override
   public boolean isProcessorType(Class<?> type) 
   {
      return type.isInstance(processor);
   }

}
