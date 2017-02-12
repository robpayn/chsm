package org.payn.chsm.io;

import org.payn.chsm.OutputHandler;
import org.payn.chsm.io.xml.ElementOutput;

/**
 * Factory that initializes the output handler based on XML configuration data
 * 
 * @author v78h241
 * 
 * @param <HT> 
 *
 */
public abstract class OutputHandlerFactoryXML<HT extends OutputHandler> 
   extends OutputHandlerFactory<ElementOutput,HT> {
  
}
