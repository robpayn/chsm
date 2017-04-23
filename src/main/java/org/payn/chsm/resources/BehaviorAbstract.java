package org.payn.chsm.resources;

import java.util.HashMap;
import java.util.Map.Entry;

import org.payn.chsm.Holon;
import org.payn.chsm.State;
import org.payn.chsm.StateVariable;
import org.payn.chsm.processors.Controller;
import org.payn.chsm.processors.Processor;
import org.payn.chsm.values.Value;

/**
 * Abstract implementation of Behavior interface
 * 
 * @author robpayn
 *
 */
public abstract class BehaviorAbstract implements Behavior{

   /**
    * Name of the behavior
    */
   private String name;
   
   @Override
   public String getName()
   {
      return name;
   }

   /**
    * Simple name of the behavior (without resource prefix)
    */
   private String simpleName;
   
   @Override
   public String getSimpleName()
   {
      return simpleName;
   }

   /**
    * The resource characterized by this behavior
    */
   protected Resource resource;

   @Override
   public void setResource(Resource resource)
   {
      this.resource = resource;
   }

   @Override
   public Resource getResource()
   {
      return resource;
   }

   @Override
   public boolean hasResource()
   {
      return resource != null;
   }
   
   /**
    * Map of processor to install with the behavior
    */
   protected HashMap<String, Class<? extends Processor>> processorMap;
   
   /**
    * Map of registered states
    */
   private HashMap<String, Class<? extends Value>> registeredStateMap;
   
   /**
    * Text that provided the file system path for loading the behavior
    */
   public String fileSystemPath;
   
   @Override
   public void setFileSystemPath(String fileSystemPath)
   {
      this.fileSystemPath = fileSystemPath;
   }
   
   @Override
   public String getFileSystemPath() 
   {
      return fileSystemPath;
   }

   /**
    * Construct a new instance
    */
   public BehaviorAbstract()
   {
       this.processorMap = new HashMap<String, Class<? extends Processor>>();
       this.registeredStateMap = new HashMap<String, Class<? extends Value>>();
   }

   @Override
   public void initialize(String name) 
   {
      this.simpleName = name;
      this.name = resource.getName() + "." + name;
      addProcessors();
      registerStates();
   }

   @Override
   public Value createValueForRegisteredState(String stateName) throws Exception
   {
      if (!isStateRegistered(stateName))
      {
         return null;
      }
      else
      {
         return (Value)registeredStateMap.get(stateName).newInstance();
      }
   }

   @Override
   public void addProcessor(String stateName, 
         Class<? extends Processor> processorClass, Class<? extends Value> valueClass)
   {
      processorMap.put(stateName, processorClass);
      registeredStateMap.put(stateName, valueClass);
   }

   @Override
   public void addProcessorAbstract(String stateName,
         Class<? extends Processor> processorClass, Class<? extends Value> valueClass) 
   {
      addProcessor(resource.getName() + stateName, processorClass, valueClass);
   }

   @Override
   public void registerState(String stateName, Class<? extends Value> valueClass)
   {
      registeredStateMap.put(stateName, valueClass);
   }

   @Override
   public void registerStateAbstract(String stateName,
         Class<? extends Value> valueClass) 
   {
      registerState(resource.getName() + stateName, valueClass);
   }

   @Override
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

   @Override
   public void installProcessor(Holon holon, Controller controller,
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
         holon.trackProcessor(state);
      }
      controller.addProcessor(processor);
   }

   @Override
   public boolean isStateRegistered(String stateName) 
   {
      return registeredStateMap != null && 
            registeredStateMap.containsKey(stateName);
   }
   
   @Override
   public String getAbstractStateName(String stateName)
   {
      return resource.getName() + stateName;
   }

   /**
    * Add the processors to be installed in this behavior
    */
   protected abstract void addProcessors();

   /**
    * Register states without processors for the behavior
    */
   protected abstract void registerStates();

}
