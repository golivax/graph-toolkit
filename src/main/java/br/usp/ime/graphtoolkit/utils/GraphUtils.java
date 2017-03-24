package br.usp.ime.graphtoolkit.utils;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class GraphUtils {

	/**
	 * Simpler version of {@link edu.uci.ics.jung.algorithms.transformation.DirectionTransformer#toUndirected(edu.uci.ics.jung.graph.Graph, org.apache.commons.collections15.Factory, org.apache.commons.collections15.Factory, boolean)}
	 * @param directedGraph
	 * @return
	 */
	public static <V,E> UndirectedGraph<V,E> toUndirected(DirectedGraph<V,E> directedGraph){

		UndirectedGraph<V,E> out = new UndirectedSparseGraph<>();

		for (V v : directedGraph.getVertices())
			out.addVertex(v);

		for (E e : directedGraph.getEdges())
		{
			Pair<V> endpoints = directedGraph.getEndpoints(e);
			V v1 = endpoints.getFirst();
			V v2 = endpoints.getSecond();
			E to_add = e;
			out.addEdge(to_add, v1, v2, EdgeType.UNDIRECTED);
		}
		return out;
	}
}
