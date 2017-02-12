package org.payn.chsm.io.file;

import org.payn.chsm.io.OutputHandlerFactoryXML;
import org.payn.chsm.io.xml.ElementOutput;
import org.w3c.dom.Element;

/**
 * Initializer for an interval output handler
 * 
 * @author v78h241
 *
 */
public class OutputHandlerIntervalFactoryXML extends OutputHandlerFactoryXML<OutputHandlerInterval> {
   
   /**
    * Construct the factory with the provided output handler
    * 
    * @param outputHandler
    * @param config 
    */
   public OutputHandlerIntervalFactoryXML(OutputHandlerInterval outputHandler, ElementOutput config)
   {
      this.outputHandler = outputHandler;
      this.config = config;
   }

   @Override
   public void init() 
   {
      Element intElement = (Element)config.getFirstChildElement("interval");
      String interval = intElement.getAttribute("interval");
      if (interval != null && interval != "")
      {
         ((OutputHandlerInterval)outputHandler).setInterval(Long.valueOf(interval));
      }
      else
      {
         ((OutputHandlerInterval)outputHandler).setInterval(0);
      }
      
      new OutputHandlerFileSystemFactoryXML(outputHandler, config).init();
   }

   @Override
   public OutputHandlerInterval newOutputHandler() throws Exception 
   {
      throw new Exception("Cannot create an abstract interval output handler");
   }

}
