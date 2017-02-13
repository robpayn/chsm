package org.payn.chsm;

import java.io.File;
import java.util.HashMap;

import org.payn.chsm.processors.ControllerHolon;
import org.payn.chsm.values.ValueDouble;

/**
 * Abstract class with generic code for creating model builders
 * 
 * @author v78h241
 * 
 * @param <HT> 
 *
 */
public abstract class ModelBuilder<HT extends Holon> {

   /**
    * Create a hashmap of key value pairs from the command line arguments.
    * Delimiter between the key and value is expected to be an equals sign.
    * 
    * @param args
    *       Array of arguments
    * @return
    *       Hashmap of key value pairs
    * @throws Exception
    *       If error in key value format
    */
   public static HashMap<String,String> createArgMap(String[] args) throws Exception
   {
      // Build the argument map
      HashMap<String,String> argMap = new HashMap<String,String>();
      for (int i = 0; i < args.length; i++)
      {
         String[] arg = args[i].split("=");
         if (arg.length != 2)
         {
            throw new Exception(String.format(
                  "'%s' is a malformed command line argument.",
                  args[i]
                  ));
         }
         argMap.put(arg[0], arg[1]);
      }
      return argMap;
   }

   /**
    * Map of supported aliases for initial value types
    */
   public static final HashMap<String,Class<? extends Value>> SUPPORTED_INIT_VALTYPES = 
         new HashMap<String,Class<? extends Value>>();
   static
   {
      SUPPORTED_INIT_VALTYPES.put(ValueDouble.ALIAS, ValueDouble.class);
   }
   
   /**
    * The resource map used to create the factory
    */
   protected HashMap<String, Resource> resourceMap;

   /**
    * The holon containing the model
    */
   protected HT holon;
   
   /**
    * Set the holon that will be the subject of the build
    * 
    * @param holon
    */
   public void setHolon(HT holon) 
   {
      this.holon = holon;
   }
   
   /**
    * Get the matrix being built
    * 
    * @return
    *       matrix
    */
   public HT getHolon()
   {
      return holon;
   }

   /**
    * The cell network controller created by the factory
    */
   protected ControllerHolon controller;
   
   /**
    * Setter
    * 
    * @param controller
    *       Holon controller for the model being built
    */
   public void setController(ControllerHolon controller)
   {
      this.controller = controller;
   }
   
   /**
    * Getter
    * 
    * @return
    *       Holon controller configured by this builder
    */
   public ControllerHolon getController()
   {
      return controller;
   }

   /**
    * Loader for this builder
    */
   protected ModelLoader<?> loader;
   
   /**
    * Setter for the loader for this builder
    * 
    * @param loader
    *       MatrixBuilderLoader for this builder
    */
   public void setLoader(ModelLoader<?> loader)
   {
      this.loader = loader;
   }
   
   /**
    * Getter
    * 
    * @return
    *       MatrixBuilderLoader that loaded this builder
    */
   public ModelLoader<?> getLoader()
   {
      return loader;
   }
   
   /**
    * Working directory for the builder
    */
   protected File workingDir;
   
   /**
    * The working directory for the builder
    * 
    * @param workingDir
    */
   public void setWorkingDirectory(File workingDir) 
   {
      this.workingDir = workingDir;
   }
   
   /**
    * Map of arguments for locating build configuration information
    */
   protected HashMap<String,String> argMap;
   
   /**
    * Setter for the argument map
    * 
    * @param argMap
    *       argument map
    */
   public void setArgMap(HashMap<String,String> argMap)
   {
      this.argMap = argMap;
   }
   
   /**
    * Previous time in milliseconds
    */
   protected long previousTime;
   
   /**
    * The logger manager to be used for logging
    */
   protected LoggerManager loggerManager;
   
   /**
    * Set the logger manager
    * 
    * @param loggerManager
    *       logger manager to be used for logging
    */
   public void setLogger(LoggerManager loggerManager)
   {
      this.loggerManager = loggerManager;
   }
   
   /**
    * Get the logger manager used for logging
    * 
    * @return
    *       logger manager
    */
   public LoggerManager getLogger() 
   {
      return loggerManager;
   }

   
   /**
    * Basic constructor
    */
   public ModelBuilder()
   {
      resourceMap = new HashMap<String, Resource>();
      startTimer();
   }
   
   /**
    * Start the timer
    */
   public void startTimer()
   {
      previousTime = System.currentTimeMillis();
   }

   /**
    * Initialize a value
    * 
    * @param holon
    *       holon where the state variable to be initialized resides
    * @param behavior
    *       behavior for the state variable
    * @param stateVarName
    *       name of the state variable
    * @param stringValue
    *       string representation of the value for the variable
    * @param typeAlias 
    *       alias for the type to be added
    * @throws Exception
    *       if error in setting initial value
    */
   public void initializeValue(Holon holon, Behavior behavior, String stateVarName, 
         String stringValue, String typeAlias) throws Exception 
   {
      State stateVar = holon.getState(stateVarName);
      if (stateVar == null)
      {
         stateVar = new StateVariable(stateVarName, behavior, holon);
         Value value = behavior.createValueForReqState(stateVarName);
         if (typeAlias != null && !typeAlias.isEmpty())
         {
            Class<? extends Value> valueClass = SUPPORTED_INIT_VALTYPES.get(typeAlias);
            if (valueClass != null)
            {
               if (value == null)
               {
                  value = valueClass.newInstance();
               }
               else
               {
                  if (!value.getClass().equals(valueClass))
                  {
                     throw new Exception(String.format(
                           "State variable %s in holon %s cannot be created from an initial value " +
                                 "because type %s in configuration does not match %s requred by behavior.",
                           stateVar.getName(),
                           holon.getName(),
                           value.getClass().getSimpleName(),
                           valueClass.getSimpleName()
                           ));
                  }
               }
            }
         }
         if (value == null)
         {
            throw new Exception(String.format(
                  "State variable %s in holon %s cannot be created from an initial value " +
                        "because it is not required by behavior %s.",
                  stateVar.getName(),
                  holon.getName(),
                  behavior.getName()
                  ));
         }
         stateVar.setValue(value);
      }
      stateVar.getValue().setToValueOf(stringValue);
   }

   /**
    * Add a behavior in the specified holon
    * 
    * @param holon
    *       holon to which the behavior is added
    * @param behavior
    *       behavior to add
    * @throws Exception
    *       if error in adding behavior
    */
   protected void addBehavior(Holon holon, Behavior behavior) throws Exception 
   {
      behavior.createBehaviorProcessors(holon, controller);
   }

   /**
    * Get the behavior with the given name from the resource with the given name
    * 
    * @param resourceName
    * @param behaviorName
    * @return
    * @throws Exception
    */
   protected Behavior getBehaviorFromResource(String resourceName, String behaviorName) throws Exception 
   {
      Resource resource = resourceMap.get(resourceName);
      Behavior behavior;
      try
      {
         behavior = resource.getBehavior(behaviorName);
      }
      catch(Exception e)
      {
         throw new Exception("Error loading behavior " + behaviorName + " from resource " + resourceName, e);
      }
      return behavior;
   }

   /**
    * Add a resource to the resource map
    * 
    * @param resource
    *       Resource object
    */
   public void addResource(Resource resource) 
   {
      resourceMap.put(resource.getName(), resource);
   }
   
   /**
    * Build the model
    * 
    * @throws Exception
    *       if error in building the model
    */
   public abstract void build() throws Exception;

}