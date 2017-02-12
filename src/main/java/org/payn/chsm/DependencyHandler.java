package org.payn.chsm;

import java.util.ArrayList;

import org.payn.chsm.dependencies.graph.Graph;

/**
 * Handles dependencies between processors
 * 
 * @author rob payn
 * @param <PT> 
 *
 */
public abstract class DependencyHandler<PT extends Processor> {
   
   /**
    * List of processors
    */
   protected ArrayList<PT> processors;
   
   /**
    * Directed graph used to sort processors topologically (i.e. determine correct processing order)
    */
   private Graph<PT> graph;
   
   /**
    * Create a new instance based on the provided list of processors
    * 
    * @param processors
    *       list of processors
    */
   public DependencyHandler(ArrayList<PT> processors) 
   {
      graph = new Graph<PT>();
      this.processors = processors;
      for (PT processor: processors)
      {
         graph.addNode(processor);
      }
   }
   
   /**
    * Add a single dependency to the graph 
    * 
    * (needed processor must be executed before the dependent processor)
    * 
    * @param processor
    *       Dependent processor
    * @param neededProcessor
    *       Needed processor
    */
   protected final void addToGraph(PT processor, PT neededProcessor)
   {
      graph.addEdge(neededProcessor, processor);
   }
   
   /**
    * Provide the sorted list of processors in the correct execution order
    * 
    * @return
    *       sorted list of processors
    * @throws Exception
    *       if error in sorting list (e.g. cycles)
    */
   public ArrayList<PT> sort() throws Exception
   {
      return graph.sort();
   }

   /**
    * Populate the processor graph and sort it topographically
    * 
    * @return
    *       sorted list of processors
    * @throws Exception
    */
   public ArrayList<PT> getSortedProcessors() throws Exception
   {
      addDependencies();
      return sort();
   }

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
   public abstract void addDependency(Processor processor, Processor neededProcessor) throws Exception;

   /**
    * Add all processors to the graph
    * @throws Exception 
    */
   protected abstract void addDependencies() throws Exception;


}
