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
    * Name for the state attribute
    */
   public static final String ATTR_NAME_STATE = "state";
   
   /**
    * Name for the type alias attribute
    */
   public static final String ATTR_NAME_TYPE_ALIAS = "typealias";
   
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
    * Get the name of the state for the initial value
    * 
    * @return
    *       state name
    */
   public String getStateName() 
   {
      return element.getAttribute(ATTR_NAME_STATE);
   }

   /**
    * Set the name of the state
    * 
    * @param stateName
    *       name of state
    */
   public void setStateName(String stateName) 
   {
      setAttribute(ATTR_NAME_STATE, stateName);
   }

   /** 
    * Get the type alias attribute
    * 
    * @return
    *       type alias attribute value
    */
   public String getTypeAlias() 
   {
      return element.getAttribute(ATTR_NAME_TYPE_ALIAS);
   }

   /**
    * Sets the type alias attribute
    * 
    * @param typeAlias
    *       type alias
    */
   public void setTypeAlias(String typeAlias) 
   {
      setAttribute(ATTR_NAME_TYPE_ALIAS, typeAlias);
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
