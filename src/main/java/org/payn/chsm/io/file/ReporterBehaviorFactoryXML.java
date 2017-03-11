package org.payn.chsm.io.file;

import java.util.ArrayList;

import org.payn.chsm.io.ReporterFactoryXML;
import org.payn.chsm.io.xml.ElementBehavior;
import org.payn.chsm.io.xml.ElementHelper;
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
   public void init() 
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
            NodeList stateList = behaviorElement.getElementsByTagName("state");
            ArrayList<String> stateFilterList = null;
            if (stateList.getLength() > 0)
            {
               stateFilterList = new ArrayList<String>();
               for(int stateCount = 0; stateCount < stateList.getLength(); stateCount++)
               {
                  stateFilterList.add(((Element)stateList.item(stateCount)).getAttribute("name"));
               }
            }
            reporter.addBehaviorFilter(behaviorElement.getAttribute("name"), stateFilterList);
         }
      }
   }

   @Override
   public ReporterBehavior newReporter() 
   {
      return new ReporterBehavior();
   }

}
