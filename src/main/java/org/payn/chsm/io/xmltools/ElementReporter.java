package org.payn.chsm.io.xmltools;

import org.w3c.dom.Element;

/**
 * Configuration XML element for outputters
 * 
 * @author robpayn
 *
 */
public class ElementReporter extends ElementHelperLoader {

   /**
    * Tag name for the output element
    */
   public static final String TAG_NAME = "reporter";

   /**
    * Create an output element based on given XML element
    * 
    * @param element
    *       XML element
    */
   public ElementReporter(Element element) 
   {
      super(element);
   }

}
