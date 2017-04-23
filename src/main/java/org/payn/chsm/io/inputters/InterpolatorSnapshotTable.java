package org.payn.chsm.io.inputters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map.Entry;

import org.payn.chsm.Holon;
import org.payn.chsm.io.SnapshotTable;
import org.payn.chsm.io.reporters.ReporterBehavior;
import org.payn.chsm.processors.Controller;
import org.payn.chsm.processors.ProcessorDouble;
import org.payn.chsm.values.ValueDouble;
import org.payn.chsm.values.ValueString;

/**
 * An interpolator for data in the form of a snapshot table output
 * 
 * @author robpayn
 *
 */
public class InterpolatorSnapshotTable implements Inputter {
   
   /**
    * Name of required state for interpolation type
    */
   public static final String NAME_TYPE = "InterpolationType";

   /**
    * Name of required state for path to interpolation file
    */
   public static final String NAME_PATH = "InterpolationPath";

   /**
    * Name of required state for delimiter
    */
   public static final String NAME_DELIMITER = "Delimiter";

   /**
    * Name of the state for the header of the column to interpolate
    */
   public static final String DEFAULT_NAME_HEADER = "InterpolationHeader";

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
      String header;
      try
      {
         header = ((ValueString)processor.createDependency(
               DEFAULT_NAME_HEADER
               ).getValue()).string;
      }
      catch(Exception e)
      {
         header = processor.getState().toString();
      }
      return getInterpolatorInstance(processor, header);
   }
 
   /**
    * Get an instance of an interpolator based on the processor
    * and the given header in the interpolation file
    * 
    * @param processor
    * @param header
    * @return
    *       interpolator
    * @throws Exception
    */
   public static Interpolator getInterpolatorInstance(ProcessorDouble processor,
         String header) throws Exception 
   {
      ValueString pathName = (ValueString)processor.createDependencyOnValue(
            InterpolatorSnapshotTable.NAME_PATH
            );
      ValueString type = (ValueString)processor.createDependencyOnValue(
            InterpolatorSnapshotTable.NAME_TYPE
            );
      ValueString delimiter = (ValueString)processor.createDependencyOnValue(
            InterpolatorSnapshotTable.NAME_DELIMITER
            );
      ValueDouble time = (ValueDouble)((Holon)processor.getController().getState()).getState(
            Time.class.getSimpleName()
            ).getValue();
      return InterpolatorSnapshotTable.getInterpolatorInstance(
            processor.getController(), 
            new File(pathName.string), 
            time, 
            SnapshotTable.getDelimiter(delimiter.string), 
            header, 
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
      String header;
      try
      {
         header = ((ValueString)processor.createAbstractDependency(
               DEFAULT_NAME_HEADER
               ).getValue()).string;
      }
      catch(Exception e)
      {
         header = processor.getState().toString();
      }
      return getInterpolatorInstanceAbstract(processor, header);
   }
   
   /**
    * Get an instance of an interpolator based on the processor
    * and the given header in the interpolation file
    * 
    * @param processor
    * @param header
    * @return
    *       interpolator
    * @throws Exception
    */
   public static Interpolator getInterpolatorInstanceAbstract(
         ProcessorDouble processor, String header) throws Exception
   {
      ValueString pathName = (ValueString)processor.createAbstractDependency(
            InterpolatorSnapshotTable.NAME_PATH
            ).getValue();
      ValueString type = (ValueString)processor.createAbstractDependency(
            InterpolatorSnapshotTable.NAME_TYPE
            ).getValue();
      ValueString delimiter = (ValueString)processor.createAbstractDependency(
            InterpolatorSnapshotTable.NAME_DELIMITER
            ).getValue();
      ValueDouble time = (ValueDouble)((Holon)processor.getController().getState()).getState(
            Time.class.getSimpleName()
            ).getValue();
      return InterpolatorSnapshotTable.getInterpolatorInstance(
            processor.getController(), 
            new File(pathName.string), 
            time, 
            delimiter.string, 
            header, 
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
         controller.addInputter(table);
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
      if (!path.exists())
      {
         throw new Exception(String.format(
               "Interpolation file %s does not exist.", 
               path.getAbsolutePath()
               ));
      }
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
