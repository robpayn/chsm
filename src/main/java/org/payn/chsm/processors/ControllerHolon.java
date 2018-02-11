package org.payn.chsm.processors;

import org.payn.chsm.Holon;
import org.payn.chsm.io.ModelBuilder;
import org.payn.chsm.values.ValueStateMap;

/**
 * A controller for a holon state
 * 
 * @author robpayn
 *
 */
public abstract class ControllerHolon extends ControllerAbstract<ValueStateMap> {

   /**
    * Class loader for simulation
    */
   protected ClassLoader classLoader;

   /**
    * Builder for the matrix
    */
   protected ModelBuilder builder;
   
   @Override
   public Holon getState()
   {
      return (Holon)state;
   }
   
   /**
    * Set the builder for the matrix
    * 
    * @param builder
    */
   public void setBuilder(ModelBuilder builder) 
   {
      this.builder = builder;
      this.classLoader = builder.getClass().getClassLoader();
      builder.setController(this);
   }
   
   @Override 
   public void createValue() throws Exception
   {
      setValue(new ValueStateMap());
   }
   
   @Override
   public void setValueFromState() throws Exception
   {
      setValue((ValueStateMap)state.getValue());
   }
   
   /**
    * Initialize the controller
    * 
    * @throws Exception
    *       if error in initialization
    */
   public abstract void initializeController() throws Exception;
   
   /**
    * Execute the controller
    * 
    * @ throws Exception
    *       if error in execution
    */
   public abstract void executeController() throws Exception;

}
