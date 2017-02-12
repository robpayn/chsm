package org.payn.chsm.dependencies.graph;

import java.util.HashSet;

/**
 * Node in a directed graph
 * 
 * @author rob payn
 *
 * @param <NT>
 *      type of object associated with a node
 */
public class Node<NT> {
   
   /**
    * Associated object
    */
   public final NT object;
   
   /**
    * Get the object associated with the node
    * 
    * @return
    *       associated object
    */
   public NT getObject() 
   {
      return object;
   }

   /**
    * Set of incoming edges
    */
   public final HashSet<Edge<NT>> inEdges;
      
   /**
    * Set of outgoing edges
    */
   public final HashSet<Edge<NT>> outEdges;
      
   /**
    * Construct a new node associated with the provided object
    * 
    * @param object
    *       object associated with node
    */
   public Node(NT object) 
   {
      this.object = object;
      inEdges = new HashSet<Edge<NT>>();
      outEdges = new HashSet<Edge<NT>>();
   }
      
   /**
    * Add an outgoing edge to the specified node
    * 
    * @param node
    *       Node on the other side of outgoing edge
    * @return
    *       error if adding node
    */
   public Node<?> addEdge(Node<NT> node)
   {
      Edge<NT> e = new Edge<NT>(this, node);
      outEdges.add(e);
      node.inEdges.add(e);
      return this;
   }
      
}
