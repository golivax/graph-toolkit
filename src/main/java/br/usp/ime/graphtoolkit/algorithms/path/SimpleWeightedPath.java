package br.usp.ime.graphtoolkit.algorithms.path;

import java.util.List;

import br.usp.ime.graphtoolkit.api.WeightedGraph;
import br.usp.ime.graphtoolkit.api.WeightedPath;

public class SimpleWeightedPath<V,E> extends SimplePath<V,E> implements WeightedPath<V, E>{

	public SimpleWeightedPath(WeightedGraph<V, E> graph, List<V> vertexSeq) {
		super(graph, vertexSeq);
		
		for(E edge : edges){
			pathAsGraph.setEdgeWeight(edge, graph.getEdgeWeight(edge));	
		}		
	}

	@Override
	public Double getEdgeWeight(V v1, V v2) {
		E e = this.getEdge(v1, v2);
		return this.getEdgeWeight(e);
	}

	@Override
	public Double getEdgeWeight(E e) {
		return pathAsGraph.getEdgeWeight(e);
	}

	@Override
	public Double getWeight() {
		double weight = 0;
		for(E e : edges){
			weight += this.getEdgeWeight(e);
		}
		return weight;
	}

	@Override
	public WeightedPath<V, E> getSubpath(V from, V to){
		
		List<V> vertices = getVertices();
		int fromIndex = vertices.indexOf(from);
		int toIndex = vertices.indexOf(to);
		List<V> vertexSeq = vertices.subList(fromIndex, toIndex+1);
		
		WeightedPath<V,E> subpath = new SimpleWeightedPath<>(
				pathAsGraph, vertexSeq);
		
		return subpath;
	}
	
}