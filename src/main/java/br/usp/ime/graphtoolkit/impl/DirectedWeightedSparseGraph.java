package br.usp.ime.graphtoolkit.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.usp.ime.graphtoolkit.api.DirectedWeightedGraph;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class DirectedWeightedSparseGraph<V,E> implements DirectedWeightedGraph<V, E>, Serializable{

	private static final long serialVersionUID = 6152394817287067770L;
	
	protected DirectedGraph<V,E> directedGraph;
	protected Map<E,Double> edgeWeightMap;
	protected Double defaultEdgeWeight = 1.0;
	
	public DirectedWeightedSparseGraph(){
		this.directedGraph = new DirectedSparseGraph<>();
		this.edgeWeightMap = new HashMap<>();
	}
	
	public DirectedWeightedSparseGraph(DirectedGraph<V,E> directedGraph){
		this.directedGraph = directedGraph;
		this.edgeWeightMap = new HashMap<>();
	}
	
	@Override
	public Collection<E> getInEdges(V vertex) {
		return directedGraph.getInEdges(vertex);
	}

	@Override
	public Collection<E> getOutEdges(V vertex) {
		return directedGraph.getOutEdges(vertex);
	}

	@Override
	public Collection<V> getPredecessors(V vertex) {
		return directedGraph.getPredecessors(vertex);
	}

	@Override
	public Collection<V> getSuccessors(V vertex) {
		return directedGraph.getSuccessors(vertex);
	}

	@Override
	public int inDegree(V vertex) {
		return directedGraph.inDegree(vertex);
	}

	@Override
	public int outDegree(V vertex) {
		return directedGraph.outDegree(vertex);
	}

	@Override
	public boolean isPredecessor(V v1, V v2) {
		return directedGraph.isPredecessor(v1, v2);
	}

	@Override
	public boolean isSuccessor(V v1, V v2) {
		return directedGraph.isSuccessor(v1, v2);
	}

	@Override
	public int getPredecessorCount(V vertex) {
		return directedGraph.getPredecessorCount(vertex);
	}

	@Override
	public int getSuccessorCount(V vertex) {
		return directedGraph.getSuccessorCount(vertex);
	}

	@Override
	public V getSource(E directed_edge) {
		return directedGraph.getSource(directed_edge);
	}

	@Override
	public V getDest(E directed_edge) {
		return directedGraph.getDest(directed_edge);
	}

	@Override
	public boolean isSource(V vertex, E edge) {
		return directedGraph.isSource(vertex, edge);
	}

	@Override
	public boolean isDest(V vertex, E edge) {
		return directedGraph.isDest(vertex, edge);
	}

	@Override
	public boolean addEdge(E e, V v1, V v2) {
		return directedGraph.addEdge(e, v1, v2);
	}

	@Override
	public boolean addEdge(E e, V v1, V v2, EdgeType edgeType) {
		return directedGraph.addEdge(e, v1, v2, edgeType);
	}

	@Override
	public Pair<V> getEndpoints(E edge) {
		return directedGraph.getEndpoints(edge);
	}

	@Override
	public V getOpposite(V vertex, E edge) {
		return directedGraph.getOpposite(vertex, edge);
	}

	@Override
	public Collection<E> getEdges() {
		return directedGraph.getEdges();
	}

	@Override
	public Collection<V> getVertices() {
		return directedGraph.getVertices();
	}

	@Override
	public boolean containsVertex(V vertex) {
		return directedGraph.containsVertex(vertex);
	}

	@Override
	public boolean containsEdge(E edge) {
		return directedGraph.containsEdge(edge);
	}

	@Override
	public int getEdgeCount() {
		return directedGraph.getEdgeCount();
	}

	@Override
	public int getVertexCount() {
		return directedGraph.getVertexCount();
	}

	@Override
	public Collection<V> getNeighbors(V vertex) {
		return directedGraph.getNeighbors(vertex);
	}

	@Override
	public Collection<E> getIncidentEdges(V vertex) {
		return directedGraph.getIncidentEdges(vertex);
	}

	@Override
	public Collection<V> getIncidentVertices(E edge) {
		return directedGraph.getIncidentVertices(edge);
	}

	@Override
	public E findEdge(V v1, V v2) {
		return directedGraph.findEdge(v1, v2);
	}

	@Override
	public Collection<E> findEdgeSet(V v1, V v2) {
		return directedGraph.findEdgeSet(v1, v2);
	}

	@Override
	public boolean addVertex(V vertex) {
		return directedGraph.addVertex(vertex);
	}

	@Override
	public boolean addEdge(E edge, Collection<? extends V> vertices) {
		return directedGraph.addEdge(edge, vertices);
	}

	@Override
	public boolean addEdge(E edge, Collection<? extends V> vertices,
			EdgeType edge_type) {
		return directedGraph.addEdge(edge, vertices, edge_type);
	}

	@Override
	public boolean removeVertex(V vertex) {
		return directedGraph.removeVertex(vertex);
	}

	@Override
	public boolean removeEdge(E edge) {
		return directedGraph.removeEdge(edge);
	}

	@Override
	public boolean isNeighbor(V v1, V v2) {
		return directedGraph.isNeighbor(v1, v2);
	}

	@Override
	public boolean isIncident(V vertex, E edge) {
		return directedGraph.isIncident(vertex, edge);
	}

	@Override
	public int degree(V vertex) {
		return directedGraph.degree(vertex);
	}

	@Override
	public int getNeighborCount(V vertex) {
		return directedGraph.getNeighborCount(vertex);
	}

	@Override
	public int getIncidentCount(E edge) {
		return directedGraph.getIncidentCount(edge);
	}

	@Override
	public EdgeType getEdgeType(E edge) {
		return directedGraph.getEdgeType(edge);
	}

	@Override
	public EdgeType getDefaultEdgeType() {
		return directedGraph.getDefaultEdgeType();
	}

	@Override
	public Collection<E> getEdges(EdgeType edge_type) {
		return directedGraph.getEdges(edge_type);
	}

	@Override
	public int getEdgeCount(EdgeType edge_type) {
		return directedGraph.getEdgeCount(edge_type);
	}
	
	public String toString(){
		return directedGraph.toString() + "\n" + edgeWeightMap.toString();
	}

	@Override
	public Double getEdgeWeight(E e) {
		Double weight = edgeWeightMap.get(e);
		if (weight != null) {
			return weight;
		}else{
			return defaultEdgeWeight;
		}
	}

	@Override
	public void setEdgeWeight(E e, double weight) {
		edgeWeightMap.put(e, weight);
	}

	@Override
	public Map<E, Double> getEdgeWeightMap() {
		return edgeWeightMap;
	}
	
	@Override
	public Double getTotalWeight() {
		Double sum = 0.0;
		Collection<Double> weights = getEdgeWeightMap().values();
		for(Double weight : weights){
			sum += weight;
		}
		return sum;
	}

}