package br.usp.ime.graphtoolkit.api;

import edu.uci.ics.jung.graph.DirectedGraph;

//This is ugly, but there's no way out 
//(only alternative is to change Jung's Interfaces)
public interface DirectedWeightedGraph<V,E> extends DirectedGraph<V, E>, 
	WeightedGraph<V, E>{
	
}
