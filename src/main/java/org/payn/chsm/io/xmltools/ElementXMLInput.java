package org.payn.chsm.io.xmltools;

import java.io.File;

import org.payn.chsm.io.xmltools.ElementHelper;
import org.w3c.dom.Element;

/**
 * Element helper containing XML information for the 
 * model builder
 * 
 * @author robpayn
 *
 */
public class ElementXMLInput extends ElementHelper {
   
   /**
    * Tag name for the element
    */
   public static final String TAG_NAME = "xmlinput";

   /**
    * Input directory
    */
   protected File inputDirectory;

   /**
    * Constructor based on the provided input XML element and a working directory
    * 
    * @param element
    * @param workingDir
    * @throws Exception
    */
   public ElementXMLInput(Element element, File workingDir) throws Exception 
   {
      super(element);
      getInputDirectory(workingDir);
   }

   /**
    * Check if the "from working directory" flag is set
    * 
    * @return
    *       true if set, false otherwise
    */
   public boolean isFromWorkingDir() 
   {
      return Boolean.valueOf(getAttribute("fromWorkingDir"));
   }

   /**
    * Get the input directory based on working directory flag
    * 
    * @param workingDir
    *       working directory
    * @throws Exception
    */
   private void getInputDirectory(File workingDir) throws Exception
   {
      if (isFromWorkingDir())
      {
         inputDirectory = workingDir;
      }
      else
      {
         inputDirectory = new File(getAttribute("path")); 
      }
      if (!inputDirectory.isDirectory())
      {
         throw new Exception("RawXML source must be a directory.");
      }
   }

   /**
    * Get the XML file with the holon configuration
    * 
    * @return
    *       XML file
    */
   public File getHolonFile() 
   {
      return new File(
            inputDirectory.getAbsolutePath() + File.separator + this.getAttribute("holonFile")
            );
   }

}
