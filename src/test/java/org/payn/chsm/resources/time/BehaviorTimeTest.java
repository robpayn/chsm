package org.payn.chsm.resources.time;

import org.payn.chsm.resources.time.BehaviorTimeStepper;

/**
 * Class with methods to test the BehaviorTime class
 * 
 * @author robpayn
 *
 */
public class BehaviorTimeTest {
   
   /**
    * An entry point for direct testing
    * 
    * @param args
    */
   public static void main(String[] args)
   {
      System.out.println(BehaviorTimeStepper.parseTimeInMillis(2*24*60*60*1000 + 5*60*60*1000 + 43*60*1000 + 1*1000 + 34));
   }

}
