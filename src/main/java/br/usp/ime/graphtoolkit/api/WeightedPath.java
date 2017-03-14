package br.usp.ime.graphtoolkit.api;

public interface WeightedPath<V,E> extends Path<V,E>{

	public Double getEdgeWeight(V v1, V v2);
	
	public Double getEdgeWeight(E e);
	
	public Double getWeight();
	
	public WeightedPath<V,E> getSubpath(V from, V to);
	
}
