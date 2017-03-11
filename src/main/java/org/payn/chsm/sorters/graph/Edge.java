package org.payn.chsm.sorters.graph;


/**
 * Edge in a directed graph
 * 
 * @author rob payn
 *
 * @param <NT>
 *      type of object associated with each node
 */
public class Edge<NT> {

   /**
    * Node on from side of edge
    */
   public final Node<NT> from;

   /**
    * Node on to side of edge
    */
   public final Node<NT> to;
   
   /**
    * Construct a new edge between the provided nodes
    * 
    * @param fromNode
    *       node on from side
    * @param toNode
    *       node on to side
    */
   public Edge(Node<NT> fromNode, Node<NT> toNode) 
   {
      this.from = fromNode;
      this.to = toNode;
   }
   
   /**
    * Checks for equality with another edge
    */
   @Override
   public boolean equals(Object obj) 
   {
      Edge<?> e = (Edge<?>)obj;
      return e.from == from && e.to == to;
   }
   
}
