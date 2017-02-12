package org.payn.chsm.values;

import java.util.ArrayList;

import org.payn.chsm.values.ValueAbstract;

import resources.particle.Particle;

public class ValueParticleList extends ValueAbstract {

   public ArrayList<Particle> particleList;

   @Override
   public void setToNoValue() 
   {
      particleList = null;
   }

   @Override
   public boolean isNoValue() 
   {
      return particleList == null;
   }

   @Override
   public String getValueAsString() 
   {
      return "No text representation";
   }

   @Override
   public void setToValueOf(String valueString) throws Exception 
   {
   }

   public void newParticleList() 
   {
      particleList = new ArrayList<Particle>();
   }

   public int count() 
   {
      return particleList.size();
   }

}
