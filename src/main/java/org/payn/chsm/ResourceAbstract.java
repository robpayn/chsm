package org.payn.chsm;

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
   public ClassLoader getClassLoader() 
   {
      return getClass().getClassLoader();
   }

   @Override
   public Behavior getBehavior(String fullBehaviorName) throws Exception
   {
      Behavior behavior = behaviorMap.get(fullBehaviorName);
      if (behavior == null)
      {
         String url = getBehaviorClassPath(fullBehaviorName);
         if (url == null)
            throw new Exception(String.format(
                  "Behavior %s could not be found for resource %s.",
                  fullBehaviorName,
                  name
                  ));
         behavior = (Behavior)loadClass(url).newInstance();
         behavior.setResource(this);
         behavior.initialize(fullBehaviorName);
         behaviorMap.put(behavior.getName(), behavior);
      }
      return behavior;
   }

   @Override
   public Class<?> loadClass(String classpath) throws Exception 
   {
      return getClassLoader().loadClass(classpath);
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

}
