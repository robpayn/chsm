package org.payn.chsm.resources.time;

import org.payn.chsm.Resource;

/**
 * Time resource
 * 
 * @author robpayn
 *
 */
public class ResourceTime extends Resource {

   /**
    * Default name for the resource
    */
   public static final String DEFAULT_NAME = "time";
   
   /**
    * Name of the simulation time behavior
    */
   public static final String BEHAVIOR_TIME = "SimulationTime";

   @Override
   protected void addBehaviors() 
   {
      addBehavior(
            BEHAVIOR_TIME,
            BehaviorTime.class.getCanonicalName()
            );
   }

}
