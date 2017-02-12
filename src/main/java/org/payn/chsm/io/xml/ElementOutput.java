package org.payn.chsm.io.xml;

import org.w3c.dom.Element;

/**
 * Configuration XML element for outputters
 * 
 * @author robpayn
 *
 */
public class ElementOutput extends ElementHelperLoader {

   /**
    * Tag name for the output element
    */
   public static final String TAG_NAME = "output";

   /**
    * Create an output element based on given XML element
    * 
    * @param element
    *       XML element
    */
   public ElementOutput(Element element) 
   {
      super(element);
   }

}
