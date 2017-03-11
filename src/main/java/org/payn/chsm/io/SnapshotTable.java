package org.payn.chsm.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.payn.chsm.State;
import org.payn.chsm.values.ValueDouble;
import org.payn.chsm.values.ValueLong;

/**
 * A file outputter that tracks the values of a provided set of state variables in their
 * respective holons over time.
 * 
 * @author v78h241
 *
 */
public class SnapshotTable {

   /**
    * A map of delimiters
    */
   private static final HashMap<String, String> DELIMITER_MAP;
   static
   {
      DELIMITER_MAP = new HashMap<String, String>(2);
      DELIMITER_MAP.put("space", " ");
      DELIMITER_MAP.put("comma", ",");
   }

   /**
    * Check if a key word is used for a delimiter and return
    * the appropriate delimiter
    * 
    * @param delimiterKeyword
    * @return
    *       delimiter associated with the keyword, the keyword
    *       is return as the delimiter if it is not recognized
    */
   public static String getDelimiter(String delimiterKeyword) 
   {
      String delimiter = DELIMITER_MAP.get(delimiterKeyword);
      if (delimiter == null)
      {
         delimiter = delimiterKeyword;
      }
      return delimiter;
   }

   /**
    * Array of values to track
    */
   private String[] values;
   
   /**
    * Map of the columns in the table and their indeces
    */
   private LinkedHashMap<State, Integer> columns;
   
   /**
    * Number of columns
    */
   private int columnCount = 0;
   
   /**
    * Writer for output
    */
   private BufferedWriter writer;
   
   /**
    * Flag to signify if data should be appended in an existing file 
    * (true = append, false = overwrite)
    */
   private boolean append;

   /**
    * String representation of the iteration number
    */
   private String iteration;

   /**
    * String representation of the time
    */
   private String time;

   /**
    * File to which output is written
    */
   private File file;

   /**
    * Delimiter for columns
    */
   private String delimiter;
   
   /**
    * Raw constructor
    */
   public SnapshotTable()
   {
      columns = new LinkedHashMap<State, Integer>();
   }
   
   /**
    * Constructs an instance with the provided file and append flag
    * 
    * @param file
    *       file to which output is written
    * @param append
    *       flag to signify append status (true = append to file, false = overwrite file)
    */
   public SnapshotTable(File file, boolean append)
   {
      this();
      this.file = file;
      this.append = append;
   }
   
   /**
    * Create a new instance that writes to the provided file and delimiter.
    * 
    * @param file
    *       file to which output is written
    * @param append
    *       flag indicating if output should be appended to the end of the file
    *       (true = append, false = overwrite)
    * @param delimiter
    *       delimiter between columns
    */
   public SnapshotTable(File file, boolean append, String delimiter) 
   {
      this(file, append);
      this.delimiter = delimiter;
   }

   /**
    * Add a state variable to the table
    * 
    * @param stateVar
    *       state variable to be outputted
    */
   public void addStateVariable(State stateVar)
   {
      if (!columns.containsKey(stateVar))
      {
         columns.put(stateVar, columnCount);
         columnCount++;
      }
   }

   /**
    * Open the file location for writing.  Add column headers to file.
    * 
    * @throws Exception
    *       if error in opening the file system location
    */
   public void openLocationWriter() throws Exception 
   {
      writer = new BufferedWriter(new FileWriter(file, append));
      if (!append)
      {
         writer.write(String.format(
               "Iteration%sTime%s",
               delimiter,
               delimiter
               ));
         for (State stateVar: columns.keySet())
         {
            writer.write(stateVar.toString() + delimiter);
         }
         writer.newLine();
      }
   }

   /**
    * Buffer the table output to memory for later writing
    * 
    * @param iteration
    *       value representing the iteration number
    * @param time
    *       value representing the simulation time
    */
   public void bufferOutput(ValueLong iteration, ValueDouble time) 
   {
      this.iteration = iteration.getValueAsString();
      this.time = time.getValueAsString();
      values = new String[columns.size()];
      int j = 0;
      for(State stateVar: columns.keySet())
      {
         values[j] = stateVar.getValue().toString();
         j++;
      }
   }

   /**
    * Write to IO
    * 
    * @throws Exception
    *       if error in writing to file system
    */
   public void write() throws Exception 
   {
      writer.write(iteration + delimiter);
      writer.write(time + delimiter);
      for (int colIndex = 0; colIndex < columnCount; colIndex++)
      {
         writer.write(values[colIndex] + delimiter);
      }
      writer.newLine();
      writer.flush();
   }
   
   /**
    * Close the writer
    * 
    * @throws Exception
    *       if error in closing file system location
    */
   public void closeLocationWriter() throws Exception 
   {
      writer.close();
   }

}
