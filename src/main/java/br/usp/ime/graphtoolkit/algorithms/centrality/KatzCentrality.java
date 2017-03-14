package br.usp.ime.graphtoolkit.algorithms.centrality;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * Katz centrality: x_i = alpha * sum_j ( A_ij * x_j ) + beta
 * 
 * - Equal to eigenvector centrality when beta = 0.
 * - Constant when alpha = 0.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class KatzCentrality<V,E> {

	 public static int MAX_ITERATIONS = 100;
     public static double EPSILON = 1e-6;

     private double alpha;
     private double beta;
     
     private Graph graph;

     public KatzCentrality (Graph network)
     {
             this(network,1.0,1.0);
     }
     
     public KatzCentrality (Graph network, double alpha)
     {
             this(network,alpha,1.0);
     }
     
     public KatzCentrality (Graph network, double alpha, double beta)
     {
    	 	 this.graph = network;
             this.alpha = alpha;
             this.beta = beta;
     }       

     
    
     public String getName() 
     {
             return "katz("+alpha+","+beta+")";
     }       

    
     public String getDescription() 
     {
             return "Katz centrality (alpha="+alpha+"; beta="+beta+")";
     }       

    
     public Map<V,Double> compute ()
     {
             Graph<V,E> net = this.graph;
             
             BidiMap<Integer,V> vertexMap = new DualHashBidiMap<>();
             int count = 0;
             for(V v : net.getVertices()){
            	 vertexMap.put(count,v);
            	 count++;
             }
             
             int     size = net.getVertexCount();
             double  centrality[];
             double  old[];
             double  tmp[];
             double  change;
             double  sum2;
             double  norm;
             int     iteration;
             
             // Initialization: 1/N
             
             centrality = new double[size];
             old = new double[size];
     
             for (int i=0; i<size; i++) {
                     centrality[i] = 1.0/size;
                     old[i] = centrality[i];
             }
             
             
             // Power iteration: O(k(n+m))
             // The value of norm converges to the dominant eigenvalue, and the vector 'centrality' to an associated eigenvector
             // ref. http://en.wikipedia.org/wiki/Power_iteration
             
             change = Double.MAX_VALUE;
             
             for (iteration=0; (iteration<MAX_ITERATIONS) && (change>EPSILON); iteration++) {

                     tmp = old;         // Swap old-centrality
                     old = centrality;
                     centrality = tmp;

                     sum2 = 0;
                     
                     for (int v=0; v<size; v++) {
                             
                    	 	 
                    	 
                             centrality[v] = 0.0;
                             
                             // Right eigenvector
                             
                             List<V> predecessors = new ArrayList<>(
                            		 net.getPredecessors(vertexMap.get(v)));
                            		 
                             for (V predecessor : predecessors) {
                            	 	int w = vertexMap.getKey(predecessor);
                                    centrality[v] += old[w];
                             }
                             
                             // Katz centrality

                             centrality[v] = alpha*centrality[v] + beta;
                             
                             sum2 += centrality[v]*centrality[v];
                     }
                 
                 // Normalization
                     
                     norm = Math.sqrt(sum2);
                     change = 0;
                     
                     for (int v=0; v<size; v++) {
                             centrality[v] /= norm;

                             if ( Math.abs(centrality[v]-old[v]) > change )
                                     change = Math.abs(centrality[v]-old[v]);
                     }
             }

             Map<V,Double> centralityMap = new HashMap<>();
             for(int v=0; v<size; v++){
            	 V vertex = vertexMap.get(v);
            	 double centralityValue = centrality[v];
            	 centralityMap.put(vertex, centralityValue);
             }
             
             return centralityMap;
     }    
     
	public static void main(String[] args) {
		Graph<Integer,Integer> graph = new UndirectedSparseGraph<>();
		graph.addEdge(1, 1,2);
		graph.addEdge(2, 2,3);
		graph.addEdge(3, 4,5);
		graph.addEdge(4, 5,6);
		KatzCentrality<Integer,Integer> katzCentrality = new KatzCentrality<>(
				graph,1,1);
		System.out.println(katzCentrality.compute());
	}
}
