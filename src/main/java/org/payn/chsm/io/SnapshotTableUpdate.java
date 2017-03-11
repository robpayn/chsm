package org.payn.chsm.io;

import java.io.File;

import org.payn.chsm.State;
import org.payn.chsm.processors.interfaces.Updater;

/**
 * A snapshot table that only adds state variables with processor that implement the updater interface
 * 
 * @author rob payn
 *
 */
public class SnapshotTableUpdate extends SnapshotTable {

   /**
    * Construct a new instance for the provided file and append flag
    * 
    * @param file
    *       file to which output is written
    * @param append
    *       signifies append status for writing (true = append to file, false = overwrite file)
    */
   public SnapshotTableUpdate(File file, boolean append) 
   {
      super(file, append);
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
   public SnapshotTableUpdate(File file, boolean append, String delimiter) 
   {
      super(file, append, delimiter);
   }

   /**
    * Add a state variable to the table.  Only dynamic state variables with processors that
    * implement the updater interface will be added.
    */
   @Override
   public void addStateVariable(State stateVar)
   {
      if (stateVar.isDynamic() &&
            Updater.class.isInstance(stateVar.getProcessor()))
      {
         super.addStateVariable(stateVar);
      }
   }
   
}
