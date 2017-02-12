package org.payn.chsm;

/**
 * Interface to a state in a composite hierarchy state machine
 * 
 * @author robpayn
 *
 */
public interface State {

   /**
    * Get the behavior
    * 
    * @return
    *       behavior tracked by this state variable
    */
   public abstract Behavior getBehavior();

   /**
    * Getter for the state name
    * 
    * @return
    *       name of the state
    */
   public abstract String getName();

   /**
    * Set the parent holon
    * 
    * @param parentHolon
    *       parent holon for this state variable
    */
   public abstract void setParentHolon(Holon parentHolon);
   
   /**
    * Setter for the processor
    * 
    * @param processor 
    *       Processor for the state
    *       
    * @throws Exception
    *       If error in creating the value 
    */
   public abstract void setProcessor(Processor processor) throws Exception;
   
   /**
    * Getter for the processor
    * 
    * @return 
    *       Processor associated with the state; null if static state
    */
   public abstract Processor getProcessor();

   /**
    * Setter for the value
    * 
    * @param value
    *       value to be associated with the state variable
    */
   public abstract void setValue(Value value);

   /**
    * Getter for the value
    * 
    * @return
    *       value of the state variable
    */
   public abstract Value getValue();

   /**
    * Get the parent holon for this state variable
    * 
    * @return
    *       parent holon
    */
   public abstract Holon getParentHolon();

   /**
    * Determines if this state is required by the model
    * 
    * @return 
    *       true if required, false otherwise
    */
   public abstract boolean isRequired();
   
   /**
    * Determines if the state is dynamic during run time
    * 
    * @return
    *       true if dynamic; false otherwise
    */
   public abstract boolean isDynamic();

}
