package org.payn.chsm;

import java.util.HashMap;

/**
 * Definition of a simulated resource in a composite hierarchy state machine
 * 
 * @author rob payn
 *
 */
public abstract class Resource {
   
   /**
    * The behavior map used to create the factory
    */
   protected HashMap<String, Behavior> behaviorMap;
   
   /**
    * Getter for the behavior map
    * 
    * @return
    *       behavior map
    */
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
    * Setter for the name
    * 
    * @param name
    *       name
    */
   public void initialize(String name) 
   {
      this.name = name;
      addBehaviors();
   }

   /**
    * Getter for the name
    * 
    * @return
    *       name
    */
   public String getName() 
   {
      return name;
   }

    /**
    * Getter for the class loader
    * 
    * @return
    *       class loader
    */
   public ClassLoader getClassLoader() 
   {
      return getClass().getClassLoader();
   }

   /**
    * Raw constructor
    */
   public Resource()
   {
      behaviorClassMap = new HashMap<String, String>();
      behaviorMap = new HashMap<String, Behavior>();
   }
   
   /**
    * Load and create an instance of the named behavior based on the full behavior name
    * 
    * @param fullBehaviorName
    *       name of behavior
    * @return
    *       created behavior
    * @throws Exception
    *       if error in instantiating the behavior
    */
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
   
  /**
    * Load the class at the specified class path
    * 
    * @param classpath
    *       class path identifying the class
    * @return
    *       instantiated class
    * @throws Exception
    *       if error in instantiation
    */
   public Class<?> loadClass(String classpath) throws Exception 
   {
      return getClassLoader().loadClass(classpath);
   }

   /**
    * Get the class path for the named behavior
    * 
    * @param behaviorName
    *       name of behavior
    * @return
    *       class path to the named behavior
    * @throws Exception
    *       if error in getting behavior
    */
   public String getBehaviorClassPath(String behaviorName) throws Exception
   {
      return behaviorClassMap.get(behaviorName);
   }

   /**
    * Add a behavior to the resource
    * 
    * @param behaviorName
    *       name of the behavior
    * @param classPath
    *       class path to the behavior package
    */
   protected void addBehavior(String behaviorName, String classPath)
   {
       behaviorClassMap.put(behaviorName, classPath);
   }

   /**
    * Get the full state name using the base name
    * 
    * @param baseName
    *       base name
    * @return
    *       full state name
    */
   public String getStateName(String baseName) 
   {
      return name + baseName;
   }

   /**
    * Add the behaviors for the resource
    */
   protected abstract void addBehaviors();

}
