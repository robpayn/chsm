package org.payn.chsm;

/**
 * Abstract implementation of a state variable, 
 * a fundamental data unit for a continuous hierarchical state machine
 * 
 * @author robpayn
 *
 */
public class StateVariable implements State {

   /**
    * Name of the state
    */
   protected String name;
   
   /**
    * Getter for the state name
    * 
    * @return
    *       name of the state
    */
   @Override
   public String getName() 
   {
      return name;
   }
   
   /**
    * Behavior tracked by the state
    */
   protected Behavior behavior = null;

   /**
    * Get the behavior
    * 
    * @return
    *       behavior tracked by this state variable
    */
   @Override
   public Behavior getBehavior() 
   {
      return behavior;
   }

   /**
    * Value of the state
    */
   protected Value value = null;
   
   /**
    * Setter for the value
    * 
    * @param value
    *       value to be associated with the state variable
    */
   @Override
   public void setValue(Value value) 
   {
      this.value = value;
   }

   /**
    * Getter for the value
    * 
    * @return
    *       value of the state variable
    */
   @Override
   public Value getValue() 
   {
      return value;
   }

   /**
    * Containing holon
    */
   protected Holon parentHolon;
   
   /**
    * Set the parent holon
    * 
    * @param parentHolon
    *       parent holon for this state variable
    */
   @Override
   public void setParentHolon(Holon parentHolon)
   {
      this.parentHolon = parentHolon;
   }

   /**
    * Get the parent holon for this state variable
    * 
    * @return
    *       parent holon
    */
   @Override
   public Holon getParentHolon() 
   {
      return parentHolon;
   }
   
   /**
    * Processor for the state (null if static state)
    */
   protected Processor processor;
   
   /**
    * Setter for the processor.  
    */
   @Override
   public void setProcessor(Processor processor) throws Exception
   {
      this.processor = processor;
      if (processor != null)
      {
         processor.setState(this);
      }
   }
   
   /**
    * Getter for the processor
    */
   @Override
   public Processor getProcessor()
   {
      return processor;
   }
   
   /**
    * Raw constructor
    * 
    * @param name
    *       Name of the state variable
    */
   protected StateVariable(String name)
   {
      this.name = name;
   }
   
   /**
    * Create a new state variable with the given name and behavior
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
    * Construct a new state variable in the provided holon
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
      holon.addStateVariable(this);
   }
   
   /**
    * Create a new state variable with the given name and behavior, and add
    * it to the given holon
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
    * Construct a new state variable with the provided processor and insert into the provided holon
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
      holon.addStateVariable(this);
   }
   
   /**
    * Unique string for the state
    */
   @Override
   public String toString()
   {
      return createUniqueName();
   }

   /**
    * Creates a period-delimited unique name based on hierarchy of holon names
    * 
    * @return 
    *       name
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
   
   /**
    * Determines if this state is required by the associated behavior
    * 
    * @return 
    *       true if required, false otherwise
    */
   @Override
   public boolean isRequired()
   {
      return (behavior.isStateRequired(this));
   }
   
   /**
    * Determines if state is dynamic during runtime
    */
   @Override
   public boolean isDynamic()
   {
      return processor != null;
   }

}
