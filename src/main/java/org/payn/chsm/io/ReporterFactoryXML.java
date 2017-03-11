package org.payn.chsm.io;

import org.payn.chsm.Reporter;
import org.payn.chsm.io.xml.ElementReporter;

/**
 * Factory that initializes the reporter based on XML configuration data
 * 
 * @author v78h241
 * 
 * @param <HT> 
 *
 */
public abstract class ReporterFactoryXML<HT extends Reporter> 
   extends ReporterFactory<ElementReporter,HT> {
  
}
