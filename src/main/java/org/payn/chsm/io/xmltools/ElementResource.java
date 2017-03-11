package org.payn.chsm.io.xmltools;

import org.w3c.dom.Element;

/**
 * XML element with information about the resource configuration
 * 
 * @author robpayn
 *
 */
public class ElementResource extends ElementHelperLoader {

   /**
    * Tag name for the resource element
    */
   public static final String TAG_NAME = "resource";

   /**
    * Construct the resource element based on the provided XML element
    * 
    * @param element
    */
   public ElementResource(Element element) 
   {
      super(element);
   }

}
