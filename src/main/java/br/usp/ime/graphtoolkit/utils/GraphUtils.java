package br.usp.ime.graphtoolkit.utils;

import org.apache.commons.collections4.CollectionUtils;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
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

		for (V v : directedGraph.getVertices()) {
			out.addVertex(v);
		}

		for (E e : directedGraph.getEdges()) {
			Pair<V> endpoints = directedGraph.getEndpoints(e);
			V v1 = endpoints.getFirst();
			V v2 = endpoints.getSecond();
			E to_add = e;
			out.addEdge(to_add, v1, v2, EdgeType.UNDIRECTED);
		}
		return out;
	}

	public static <V,E> DirectedGraph<V,E> union(DirectedGraph<V,E> g1, DirectedGraph<V,E> g2) {
		DirectedGraph<V,E> unionGraph = new DirectedSparseGraph<>();
		for (V v : CollectionUtils.union(g1.getVertices(), g2.getVertices())) {
			unionGraph.addVertex(v);
		}
		for (E e : g1.getEdges()) {
			unionGraph.addEdge(e, g1.getSource(e), g1.getDest(e));
		}
		for (E e : g2.getEdges()) {
			unionGraph.addEdge(e, g2.getSource(e), g2.getDest(e));
		}
		return unionGraph;
	}
	
	public static <V,E> UndirectedGraph<V,E> union(UndirectedGraph<V,E> g1, UndirectedGraph<V,E> g2) {
		UndirectedGraph<V,E> unionGraph = new UndirectedSparseGraph<>();
		for (V v : CollectionUtils.union(g1.getVertices(), g2.getVertices())) {
			unionGraph.addVertex(v);
		}
		for (E e : g1.getEdges()) {
			unionGraph.addEdge(e, g1.getEndpoints(e));
		}
		for (E e : g2.getEdges()) {
			unionGraph.addEdge(e, g2.getEndpoints(e));
		}
		return unionGraph;
	}
}