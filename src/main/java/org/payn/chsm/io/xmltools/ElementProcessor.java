package org.payn.chsm.io.xmltools;


import org.w3c.dom.Element;


/**
 * XML element with configuration information for the processor
 * 
 * @author robpayn
 *
 */
public class ElementProcessor extends ElementHelperLoader {

   /**
    * Tag name for the processor element
    */
   public static final String TAG_NAME = "processor";
   
   /**
    * Construct the processor element based on the provided XML element
    * 
    * @param element
    *       XML element
    */
   public ElementProcessor(Element element) 
   {
      super(element);
   }

}
