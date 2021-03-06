package org.payn.chsm.io;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.payn.chsm.Holon;
import org.payn.chsm.HolonBasic;
import org.payn.chsm.io.xmltools.DocumentHolon;
import org.payn.chsm.io.xmltools.ElementBehavior;
import org.payn.chsm.io.xmltools.ElementBuilder;
import org.payn.chsm.io.xmltools.ElementHolon;
import org.payn.chsm.io.xmltools.ElementInitValue;
import org.payn.chsm.io.xmltools.ElementXMLInput;
import org.payn.chsm.io.xmltools.XMLDocumentModelConfig;
import org.payn.chsm.resources.Behavior;
import org.payn.chsm.resources.Resource;
import org.payn.chsm.resources.time.BehaviorTime;

/**
 * Builds models based on information in an XML file
 * 
 * @author robpayn
 *
 */
public class ModelBuilderXML extends ModelBuilder {

   /**
    * Map of default values for behaviors
    */
   protected HashMap<String, ElementBehavior> defaultBehaviorMap;
   
   /**
    * Map of initial value tables for behaviors
    */
   protected HashMap<String, InitialConditionTable> behaviorTableMap;

   @Override
   public Holon buildModel() throws Exception 
   {
      // Build the matrix
      loggerManager.statusUpdate("");
      
      // Check for valid configuration file
      if (!argMap.containsKey("config"))
      {
         throw new Exception(
               "Must provide an argument for configuration file relative to working directory " +
                     "(e.g. 'config=./config/config.xml')"
               );
      }
      File configFile = new File(
            workingDir.getAbsolutePath() + argMap.get("config")
            );
      if (!configFile.exists() || configFile.isDirectory()) 
      {
         throw new Exception(String.format(
               "%s is an invalid configuration file.", 
               configFile.getAbsolutePath()
               ));
      }
      
      // Parse the XML configuration file
      XMLDocumentModelConfig document = new XMLDocumentModelConfig(configFile);
      ElementBuilder element = document.getBuilderElement();

      ElementXMLInput inputElem = new ElementXMLInput(
            element.getXMLInputElement(), 
            workingDir
            );
      DocumentHolon holonDoc = new DocumentHolon(inputElem.getHolonFile());
      ElementHolon rootHolonElement = holonDoc.getRootHolonElement();

      // Create the matrix 
      loggerManager.statusUpdate("Installing model holons...");
      baseHolon = createHolon(holonDoc.getRootElementHelper().getName(), null);
      installHolons(rootHolonElement, baseHolon);

      // Install the behaviors in the matrix
      loggerManager.statusUpdate("Installing model behaviors...");
      defaultBehaviorMap = holonDoc.getDefaultBehaviorMap();
      behaviorTableMap = new HashMap<String, InitialConditionTable>();
      for (ElementBehavior elementBehavior: defaultBehaviorMap.values())
      {
         if (elementBehavior.hasInitialConditionConfig())
         {
            InitialConditionTable behaviorTable = InitialConditionTable.getInstance(
                  new File(
                        workingDir 
                        + File.separator 
                        + elementBehavior.getAttributeInitConditionPath()
                        ), 
                  elementBehavior.getAttributeInitConditionDelimiter()
                  );
            behaviorTableMap.put(elementBehavior.getFullBehaviorName(), behaviorTable);
         }
      }
      installBehaviors(rootHolonElement, baseHolon);

      loggerManager.statusUpdate(String.format(
            "Model build time = %s ...", 
            BehaviorTime.parseTimeInMillis(System.currentTimeMillis() - previousTime)
            ));
      loggerManager.statusUpdate("");
      
      return baseHolon;
   }
   
   /**
    * Recursively install holons
    * 
    * @param element
    * @param holon
    * @throws Exception
    */
   protected void installHolons(ElementHolon element, Holon holon) throws Exception
   {
      ArrayList<ElementHolon> children = element.getHolonChildren();
      for (ElementHolon childElement: children)
      {
         Holon childHolon = createHolon(childElement.getName(), holon);
         installHolons(childElement, childHolon);
      }
   }
   
