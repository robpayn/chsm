package org.payn.chsm.resources;

import java.util.HashMap;

/**
 * Abstract implementation of the resource interface
 * 
 * @author robpayn
 *
 */
public abstract class ResourceAbstract implements Resource {

   /**
    * The behavior map used to create the factory
    */
   protected HashMap<String, Behavior> behaviorMap;
   
   @Override
   public HashMap<String, Behavior> getBehaviorMap()
   {
      return behaviorMap;
   }

   /**
    * Map of behavior names
    */
   protected HashMap<String, String> behaviorClassMap;
   
   /**
    * Name of the resource
    */
   protected String name;

   /**
    * Map of alias names for default state names
    */
   private HashMap<String, String> aliasMap;

   @Override
   public String getName() 
   {
      return name;
   }

   /**
    * Raw constructor
    */
   public ResourceAbstract()
   {
      behaviorClassMap = new HashMap<String, String>();
      behaviorMap = new HashMap<String, Behavior>();
   }
   
   @Override
   public void initialize(String name) 
   {
      this.name = name;
      addBehaviors();
   }
   
   @Override
   public Behavior getBehavior(String defaultBehaviorName) throws Exception
   {
       Behavior behavior = behaviorMap.get(defaultBehaviorName);
      if (behavior == null)
      {
         String classpath = getBehaviorClassPath(defaultBehaviorName);
         if (classpath == null)
            throw new Exception(String.format(
                  "Behavior %s could not be found for resource %s.",
                  defaultBehaviorName,
                  name
                  ));
         behavior = (Behavior)loadClass(classpath).getConstructor().newInstance();
         behavior.setResource(this);
         behavior.initialize(defaultBehaviorName);
         behaviorMap.put(behavior.getName(), behavior);
      }
      return behavior;
   }

   @Override
   public Class<?> loadClass(String classpath) throws Exception 
   {
      ClassLoader cl = getClass().getClassLoader();
      return Class.forName(classpath, false, cl);
   }

   @Override
   public String getBehaviorClassPath(String behaviorName) throws Exception
   {
      return behaviorClassMap.get(behaviorName);
   }

   @Override
   public void addBehavior(String behaviorName, String classPath)
   {
       behaviorClassMap.put(behaviorName, classPath);
   }

   @Override
   public String getStateName(String defaultStateName)
   {
      if (aliasMap == null)
      {
         return defaultStateName;
      }
      else
      {
         String alias = aliasMap.get(defaultStateName);
         if (alias == null)
         {
            return defaultStateName;
         }
         else
         {
            return alias;
         }
      }
   }
   
}
