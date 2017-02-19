package org.payn.chsm.values;

import org.payn.chsm.ValueAbstract;

/**
 * Controls a boolean value
 * 
 * @author v78h241
 *
 */
public class ValueBoolean extends ValueAbstract {

    /**
     * Stores the value of this boolean
     */
    public boolean n;
    
    /**
     * Determines if value is valid
     */
    private boolean noValue = false;
    
    @Override
    public void setToNoValue()
    {
        noValue = true;
    }

    @Override
    public boolean isNoValue()
    {
        return noValue;
    }

    @Override
    public String getValueAsString()
    {
        return Boolean.toString(n);
    }

    @Override
    public void setToValueOf(String valueString) throws Exception
    {
        n = Boolean.valueOf(valueString);
    }

}
