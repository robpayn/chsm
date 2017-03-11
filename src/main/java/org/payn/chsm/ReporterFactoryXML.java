package org.payn.chsm;

import org.payn.chsm.io.xmltools.ElementReporter;

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
