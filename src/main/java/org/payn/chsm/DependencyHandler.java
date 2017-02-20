package org.payn.chsm;

import java.util.ArrayList;

/**
 * Handles dependencies between processors
 * 
 * @author rob payn
 * @param <PT> 
 *      Processor type handled by this class
 *
 */
public interface DependencyHandler<PT extends Processor> {

   /**
    * Provide the sorted list of processors in the correct execution order
    * 
    * @return
    *       sorted list of processors
    * @throws Exception
    *       if error in sorting list (e.g. cycles)
    */
   ArrayList<PT> sort() throws Exception;
   
   /**
    * Populate the processor graph and sort it topographically
    * 
    * @return
    *       sorted list of processors
    * @throws Exception
    */
   ArrayList<PT> getSortedProcessors() throws Exception;
   
   /**
    * Add a single dependency from a processor to another state variable's processor
    * 
    * (processor for needed state variable must be executed before the dependent processor)
    * 
    * @param processor
    *       dependent processor
    * @param neededProcessor 
    *       needed processor
    * @throws Exception 
    */
   void addDependency(Processor processor, Processor neededProcessor) throws Exception;

}
