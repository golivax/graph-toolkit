package br.usp.ime.graphtoolkit.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.usp.ime.graphtoolkit.api.UndirectedWeightedGraph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class UndirectedWeightedSparseGraph<V,E> implements 
	UndirectedWeightedGraph<V, E>{

	protected UndirectedGraph<V,E> undirectedGraph;
	protected Map<E,Double> edgeWeightMap;
	protected Double defaultEdgeWeight = 1.0;
	
	public UndirectedWeightedSparseGraph(){
		this.undirectedGraph = new UndirectedSparseGraph<>();
		this.edgeWeightMap = new HashMap<>();
	}
	
	public UndirectedWeightedSparseGraph(UndirectedGraph<V,E> undirectedGraph){
		this.undirectedGraph = undirectedGraph;
		this.edgeWeightMap = new HashMap<>();
	}
	
	@Override
	public Collection<E> getInEdges(V vertex) {
		return undirectedGraph.getInEdges(vertex);
	}

	@Override
	public Collection<E> getOutEdges(V vertex) {
		return undirectedGraph.getOutEdges(vertex);
	}

	@Override
	public Collection<V> getPredecessors(V vertex) {
		return undirectedGraph.getPredecessors(vertex);
	}

	@Override
	public Collection<V> getSuccessors(V vertex) {
		return undirectedGraph.getSuccessors(vertex);
	}

	@Override
	public int inDegree(V vertex) {
		return undirectedGraph.inDegree(vertex);
	}

	@Override
	public int outDegree(V vertex) {
		return undirectedGraph.outDegree(vertex);
	}

	@Override
	public boolean isPredecessor(V v1, V v2) {
		return undirectedGraph.isPredecessor(v1, v2);
	}

	@Override
	public boolean isSuccessor(V v1, V v2) {
		return undirectedGraph.isSuccessor(v1, v2);
	}

	@Override
	public int getPredecessorCount(V vertex) {
		return undirectedGraph.getPredecessorCount(vertex);
	}

	@Override
	public int getSuccessorCount(V vertex) {
		return undirectedGraph.getSuccessorCount(vertex);
	}

	@Override
	public V getSource(E directed_edge) {
		return undirectedGraph.getSource(directed_edge);
	}

	@Override
	public V getDest(E directed_edge) {
		return undirectedGraph.getDest(directed_edge);
	}

	@Override
	public boolean isSource(V vertex, E edge) {
		return undirectedGraph.isSource(vertex, edge);
	}

	@Override
	public boolean isDest(V vertex, E edge) {
		return undirectedGraph.isDest(vertex, edge);
	}

	@Override
	public boolean addEdge(E e, V v1, V v2) {
		return undirectedGraph.addEdge(e, v1, v2);
	}

	@Override
	public boolean addEdge(E e, V v1, V v2, EdgeType edgeType) {
		return undirectedGraph.addEdge(e, v1, v2, edgeType);
	}

	@Override
	public Pair<V> getEndpoints(E edge) {
		return undirectedGraph.getEndpoints(edge);
	}

	@Override
	public V getOpposite(V vertex, E edge) {
		return undirectedGraph.getOpposite(vertex, edge);
	}

	@Override
	public Collection<E> getEdges() {
		return undirectedGraph.getEdges();
	}

	@Override
	public Collection<V> getVertices() {
		return undirectedGraph.getVertices();
	}

	@Override
	public boolean containsVertex(V vertex) {
		return undirectedGraph.containsVertex(vertex);
	}

	@Override
	public boolean containsEdge(E edge) {
		return undirectedGraph.containsEdge(edge);
	}

	@Override
	public int getEdgeCount() {
		return undirectedGraph.getEdgeCount();
	}

	@Override
	public int getVertexCount() {
		return undirectedGraph.getVertexCount();
	}

	@Override
	public Collection<V> getNeighbors(V vertex) {
		return undirectedGraph.getNeighbors(vertex);
	}

	@Override
	public Collection<E> getIncidentEdges(V vertex) {
		return undirectedGraph.getIncidentEdges(vertex);
	}

	@Override
	public Collection<V> getIncidentVertices(E edge) {
		return undirectedGraph.getIncidentVertices(edge);
	}

	@Override
	public E findEdge(V v1, V v2) {
		return undirectedGraph.findEdge(v1, v2);
	}

	@Override
	public Collection<E> findEdgeSet(V v1, V v2) {
		return undirectedGraph.findEdgeSet(v1, v2);
	}

	@Override
	public boolean addVertex(V vertex) {
		return undirectedGraph.addVertex(vertex);
	}

	@Override
	public boolean addEdge(E edge, Collection<? extends V> vertices) {
		return undirectedGraph.addEdge(edge, vertices);
	}

	@Override
	public boolean addEdge(E edge, Collection<? extends V> vertices,
			EdgeType edge_type) {
		return undirectedGraph.addEdge(edge, vertices, edge_type);
	}

	@Override
	public boolean removeVertex(V vertex) {
		return undirectedGraph.removeVertex(vertex);
	}

	@Override
	public boolean removeEdge(E edge) {
		return undirectedGraph.removeEdge(edge);
	}

	@Override
	public boolean isNeighbor(V v1, V v2) {
		return undirectedGraph.isNeighbor(v1, v2);
	}

	@Override
	public boolean isIncident(V vertex, E edge) {
		return undirectedGraph.isIncident(vertex, edge);
	}

	@Override
	public int degree(V vertex) {
		return undirectedGraph.degree(vertex);
	}

	@Override
	public int getNeighborCount(V vertex) {
		return undirectedGraph.getNeighborCount(vertex);
	}

	@Override
	public int getIncidentCount(E edge) {
		return undirectedGraph.getIncidentCount(edge);
	}

	@Override
	public EdgeType getEdgeType(E edge) {
		return undirectedGraph.getEdgeType(edge);
	}

	@Override
	public EdgeType getDefaultEdgeType() {
		return undirectedGraph.getDefaultEdgeType();
	}

	@Override
	public Collection<E> getEdges(EdgeType edge_type) {
		return undirectedGraph.getEdges(edge_type);
	}

	@Override
	public int getEdgeCount(EdgeType edge_type) {
		return undirectedGraph.getEdgeCount(edge_type);
	}
	
	public String toString(){
		return undirectedGraph.toString();
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