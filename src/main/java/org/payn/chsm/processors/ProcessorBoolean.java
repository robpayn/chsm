package org.payn.chsm.processors;

import org.payn.chsm.values.ValueBoolean;

/**
 * Processor for a boolean value
 * 
 * @author v78h241
 *
 */
public class ProcessorBoolean extends ProcessorAbstract<ValueBoolean> {

    @Override
    public void createValue() throws Exception
    {
       setValue(new ValueBoolean());
    }
    
    @Override
    public void setValueFromState()
    {
       setValue((ValueBoolean)state.getValue());
    }

}
