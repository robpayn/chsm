package org.payn.chsm.resources.statespace;

import org.payn.chsm.resources.Behavior;
import org.payn.chsm.resources.ResourceAbstract;

/**
 * State space resource
 * 
 * @author robpayn
 *
 */
public class ResourceStateSpace extends ResourceAbstract {
   
   /**
    * Create a behavior hierarchy in the state space resource
    * 
    * @param name
    *       Name of the behavior
    * @return
    *       Hierarchy behavior
    * @throws Exception
    */
   public static Behavior createBehaviorHierarchy(String name) throws Exception
   {
      ResourceStateSpace resource = new ResourceStateSpace();
      resource.initialize("statespace");
      resource.addBehavior(name, BehaviorHierarchy.class.getCanonicalName());
      return resource.getBehavior(name);
   }

   @Override
   public void addBehaviors() 
   {
   }

}
