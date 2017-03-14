package br.usp.ime.graphtoolkit.api;

import java.util.List;

public interface Path<V,E>{

	public List<V> getVertices();
	
	public Integer getVertexCount();
	
	public List<E> getEdges();
	
	public Integer getEdgeCount();
	
	public E getEdge(V v1, V v2);

	public V getSource(E e);
	
	public V getTarget(E e);
	
	public Path<V,E> getSubpath(V from, V to);
	
	public void reverse();
	
}
