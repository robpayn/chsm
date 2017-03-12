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
   String getName();
   
   /**
    * Getter
    * 
    * @return
    *       simple name of the behavior (without resource prefix)
    */
   String getSimpleName();
   
   /**
    * Set the resource for the behavior
    * 
    * @param resource
    *       Resource for this behavior
    */
   void setResource(Resource resource);

   /**
    * Get the resource characterized by this behavior
    * 
    * @return
    *       Resource characterized by this behavior
    */
   Resource getResource();

   /**
    * Check if the behavior has a resource
    * 
    * @return
    *       true if a resource is defined, false otherwise
    */
   public abstract boolean hasResource();

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
    * Get the value for a state variable registered with the behavior
    * 
    * @param stateName
    *       name of the registered state 
    * @return
    *       value associated with the registered state
    * @throws Exception
    *       if error in finding the state
    */
   public abstract Value createValueForRegisteredState(String stateName) throws Exception;

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
   public abstract void addProcessorAbstract(String name,
         Class<? extends Processor> processorClass, Class<? extends Value> valueClass);
   
   /**
    * Register a state that may (or may not) be added as part of this
    * behavior without a processor
    * 
    * @param name
    *       name of state
    * @param valueClass
    *       value class for the state
    */
   public void registerState(String name, Class<? extends Value> valueClass);

   /**
    * Register an abstracted state
    * 
    * Resource name will be appended to the state name
    * 
    * @param stateName
    *       name of registered state
    * @param valueClass
    *       value class for the registered state
    */
   public abstract void registerStateAbstract(String stateName,
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
    * Determine if a state is registered, 
    * either as a processor or registered state
    * 
    * @param stateName
    *       name of state to check
    * @return
    *       true if state is registered, false otherwise
    */
   public abstract boolean isStateRegistered(String stateName);

   /**
    * Get the state name for abstracted behaviors
    * 
    * @param stateName
    * @return
    *       state name with resource name prefix
    */
   public String getAbstractStateName(String stateName);

}
