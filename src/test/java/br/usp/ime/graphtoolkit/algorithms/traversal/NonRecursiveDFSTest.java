package br.usp.ime.graphtoolkit.algorithms.traversal;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

import br.usp.ime.graphtoolkit.impl.IntegerEdgeFactory;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class NonRecursiveDFSTest {

	@Test
	public final void shouldTraverseAllNodesInUndirectedGraph1() {

		//https://upload.wikimedia.org/wikipedia/commons/thumb/1/1f/Depth-first-tree.svg/450px-Depth-first-tree.svg.png
		NonRecursiveDFS<Integer, Integer> dfs = new NonRecursiveDFS<>();
		
		IntegerEdgeFactory integerEdgeFactory = new IntegerEdgeFactory(1,1);
		
		UndirectedGraph<Integer,Integer> wikiUndirectedGraphExample =
				new UndirectedSparseGraph<>();
		
		wikiUndirectedGraphExample.addEdge(integerEdgeFactory.getNewEdge(), 1, 2);
		wikiUndirectedGraphExample.addEdge(integerEdgeFactory.getNewEdge(), 1, 7);
		wikiUndirectedGraphExample.addEdge(integerEdgeFactory.getNewEdge(), 1, 8);
		wikiUndirectedGraphExample.addEdge(integerEdgeFactory.getNewEdge(), 2, 3);
		wikiUndirectedGraphExample.addEdge(integerEdgeFactory.getNewEdge(), 2, 6);
		wikiUndirectedGraphExample.addEdge(integerEdgeFactory.getNewEdge(), 8, 9);
		wikiUndirectedGraphExample.addEdge(integerEdgeFactory.getNewEdge(), 8, 12);
		wikiUndirectedGraphExample.addEdge(integerEdgeFactory.getNewEdge(), 3, 4);
		wikiUndirectedGraphExample.addEdge(integerEdgeFactory.getNewEdge(), 3, 5);
		wikiUndirectedGraphExample.addEdge(integerEdgeFactory.getNewEdge(), 9, 10);
		wikiUndirectedGraphExample.addEdge(integerEdgeFactory.getNewEdge(), 9, 11);
		
		dfs.traverse(wikiUndirectedGraphExample);
		Integer[] expectedVisitOrder = {1,8,12,9,11,10,7,2,6,3,5,4};
		assertEquals(Arrays.asList(expectedVisitOrder),dfs.getDiscoveredNodes());	
	}
	
	@Test
	public final void shouldTraverseAllNodesInUndirectedGraph2() {

		NonRecursiveDFS<String, String> dfs = new NonRecursiveDFS<>();
		
		//https://en.wikipedia.org/wiki/Depth-first_search#/media/File:Graph.traversal.example.svg
		UndirectedGraph<String,String> wikiStringUndirectedGraphExample =
				new UndirectedSparseGraph<>();


		wikiStringUndirectedGraphExample.addEdge("A---B", "A", "B");
		wikiStringUndirectedGraphExample.addEdge("A---C", "A", "C");
		wikiStringUndirectedGraphExample.addEdge("A---E", "A", "E");
		wikiStringUndirectedGraphExample.addEdge("B---D", "B", "D");
		wikiStringUndirectedGraphExample.addEdge("B---F", "B", "F");
		wikiStringUndirectedGraphExample.addEdge("C---G", "C", "G");
		wikiStringUndirectedGraphExample.addEdge("E---F", "E", "F");

		dfs.traverse(wikiStringUndirectedGraphExample);
		String[] expectedVisitOrder = {"A","E","F","B","D","C","G"};
		assertEquals(Arrays.asList(expectedVisitOrder),dfs.getDiscoveredNodes());
		
		//System.out.println(dfs.getDiscoveredNodes());
		//System.out.println(dfs.getVisitedEdges());
		//System.out.println(dfs.getVisitedNodes());
		System.out.println(dfs.getBackEdges());
	}

}
