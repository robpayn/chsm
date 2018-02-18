package org.payn.chsm.io.reporters;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.payn.chsm.Holon;
import org.payn.chsm.State;
import org.payn.chsm.io.SynopticTimeSeries;

/**
 * File system outputter that generates one file for each behavior.
 * 
 * @author rob payn
 * 
 */
public class ReporterBehavior extends ReporterSingleThread {
   
   /**
    * Default delimiter for columns
    */
   public static final String DEFAULT_DELIMITER = " ";

   /**
    * Map of the behaviors to be outputted
    */
   private HashMap<String,SynopticTimeSeries> behaviorMap;
   
   /**
    * Map of the time series table indexed by file name
    */
   private LinkedHashMap<String,SynopticTimeSeries> timeSeriesMap;

   /**
    * Delimiter for text files
    */
   private String delimiter = DEFAULT_DELIMITER;

   /**
    * Setter for the delimiter
    * 
    * @param delimiter
    *       string for delimiter
    */
   public void setDelimiter(String delimiter) 
   {
      this.delimiter = SynopticTimeSeries.getDelimiter(delimiter);
   }
   
   /**
    * Filter map of behavior and state names.
    * 
    * Hashmap keyed by behavior name and with values that are array lists of states
    */
   private LinkedHashMap<String, HashMap<String, String>> filterMap = null;

   /**
    * Flag for tracking if initialization output is active
    */
   private boolean isInitActive;

   /**
    * Map of filenames for filtered behaviors
    */
   private LinkedHashMap<String, String> filenameMap;
   
   /**
    * Construct a new instance with the provided working directory and
    * argument map
    * 
    * @param workingDir
    * @param argMap
    */
   public ReporterBehavior(File workingDir, HashMap<String, String> argMap) 
   {
      super(workingDir, argMap);
      filenameMap = new LinkedHashMap<String, String>();
   }

   /**
    * Open the file location for writing.
    */
   @Override
   public void openLocation() throws Exception 
   {
      behaviorMap = new HashMap<String, SynopticTimeSeries>();
      timeSeriesMap = new LinkedHashMap<String, SynopticTimeSeries>();
      recurseHolons(source);
      for (SynopticTimeSeries timeSeries: behaviorMap.values())
      {
         timeSeries.openLocationWriter();
      }
   }

   /**
    * Recurse through the holons to find all the behaviors to be reported.
    * 
    * @param holon
    *       starting holon
    * @throws Exception
    *       if error in setting up the output locations
    */
   private void recurseHolons(Holon holon) throws Exception 
   {
      for (State currentState: holon.getValue().getMap().values())
      {
         if (Holon.class.isInstance(currentState))
         {
            recurseHolons((Holon)currentState);
         }
         else
         {
            String behaviorName = currentState.getBehavior().getName();
            
            // Check for filter status
            HashMap<String, String> filterStateList = null;
            boolean isBehaviorFiltered = true;
            if (filterMap != null)
            {
               filterStateList = filterMap.get(behaviorName);
               isBehaviorFiltered = (filterStateList == null && filterMap.containsKey(behaviorName)) || 
                     (filterStateList != null && filterStateList.size() > 0);
            }
            
            // Get the update time series for the behavior if it has already been created
            SynopticTimeSeries updateTimeSeries = behaviorMap.get(behaviorName);
            
            // Prepare both update and init time series if model is in initialization
            if (iterationValue.n == 0)
            {
               // Prepare the update time series if the behavior is in the filter
               if (isBehaviorFiltered)
               {
                  configureUpdateTimeSeries(updateTimeSeries, behaviorName, 
                        filterStateList, currentState, false);
               }
               
               // Get the init synoptic for the behavior if it has already been created
               SynopticTimeSeries initSynoptic = behaviorMap.get(behaviorName + "_init");
               
               // Add to the init synoptic of initialization output is active
               if (isInitActive)
               {
                  // Create the init synoptic for the behavior if it has not been created
                  if (initSynoptic == null)
                  {
                     String filename = behaviorName.replaceAll("\\.", "_") 
                           + "_init" + ".txt";
                     File file = new File(
                           outputDir.getAbsolutePath() + File.separator + filename
                           );
                     initSynoptic = new SynopticTimeSeries(file, false, delimiter);
                     behaviorMap.put(behaviorName + "_init", initSynoptic);
                     timeSeriesMap.put(filename, initSynoptic);
                  }
                  
                  // Add the state to the init time series
                  initSynoptic.addState(currentState, currentState.getName());
               }
            }
            
            // Prepare the update time series if model is in execution 
            // and the behavior is in the filter
            else if (isBehaviorFiltered)
            {
               configureUpdateTimeSeries(updateTimeSeries, behaviorName, 
                     filterStateList, currentState, true);
            }
         }
      }
   }

