package org.payn.chsm.io.file.interpolate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Time;
import java.util.HashMap;

import org.payn.chsm.Controller;
import org.payn.chsm.io.file.OutputHandlerBehavior;
import org.payn.chsm.values.ValueDouble;

/**
 * An interpolator for data in the form of a snapshot table output
 * 
 * @author robpayn
 *
 */
public class InterpolatorSnapshotTable extends Interpolator {
   
   /**
    * Map of interpolators to ensure only one instance for each file
    */
   private static HashMap<File, InterpolatorSnapshotTable> interpolatorMap  = null;

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
   public static InterpolatorSnapshotTable getInstance(
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
         table = new InterpolatorSnapshotTable(path, time, delimiter, interpolationType);
         interpolatorMap.put(path, table);
      }
      else
      {
         table = interpolatorMap.get(path);
         if (!interpolationType.equals(table.getCalculator().getTypeName()))
         {
            throw new Exception("Cannot change the interpolation type on an existing calculator.");
         }
      }
      table.addInterpolatedHeader(header);
      controller.addInputHandler(table);
      return table;
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
    * Map of columns with registered interpolators
    */
   private HashMap<String, Integer> interpolatedColumnMap;
   
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
   private String delimiter = OutputHandlerBehavior.DEFAULT_DELIMITER;
   
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
    * Constructor (private to control unique instantiations for each path)
    * 
    * @param path
    * @param time
    * @param delimiter
    * @param interpolationType
    * @throws Exception
    */
   private InterpolatorSnapshotTable(File path, ValueDouble time, 
         String delimiter, String interpolationType) 
         throws Exception
   {
      this.time = time;
      setCalculator(interpolationType, time);
      if (delimiter != null)
      {
         this.delimiter  = delimiter;
      }
      reader = new BufferedReader(new FileReader(path));
      String[] header = readLine();
      columnMap = new HashMap<String, Integer>();
      interpolatedColumnMap = new HashMap<String, Integer>();
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
      nextLine = null;
      lastLine = null;
      nextTime = -1;
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
    * Add an interpolated header to register it
    * 
    * @param header
    */
   private void addInterpolatedHeader(String header) 
   {
      interpolatedColumnMap.put(header, columnMap.get(header));
   }

   /**
    * Interpolate, including moving to the next line if necessary
    * 
    * @param header
    * @return
    *       interpolated value
    * @throws Exception
    */
   public double interpolate(String header) throws Exception 
   {
      while (nextTime < time.n)
      {
         lastLine = nextLine;
         nextLine = readLine();
         nextTime = Double.valueOf(nextLine[timeIndex]);
         calculator.updateTime(nextTime);
      }
      calculator.setLastValue(Double.valueOf(lastLine[interpolatedColumnMap.get(header)]));
      calculator.setNextValue(Double.valueOf(nextLine[interpolatedColumnMap.get(header)]));
      
      return calculator.calculate();
   }

   @Override
   public void close() throws Exception 
   {
      reader.close();
   }
 
}
