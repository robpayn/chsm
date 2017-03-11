package org.payn.chsm.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * An XML document with information about a model configuration
 * 
 * @author robpayn
 *
 */
public class XMLDocumentModelConfig extends XMLDocument {

   /**
    * Element containing the base holon information
    */
   private ElementHolon holonElem;

   /**
    * Get a holon helper element for the root element
    * 
    * @return
    *       holon element
    */
   public ElementHolon getHolonElement() 
   {
      return holonElem;
   }

   /**
    * Element containing information about the builder
    */
   private ElementBuilder builderElem;

   /**
    * Get the builder element
    * 
    * @return
    *       Element for the builder
    */
   public ElementBuilder getBuilderElement() 
   {
      return builderElem;
   }

   /**
    * The the root path for paths to java classes
    */
   private String pathRoot;
   
   /**
    * Get the path root
    * 
    * @return
    *       path root
    */
   public String getPathRoot()
   {
      return pathRoot;
   }

   /**
    * Construct a new instance based on the provided XML file
    * 
    * @param file
    * @throws Exception
    */
   public XMLDocumentModelConfig(File file) throws Exception 
   {
      super(file);
      builderElem = new ElementBuilder(rootElementHelper.getFirstChildElement(ElementBuilder.TAG_NAME));
      holonElem = new ElementHolon(rootElementHelper.getFirstChildElement(ElementHolon.TAG_NAME));
      pathRoot = this.rootElementHelper.getAttribute("pathroot");
   }

   /**
    * Get an iterator over the logger elements
    * 
    * @return
    *       iterator over logger elements, null if loggers are not specified or inactive
    */
   public Iterator<ElementLogger> getLoggerIterator() 
   {
      ElementHelper loggerElement = rootElementHelper.getFirstChildElementHelper("loggers");
      if (loggerElement != null && loggerElement.isActive())
      {
         NodeList nodeList = loggerElement.getElement().getChildNodes();
         ArrayList<ElementLogger> list = new ArrayList<ElementLogger>();
         for(int i = 0; i < nodeList.getLength(); i++)
         {
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE &&
                  ((Element)nodeList.item(i)).getTagName().equals(ElementLogger.TAG_NAME))
            {
               list.add(new ElementLogger((Element)nodeList.item(i)));
            }
         }
         return list.iterator();
      }
      else
      {
         return null;
      }
   }

   /**
    * Get the processor element
    * 
    * @return
    *       processor element
    */
   public ElementProcessor getProcessorElement() 
   {
      Element element = rootElementHelper.getFirstChildElement(ElementProcessor.TAG_NAME);
      if (element != null)
      {
         return new ElementProcessor(element);
      }
      else
      {
         return null;
      }
   }

   /**
    * Create an iterator over the resources
    * 
    * @return
    *       iterator for resources, null if list is inactive or absent
    */
   public Iterator<ElementResource> getResourceIterator() 
   {
      ElementHelper resourceElement = rootElementHelper.getFirstChildElementHelper("resources");
      if (resourceElement != null && resourceElement.isActive())
      {
         NodeList nodeList = resourceElement.getElement().getChildNodes();
         ArrayList<ElementResource> list = new ArrayList<ElementResource>();
         for(int i = 0; i < nodeList.getLength(); i++)
         {
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE &&
                  ((Element)nodeList.item(i)).getTagName().equals(ElementResource.TAG_NAME))
            {
               list.add(new ElementResource((Element)nodeList.item(i)));
            }
         }
         return list.iterator();
      }
      else
      {
         return null;
      }
   }

   /**
    * Get the iterator over the output elements
    * 
    * @return 
    *       iterator over output handler elements
    */
   public Iterator<ElementReporter> getReporterIterator() 
   {
      Element reporterElement = (Element)rootElementHelper.getFirstChildElement("reporters");
      NodeList nodeList = reporterElement.getChildNodes();
      ArrayList<ElementReporter> reporterList = new ArrayList<ElementReporter>();
      for(int i = 0; i < nodeList.getLength(); i++)
      {
         if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE &&
               ((Element)nodeList.item(i)).getTagName().equals(ElementReporter.TAG_NAME))
         {
            reporterList.add(new ElementReporter((Element)nodeList.item(i)));
         }
      }
      return reporterList.iterator();
   }

   /**
    * Get the name attribute of the document element
    * 
    * @return
    *       name of the document element
    */
   public String getHolonName() 
   {
      return holonElem.getName();
   }

}
