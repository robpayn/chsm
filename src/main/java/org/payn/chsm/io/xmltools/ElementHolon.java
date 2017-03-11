package org.payn.chsm.io.xmltools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.payn.chsm.Behavior;
import org.payn.chsm.Holon;
import org.payn.chsm.State;
import org.payn.chsm.resources.statespace.BehaviorHierarchy;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML element helper for a holon element
 * 
 * @author robpayn
 *
 */
public class ElementHolon extends ElementHelper {

   /**
    * Tag name for the holon element
    */
   public static final String TAG_NAME = "holon";

   /**
    * Create a new instance based on information in
    * the provided XML element
    * 
    * @param element
    *       XML element
    */
   public ElementHolon(Element element) 
   {
      super(element);
   }

   /**
    * Get an iterator over behavior elements
    * 
    * @return 
    *       iterator
    */
   public Iterator<ElementBehavior> iterator() 
   {
      NodeList nodeList = element.getChildNodes();
      ArrayList<ElementBehavior> list = new ArrayList<ElementBehavior>();
      for (int i = 0; i < nodeList.getLength(); i++)
      {
         if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE &&
               ((Element)nodeList.item(i)).getTagName().equals(ElementBehavior.TAG_NAME))
         {
            list.add(this.newBehaviorInstance((Element)nodeList.item(i)));
         }
      }
      return list.iterator();
   }
   
   /**
    * Convenience method equivalent to calling createBehaviorElment(behavior, true)
    * 
    * @param behavior
    * @return
    *       a matrix behavior element
    *       
    */
   public ElementBehavior createBehaviorElement(Behavior behavior)
   {
      return createBehaviorElement(behavior, true);
   }
   
   /**
    * Create a new behavior element in the holon
    * 
    * @param behavior 
    *       behavior on which to base the element
    * @param install 
    *       determines the state of the install flag in the behavior element
    * @return
    *       behavior element
    */
   public ElementBehavior createBehaviorElement(Behavior behavior, boolean install) 
   {
      ElementBehavior behaviorElement = newBehaviorInstance(
            element.getOwnerDocument().createElement(ElementBehavior.TAG_NAME)
            );
      behaviorElement.configureBehaviorElement(behavior, install);
      behaviorElement.setParentElement(element);
      return behaviorElement;
   }

   /**
    * Create a new instance of a behavior element
    * 
    * @param element
    * @return
    */
   protected ElementBehavior newBehaviorInstance(Element element) 
   {
      return new ElementBehavior(element);
   }

   /**
    * Clear the behavior elements from the holon element
    */
   public void clearBehaviorElements() 
   {
      NodeList nodeList = element.getElementsByTagName(ElementBehavior.TAG_NAME);
      while (nodeList.getLength() > 0)
      {
         element.removeChild((Element)nodeList.item(0));
      }
   }

   /**
    * Create behavior elements in the provided holon element
    * 
    * @param holon
    *       holon with information about behaviors
    */
   public void createBehaviorElements(Holon holon) 
   {
      HashMap<String, ElementBehavior> behaviorElemMap = new HashMap<String, ElementBehavior>();
      for (Behavior behavior: holon.getBehaviorMap().values())
      {
         if (!BehaviorHierarchy.class.isInstance(behavior))
         {
            ElementBehavior behaviorElem = createBehaviorElement(
                  behavior, 
                  holon.isBehahiorInstalled(behavior)
                  );
            behaviorElemMap.put(behavior.getName(), behaviorElem);
         }
      }
      for(State state: holon.getValue().getMap().values())
      {
         if (!Holon.class.isInstance(state))
         {
            ElementBehavior behaviorElem = behaviorElemMap.get(state.getBehavior().getName());
            behaviorElem.createInitValueElement(state);
         }
      }
   }

}
