package org.payn.chsm.io.file.interpolate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map.Entry;

import org.payn.chsm.Controller;
import org.payn.chsm.Holon;
import org.payn.chsm.InputHandler;
import org.payn.chsm.io.file.ReporterBehavior;
import org.payn.chsm.io.file.SnapshotTable;
import org.payn.chsm.processors.ProcessorDouble;
import org.payn.chsm.values.ValueDouble;
import org.payn.chsm.values.ValueString;

/**
 * An interpolator for data in the form of a snapshot table output
 * 
 * @author robpayn
 *
 */
public class InterpolatorSnapshotTable implements InputHandler {
   
   /**
    * Name of required state for interpolation type
    */
   public static final String REQ_STATE_TYPE = "InterpolationType";

   /**
    * Name of required state for path to interpolation file
    */
   public static final String REQ_STATE_PATH = "InterpolationPath";

   /**
    * Name of required state for delimiter
    */
   public static final String REQ_STATE_DELIMITER = "Delimiter";

   /**
    * Map of interpolators to ensure only one instance for each file
    */
   private static HashMap<File, InterpolatorSnapshotTable> interpolatorMap  = null;

   /**
    * Get an instance of an interpolator for a basic model setup
    * 
    * @param processor
    * @return
    *       interpolator
    * @throws Exception
    */
   public static Interpolator getInterpolatorInstance(ProcessorDouble processor) throws Exception 
   {
      ValueString pathName = (ValueString)processor.createDependencyOnValue(
            InterpolatorSnapshotTable.REQ_STATE_PATH
            );
      ValueString type = (ValueString)processor.createDependencyOnValue(
            InterpolatorSnapshotTable.REQ_STATE_TYPE
            );
      ValueString delimiter = (ValueString)processor.createDependencyOnValue(
            InterpolatorSnapshotTable.REQ_STATE_DELIMITER
            );
      ValueDouble time = (ValueDouble)((Holon)processor.getController().getState()).getState(
            Time.class.getSimpleName()
            ).getValue();
      return InterpolatorSnapshotTable.getInterpolatorInstance(
            processor.getController(), 
            new File(pathName.string), 
            time, 
            SnapshotTable.getDelimiter(delimiter.string), 
            processor.getState().toString(), 
            type.toString()
            );
   }
 
   /**
    * Get an instance of an interpolator for a behavior that has been abstracted
    * 
    * @param processor
    * @return
    *       interpolator
    * @throws Exception
    */
   public static Interpolator getInterpolatorInstanceAbstract(ProcessorDouble processor) throws Exception 
   {
      ValueString pathName = (ValueString)processor.createAbstractDependency(
            InterpolatorSnapshotTable.REQ_STATE_PATH
            ).getValue();
      ValueString type = (ValueString)processor.createAbstractDependency(
            InterpolatorSnapshotTable.REQ_STATE_TYPE
            ).getValue();
      ValueString delimiter = (ValueString)processor.createAbstractDependency(
            InterpolatorSnapshotTable.REQ_STATE_DELIMITER
            ).getValue();
      ValueDouble time = (ValueDouble)((Holon)processor.getController().getState()).getState(
            Time.class.getSimpleName()
            ).getValue();
      return InterpolatorSnapshotTable.getInterpolatorInstance(
            processor.getController(), 
            new File(pathName.string), 
            time, 
            delimiter.string, 
            processor.getState().toString(), 
            type.toString()
            );
   }

   /**
    * Get the instance for a given file.
    * 
    * Static definition ensures only one instance of the interpolator per file.
    * 
    * @param controller
    *       controller for the interpolator (ensures clean close after execution)
    * @param path
    *       path for the file with data
    * @param time
    *       time value
    * @param delimiter
    *       delimiter used in the file
    * @param header
    *       header name to be registered when requesting the instance
    * @param interpolationType
    *       type option for calculator
    * @return
    *       interpolator (existing instance for path, or new instance if none have been created for the path)
    * @throws Exception
    *       if error in creating interpolator
    */
   public static Interpolator getInterpolatorInstance(
         Controller controller, File path, ValueDouble time, String delimiter, 
         String header, String interpolationType
         ) throws Exception 
   {
      InterpolatorSnapshotTable table;
      if (interpolatorMap == null || !interpolatorMap.containsKey(path))
      {
         if (interpolatorMap == null)
         {
            interpolatorMap = new HashMap<File, InterpolatorSnapshotTable>();
         }
         table = new InterpolatorSnapshotTable(
               path, 
               time, 
               SnapshotTable.getDelimiter(delimiter)
               );
         controller.addInputHandler(table);
         interpolatorMap.put(path, table);
      }
      else
      {
         table = interpolatorMap.get(path);
      }
      return table.createInterpolator(header, interpolationType, time);
   }

