package org.payn.chsm.io.initialize;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.payn.chsm.State;
import org.payn.chsm.io.SnapshotTable;
import org.payn.chsm.processors.ProcessorDouble;
import org.payn.chsm.values.ValueString;

/**
 * Manages tables of initial conditions.
 * 
 * @author v78h241
 *
 */
public class InitialConditionTable {

   /**
    * Name of the state for the path to the initial condition table
    */
   public static final String NAME_INITIAL_CONDITION_PATH = "InitialConditionPath";

   /**
    * Name of the state for the delimiter for the initial condition table
    */
   public static final String NAME_INITIAL_CONDITION_DELIMITER = "InitialConditionDelimiter";

   /**
    * Map of interpolators to ensure only one instance for each file
    */
   private static HashMap<File, InitialConditionTable> tableMap  = null;

   /**
    * Get an instance of an initial condition table for a basic model setup
    * 
    * @param processor
    * @return
    *       initial condition table
    * @throws Exception
    */
   public static InitialConditionTable getInstance(ProcessorDouble processor) throws Exception 
   {
      ValueString initPath = null;
      ValueString initDelimiter = null;
      try
      {
         initPath = (ValueString)processor.createDependencyOnValue(
               InitialConditionTable.NAME_INITIAL_CONDITION_PATH
               );
         initDelimiter = (ValueString)processor.createDependencyOnValue(
               InitialConditionTable.NAME_INITIAL_CONDITION_DELIMITER
               );
      }
      catch (Exception e)
      {
         return null;
      }
      return InitialConditionTable.getInstance(
            new File(initPath.string), 
            initDelimiter.string
            );
   }
   
   /**
    * Get an instance of the the initial condition table for the path selected
    * 
    * @param controller
    * @param pathToFile
    * @param delimiter
    * @return
    *       reference to initial condition table
    * @throws Exception
    */
   public static InitialConditionTable getInstance(File pathToFile, 
         String delimiter) throws Exception
   {
      InitialConditionTable table;
      if (tableMap == null || !tableMap.containsKey(pathToFile))
      {
         if (tableMap == null)
         {
            tableMap = new HashMap<File, InitialConditionTable>();
         }
         table = new InitialConditionTable(pathToFile, SnapshotTable.getDelimiter(delimiter));
         tableMap.put(pathToFile, table);
      }
      else
      {
         table = tableMap.get(pathToFile);
      }
      return table;
   }

   /**
    * Map of states and their values
    */
   private LinkedHashMap<String, HashMap<String, String>> stateMap;

   /**
    * The file object with the initial value table
    */
   private File pathToFile;

   /**
    * Construct a new instance for the provided path
    * 
    * @param pathToFile
    * @param delimiter
    * @throws Exception
    */
   private InitialConditionTable(File pathToFile, String delimiter) throws Exception
   {
      this.pathToFile = pathToFile;
      BufferedReader reader = new BufferedReader(new FileReader(pathToFile));
      String[] line = reader.readLine().split(delimiter);
      stateMap = new LinkedHashMap<String, HashMap<String, String>>();
      for (int column = 1; column < line.length; column++)
      {
         stateMap.put(line[column], new LinkedHashMap<String, String>());
      }
      String[] headers = stateMap.keySet().toArray(new String[0]);
      while(reader.ready())
      {
         line = reader.readLine().split(delimiter);
         for (int column = 1; column < line.length; column++)
         {
            stateMap.get(headers[column - 1]).put(line[0], line[column]);
         }
      }
      reader.close();

   }

   /**
    * Find the initial condition of the provided state.
    * 
    * This method assumes fully qualified state and holon names
    * are provided in the initial condition table.
    * 
    * @param state
    * @return
    *       initial value for the state
    * @throws Exception
    */
   public String find(State state) throws Exception 
   {
      return find(
            state.getBehavior().getName() + "." + state.getName(), 
            state.getParentHolon().toString()
            );
   }

   /**
    * Find a state's initial value from the table
    * 
    * @param stateName
    * @param holonName
    * @return
    *       initial value of the state
    * @throws Exception
    */
   public String find(String stateName, String holonName) throws Exception
   {
      if (stateMap.containsKey(stateName))
      {
         return stateMap.get(stateName).get(holonName);
      }
      else
      {
         throw new Exception(String.format(
               "Header for state %s cannot be found in file %s",
               stateName,
               pathToFile.getAbsolutePath()
               ));
      }
   }

}
