package org.payn.chsm.io.xml;

import org.payn.chsm.io.xml.ElementHelperLoader;
import org.w3c.dom.Element;

/**
 * Element containing configuration for a logger
 * 
 * @author robpayn
 *
 */
public class ElementLogger extends ElementHelperLoader {

   /**
    * Tag name for the element
    */
   public static final String TAG_NAME = "logger";

   /**
    * Constructor
    * 
    * @param element
    */
   public ElementLogger(Element element) 
   {
      super(element);
   }

}
