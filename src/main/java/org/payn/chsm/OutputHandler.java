package org.payn.chsm;

import org.payn.chsm.io.OutputHandlerFactory;

/**
 * Interface for an output handler
 * 
 * @author robpayn
 *
 */
public interface OutputHandler {

   /**
    * Setter 
    * 
    * @param factory
    *       factory that created the output handler
    */
   public abstract void setFactory(OutputHandlerFactory<?,?> factory);
   
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
    * Initialize the output handler
    * 
    * @param configObject
    *       Object with configuration information
    * @param source
    *       Source holon for output
    * @throws Exception
    */
   public abstract void initialize(Holon source) throws Exception;

}
