package br.usp.ime.graphtoolkit.algorithms.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

public class RootedTreeUtils<V,E> {

	public RootedTreeUtils(){
		
	}
	
	public int height(DirectedGraph<V,E> graph, V v){

		List<Integer> heights = new ArrayList<>();
		
		if(graph.getSuccessorCount(v) == 0){
			heights.add(0);
		}
		else{
			for(V w : graph.getSuccessors(v)){
				heights.add(height(graph, w));
			}
		}
		
		return 1 + Collections.max(heights);

	}
	
	public int diameter(DirectedGraph<V,E> graph, V v){
		int[] ret = diameter_(graph,v);
		return ret[1];		
	}
	
	//Produces graph.vertexCount() recursions
	private int[] diameter_(DirectedGraph<V,E> graph, V v){
		
		int ret[] = new int[2];
		
		List<int[]> results = new ArrayList<>();
		
		if(graph.getSuccessorCount(v) == 0){
			int zeroRet[] = new int[2];
			zeroRet[0] = 0;
			zeroRet[1] = 0;
			results.add(zeroRet);
		}
	
		for(V w : graph.getSuccessors(v)){
			results.add(diameter_(graph, w));
		}

		List<Integer> diameters = new ArrayList<>();
		List<Integer> heights = new ArrayList<>();
		for(int[] result : results){
			heights.add(result[0]);
			diameters.add(result[1]);
		}

		Collections.sort(heights);
		Collections.sort(diameters);

		int maxH1 = heights.get(heights.size() - 1);
		int maxPath = maxH1;
		if(heights.size() > 1){
			int maxH2 = heights.get(heights.size() - 2);
			maxPath = maxH1 + maxH2;
		}

		int maxDiameter = diameters.get(diameters.size() - 1);

		ret[0] = 1 + maxH1;
		ret[1] = Math.max(maxPath,maxDiameter);

		return ret;	
	}

	public static void main(String[] args) {
		DirectedGraph<Integer, Integer> tree = 
				new DirectedSparseGraph<Integer,Integer>();
		
		tree.addEdge(1, 1, 2);
		tree.addEdge(2, 2, 3);
		tree.addEdge(3, 3, 4);
		tree.addEdge(4, 3, 5);
		tree.addEdge(5, 5, 6);
		tree.addEdge(6, 6, 7);
		tree.addEdge(7, 6, 8);
		tree.addEdge(8, 2, 9);
		tree.addEdge(9, 9, 10);
		tree.addEdge(10, 10, 11);
		tree.addEdge(11, 10, 12);
		tree.addEdge(12, 12, 13);
		tree.addEdge(13, 1, 14);
		tree.addEdge(14, 14, 15);
		
		RootedTreeUtils<Integer, Integer> treeUtils = new RootedTreeUtils<>();
		System.out.println(treeUtils.diameter(tree, 1));
		//System.out.println(treeUtils.height(tree, 1));
	}
}