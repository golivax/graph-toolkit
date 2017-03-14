package br.usp.ime.graphtoolkit.algorithms.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.usp.ime.graphtoolkit.api.DirectedWeightedGraph;
import br.usp.ime.graphtoolkit.api.Path;
import br.usp.ime.graphtoolkit.impl.DirectedWeightedSparseGraph;
import edu.uci.ics.jung.graph.Graph;

public class SimplePath<V,E> implements Path<V,E>{
	
	protected DirectedWeightedGraph<V, E> pathAsGraph;
	
	protected List<V> vertices;
	protected List<E> edges;
		
	public SimplePath(Graph<V, E> graph, List<V> vertexSeq){
				
		pathAsGraph = new DirectedWeightedSparseGraph<>();
		
		this.vertices = new ArrayList<>(vertexSeq);
		this.edges = new ArrayList<>();
				
		for(int i = 0; i < (vertexSeq.size() - 1); i++){
			V source = vertexSeq.get(i);
			V target = vertexSeq.get(i+1);
			E edge = graph.findEdge(source, target);
			
			edges.add(edge);
			pathAsGraph.addEdge(edge, source, target);
		}
		
	}

	@Override
	public List<V> getVertices() {
		return vertices;
	}

	@Override
	public List<E> getEdges() {
		return edges;
	}

	@Override
	public E getEdge(V v1, V v2) {
		return pathAsGraph.findEdge(v1,v2);
	}	
	
	public String toString(){
		return vertices.toString();
	}
	
	@Override
	public V getSource(E e){
		return pathAsGraph.getSource(e);
	}
	
	@Override
	public V getTarget(E e){
		return pathAsGraph.getDest(e);
	}

	@Override
	public Path<V, E> getSubpath(V from, V to) {
		List<V> vertices = getVertices();
		int fromIndex = vertices.indexOf(from);
		int toIndex = vertices.indexOf(to);
		List<V> vertexSeq = vertices.subList(fromIndex, toIndex+1);
		
		Path<V,E> subpath = new SimplePath<>(pathAsGraph, vertexSeq);
		
		return subpath;
	}

	@Override
	public Integer getVertexCount() {
		return vertices.size();
	}

	@Override
	public Integer getEdgeCount() {
		return edges.size();
	}

	@Override
	public void reverse() {
		Collections.reverse(vertices);
		Collections.reverse(edges);
		
		DirectedWeightedGraph<V, E> newPathAsGraph = 
				new DirectedWeightedSparseGraph<>();
				
		for(E e : edges){
			V source = pathAsGraph.getSource(e);
			V target = pathAsGraph.getDest(e);
			newPathAsGraph.addEdge(e, target, source);
		}
		
		this.pathAsGraph = newPathAsGraph;
	}
	
	
}