   /**
    * Time value
    */
   private ValueDouble time;
   
   /**
    * Map of columns in the snapshot table
    */
   private HashMap<String, Integer> columnMap;
   
   /**
    * Reader for the file
    */
   private BufferedReader reader;
   
   /**
    * Index of the time column in the file
    */
   private int timeIndex;
   
   /**
    * Delimiter for columns in the file
    */
   private String delimiter = ReporterBehavior.DEFAULT_DELIMITER;
   
   /**
    * The next line of text in the file
    */
   private String[] nextLine;
   
   /**
    * The last line of text in the file
    */
   private String[] lastLine;
   
   /**
    * The next time
    */
   private double nextTime;
   
   /**
    * Map of calculators using this table
    */
   private HashMap<InterpolatorCalculator, Integer> calculatorMap;

   /**
    * The path to the file
    */
   private File path;
   
   /**
    * Constructor (private to control unique instantiations for each path)
    * 
    * @param path
    * @param time
    * @param delimiter
    * @param interpolationType
    * @throws Exception
    */
   private InterpolatorSnapshotTable(File path, ValueDouble time, 
         String delimiter) 
         throws Exception
   {
      this.time = time;
      if (delimiter != null)
      {
         this.delimiter  = delimiter;
      }
      this.path = path;
      reader = new BufferedReader(new FileReader(path));
      String[] header = readLine();
      columnMap = new HashMap<String, Integer>();
      for (int i = 0; i < header.length; i++)
      {
         columnMap.put(header[i], new Integer(i));
      }
      if (!columnMap.containsKey(Time.class.getSimpleName()))
      {
         throw new Exception("Cannot interpet an input table without a time column.");
      }
      else
      {
         timeIndex = columnMap.get(Time.class.getSimpleName());
      }
      calculatorMap = new HashMap<InterpolatorCalculator, Integer>();
      nextLine = null;
      lastLine = null;
      nextTime = -1;
   }

   /**
    * Create a new interpolator for this table
    * 
    * @param header
    * @param interpolationType
    * @param time
    * @return
    * @throws Exception 
    */
   private Interpolator createInterpolator(String header, String interpolationType,
         ValueDouble time) throws Exception 
   {
      Interpolator interp = new Interpolator(this, interpolationType, time);
      if (!columnMap.containsKey(header))
      {
         throw new Exception(String.format(
               "Interpolation table %s does not have the header %s.",
               path,
               header
               ));
      }
      calculatorMap.put(interp.getCalculator(), columnMap.get(header));
      return interp;
   }

   /**
    * Read a line of data separated by delimiter
    * 
    * @return
    *       line of data
    * @throws Exception
    */
   private String[] readLine() throws Exception 
   {
      return reader.readLine().split(delimiter);
   }

   /**
    * Interpolate, including moving to the next line if necessary
    * 
    * @param calculator 
    * @param column 
    * @throws Exception 
    * 
    */
   public void checkTime() throws Exception 
   {
      while (nextTime <= time.n)
      {
         lastLine = nextLine;
         if (reader.ready())
         {
            nextLine = readLine();
            nextTime = Double.valueOf(nextLine[timeIndex]);
         }
         else
         {
            nextLine = lastLine;
            nextTime = Double.MAX_VALUE;
         }
         if (nextTime > time.n)
         {
            for (Entry<InterpolatorCalculator, Integer> entry: calculatorMap.entrySet())
            {
               entry.getKey().updateTime(nextTime);
               entry.getKey().setLastValue(Double.valueOf(lastLine[entry.getValue()]));
               entry.getKey().setNextValue(Double.valueOf(nextLine[entry.getValue()]));
            }
         }
      }
   }

   @Override
   public void close() throws Exception 
   {
      reader.close();
   }

}
