package org.payn.chsm;

/**
 * Simple implementation of a variable state, 
 * an atomic data unit for a continuous hierarchical state machine
 * 
 * @author robpayn
 *
 */
public class StateVariable implements State {

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
    * Value of the state
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
    * Processor that changes the value of the state 
    * (null if state is not dynamic)
    */
   protected Processor processor;
   
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
    * Constructs a new state variable with the given name
    * 
    * @param name
    *       Name of the state variable
    */
   protected StateVariable(String name)
   {
      this.name = name;
   }
   
   /**
    * Constructs a new state variable with the given name and behavior
    * 
    * @param name
    *       name of the state
    * @param behavior
    *       behavior tracked by the state
    */
   protected StateVariable(String name, Behavior behavior)
   {
      this(name);
      this.behavior = behavior;
   }
   
   /**
    * Construct a new state variable composing the provided holon
    * 
    * @param name
    *       name of the state variable
    * @param behavior
    *       behavior tracked by the state
    * @param holon
    *       holon to contain the state variable
    * @throws Exception
    *       if error in constructing state variable
    */
   public StateVariable(String name, Behavior behavior, Holon holon) throws Exception
   {
      this(name, behavior);
      holon.addState(this);
   }
   
   /**
    * Construct a new state variable with the provided processor
    * 
    * @param name
    *       name of the state variable
    * @param behavior
    *       behavior of the state variable
    * @param processor 
    *       processor for this state
    * @throws Exception 
    *       if error in assigning processor
    */
   protected StateVariable(String name, Behavior behavior, Processor processor) throws Exception
   {
      this(name, behavior);
      setProcessor(processor);
   }
   
   /**
    * Construct a new state variable with the provided processor and composing the provided holon
    * 
    * @param name
    *       name of the state variable
    * @param behavior
    *       behavior of the state variable
    * @param processor 
    *       processor for this state
    * @param holon 
    *       holon to contain the state value
    * @throws Exception 
    *       if error in assigning processor
    */
   public StateVariable(String name, Behavior behavior, Processor processor, Holon holon) throws Exception
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
      return createUniqueName();
   }

   /**
    * Creates a period-delimited string representation of the composition hierarchy 
    * 
    * @return 
    *       name string
    */
   private String createUniqueName() 
   {
      String uniqueName = null;
      if (parentHolon != null)
      {
         uniqueName = parentHolon.toString() + "." + name;
      }
      else
      {
         uniqueName = name;
      }
      return uniqueName;
   }
   
   @Override
   public boolean isRequired()
   {
      return (behavior.isStateRequired(this));
   }
   
   @Override
   public boolean isDynamic()
   {
      return processor != null;
   }

}
