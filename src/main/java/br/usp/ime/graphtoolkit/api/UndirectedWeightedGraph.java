package br.usp.ime.graphtoolkit.api;

import edu.uci.ics.jung.graph.UndirectedGraph;

//This is ugly, but there's no way out 
//(only alternative is to change Jung's Interfaces)
public interface UndirectedWeightedGraph<V,E> extends UndirectedGraph<V, E>, 
	WeightedGraph<V, E>{

}
