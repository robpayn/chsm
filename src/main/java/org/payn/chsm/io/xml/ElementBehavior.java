package org.payn.chsm.io.xml;

import java.util.ArrayList;
import java.util.Iterator;

import org.payn.chsm.Behavior;
import org.payn.chsm.State;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML element representing a behavior
 * 
 * @author rob payn
 *
 */
public class ElementBehavior extends ElementHelperLoader {

   /**
    * Tag name for the element
    */
   public static final String TAG_NAME = "behavior";
   
   /**
    * Create a new instance from an existing element
    * 
    * @param element
    *       XML element
    */
   public ElementBehavior(Element element) 
   {
      super(element);
   }

   /**
    * Create an iterator over the initial value elements of the behavior
    * 
    * @return
    *       iterator over initial value elements
    */
   public Iterator<ElementInitValue> iterator() 
   {
      NodeList nodeList = element.getChildNodes();
      ArrayList<ElementInitValue> list = new ArrayList<ElementInitValue>();
      for (int i = 0; i < nodeList.getLength(); i++)
      {
         if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE &&
               ((Element)nodeList.item(i)).getTagName().equals(ElementInitValue.TAG_NAME))
         {
            list.add(new ElementInitValue((Element)nodeList.item(i)));
         }
      }
      return list.iterator();
   }

   /**
    * Create an initial value element within the behavior based on the provided state
    * 
    * @param state 
    *       state with information
    * @return
    *       initial value element created
    */
   public ElementInitValue createInitValueElement(State state) 
   {
      ElementInitValue initValElem = new ElementInitValue(
            element.getOwnerDocument().createElement(ElementInitValue.TAG_NAME)
            );
      initValElem.setStateVariableName(state.getName());
      initValElem.setValue(state.getValue().toString());
      initValElem.setParentElement(element);
      if (!state.isRequired())
      {
         initValElem.setTypeAlias(state.getValue().getClass().getSimpleName());
      }
      return initValElem;
   }

   /**
    * Create an initial value element based on the state name, value, and value type provided
    * 
    * @param stateName
    *       name of the state
    * @param value
    *       string representing the value of the state
    * @param valueType
    *       type of the state (necessary if state is not required)
    * @return
    *       created initial value element
    */
   public ElementInitValue createInitValueElement(String stateName, String value, String valueType) 
   {
      ElementInitValue initValElem = new ElementInitValue(
            element.getOwnerDocument().createElement(ElementInitValue.TAG_NAME)
            );
      initValElem.setStateVariableName(stateName);
      initValElem.setValue(value);
      initValElem.setParentElement(element);
      if (valueType != null)
      {
         initValElem.setTypeAlias(valueType);
      }
      return initValElem;
   }
   
   /**
    * Set the install flag value
    * 
    * @param installValue
    *       value for install flag
    */
   protected void setInstall(String installValue) 
   {
      this.setAttribute("install", installValue);
   }
   
   /**
    * Determine if install flag is set
    * 
    * @return
    *       true if install flag is set to true, false otherwise
    *       absence of install flag defaults to true
    */
   public boolean isInstalled()
   {
      return getAttribute("install").equals("") || 
            Boolean.valueOf(getAttribute("install"));
   }

   /**
    * Set the class path attribute
    * 
    * @param canonicalName
    *       class path
    */
   public void setClassPath(String canonicalName) 
   {
      this.setAttribute("classpath", canonicalName);
   }

   /**
    * Set the path attribute
    * 
    * @param path
    *       value for the path attribute
    */
   public void setPath(String path) 
   {
      this.setAttribute("path", path);
   }

   /**
    * Configure the behavior element based on the provided behavior
    * 
    * @param behavior
    *       behavior with information for element
    * @param install 
    *       value for the install flag
    */
   public void configureBehaviorElement(Behavior behavior, boolean install) 
   {
      setName(behavior.getName());
      if (!install)
      {
         setInstall(Boolean.toString(install));
      }
      setClassPath(behavior.getClass().getCanonicalName());
      setPath(behavior.getFileSystemPath());
   }

   /**
    * Determines if there is a resource specified
    * 
    * @return
    *       true if resource is present, false otherwise
    */
   public boolean hasResource() 
   {
      return element.hasAttribute(ElementResource.TAG_NAME);
   }

   /**
    * Set the resource name in the element
    * 
    * @param resourceName
    */
   public void setResourceName(String resourceName) 
   {
      setAttribute(ElementResource.TAG_NAME, resourceName);
   }

   /**
    * Get the resource name from the element
    * 
    * @return
    *       resource name
    */
   public String getResourceName() 
   {
      return element.getAttribute(ElementResource.TAG_NAME);
   }

}
