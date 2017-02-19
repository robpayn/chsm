package org.payn.chsm.values;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.payn.chsm.ValueAbstract;
import org.payn.chsm.values.ValueDouble;

public class ValueTimeSeries extends ValueAbstract {
   
   public LinkedHashMap<ValueDouble, ValueDouble> map;

   @Override
   public void setToNoValue() 
   {
      map = null;
   }

   @Override
   public boolean isNoValue() 
   {
      return map == null;
   }

   @Override
   public String getValueAsString() 
   {
      String valueString = "";
      for (Entry<ValueDouble, ValueDouble> entry: map.entrySet())
      {
         valueString += String.format("%s,%s:", entry.getKey().toString(), entry.getValue().toString());
      }
      return valueString;
   }

   @Override
   public void setToValueOf(String valueString) throws Exception 
   {
      newValue();
      String[] elements = valueString.split(":");
      for (String element: elements)
      {
         String[] valueStrings = element.split(",");
         map.put(
               new ValueDouble(valueStrings[1]), 
               new ValueDouble(valueStrings[2])
               );
      }
   }

   public void newValue() 
   {
      map = new LinkedHashMap<ValueDouble, ValueDouble>();
   }
   
   public void addValue(ValueDouble time, ValueDouble value) 
   {
      map.put(time, value);
   }

   public double getTimeWeightedMean() 
   {
      double sum = 0;
      int count = 0;
      for (ValueDouble value: map.values())
      {
         sum = sum + Math.log(value.n);
         count += 1;
      }
      return Math.exp(sum / count);
      
//      double totalTime = 0;
//      double totalValue = 0;
//      Iterator<Entry<ValueDouble,ValueDouble>> iter = map.entrySet().iterator();
//      if (iter.hasNext())
//      {
//         Entry<ValueDouble,ValueDouble> lastEntry = iter.next();
//         if (iter.hasNext())
//         {
//            while(iter.hasNext())
//            {
//               Entry<ValueDouble,ValueDouble> entry = iter.next();
//               double deltaTime = entry.getKey().n - lastEntry.getKey().n;
//               totalTime += deltaTime;
//               totalValue += 0.5 * (entry.getValue().n + lastEntry.getValue().n) * deltaTime;
//               lastEntry = entry;
//            }
//            return totalValue / totalTime;
//         }
//         else
//         {
//            return lastEntry.getValue().n;
//         }
//      }
//      else
//      {
//         return 0;
//      }
   }

}
