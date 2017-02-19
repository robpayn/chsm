package org.payn.chsm.io.file.interpolate;

import java.io.File;
import java.sql.Time;

import org.payn.chsm.Holon;
import org.payn.chsm.processors.ProcessorDouble;
import org.payn.chsm.processors.interfaces.UpdaterAutoSimple;
import org.payn.chsm.values.ValueDouble;
import org.payn.chsm.values.ValueString;

/**
 * A generic processor for interpolating the value of a state
 * 
 * @author robpayn
 *
 */
public class ProcessorInterpolateSnapshotTable extends ProcessorDouble implements UpdaterAutoSimple {
   
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
   private InterpolatorSnapshotTable interp;
   
   /**
    * Header name for state to be interpolated
    */
   private String header;

   @Override
   public void setUpdateDependencies() throws Exception 
   {
      ValueString pathName = (ValueString)createDependency(getPathName()).getValue();
      ValueString type = (ValueString)createDependency(getTypeName()).getValue();
      ValueString delimiter = (ValueString)createDependency(getDelimiter()).getValue();
      ValueDouble time = (ValueDouble)createDependency(
            (Holon)getController().getState(),
            Time.class.getSimpleName()
            ).getValue();
      this.header = getHeaderName();
      interp = InterpolatorSnapshotTable.getInstance(
            controller, 
            new File(pathName.toString()), 
            time, 
            delimiter.toString(), 
            header, 
            type.toString()
            );
   }

   /**
    * Get the delimiter
    * 
    * @return
    *       name of delimiter state
    */
   protected String getDelimiter() 
   {
      return state.getBehavior().getResource().getName() + REQ_STATE_DELIMITER;
   }

   /**
    * Get the name of the type
    * 
    * @return
    *       name of the type state
    */
   protected String getTypeName() 
   {
      return state.getBehavior().getResource().getName() + REQ_STATE_TYPE;
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

   /**
    * Get the name of the path state
    * 
    * @return
    *       name of the path state
    */
   protected String getPathName() 
   {
      return state.getBehavior().getResource().getName() + REQ_STATE_PATH;
   }

   @Override
   public void update() throws Exception 
   {
      value.n = interp.interpolate(header);
   }

}