   /**
    * Recursively install behaviors
    * 
    * @param element
    * @param holon
    * @throws Exception
    */
   protected void installBehaviors(ElementHolon element, Holon holon) throws Exception
   {
      for (ElementBehavior elementBehavior: element.getBehaviorList())
      {
         String resourceName = elementBehavior.getResourceName();
         Behavior behavior = resourceMap.get(resourceName)
               .getBehavior(elementBehavior.getName());
         if (elementBehavior.isInstalled())
         {
            addBehavior(baseHolon, behavior);
            baseHolon.addInstalledBehavior(behavior);
         }
         loadInitValues(baseHolon, behavior, elementBehavior, null, null);
         loggerManager.statusUpdate(String.format(
               "   Installed behavior %s", 
               behavior.getName()
               ));
      }
      ArrayList<ElementHolon> children = element.getHolonChildren();
      for (ElementHolon childElement: children)
      {
         Holon childHolon = (Holon)holon.getState(childElement.getName());
         installBehaviors(childElement, childHolon);
      }
   }

   /**
    * Create a basic base holon for the model.
    * 
    * Should be overridden for more specific model types
    * 
    * @param name
    * @return
    * @throws Exception
    */
   protected Holon createHolon(String name, Holon parentHolon) throws Exception 
   {
      return new HolonBasic(name, parentHolon);
   }
   
   /**
    * Load the initial values in the provided behavior element
    * 
    * @param targetHolon
    *       holon in which initial values are loaded
    * @param behavior
    *       behavior for the states in which initial values are loaded
    * @param element
    *       behavior element with initial value information
    * @throws Exception
    *       if error in loading initial values
    */
   private void loadInitValues(
         Holon targetHolon, 
         Behavior behavior,
         ElementBehavior element, 
         ElementBehavior defaultElement, 
         InitialConditionTable initialConditionTable
         ) throws Exception 
   {
      if (initialConditionTable != null)
      {
         for (ElementInitValue elementInitValue: defaultElement.getInitValueList())
         {
            if (elementInitValue.getValue().equals(""))
            {
               initializeValue(
                     targetHolon,
                     behavior,
                     elementInitValue.getStateVariableName(),
                     initialConditionTable.find(
                           behavior.getName() + "." + elementInitValue.getStateVariableName(),
                           targetHolon.toString()
                           ),
                     elementInitValue.getTypeAlias()
                     );
            }
         }
      }
      if (defaultElement != null)
      {
         for (ElementInitValue elementInitValue: defaultElement.getInitValueList())
         {
            if (!elementInitValue.getValue().equals(""))
            {
               initializeValue(
                     targetHolon,
                     behavior,
                     elementInitValue.getStateVariableName(),
                     elementInitValue.getValue(),
                     elementInitValue.getTypeAlias()
                     );
            }
         }
      }
      for (ElementInitValue elementInitValue: element.getInitValueList())
      {
         initializeValue(
               targetHolon,
               behavior,
               elementInitValue.getStateVariableName(),
               elementInitValue.getValue(),
               elementInitValue.getTypeAlias()
               );
      }
   }

   /**
    * Load a behavior in the provided holon
    * 
    * @param targetHolon
    *       holon in which to load the behavior
    * @param element
    *       XML element containing information about the behavior
    * @throws Exception
    *       if error in loading behavior
    */
   protected Behavior loadBehavior(
         Holon targetHolon, 
         ElementBehavior element, 
         ElementBehavior defaultElement,
         InitialConditionTable initialConditionTable
         ) throws Exception 
   {
      Behavior behavior = getBehaviorFromResource(element.getResourceName(), element.getName());
      loadInitValues(targetHolon, behavior, element, defaultElement, initialConditionTable);
      if (element.isInstalled())
      {
         addBehavior(targetHolon, behavior);
         targetHolon.addInstalledBehavior(behavior);
      }
      else
      {
         Resource resource = resourceMap.get(element.getResourceName());
         behavior = resource.getBehavior(element.getName());
      }
      return behavior;
   }

}
