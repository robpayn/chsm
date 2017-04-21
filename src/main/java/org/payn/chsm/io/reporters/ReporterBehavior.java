package org.payn.chsm.io.reporters;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.payn.chsm.Holon;
import org.payn.chsm.State;
import org.payn.chsm.io.SnapshotTable;

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
   private HashMap<String,SnapshotTable> behaviorMap;
   
   /**
    * Map of the snapshot table indexed by file name
    */
   private LinkedHashMap<String,SnapshotTable> snapshotTableMap;

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
      this.delimiter = SnapshotTable.getDelimiter(delimiter);
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
      behaviorMap = new HashMap<String, SnapshotTable>();
      snapshotTableMap = new LinkedHashMap<String, SnapshotTable>();
      recurseHolons(source);
      for (SnapshotTable snapshot: behaviorMap.values())
      {
         snapshot.openLocationWriter();
      }
   }

   /**
    * Recurse through the holons to find all the behaviors to be outputted.
    * 
    * @param holon
    *       starting holon
    * @throws Exception
    *       if error in setting up the output locations
    */
   private void recurseHolons(Holon holon) throws Exception 
   {
      for (State currentStateVar: holon.getValue().getMap().values())
      {
         if (Holon.class.isInstance(currentStateVar))
         {
            recurseHolons((Holon)currentStateVar);
         }
         else
         {
            String behaviorName = currentStateVar.getBehavior().getName();
            
            // Check for filter status
            HashMap<String, String> filterStateList = null;
            boolean isBehaviorFiltered = true;
            if (filterMap != null)
            {
               filterStateList = filterMap.get(behaviorName);
               isBehaviorFiltered = (filterStateList == null && filterMap.containsKey(behaviorName)) || 
                     (filterStateList != null && filterStateList.size() > 0);
            }
            
            // Get the update snapshot for the behavior if it has already been created
            SnapshotTable updateSnapshot = behaviorMap.get(behaviorName);
            
            // Prepare both update and init snapshots if model is in initialization
            if (iterationValue.n == 0)
            {
               // Prepare the update snapshot if the behavior is in the filter
               if (isBehaviorFiltered)
               {
                  // Create the update snapshot for the behavior if it has not been created
                  // (create the file in "overwrite" mode)
                  if (updateSnapshot == null)
                  {
                     if (filenameMap.containsKey(behaviorName))
                     {
                        String filename = filenameMap.get(behaviorName);
                        if (snapshotTableMap.containsKey(filename))
                        {
                           behaviorMap.put(behaviorName, snapshotTableMap.get(filename));
                        }
                        else
                        {
                           File updateFile = new File(
                                 outputDir.getAbsolutePath() + File.separator + 
                                    filename
                                 );
                           updateSnapshot = new SnapshotTable(updateFile, false, delimiter);
                           behaviorMap.put(behaviorName, updateSnapshot);
                           snapshotTableMap.put(filename, updateSnapshot);
                        }
                     }
                     else
                     {
                        String filename = behaviorName.replaceAll("\\.", "_") + ".txt";
                        File updateFile = new File(
                              outputDir.getAbsolutePath() + File.separator + filename
                              );
                        updateSnapshot = new SnapshotTable(updateFile, false, delimiter);
                        behaviorMap.put(behaviorName, updateSnapshot);
                        snapshotTableMap.put(filename, updateSnapshot);
                     }
                  }
                  
                  // Add the dynamic state to the snapshot if it is in the filter or a filter is not specified for the behavior
                  if (currentStateVar.isDynamic() && 
                        (filterStateList == null || filterStateList.containsKey(currentStateVar.toString())))
                  {
                     String header = null;
                     if (filterStateList == null)
                     {
                        header = currentStateVar.toString();
                     }
                     else
                     {
                        header = filterStateList.get(currentStateVar.toString());
                     }
                     updateSnapshot.addStateVariable(currentStateVar, header);
                  }
               }
               
               // Get the init snapshot for the behavior if it has already been created
               SnapshotTable initSnapshot = behaviorMap.get(behaviorName + "_init");
               
               // Add to the init snapshot of initialization output is active
               if (isInitActive)
               {
                  // Create the init snapshot for the behavior if it has not been created
                  if (initSnapshot == null)
                  {
                     String filename = behaviorName.replaceAll("\\.", "_") + "_init" + ".txt";
                     File file = new File(
                           outputDir.getAbsolutePath() + File.separator + filename
                           );
                     initSnapshot = new SnapshotTable(file, false, delimiter);
                     behaviorMap.put(behaviorName + "_init", initSnapshot);
                     snapshotTableMap.put(filename, initSnapshot);
                  }
                  
                  // Add the state to the init snapshot
                  initSnapshot.addStateVariable(currentStateVar, currentStateVar.getName());
               }
            }
            
            // Prepare the update snapshot if model is in execution and the behavior is in the filter
            else if (isBehaviorFiltered)
            {
               // Create the update snapshot for the behavior if it has not been created
               // (create the file in "append" mode)
               if (updateSnapshot == null)
               {
                  if (filenameMap.containsKey(behaviorName))
                  {
                     String filename = filenameMap.get(behaviorName);
                     if (snapshotTableMap.containsKey(filename))
                     {
                        behaviorMap.put(behaviorName, snapshotTableMap.get(filename));
                     }
                     else
                     {
                        File updateFile = new File(
                              outputDir.getAbsolutePath() + File.separator + 
                                 filename
                              );
                        updateSnapshot = new SnapshotTable(updateFile, true, delimiter);
                        behaviorMap.put(behaviorName, updateSnapshot);
                        snapshotTableMap.put(filename, updateSnapshot);
                     }
                  }
                  else
                  {
                     String filename = behaviorName.replaceAll("\\.", "_") + ".txt";
                     File updateFile = new File(
                           outputDir.getAbsolutePath() + File.separator + filename                              
                           );
                     updateSnapshot = new SnapshotTable(updateFile, true, delimiter);
                     behaviorMap.put(behaviorName, updateSnapshot);
                     snapshotTableMap.put(filename, updateSnapshot);
                  }
               }
               // Add the state to the snapshot if it is in the filter or a filter is not specified for the behavior
               if (currentStateVar.isDynamic() &&
                     (filterStateList == null || filterStateList.containsKey(currentStateVar.toString())))
               {
                  String header = null;
                  if (filterStateList == null)
                  {
                     header = currentStateVar.toString();
                  }
                  else
                  {
                     header = filterStateList.get(currentStateVar.toString());
                  }
                  updateSnapshot.addStateVariable(currentStateVar, header);
               }
            }
         }
      }
   }

   /**
    * Buffer the output to memory
    */
   @Override
   public void bufferOutput() throws Exception 
   {
      for (SnapshotTable snapshot: snapshotTableMap.values())
      {
         snapshot.bufferOutput(iterationValue, timeValue);
      }
   }

   /**
    * Write the output to IO in the background
    */
   @Override
   public void backgroundWrite() throws Exception 
   {
      for (SnapshotTable snapshot: snapshotTableMap.values())
      {
         snapshot.write();
      }
   }

   /**
    * Close the file after the last thread is finished
    */
   @Override
   public void closeWhenFinished() throws Exception 
   {
      for (SnapshotTable snapshot: snapshotTableMap.values())
      {
         snapshot.closeLocationWriter();
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
