package org.payn.chsm;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * A collection of state variables that controls a behavior within a holon
 * 
 * @author robpayn
 *
 */
public abstract class Behavior {
   
   /**
    * Name of the behavior
    */
   private String name;
   
   /**
    * Get the name of the behavior
    * 
    * @return
    *       name of the behavior
    */
   public String getName()
   {
      return name;
   }
   
   /**
    * Simple name of the behavior (without resource prefix)
    */
   private String simpleName;
   
   /**
    * Getter
    * 
    * @return
    *       simple name of the behavior (without resource prefix)
    */
   public String getSimpleName()
   {
      return simpleName;
   }
   
   /**
    * The resource characterized by this behavior
    */
   protected Resource resource;

   /**
    * Set the resource for the behavior
    * 
    * @param resource
    *       Resource for this behavior
    */
   public void setResource(Resource resource)
   {
      this.resource = resource;
   }

   /**
    * Get the resource characterized by this behavior
    * 
    * @return
    *       Resource characterized by this behavior
    */
   public Resource getResource()
   {
      return resource;
   }

   /**
    * Map of processor to install with the behavior
    */
   protected HashMap<String, Class<? extends Processor>> processorMap;
   
   /**
    * Map of required states
    */
   private HashMap<String, Class<? extends Value>> reqStateMap;
   
   /**
    * Text that provided the file system path for loading the behavior
    */
   public String fileSystemPath;
   
   /**
    * Setter for the file system path
    * 
    * @param fileSystemPath
    *       file system path
    */
   public void setFileSystemPath(String fileSystemPath)
   {
      this.fileSystemPath = fileSystemPath;
   }
   
   /**
    * Get the file system path
    * 
    * @return
    *       file system path
    */
   public String getFileSystemPath() 
   {
      return fileSystemPath;
   }
   
   /**
    * Construct a new instance
    */
   public Behavior()
   {
       this.processorMap = new HashMap<String, Class<? extends Processor>>();
       this.reqStateMap = new HashMap<String, Class<? extends Value>>();
   }

   /**
    * Initialize the behavior
    * 
    * @param name
    *       name of the behavior
    */
   public void initialize(String name) 
   {
      this.simpleName = name;
      this.name = resource.getName() + "." + name;
      addProcessors();
      addRequiredStates();
   }
   
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
   public Value createValueForReqState(String stateName) throws Exception
   {
      if (!hasReqState(stateName))
      {
         return null;
      }
      else
      {
         return (Value)reqStateMap.get(stateName).newInstance();
      }
   }

   /**
    * Determines if a given state name is required
    * 
    * @param stateName
    *       state name to test
    * @return
    *       true if state is required, false otherwise
    */
   public boolean hasReqState(String stateName) 
   {
      if (reqStateMap == null)
      {
         return false;
      }
      else
      {
         return reqStateMap.containsKey(stateName);
      }
   }

   /**
    * Add a processor class to the processor map
    * 
    * @param name
    *       name of processor
    * @param processorClass
    *       processor class
    */
   protected void addProcessor(String name, Class<? extends Processor> processorClass, Class<? extends Value> valueClass)
   {
      String fullName = resource.getName() + name;
      processorMap.put(fullName, processorClass);
      reqStateMap.put(fullName, valueClass);
   }

   /**
    * Add a required state to the state map
    * 
    * @param name
    *       name of required state
    * @param valueClass
    *       value class for the required state
    */
   protected void addRequiredState(String name, Class<? extends Value> valueClass)
   {
       reqStateMap.put(name, valueClass);
   }

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
   public void createBehaviorProcessors(Holon holon, Controller controller) throws Exception 
   {
      if (processorMap == null)
      {
         return;
      }
      for (Entry<String,Class<? extends Processor>> entry: processorMap.entrySet())
      {
         try 
         {
            installProcessor(holon, controller, entry.getKey(), entry.getValue().newInstance());
         }
         catch (Exception e)
         {
            throw new Exception(String.format(
                  "Processor %s from behavior %s could not be found for installation in holon %s.",
                  entry.getValue().getSimpleName(),
                  getName(),
                  holon.getName()
                  ), e);
         }
      }
   }
   
   /**
    * Install a processor
    * 
    * @param holon
    *       holon for the relative location of the processor
    * @param controller
    *       controller for the processor
    * @param stateName
    *       name of the state associated with the processor
    * @param processorClass
    *       class of the processor
    * @throws Exception
    *       if error in creating or installing the processor
    */
   protected void installProcessor(Holon holon, Controller controller,
         String stateName, Processor processor) throws Exception 
   {
      processor.setController(controller);
      State state = holon.getState(stateName);
      if (state == null)
      {
         new StateVariable(stateName, this, processor, holon);
      }
      else
      {
         state.setProcessor(processor);
         holon.addProcessorForState(state);
      }
      controller.addProcessor(processor);
   }

   /**
    * Determine if a state is required by the behavior, either as a processor or required state
    * 
    * @param state
    *       state to check
    * @return
    *       true if state is required, false otherwise
    */
   public boolean isStateRequired(State state) 
   {
      return (processorMap != null && 
            processorMap.containsKey(name)) || 
            (reqStateMap != null && 
                  reqStateMap.containsKey(name));
   }

   /**
    * Add the states required by this behavior
    */
   protected abstract void addRequiredStates();

   /**
    * Add the processors to be installed in this behavior
    */
   protected abstract void addProcessors();

}
