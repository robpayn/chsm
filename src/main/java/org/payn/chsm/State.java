package org.payn.chsm;

import org.payn.chsm.processors.Processor;
import org.payn.chsm.resources.Behavior;
import org.payn.chsm.values.Value;

/**
 * <p>Interface for a state in a composite hierarchy state machine.</p>
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
public interface State {

   /**
    * Get the behavior characterized by this state
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
    * Set the parent holon that is composed by this state.
    * 
    * @param parentHolon
    *       parent holon object
    */
   public abstract void setParentHolon(Holon parentHolon);
   
   /**
    * Get the parent holon that is composed by this state.
    * 
    * @return
    *       parent holon
    */
   public abstract Holon getParentHolon();

   /**
    * Set the processor that alters this state during run time
    * 
    * @param processor 
    *       processor object
    *       
    * @throws Exception
    *       if error in creating the value for the state 
    */
   public abstract void setProcessor(Processor processor) throws Exception;
   
   /**
    * Get the processor that alters this state during run time
    * 
    * @return 
    *       processor object, null if state is not dynamic
    */
   public abstract Processor getProcessor();

   /**
    * Set the value containing data for this state
    * 
    * @param value
    *       value object
    */
   public abstract void setValue(Value value);

   /**
    * Get the value containing data for this state
    * 
    * @return
    *       value object
    */
   public abstract Value getValue();

   /**
    * Determines if this state is registered by the behavior.
    * 
    * @return 
    *       true if registered, false otherwise
    */
   public abstract boolean isRegistered();
   
   /**
    * Determines if the state has potential to be
    * dynamic during run time.
    * 
    * @return
    *       true if dynamic; false otherwise
    */
   public abstract boolean isStatic();

}
