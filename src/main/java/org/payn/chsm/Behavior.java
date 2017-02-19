package org.payn.chsm;

/**
 * A collection of state variables that controls a behavior within a holon
 * 
 * @author robpayn
 *
 */
public interface Behavior {
   
   /**
    * Get the name of the behavior
    * 
    * @return
    *       name of the behavior
    */
   public abstract String getName();
   
   /**
    * Getter
    * 
    * @return
    *       simple name of the behavior (without resource prefix)
    */
   public abstract String getSimpleName();
   
   /**
    * Set the resource for the behavior
    * 
    * @param resource
    *       Resource for this behavior
    */
   public abstract void setResource(Resource resource);

   /**
    * Get the resource characterized by this behavior
    * 
    * @return
    *       Resource characterized by this behavior
    */
   public abstract Resource getResource();

   /**
    * Setter for the file system path
    * 
    * @param fileSystemPath
    *       file system path
    */
   public abstract void setFileSystemPath(String fileSystemPath);
   
   /**
    * Get the file system path
    * 
    * @return
    *       file system path
    */
   public abstract String getFileSystemPath();
   
   /**
    * Initialize the behavior
    * 
    * @param name
    *       name of the behavior
    */
   public abstract void initialize(String name);
   
   /**
    * Get the value for a state variable required by the behavior
    * 
    * @param stateName
    *       name of the state variable required
    * @return
    *       value associated with the state variable required
    * @throws Exception
    *       if error in finding the state variable
    */
   public abstract Value createValueForReqState(String stateName) throws Exception;

   /**
    * Determines if a given state name is required
    * 
    * @param stateName
    *       state name to test
    * @return
    *       true if state is required, false otherwise
    */
   public abstract boolean hasReqState(String stateName);

   /**
    * Add a processor class to the processor map
    * 
    * @param name
    *       name of processor
    * @param processorClass
    *       class of the processor
    * @param valueClass
    *       class of the value
    */
   public abstract void addProcessor(String name, 
         Class<? extends Processor> processorClass, Class<? extends Value> valueClass);

   /**
    * Add an abstract processor class to the processor map
    * 
    * Resource name will be appended to name
    * 
    * @param name
    *       name of processor
    * @param processorClass
    *       class of the processor
    * @param valueClass
    *       class of the value
    */
   public abstract void addAbstractProcessor(String name,
         Class<? extends Processor> processorClass, Class<? extends Value> valueClass);
   
   /**
    * Add a required state to the state map
    * 
    * @param name
    *       name of required state
    * @param valueClass
    *       value class for the required state
    */
   public void addRequiredState(String name, 
         Class<? extends Value> valueClass);

   /**
    * Add an abstracted required state
    * 
    * Resource name will be appended to the state name
    * 
    * @param stateName
    *       name of required state
    * @param valueClass
    *       value class for the required state
    */
   public abstract void addAbstractRequiredState(String stateName,
         Class<? extends Value> valueClass);
   
   /**
    * Default implementation for installing a given behavior in a holon
    * 
    * @param holon
    *       holon in which to install the behavior
    * @param controller
    *       controller with which to register the processors
    * @throws Exception
    *       if error in creating processors
    */
   public abstract void createBehaviorProcessors(Holon holon, Controller controller) 
         throws Exception;   
   
   /**
    * Install a processor
    * 
    * @param holon
    *       holon for the relative location of the processor
    * @param controller
    *       controller for the processor
    * @param stateName
    *       name of the state associated with the processor
    * @param processor
    *       processor
    * @throws Exception
    *       if error in creating or installing the processor
    */
   public abstract void installProcessor(Holon holon, Controller controller,
         String stateName, Processor processor) throws Exception;

   /**
    * Determine if a state is required by the behavior, either as a processor or required state
    * 
    * @param state
    *       state to check
    * @return
    *       true if state is required, false otherwise
    */
   public abstract boolean isStateRequired(State state);

}
