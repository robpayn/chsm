package org.payn.chsm;

import java.util.HashMap;
import java.util.Map.Entry;

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
       this.reqStateMap = new HashMap<String, Class<? extends Value>>();
   }

   @Override
   public void initialize(String name) 
   {
      this.simpleName = name;
      this.name = resource.getName() + "." + name;
      addProcessors();
      addRequiredStates();
   }

   @Override
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

   @Override
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

   @Override
   public void addProcessor(String name, 
         Class<? extends Processor> processorClass, Class<? extends Value> valueClass)
   {
      processorMap.put(name, processorClass);
      reqStateMap.put(name, valueClass);
   }

   @Override
   public void addAbstractProcessor(String name,
         Class<? extends Processor> processorClass, Class<? extends Value> valueClass) 
   {
      addProcessor(resource.getName() + name, processorClass, valueClass);
   }

   @Override
   public void addRequiredState(String name, 
         Class<? extends Value> valueClass)
   {
       reqStateMap.put(name, valueClass);
   }

   @Override
   public void addAbstractRequiredState(String stateName,
         Class<? extends Value> valueClass) 
   {
      addRequiredState(resource.getName() + stateName, valueClass);
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
   public boolean isStateRequired(State state) 
   {
      return (processorMap != null && 
            processorMap.containsKey(name)) || 
            (reqStateMap != null && 
                  reqStateMap.containsKey(name));
   }

   /**
    * Add the processors to be installed in this behavior
    */
   protected abstract void addProcessors();

   /**
    * Add the states required by this behavior
    */
   protected abstract void addRequiredStates();

}
