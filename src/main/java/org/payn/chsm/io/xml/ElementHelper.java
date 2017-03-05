package org.payn.chsm.io.xml;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Helper for handling an XML element
 * 
 * @author robpayn
 *
 */
public class ElementHelper {
   
   /**
    * Element handled by the helper
    */
   protected Element element;
   
   /**
    * Parent element for the element helped
    */
   protected Element parentElement;

   /**
    * Construct a new instance based on the provided XML element
    * 
    * @param element
    *       element to be helped
    */
   public ElementHelper(Element element) 
   {
      this.element = element;
   }

   /**
    * Set the parent element
    * 
    * @param parentElement
    */
   public void setParentElement(Element parentElement) 
   {
      this.parentElement = parentElement;
      this.parentElement.appendChild(element);
   }

   /**
    * Set an attribute for the element
    * 
    * @param attributeKey
    *       key string
    * @param attributeValue
    *       value string
    */
   protected void setAttribute(String attributeKey, String attributeValue) 
   {
      Attr attr = element.getOwnerDocument().createAttribute(attributeKey);
      attr.setValue(attributeValue);
      element.setAttributeNode(attr);
   }

   /**
    * Set the text content of the element
    * 
    * @param text
    *       text for the content area
    */
   public void setText(String text) 
   {
      element.setTextContent(text);
   }
   
   /**
    * Set a name attribute
    * 
    * @param name
    *       name for the value of the attribute
    */
   public void setName(String name)
   {
      setAttribute("name", name);
   }

   /**
    * Get the text content
    * 
    * @return
    *       text content of the element
    */
   public String getText() 
   {
      return element.getTextContent();
   }

   /**
    * Get the value of the name attribute
    * 
    * @return
    *       name
    */
   public String getName() 
   {
      return element.getAttribute("name");
   }
   
   /**
    * Getter for the helped element
    * 
    * @return
    *       element being helped
    */
   public Element getElement()
   {
      return element;
   }

   /**
    * Get the base element for the associated document
    * 
    * @return
    *       document element
    */
   public Document getDocument() 
   {
      return element.getOwnerDocument();
   }
   
   /**
    * Get the first child element with the provided tag name
    * 
    * @param tagName
    *       tag name to find
    * @return
    *       first child element with the given tag name, null if no elements have the tag name
    */
   public Element getFirstChildElement(String tagName)
   {
      NodeList nodeList = element.getElementsByTagName(tagName);
      Element childElement = null;
      for (int i = 0; i < nodeList.getLength(); i++)
      {
         childElement = (Element)nodeList.item(i);
         if (childElement.getParentNode().isSameNode(element)) break;
      }
      return (Element)childElement;
   }

   /**
    * Get an element helper for the first child element with the provided tag name
    * 
    * @param tagName
    *       tag name to find
    * @return
    *       first child element with the given tag name, null if no elements have the tag name
    */
   public ElementHelper getFirstChildElementHelper(String tagName)
   {
      Element element = getFirstChildElement(tagName);
      
      if (element != null)
      {
         return new ElementHelper(element);
      }
      else
      {
         return null;
      }
   }

   /**
    * Get a value for an attribute in the element
    * 
    * @param attribute
    *       attribute for which to retrieve the value
    * @return
    *       attribute value
    */
   public String getAttribute(String attribute)
   {
      return element.getAttribute(attribute);
   }
   
   /**
    * Get the active status for the element
    * 
    * @return
    *       TRUE if active is not specified or if active attribute set to 'true',
    *       FALSE otherwise
    */
   public boolean isActive() 
   {
      if (!element.hasAttribute("active") || Boolean.valueOf(element.getAttribute("active")))
      {
         return true;
      }
      else
      {
         return false;
      }
   }

   /**
    * Checks to see if at least one element of a tag name exists
    * 
    * @param tagName
    *       tag name to seek
    * @return
    *       true if at least one element exists, false otherwise
    */
   public boolean hasElement(String tagName) 
   {
      return !(getFirstChildElement(tagName) == null);
   }

   /**
    * Get a long integer representation of an attribute
    * 
    * @param key
    * @return
    *       a Long object, or null if attribute does not exist
    */
   public Long getAttributeLong(String key) 
   {
      String value = getAttribute(key);
      if (value.equals(""))
      {
         return null;
      }
      else
      {
         return Long.valueOf(value);
      }
   }

   /**
    * Get a string representation of an attribute
    * 
    * @param key
    * @return
    *       a String object, or null if attribute does not exist
    */
   public String getAttributeString(String key) 
   {
      String value = getAttribute(key);
      if (value.equals(""))
      {
         return null;
      }
      else
      {
         return value;
      }
   }

   /**
    * Get a Double representation of an attribute
    * 
    * @param key
    * @return
    *       a Double object, or null if attribute does not exist
    */
   public Double getAttributeDouble(String key) 
   {
      String value = getAttribute(key);
      if (value.equals(""))
      {
         return null;
      }
      else
      {
         return Double.valueOf(value);
      }
   }
}
