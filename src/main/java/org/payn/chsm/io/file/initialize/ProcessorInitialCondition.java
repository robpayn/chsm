package org.payn.chsm.io.file.initialize;

import java.io.File;

import org.payn.chsm.processors.auto.ProcessorDoubleRequired;
import org.payn.chsm.values.ValueString;

/**
 * An abstract processor that uses a table to initialize its value.
 * 
 * Note this provides an abstract implementation of setInitDependencies that an be used
 * to implement InitializerAuto implementers at less abstract levels.
 * 
 * @author v78h241
 *
 */
public abstract class ProcessorInitialCondition extends ProcessorDoubleRequired {

   /**
    * Table with the initial condition for this processor
    */
   protected InitialConditionTable initialConditionTable;

   /**
    * Set the initialization dependencies
    * 
    * @throws Exception 
    *       if a problem with IO access of file with table
    */
   public void setInitDependencies() throws Exception 
   {
      if (value.isNoValue())
      {
         try
         {
            ValueString initPath = (ValueString)createDependencyOnValue(
                  InitialConditionTable.NAME_INITIAL_CONDITION_PATH
                  );
            ValueString initDelimiter = (ValueString)createDependencyOnValue(
                  InitialConditionTable.NAME_INITIAL_CONDITION_DELIMITER
                  );
            initialConditionTable = InitialConditionTable.getInstance(
                  controller, 
                  new File(initPath.string), 
                  initDelimiter.string
                  );
         }
         catch (Exception e)
         {
            initialConditionTable = null;
         }
      }
   }

   /**
    * Get the name of the holon for this processor
    * 
    * @return
    */
   private String getHolonName() 
   {
      return state.getParentHolon().toString();
   }

   /**
    * Get the name of the state for this processor
    * 
    * @return
    */
   private String getStateName() 
   {
      return state.getBehavior().getName() + "." + state.getName();
   }

   @Override
   public void initialize() throws Exception
   {
      if (value.isNoValue())
      {
         if (initialConditionTable == null)
         {
            super.initialize();
         }
         else
         {
            value.n = initialConditionTable.find(getStateName(), getHolonName());
         }
      }
   }

}
