package org.payn.chsm.io.xmltools;

import org.w3c.dom.Element;


/**
 * XML element for an initial value
 * 
 * @author robpayn
 *
 */
public class ElementInitValue extends ElementHelper {

   /**
    * Tag name for the element
    */
   public static final String TAG_NAME = "initval";
   
   /**
    * Create a new initial value element based on the provided XML element
    * 
    * @param element
    *       XML element
    */
   public ElementInitValue(Element element) 
   {
      super(element);
   }

   /**
    * Get the name of the state variable for the initial value
    * 
    * @return
    *       state variable name
    */
   public String getStateVariableName() 
   {
      return element.getAttribute("statevar");
   }

   /**
    * Set the name of the state variable
    * 
    * @param stateVariableName
    *       name of state variable
    */
   public void setStateVariableName(String stateVariableName) 
   {
      setAttribute("statevar", stateVariableName);
   }

   /** 
    * Get the type alias attribute
    * 
    * @return
    *       type alias attribute value
    */
   public String getTypeAlias() 
   {
      return element.getAttribute("typealias");
   }

   /**
    * Sets the type alias attribute
    * 
    * @param typeAlias
    *       type alias
    */
   public void setTypeAlias(String typeAlias) 
   {
      setAttribute("typealias", typeAlias);
   }

   /** 
    * Get the value string
    * 
    * @return
    *       value represented as a string
    */
   public String getValue() 
   {
      return getText();
   }

   /**
    * Set the value string
    * 
    * @param value
    *       value represented as a string
    */
   public void setValue(String value) 
   {
      setText(value);
   }

}
