package org.payn.chsm.sorters.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Generic directed graph for specified object types
 * 
 * @author rob payn
 *
 * @param <NT>
 *      type of object associated with a node
 */
public class Graph<NT> {

   /**
    * Map of nodes
    */
   private HashMap<NT,Node<NT>> nodes;
   
   /**
    * Raw constructor
    */
   public Graph()
   {
      nodes = new HashMap<NT, Node<NT>>();
   }
   
   /**
    * Add a node to the graph 
    * 
    * @param object
    *       object associated with the node
    * @return
    *       the added node
    */
   public Node<NT> addNode(NT object) 
   {
      Node<NT> node = nodes.get(object);
      if (node == null)
      {
         node = new Node<NT>(object);
         nodes.put(object, node);
      }
      return node;
   }
    
   /**
    * Add an edge to the directed graph
    * 
    * @param fromObject
    *       from side of the edge
    * @param toObject
    *       to side of the edge
    */
   public void addEdge(NT fromObject, NT toObject) 
   {
      Node<NT> fromNode = nodes.get(fromObject);
      if (fromNode == null)
      {
         fromNode = addNode(fromObject);
      }
      Node<NT> toNode = nodes.get(toObject);
      if (toNode == null)
      {
         toNode = addNode(toObject);
      }
      fromNode.addEdge(toNode);
   }

   /**
    * Provide the topologically sorted list of objects
    * 
    * @return
    *       sorted list of objects
    * @throws Exception
    *       if cycle is found in graph
    */
   public ArrayList<NT> sort() throws Exception
   {
      ArrayList<NT> sortedList = new ArrayList<NT>();
      
      HashSet<Node<NT>> noFromNodes = new HashSet<Node<NT>>(); 
      for(Node<NT> node : nodes.values())
      {
         if(node.inEdges.size() == 0)
         {
            noFromNodes.add(node);
         }
      }
   
      while(!noFromNodes.isEmpty())
      {
         Node<NT> node = noFromNodes.iterator().next();
         noFromNodes.remove(node);
   
         sortedList.add(node.getObject());
         
         for(Iterator<Edge<NT>> outEdges = node.outEdges.iterator(); outEdges.hasNext();)
         {
            Edge<NT> outEdge = outEdges.next();
            Node<NT> toNode = outEdge.to;
            outEdges.remove(); 
            toNode.inEdges.remove(outEdge);

            if(toNode.inEdges.isEmpty())
            {
               noFromNodes.add(toNode);
            }
         }
      }
      
      for(Node<NT> node : nodes.values())
      {
         if(!node.inEdges.isEmpty())
         {
            String error = "Cycle: " + node.getObject().toString();
            Node<NT> current = node.inEdges.iterator().next().from;
            int i = 0;
            while (i < 10 && !node.equals(current))
            {
               error += " <- " + current.getObject().toString();
               current = current.inEdges.iterator().next().from;
               i++;
            }
            error += " <- " + current.getObject().toString();
            throw new Exception(error);
         }
      }
      
      return sortedList;
   }

}
