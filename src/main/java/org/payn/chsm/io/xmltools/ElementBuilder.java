package org.payn.chsm.io.xmltools;

import org.payn.chsm.io.xmltools.ElementHelperLoader;
import org.w3c.dom.Element;

/**
 * XML element specifying characteristics of the matrix builder
 * 
 * @author robpayn
 *
 */
public class ElementBuilder extends ElementHelperLoader {

   /**
    * Tag name for the builder element
    */
   public static final String TAG_NAME = "builder";
   
   /**
    * Create a new instance based on the provided XML element
    * 
    * @param element
    */
   public ElementBuilder(Element element) 
   {
      super(element);
   }

   /**
    * Get the XML input element
    * 
    * @param workingDir 
    *       working directory for the input
    * 
    * @return
    *       XML input element
    * @throws Exception  
    */
   public Element getXMLInputElement() throws Exception 
   {
      return getFirstChildElement(ElementXMLInput.TAG_NAME);
   }

}
