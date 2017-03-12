package org.payn.chsm;

/**
 * Interface for a state in a composite hierarchy state machine
 * 
 * @author robpayn
 *
 */
public interface State {

   /**
    * Get the behavior tracked by this state
    * 
    * @return
    *       behavior object
    */
   public abstract Behavior getBehavior();

   /**
    * Get the name of the state
    * 
    * @return
    *       name string object
    */
   public abstract String getName();

   /**
    * Set the parent holon that is composed by this state
    * 
    * @param parentHolon
    *       parent holon object
    */
   public abstract void setParentHolon(Holon parentHolon);
   
   /**
    * Get the parent holon for this state
    * 
    * @return
    *       parent holon
    */
   public abstract Holon getParentHolon();

   /**
    * Set the processor that alters this state
    * 
    * @param processor 
    *       processor object
    *       
    * @throws Exception
    *       if error in creating the value for the state 
    */
   public abstract void setProcessor(Processor processor) throws Exception;
   
   /**
    * Get the processor that alters this state
    * 
    * @return 
    *       processor object, null if state is not dynamic
    */
   public abstract Processor getProcessor();

   /**
    * Set the value for this state
    * 
    * @param value
    *       value object
    */
   public abstract void setValue(Value value);

   /**
    * Get the value of this state
    * 
    * @return
    *       value object
    */
   public abstract Value getValue();

   /**
    * Determines if this state is required by the behavior
    * 
    * @return 
    *       true if required, false otherwise
    */
   public abstract boolean isRegistered();
   
   /**
    * Determines if the state is dynamic during run time
    * 
    * @return
    *       true if dynamic; false otherwise
    */
   public abstract boolean isDynamic();

}
