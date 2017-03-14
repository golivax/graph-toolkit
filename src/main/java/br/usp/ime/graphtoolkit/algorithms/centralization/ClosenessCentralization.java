package br.usp.ime.graphtoolkit.algorithms.centralization;

import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class ClosenessCentralization<V,E> {

	/**
	 * Calculates graph's closeness centralization according to the paper
	 * "Centrality in Social Networks - Conceptual Clarification" by Freeman
	 * in Social Networks, 1 (1978/79) 230--231
	 * @param graph A connected graph
	 * @return
	 */
	public Double calculate(Graph<V,E> graph){
			
		ClosenessCentrality<V, E> closenessCentrality = 
				new ClosenessCentrality<>(graph);
		
		Double maxCloseness = 0.0;
		for(V v : graph.getVertices()){
			Double centralityForV = closenessCentrality.getVertexScore(v);
			if(centralityForV > maxCloseness) maxCloseness = centralityForV;
		}
		
		Double sum = 0.0;
		for(V v : graph.getVertices()){
			Double centralityForV = closenessCentrality.getVertexScore(v);
			sum += maxCloseness - centralityForV;
		}
		
		Double n = (double) graph.getVertexCount();
		Double centralization = sum / ((n*n - 3*n + 2) / (2*n - 3));
		
		if(centralization > 1) centralization = 1.0;
		return centralization;
	}

	public static void main(String[] args) {
		Graph<Integer,Integer> star = new UndirectedSparseGraph<>();
		star.addEdge(1, 1, 2);
		star.addEdge(2, 1, 3);
		star.addEdge(3, 1, 4);
		star.addEdge(4, 1, 5);
		
		Graph<Integer,Integer> fork = new UndirectedSparseGraph<>();
		fork.addEdge(1, 1, 2);
		fork.addEdge(2, 2, 3);
		fork.addEdge(3, 2, 4);
		fork.addEdge(4, 4, 5);
		
		Graph<Integer,Integer> complete = new UndirectedSparseGraph<>();
		complete.addEdge(1, 1, 2);
		complete.addEdge(2, 1, 3);
		complete.addEdge(3, 1, 4);
		complete.addEdge(4, 1, 5);
		complete.addEdge(5, 2, 3);
		complete.addEdge(6, 2, 4);
		complete.addEdge(7, 2, 5);
		complete.addEdge(8, 3, 4);
		complete.addEdge(9, 3, 5);
		complete.addEdge(10, 4, 5);
		
		ClosenessCentralization<Integer,Integer> closenessCentralization = 
				new ClosenessCentralization<>();
		
		System.out.println(closenessCentralization.calculate(star));
		System.out.println(closenessCentralization.calculate(fork));
		System.out.println(closenessCentralization.calculate(complete));
		
		Graph<Integer,Integer> starWithPath = new UndirectedSparseGraph<>();
		starWithPath.addEdge(1, 1, 2);
		starWithPath.addEdge(2, 1, 3);
		starWithPath.addEdge(3, 1, 4);
		starWithPath.addEdge(4, 1, 5);
		
		starWithPath.addEdge(5, 5, 6);
		starWithPath.addEdge(6, 6, 7);
		starWithPath.addEdge(7, 7, 8);
		starWithPath.addEdge(8, 8, 9);
		starWithPath.addEdge(9, 9, 10);
		System.out.println(closenessCentralization.calculate(starWithPath));
		
	}
}
