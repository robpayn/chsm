package org.payn.chsm;

import org.payn.chsm.io.ReporterFactory;

/**
 * Interface for a reporter
 * 
 * @author robpayn
 *
 */
public interface Reporter {

   /**
    * Setter 
    * 
    * @param factory
    *       factory that created the reporter
    */
   public abstract void setFactory(ReporterFactory<?,?> factory);
   
   /**
    * Conditionally write output
    * 
    * @throws Exception
    *       if error in writing output
    */
   public abstract void conditionalWrite() throws Exception;

   /**
    * Open the location for output
    * 
    * @throws Exception
    *       if error in opening io
    */
   public abstract void openLocation() throws Exception;
   
   /**
    * Unconditionally write output to location
    * 
    * @throws Exception
    *       if error in writing to io location
    */
   public abstract void write() throws Exception;

   /**
    * Close the output location
    * 
    * @throws Exception
    *       if error in closing location to io
    */
   public abstract void closeLocation() throws Exception;

   /**
    * Initialize the reporter
    * 
    * @param configObject
    *       Object with configuration information
    * @param source
    *       Source holon for output
    * @throws Exception
    */
   public abstract void initialize(Holon source) throws Exception;

}
