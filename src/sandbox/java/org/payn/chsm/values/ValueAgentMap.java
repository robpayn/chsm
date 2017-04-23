package org.payn.chsm.values;

import java.util.HashMap;

import org.payn.chsm.Agent;

public class ValueAgentMap extends ValueAbstract {

   public HashMap<String, Agent> agentMap;
   
   @Override
   public void setToNoValue() 
   {
      agentMap = null;
   }

   @Override
   public boolean isNoValue() 
   {
      return agentMap == null;
   }

   @Override
   public String getValueAsString() 
   {
      return Integer.toString(agentMap.size());
   }

   @Override
   public void setToValueOf(String valueString) throws Exception 
   {
      initializeMap();
   }

   public void initializeMap() 
   {
      agentMap = new HashMap<String, Agent>();
   }

}
