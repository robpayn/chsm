package org.payn.chsm.io.xmltools;

import java.util.ArrayList;

import org.payn.chsm.State;
import org.payn.chsm.resources.Behavior;
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
    * The initialization table element
    */
   private ElementHelper inittableElement;
   
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
   public ArrayList<ElementInitValue> getInitValueList() 
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
      return list;
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
      initValElem.setStateName(state.getName());
      initValElem.setValue(state.getValue().toString());
      initValElem.setParentElement(element);
      if (!state.isRegistered())
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
      initValElem.setStateName(stateName);
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
      if (behavior.hasResource())
      {
         setName(behavior.getSimpleName());
         setResourceName(behavior.getResource().getName());
      }
      else
      {
         setName(behavior.getName());
         setClassPath(behavior.getClass().getCanonicalName());
         setPath(behavior.getFileSystemPath());
      }
      if (!install)
      {
         setInstall(Boolean.toString(install));
      }
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

   /**
    * Get the full name of the behavior based on the resource
    * name prefix
    * 
    * @return
    *       full behavior name
    */
   public String getFullBehaviorName() 
   {
      return getResourceName() + "." + getName();
   }

   /**
    * Determine if an initial condition table is configured
    * 
    * @return
    *       true if configured, false otherwise
    */
   public boolean hasInitialConditionConfig() 
   {
      return getInitTableElement() != null;
   }

   /**
    * Set the path and delimiter for the initial value table
    * 
    * @param path
    *       path to the file
    * @param delimiter
    *       column delimiter for the file
    */
   public void setInitTable(String path, String delimiter) 
   {
      if (!hasInitialConditionConfig())
      {
         inittableElement = 
               new ElementHelper(getDocument().createElement("inittable"));
         element.appendChild(inittableElement.getElement());
      }
      inittableElement.setAttribute("initialConditionPath", path);;
      inittableElement.setAttribute("initialConditionDelimiter", delimiter);
   }

   /**
    * Get the initial conditions table path
    * 
    * @return
    *       path
    */
   public String getAttributeInitConditionPath() 
   {
      if (getInitTableElement() != null)
      {
         return getInitTableElement().getAttributeString("initialConditionPath");
      }
      else
      {
         return null;
      }
   }

   /**
    * Column delimiter for the initial conditions table
    * 
    * @return
    *       delimiter
    */
   public String getAttributeInitConditionDelimiter() 
   {
      if (getInitTableElement() != null)
      {
         return getInitTableElement().getAttributeString("initialConditionDelimiter");
      }
      else
      {
         return null;
      }
   }

   /**
    * Get the element for the initial condition table configuration
    * 
    * @return
    *       initial condition table element
    */
   private ElementHelper getInitTableElement() 
   {
      if (inittableElement == null)
      {
         inittableElement = getFirstChildElementHelper("inittable");
      }
      return inittableElement;
   }

}