   /**
    * Configure a time series table for the update output
    * 
    * @param updateTimeSeries
    *       update time series table
    * @param behaviorName
    *       name of the behavior for the state
    * @param filterStateList
    *       list of state filters
    * @param state
    *       state to be added to output
    * @param append
    *       true to append data to the table (if it exists), false to overwrite
    */
   private void configureUpdateTimeSeries(SynopticTimeSeries updateTimeSeries,
         String behaviorName, HashMap<String, String> filterStateList,
         State state, boolean append) 
   {
      // Create the update time series for the behavior if it has not been created
      // (create the file in "overwrite" mode)
      if (updateTimeSeries == null)
      {
         if (filenameMap.containsKey(behaviorName))
         {
            String filename = filenameMap.get(behaviorName);
            if (timeSeriesMap.containsKey(filename))
            {
               updateTimeSeries = timeSeriesMap.get(filename);
               behaviorMap.put(behaviorName, updateTimeSeries);
            }
            else
            {
               File updateFile = new File(
                     outputDir.getAbsolutePath() + File.separator + 
                        filename
                     );
               updateTimeSeries = new SynopticTimeSeries(updateFile, append, delimiter);
               behaviorMap.put(behaviorName, updateTimeSeries);
               timeSeriesMap.put(filename, updateTimeSeries);
            }
         }
         else
         {
            String filename = behaviorName.replaceAll("\\.", "_") + ".txt";
            File updateFile = new File(
                  outputDir.getAbsolutePath() + File.separator + filename
                  );
            updateTimeSeries = new SynopticTimeSeries(updateFile, append, delimiter);
            behaviorMap.put(behaviorName, updateTimeSeries);
            timeSeriesMap.put(filename, updateTimeSeries);
         }
      }
      
      // Add the dynamic state to the time series if it is in the filter 
      // or a filter is not specified for the behavior
      if (!state.isStatic() && 
            (filterStateList == null || filterStateList.containsKey(state.toString())))
      {
         String header = null;
         if (filterStateList == null)
         {
            header = state.toString();
         }
         else
         {
            header = filterStateList.get(state.toString());
         }
         updateTimeSeries.addState(state, header);
      }
   }

   /**
    * Buffer the output to memory
    */
   @Override
   public void bufferOutput() throws Exception 
   {
      for (SynopticTimeSeries timeSeries: timeSeriesMap.values())
      {
         timeSeries.bufferOutput(iterationValue, timeValue);
      }
   }

   /**
    * Write the output to IO in the background
    */
   @Override
   public void backgroundWrite() throws Exception 
   {
      for (SynopticTimeSeries timeSeries: timeSeriesMap.values())
      {
         timeSeries.write();
      }
   }

   /**
    * Close the file after the last thread is finished
    */
   @Override
   public void closeWhenFinished() throws Exception 
   {
      for (SynopticTimeSeries timeSeries: timeSeriesMap.values())
      {
         timeSeries.closeLocationWriter();
      }
   }
   
   /**
    * Add a behavior filter to the filter map
    * 
    * @param behaviorName
    *       name of behavior to filter
    * @param stateMap
    *       list of specific states to filter (can be null to include all states for behavior)
    */
   public void addBehaviorFilter(String behaviorName, HashMap<String, String> stateMap)
   {
      if (filterMap == null)
      {
         filterMap = new LinkedHashMap<String, HashMap<String, String>>();
      }
      filterMap.put(behaviorName, stateMap);
   }

   /**
    * Set the flag to indicate if initialization output should be generated
    * 
    * @param isInitActive
    *       true if initialization output should be generated, false otherwise
    */
   public void setInitActive(boolean isInitActive) 
   {
      this.isInitActive = isInitActive;
   }

   /**
    * Add a filename for a behavior
    * 
    * @param behaviorName
    *       name of the behavior
    * @param fileName
    *       name of the file for the behavior output
    */
   public void addBehaviorFilename(String behaviorName, String fileName) 
   {
      filenameMap.put(behaviorName, fileName);
   }

}
