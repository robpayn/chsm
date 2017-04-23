package org.payn.chsm.io.reporters;

import java.io.File;
import java.util.HashMap;

import org.payn.chsm.io.xmltools.ElementBehavior;
import org.payn.chsm.io.xmltools.ElementHelper;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Factory for the behavior reporter
 * 
 * @author v78h241
 *
 */
public class ReporterBehaviorFactoryXML extends ReporterFactoryXML<ReporterBehavior> {

   @Override
   public void init() throws Exception 
   {
      new ReporterIntervalFactoryXML(reporter, config).init();
      Element behElement = config.getFirstChildElement("delimiter");
      if (behElement != null && behElement.hasAttribute("value"))
      {
         reporter.setDelimiter(behElement.getAttribute("value"));
      }
      
      ElementHelper initElem = config.getFirstChildElementHelper("init");
      boolean isInitActive = false;
      if (initElem != null)
      {
         isInitActive = initElem.isActive();
      }
      reporter.setInitActive(isInitActive);
      
      // Set up the filters if filter element is present and active
      ElementHelper filterElem = config.getFirstChildElementHelper("filter");
      if (filterElem != null && filterElem.isActive())
      {
         NodeList behaviorList = filterElem.getElement().getElementsByTagName(ElementBehavior.TAG_NAME);
         for(int behaviorCount = 0; behaviorCount < behaviorList.getLength(); behaviorCount++)
         {
            Element behaviorElement = (Element)behaviorList.item(behaviorCount);
            String behaviorName = behaviorElement.getAttribute("name");
            String fileName = behaviorElement.getAttribute("fileName");
            if (!fileName.equals(""))
            {
               reporter.addBehaviorFilename(behaviorName, fileName);
            }
                  
            NodeList stateList = behaviorElement.getElementsByTagName("state");
            HashMap<String, String> stateFilterList = null;
            if (stateList.getLength() > 0)
            {
               stateFilterList = new HashMap<String, String>();
               for(int stateCount = 0; stateCount < stateList.getLength(); stateCount++)
               {
                  Element stateElement = (Element)stateList.item(stateCount);
                  String stateName = stateElement.getAttribute("name");
                  String headerName = stateElement.getAttribute("headerName");
                  if (!headerName.equals(""))
                  {
                     stateFilterList.put(stateName, headerName);
                  }
                  else
                  {
                     stateFilterList.put(stateName, stateName);
                  }
               }
            }
            reporter.addBehaviorFilter(behaviorName, stateFilterList);
         }
      }
   }

   @Override
   public ReporterBehavior newReporter(File workingDir, HashMap<String, String> argMap) 
   {
      return new ReporterBehavior(workingDir, argMap);
   }

}
