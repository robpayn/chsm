package org.payn.chsm.processors;

import org.payn.chsm.values.ValueAgentMap;

/**
 * Processor that manages particle agents
 * 
 * @author robpayn
 *
 */
public class AgentManager extends ProcessorAbstract<ValueAgentMap> {

   @Override 
   public void createValue() throws Exception
   {
      setValue(new ValueAgentMap());
   }
   
   @Override
   public void setValueFromState() throws Exception
   {
      setValue((ValueAgentMap)state.getValue());
   }

}
