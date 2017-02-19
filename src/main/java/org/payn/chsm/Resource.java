package org.payn.chsm;

import java.util.HashMap;

/**
 * Definition of a simulated resource in a composite hierarchy state machine
 * 
 * @author rob payn
 *
 */
public interface Resource {
   
   /**
    * Getter for the behavior map
    * 
    * @return
    *       behavior map
    */
   public abstract HashMap<String, Behavior> getBehaviorMap();
   
   /**
    * Setter for the name
    * 
    * @param name
    *       name
    */
   public abstract void initialize(String name);

   /**
    * Getter for the name
    * 
    * @return
    *       name
    */
   public abstract String getName();

    /**
    * Getter for the class loader
    * 
    * @return
    *       class loader
    */
   public ClassLoader getClassLoader();

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
   public abstract Behavior getBehavior(String fullBehaviorName) throws Exception;
   
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
   public abstract Class<?> loadClass(String classpath) throws Exception;

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
   public abstract String getBehaviorClassPath(String behaviorName) throws Exception;

   /**
    * Add a behavior to the resource
    * 
    * @param behaviorName
    *       name of the behavior
    * @param classPath
    *       class path to the behavior package
    */
   public abstract void addBehavior(String behaviorName, String classPath);
   
   /**
    * Add the behaviors for the resource
    */
   public abstract void addBehaviors();

}
