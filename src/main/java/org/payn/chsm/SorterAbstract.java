package org.payn.chsm;

import java.util.ArrayList;

import org.payn.chsm.sorters.graph.Graph;

/**
 * Handles dependencies between processors
 * 
 * @author rob payn
 * @param <PT> 
 *
 */
public abstract class SorterAbstract<PT extends Processor> implements Sorter<PT> {
   
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
   public SorterAbstract(ArrayList<PT> processors) 
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
   @Override
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
   @Override
   public ArrayList<PT> getSortedProcessors() throws Exception
   {
      addDependencies();
      return sort();
   }

   /**
    * Add all processors to the graph
    * @throws Exception 
    */
   protected abstract void addDependencies() throws Exception;


}
