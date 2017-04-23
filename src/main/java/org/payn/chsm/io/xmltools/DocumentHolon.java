package org.payn.chsm.io.xmltools;

import java.io.File;
import java.util.HashMap;

import org.payn.chsm.resources.Behavior;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * An XML document for configuring holons
 * 
 * @author robpayn
 *
 */
public class DocumentHolon extends XMLDocument {

   /**
    * Default behavior element
    */
   private Element defaultBehaviorElement = null;
   
   /**
    * The holon element for the root
    */
   private ElementHolon rootHolonElement;
   
   /**
    * Get the root holon element
    * 
    * @return
    *       root holon element
    */
   public ElementHolon getRootHolonElement()
   {
      return rootHolonElement;
   }
   
   /**
    * Construct a new instance based on the provided file name and root element
    * tag name
    * 
    * @param fileName
    * @param rootElementTag
    * @throws Exception
    */
   public DocumentHolon(String fileName, String rootElementTag) throws Exception 
   {
      super(fileName, rootElementTag);
      rootHolonElement = new ElementHolon(this.getRootElement());
   }

   /**
    * Construct a new instance based on an existing file
    * 
    * @param file
    * @throws Exception
    */
   public DocumentHolon(File file) throws Exception 
   {
      super(file);
      rootHolonElement = new ElementHolon(this.getRootElement());
   }

   /**
    * Construct a new instance based on the provided file name
    * 
    * @param fileName
    * @throws Exception
    */
   public DocumentHolon(String fileName) throws Exception 
   {
      this(fileName, "holon");
   }

   /**
    * Get a list of default behavior elements 
    * 
    * @return
    *       list of elements
    */
   public HashMap<String, ElementBehavior> getDefaultBehaviorMap() 
   {
      HashMap<String, ElementBehavior> map = new HashMap<String, ElementBehavior>();
      Element defaultElement = getRootElementHelper().getFirstChildElement("default");
      if (defaultElement == null)
      {
         return map;
      }
      NodeList behaviorNodes = 
            defaultElement.getElementsByTagName("behavior");
      for (int i = 0; i < behaviorNodes.getLength(); i++)
      {
         if (behaviorNodes.item(i).getNodeType() == Node.ELEMENT_NODE &&
               ((Element)behaviorNodes.item(i)).getTagName().equals("behavior"))
         {
            ElementBehavior elementBehavior = 
                  new ElementBehavior((Element)behaviorNodes.item(i));
            map.put(
                  elementBehavior.getFullBehaviorName(), 
                  elementBehavior
                  );
         }
      }
      return map;
   }

   /**
    * Create a default behavior element
    * 
    * @param behavior
    *       behavior the element represents
    * @return
    *       element behavior object
    */
   public ElementBehavior createDefaultBehaviorElement(Behavior behavior) 
   {
      if (defaultBehaviorElement == null)
      {
         defaultBehaviorElement = 
               rootElementHelper.getElement().getOwnerDocument().createElement("default");
         rootElementHelper.getElement().appendChild(defaultBehaviorElement);
      }
      ElementBehavior elementBehavior = new ElementBehavior(
            document.createElement(ElementBehavior.TAG_NAME)
            );
      elementBehavior.configureBehaviorElement(behavior, true);
      elementBehavior.setParentElement(defaultBehaviorElement);
      return elementBehavior;
   }

}
