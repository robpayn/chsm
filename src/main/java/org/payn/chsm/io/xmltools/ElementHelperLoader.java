package org.payn.chsm.io.xmltools;

import java.io.File;

import org.w3c.dom.Element;

/**
 * Helper for elements that dynamically load a class
 * 
 * @author robpayn
 *
 */
public class ElementHelperLoader extends ElementHelper {

   /**
    * Create the helper element based on the provided element
    * 
    * @param element
    *       element
    */
   public ElementHelperLoader(Element element) 
   {
      super(element);
   }

   /**
    * Get the file location relative to the provided root path
    * 
    * @param pathRoot
    *       root path 
    * @return
    *       file representing the location
    */
   public File getFile(String pathRoot) 
   {
      return new File(pathRoot + element.getAttribute("path"));
   }

   /**
    * Get the class path 
    * 
    * @return
    *       class path 
    */
   public String getClassPath() 
   {
      return element.getAttribute("classpath");
   }

   /**
    * Get the path attribute
    * 
    * @return
    *       value of path attribute
    */
   public String getPath() 
   {
      return element.getAttribute("path");
   }

}
