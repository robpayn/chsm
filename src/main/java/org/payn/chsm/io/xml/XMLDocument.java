package org.payn.chsm.io.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * An XML document
 * 
 * @author rob payn
 *
 */
public class XMLDocument {
   
   /**
    * The document
    */
   protected Document document;
   
   /**
    * Root element of the document
    */
   protected ElementHelper rootElementHelper;

   /**
    * Get reference to the root element
    * 
    * @return
    *       root element
    */
   public ElementHelper getRootElementHelper() 
   {
      return rootElementHelper;
   }

   /**
    * File name for the document
    */
   private String fileName;

   /**
    * Create the XML document based on the provided file
    * 
    * @param file
    *       XML file
    * @throws Exception
    *       if error in reading file
    */
   public XMLDocument(File file) throws Exception 
   {
      populate(file);
   }

   /**
    * Create a new XML document with the provided file name
    * 
    * @param fileName
    *       name of file
    * @param rootElement
    *       root element of file
    * @throws Exception
    *       if error in creating document
    */
   public XMLDocument(String fileName, String rootElement) throws Exception
   {
      populate(fileName, rootElement);
   }
   
   /**
    * Populate from a file
    * 
    * @param file
    * @throws SAXException
    * @throws IOException
    * @throws ParserConfigurationException
    */
   protected void populate(File file) throws Exception
   {
      document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
      rootElementHelper = new ElementHelper(document.getDocumentElement());
      fileName = file.getName();
   }
   
   /**
    * Populate from a file name and root element
    * 
    * @param fileName
    * @param rootElement
    * @throws ParserConfigurationException
    */
   protected void populate(String fileName, String rootElement) throws Exception
   {
      this.fileName = fileName;
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      document = builder.newDocument();
      this.rootElementHelper = new ElementHelper(document.createElement(rootElement));
      document.appendChild(getRootElement());
   }

   /**
    * Write the XML file to the provided directory
    * 
    * @param directory
    *       directory to which file is written
    * @throws Exception
    *       if error in writing file
    */
   public void write(File directory) throws Exception 
   {
      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer xformer = factory.newTransformer();
      xformer.setOutputProperty(OutputKeys.INDENT, "yes");
      xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
      File file = new File(directory.getAbsolutePath() + File.separator + fileName);
      xformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(file)));
   }

   /**
    * Get reference to the root element
    * 
    * @return
    *       root element
    */
   public Element getRootElement() 
   {
      return rootElementHelper.getElement();
   }

}
