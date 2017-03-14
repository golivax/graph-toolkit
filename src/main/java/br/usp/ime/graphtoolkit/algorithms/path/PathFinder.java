package br.usp.ime.graphtoolkit.algorithms.path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import br.usp.ime.graphtoolkit.api.Path;
import br.usp.ime.graphtoolkit.api.WeightedGraph;
import br.usp.ime.graphtoolkit.api.WeightedPath;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;

public class PathFinder<V,E> {

	public Set<Path<V,E>> findPathsFrom(V source, V target, 
			Graph<V, E> graph){

		Set<Path<V,E>> paths = new HashSet<>();

		Set<List<V>> vertexSeqs = _findPathsFrom(source, target, graph);
		for(List<V> vertexSeq : vertexSeqs){
			Path<V,E> path = new SimplePath<>(graph, vertexSeq);
			paths.add(path);
		}
		
		return paths;
	}
	
	public Set<WeightedPath<V,E>> findPathsFrom(V source, V target, 
			WeightedGraph<V, E> graph){

		Set<WeightedPath<V,E>> paths = new HashSet<>();
				
		Set<List<V>> vertexSeqs = _findPathsFrom(source, target, graph);
		for(List<V> vertexSeq : vertexSeqs){
			WeightedPath<V,E> path = new SimpleWeightedPath<>(graph, vertexSeq);
			paths.add(path);
		}
		
		return paths;
	}
	
	private Set<List<V>> _findPathsFrom(V source, V target, Graph<V,E> graph){
		
		Stack<V> stack = new Stack<>();
		Set<List<V>> vertexSeqs = new HashSet<List<V>>();
		_findPathsFrom(source, target, graph, stack, vertexSeqs);
		
		return vertexSeqs;
	}
	
	private void _findPathsFrom(V current, V destination, 
			Graph<V, E> graph, Stack<V> stack, Set<List<V>> paths){
		
		stack.push(current);
		for(V v : graph.getSuccessors(current)){
			if (v.equals(destination)){
				List<V> vertexSeq = new ArrayList<V>(stack);
				vertexSeq.add(destination);
				paths.add(vertexSeq);
			}
			//This if is needed to handle cycles
			else if(!stack.contains(v)){
				_findPathsFrom(v, destination, graph, stack, paths);
			}
		}
		stack.pop();
	}
	
	//FIXME: Tentative de escrever o metodo acima sem recursao.
	//Acho que estou quase lá....
	private void _findPathsFrom2(V current, V destination, 
				Graph<V, E> graph, Stack<V> stack, Set<List<V>> paths){
			
		Map<V,List<V>> map = new HashMap<>();
		List<V> path = new Stack<>();
		
		stack.push(current);
		
		while(!stack.isEmpty()){
			
			//System.out.println("stack: " + stack);
			V v = stack.pop();
			
			//System.out.println("Visit: " + v);
			path.add(v);
			
			if(v.equals(destination)){
				map.get(v).add(destination);
				System.out.println("Destination: " + map.get(v));
			}
			
			for (V w : graph.getSuccessors(v)){
				map.put(w, new ArrayList<>(path));
				stack.push(w);
			}	
			
			if(graph.getSuccessors(v).isEmpty() && !stack.isEmpty()){
				V w = stack.peek();
				path = map.get(w);
			}
		}
	}
	
	public static void main(String[] args) {
		
		Graph<String, Integer> graph = new DirectedSparseGraph<>();
		
		String a = new String("A");
		String b = new String("B");
		String c = new String("C");
		String d = new String("D");
		String e = new String("E");
		String f = new String("F");
		String g = new String("G");
		String h = new String("H");
		String i = new String("I");
		String j = new String("J");
		String k = new String("K");
		String l = new String("L");
		
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		graph.addVertex(f);
		graph.addVertex(g);
		graph.addVertex(h);
		graph.addVertex(i);
		graph.addVertex(j);
		graph.addVertex(k);
		graph.addVertex(l);
	
		graph.addEdge(1, a, b);
		graph.addEdge(2, a, e);
		graph.addEdge(3, b, c);
		graph.addEdge(4, b, e);
		graph.addEdge(5, c, d);
		graph.addEdge(6, c, e);
		graph.addEdge(7, d, e);
		graph.addEdge(8, d, g);
		graph.addEdge(9, d, i);
		graph.addEdge(10, f, e);
		graph.addEdge(11, f, j);
		graph.addEdge(12, g, e);
		graph.addEdge(13, g, f);
		graph.addEdge(14, h, d);
		graph.addEdge(15, h, k);
		graph.addEdge(16, i, k);
		graph.addEdge(17, i, l);
		graph.addEdge(18, j, h);
		graph.addEdge(19, j, k);
		
		Set<String> set = new HashSet<>();
		System.out.println(set.containsAll(graph.getSuccessors(k)));
		
		PathFinder<String, Integer> pathFinder = 
				new PathFinder<String, Integer>();
		
		System.out.println(pathFinder.findPathsFrom(a,e,graph));
		
		
	
		
		pathFinder._findPathsFrom2(a, e, graph, new Stack<String>(), new HashSet());
	}
}
