package org.payn.chsm.resources.time;

import org.payn.chsm.Behavior;
import org.payn.chsm.values.ValueDouble;
import org.payn.chsm.values.ValueLong;

/**
 * Behavior controlling time
 * 
 * @author robpayn
 *
 */
public class BehaviorTime extends Behavior {
   
   /**
    * Parse an integer value of milliseconds into days/hours/minutes/seconds/milliseconds
    * 
    * @param milliseconds
    *       time in milliseconds
    * @return
    *       parsed time string
    */
   public static String parseTimeInMillis(long milliseconds) 
   {
      double seconds = (double)milliseconds / 1000;
      String timeString;
      if (seconds > 59)
      {
         double temp = (double)seconds / 60;
         long minutes = (long)temp;
         seconds = seconds - 60 * minutes;
         timeString = String.format("%5.3f seconds ", seconds);
         if (minutes > 59)
         {
            temp = (double)minutes / 60;
            long hours = (long)temp;
            minutes = minutes - 60 * hours;
            timeString = String.format("%d minutes ", minutes) + timeString;
            if (hours > 23)
            {
               temp = (double)hours / 24;
               long days = (long)temp;
               hours = hours - 24 * days;
               timeString = String.format("%d hours ", hours) + timeString;
               timeString = String.format("%d days ", days) + timeString;
            }
            else
            {
               timeString = String.format("%d hours ", hours) + timeString;
            }
         }
         else
         {
            timeString = String.format("%d minutes ", minutes) + timeString;
         }
      }
      else
      {
         timeString = String.format("%5.3f seconds ", seconds);
      }
      return timeString;
   }

   /**
    * Name of the behavior
    */
   public static String NAME = "SimulationTime";
   
   /**
    * Default name of iteration state
    */
   public static String DEFAULT_ITERATION_NAME = 
         ResourceTime.DEFAULT_NAME + Iteration.class.getSimpleName();

   /**
    * Default name of time state
    */
   public static String DEFAULT_TIME_NAME = 
         ResourceTime.DEFAULT_NAME + Time.class.getSimpleName();

   /**
    * Name of the iteration interval state variable
    */
   public static String ITERATION_INTERVAL = "TimeIterationInterval";
   
   /**
    * Name of the last iteration state variable
    */
   public static String LAST_ITERATION = "LastIteration";

   @Override
   protected void addRequiredStates()
   {
       addRequiredState(ITERATION_INTERVAL, ValueDouble.class);
       addRequiredState(LAST_ITERATION, ValueLong.class);
   }

   @Override
   protected void addProcessors()
   {
       addProcessor(Iteration.class.getSimpleName(), Iteration.class, Iteration.getValueClass());
       addProcessor(Time.class.getSimpleName(), Time.class, Time.getValueClass());
   }

}