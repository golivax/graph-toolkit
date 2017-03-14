package br.usp.ime.graphtoolkit.api;

import java.util.Map;

import edu.uci.ics.jung.graph.Graph;

//This is ugly, but there's no way out 
//(only alternative is to change Jung's Interfaces)
public interface WeightedGraph<V,E> extends Graph<V,E>{
	
	public Double getEdgeWeight(E e);
	public void setEdgeWeight(E e, double weight);
	public Map<E,Double> getEdgeWeightMap();
	public Double getTotalWeight();

}
