package org.payn.chsm.io.file.interpolate;

import java.io.File;

import org.payn.chsm.Holon;
import org.payn.chsm.processors.ProcessorDouble;
import org.payn.chsm.processors.interfaces.InitializerAutoSimple;
import org.payn.chsm.processors.interfaces.UpdaterAutoSimple;
import org.payn.chsm.resources.time.Time;
import org.payn.chsm.values.ValueDouble;
import org.payn.chsm.values.ValueString;

/**
 * A generic processor for interpolating the value of a state
 * 
 * @author robpayn
 *
 */
public abstract class ProcessorInterpolateSnapshotTable 
extends ProcessorDouble implements InitializerAutoSimple, UpdaterAutoSimple {
   
   /**
    * Name of required state for interpolation type
    */
   public static final String REQ_STATE_TYPE = "InterpolationType";

   /**
    * Name of required state for path to interpolation file
    */
   public static final String REQ_STATE_PATH = "InterpolationPath";

   /**
    * Name of required state for delimiter
    */
   public static final String REQ_STATE_DELIMITER = "Delimiter";

   /**
    * Interpolator
    */
   private Interpolator interp;
   
   /**
    * Header name for state to be interpolated
    */
   private String header;

   @Override
   public void setInitDependencies() throws Exception
   {
      ValueString pathName = createPathDependency();
      ValueString type = createTypeDependency();
      ValueString delimiter = createDelimiterDependency();
      ValueDouble time = (ValueDouble)((Holon)getController().getState()).getState(
            Time.class.getSimpleName()
            ).getValue();
      this.header = getHeaderName();
      interp = InterpolatorSnapshotTable.getInterpolator(
            controller, 
            new File(pathName.toString()), 
            time, 
            delimiter.toString(), 
            header, 
            type.toString()
            );
   }
   
   @Override
   public void initialize() throws Exception
   {
      if (value.isNoValue())
      {
         update();
      }
   }
   
   @Override
   public void setUpdateDependencies() throws Exception 
   {
   }

   /**
    * Get the name of the header state
    * 
    * @return
    *       name of the header state
    */
   protected String getHeaderName() 
   {
      return state.toString();
   }

   @Override
   public void update() throws Exception 
   {
      value.n = interp.interpolate();
   }

   /**
    * Create the required parameter dependencies in the local holon
    * 
    * @throws Exception
    */
   protected ValueString createPathDependency() throws Exception 
   {
      return (ValueString)createDependency(
            ProcessorInterpolateSnapshotTable.REQ_STATE_PATH
            ).getValue();
   }

   /**
    * Create the required parameter dependencies in the local holon
    * 
    * @throws Exception
    */
   protected ValueString createTypeDependency() throws Exception 
   {
      return (ValueString)createDependency(
            ProcessorInterpolateSnapshotTable.REQ_STATE_TYPE
            ).getValue();
   }

   /**
    * Create the required parameter dependencies in the local holon
    * 
    * @throws Exception
    */
   protected ValueString createDelimiterDependency() throws Exception 
   {
      return (ValueString)createDependency(
            ProcessorInterpolateSnapshotTable.REQ_STATE_DELIMITER
            ).getValue();
   }

}
