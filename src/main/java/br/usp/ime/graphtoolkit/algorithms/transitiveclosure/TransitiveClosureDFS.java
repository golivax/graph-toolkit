package br.usp.ime.graphtoolkit.algorithms.transitiveclosure;

import br.usp.ime.graphtoolkit.algorithms.traversal.NonRecursiveDFS;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

/**
 * 
 * @author Gustavo
 *
 * @param <V> Vertex type
 * @param <E> Edge type
 */

//TODO: Check clients of this class, as there was a bug here
public class TransitiveClosureDFS<V,E> extends NonRecursiveDFS<V,E>{

	private DirectedSparseGraph<V, SimpleEdge> transitiveClosure;
	
	public TransitiveClosureDFS(){
		
	}
	
	public DirectedGraph<V, SimpleEdge> getTransitiveClosureFrom(
			DirectedGraph<V, E> graph) {
		
		transitiveClosure = new DirectedSparseGraph<V, SimpleEdge>();
		
		System.out.println("Vertices: " + graph.getVertexCount());
		System.out.print("Computing transitive closure...");
		
		for(V root: graph.getVertices()){
			super.traverse(graph, root);
			super.reset();
		}
		
		System.out.print("done");
		System.out.println();
		
		return transitiveClosure;
	}

	@Override
	public void visit(V root, V v) {
		SimpleEdge e = new SimpleEdge(root + "," + v);
		transitiveClosure.addEdge(e, root, v);
	}
	
	public static void main(String[] args) {
		DirectedGraph<String, Integer> graph = 
				new DirectedSparseGraph<String, Integer>();
		
		String u = new String("u");
		String v = new String("v");
		String w = new String("w");
		String x = new String("x");
		String y = new String("y");
		String z = new String("z");
		
		graph.addEdge(1, u, v);
		graph.addEdge(2, u, x);
		graph.addEdge(3, v, y);
		graph.addEdge(4, w, y);
		graph.addEdge(5, w, z);
		graph.addEdge(6, x, v);
		graph.addEdge(7, y, x);
		graph.addEdge(8, z, z);
		
		TransitiveClosureDFS<String, Integer> t = new TransitiveClosureDFS<>();
		System.out.println(t.getTransitiveClosureFrom(graph));
	}

}