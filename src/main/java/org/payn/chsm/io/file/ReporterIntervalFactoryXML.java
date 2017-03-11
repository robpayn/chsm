package org.payn.chsm.io.file;

import org.payn.chsm.io.ReporterFactoryXML;
import org.payn.chsm.io.xml.ElementReporter;
import org.w3c.dom.Element;

/**
 * Initializer for an interval reporter
 * 
 * @author v78h241
 *
 */
public class ReporterIntervalFactoryXML extends ReporterFactoryXML<ReporterInterval> {
   
   /**
    * Construct the factory with the provided reporter
    * 
    * @param reporterInterval
    * @param config 
    */
   public ReporterIntervalFactoryXML(ReporterInterval reporterInterval, ElementReporter config)
   {
      this.reporter = reporterInterval;
      this.config = config;
   }

   @Override
   public void init() 
   {
      Element intElement = (Element)config.getFirstChildElement("interval");
      String interval = intElement.getAttribute("interval");
      if (interval != null && interval != "")
      {
         ((ReporterInterval)reporter).setInterval(Long.valueOf(interval));
      }
      else
      {
         ((ReporterInterval)reporter).setInterval(0);
      }
      
      new ReporterFileSystemFactoryXML(reporter, config).init();
   }

   @Override
   public ReporterInterval newReporter() throws Exception 
   {
      throw new Exception("Cannot create an abstract interval reporter");
   }

}
