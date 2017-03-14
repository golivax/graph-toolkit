package br.usp.ime.graphtoolkit.algorithms.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

import br.usp.ime.graphtoolkit.api.DirectedWeightedGraph;
import br.usp.ime.graphtoolkit.api.UndirectedWeightedGraph;
import br.usp.ime.graphtoolkit.api.WeightedGraph;
import br.usp.ime.graphtoolkit.api.WeightedPath;
import br.usp.ime.graphtoolkit.impl.DirectedWeightedSparseGraph;
import br.usp.ime.graphtoolkit.impl.UndirectedWeightedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class DijkstraAllShortestPath<V,E> {
	
	private boolean ignoreEdgeDirection = false;
	
	public DijkstraAllShortestPath(){
		
	}
	
	public DijkstraAllShortestPath(boolean ignoreEdgeDirection){
		this.ignoreEdgeDirection = ignoreEdgeDirection;
	}
	
	public DijkstraResult<V,E> compute(WeightedGraph<V,E> graph, V source){
		
		Set<V> targets = new HashSet<>();
		
		//Adding null to targets will make the 
		//algorithm bypass the stop condition
		targets.add(null);
		
		DirectedWeightedGraph<V,E> pathRecoveryGraph = 
				_compute(graph, source, targets);
		
		return new DijkstraResult<>(source, pathRecoveryGraph);
	}
	
	public DijkstraResult<V,E> compute(
			WeightedGraph<V,E> graph, V source, Collection<V> targets){
				
		DirectedWeightedGraph<V, E> pathRecoveryGraph = 
				_compute(graph,source,targets);
		
		return new DijkstraResult<>(source, pathRecoveryGraph);
	}
	
	@SafeVarargs
	public final DijkstraResult<V,E> compute(
			WeightedGraph<V,E> graph, V source, V... targets){
		
		List<V> targetList = new ArrayList<>(Arrays.asList(targets));
		return compute(graph, source, targetList);
	}
	
	
	
	public Set<WeightedPath<V,E>> compute(
			WeightedGraph<V,E> graph, V source, V target){
		
		Set<V> targets = new HashSet<>();
		targets.add(target);
		
		DirectedWeightedGraph<V,E> pathRecoveryGraph = 
				_compute(graph,source,targets);
		
		
		PathFinder<V,E> pathFinder = new PathFinder<>();
		
		//Edges are reversed in the path recovery graph, so we search for paths
		//from the target to the source
		Set<WeightedPath<V,E>> paths = pathFinder.findPathsFrom(
				target, source, pathRecoveryGraph);
		
		//Paths back to normal
		for(WeightedPath<V,E> path : paths){
			path.reverse();
		}
		
		return paths;
	}

	/**
	 * Implementation of the Dijkstra algorithm for finding *all* shortest
	 * paths between two nodes in a graph. It uses a priority queue for
	 * high efficiency. It also uses a variant of the original algorithm, which
	 * does not require a "decrease_key" operation. This variation seems more
	 * efficient than the original version in most cases, specially on sparse
	 * graphs (www.cs.sunysb.edu/~rezaul/papers/TR-07-54.pdf). The pseudocode
	 * can be found here www.ccs.neu.edu/course/cs4610/f11/L7/L7.html	
	 */	 
	private DirectedWeightedGraph<V,E> _compute(WeightedGraph<V,E> graph, 
			V source, Collection<V> targets){
		
		Map<V,Double> dist = new HashMap<>();
		Map<V,Set<V>> previous = new HashMap<>();
		
		Set<V> visited = new HashSet<>();
		
		PriorityQueue<V> queue = new PriorityQueue<>(
				graph.getVertexCount(),
				new PriorityComparator(dist));
		
		for(V v : graph.getVertices()){
			dist.put(v, Double.MAX_VALUE); //Infinite
			previous.put(v, new HashSet<V>());
		}
		
		dist.put(source, 0.0);
		queue.add(source);
		
		while(!queue.isEmpty()){
			
			V u = queue.remove();
						
			if(!visited.contains(u)){
				
				visited.add(u);
			
				//Stop condition
				if(visited.containsAll(targets)){
					break;
				}
				
				for(V v : getNextVertices(graph, u)){
					if(!visited.contains(v)){
						
						E edge = getEdge(graph, u, v);
						double edgeWeight = graph.getEdgeWeight(edge);
						
						double alt = dist.get(u) + edgeWeight;
						
						if(alt <= dist.get(v)){
							if (alt < dist.get(v)) previous.get(v).clear();
							previous.get(v).add(u);
							dist.put(v, alt);							
							queue.add(v);
						}
					}
				}
			}
		}
		
		DirectedWeightedGraph<V,E> pathRecoveryGraph = 
				buildPathRecoveryGraph(graph, previous);
		
		return pathRecoveryGraph;
	}
		
	private E getEdge(WeightedGraph<V, E> graph, V u, V v) {
		E edge = graph.findEdge(u, v);
		
		//If the graph is directed and ignoreEdgeDirection is true,
		//then v is a neighbor of u. Hence, we don't know if the 'edge'
		//is u --> v or v --> u. Therefore, if finding u --> v returns null,
		//then we ask for v --> u.
		if (edge == null && ignoreEdgeDirection){
			edge = graph.findEdge(v, u);
		}
		
		return edge;
	}

	private Collection<V> getNextVertices(Graph<V,E> graph, V v){
		
		if(ignoreEdgeDirection) return graph.getNeighbors(v);
		
		EdgeType edgeType = graph.getDefaultEdgeType();
		switch(edgeType){
			case DIRECTED:
				return graph.getSuccessors(v);
			case UNDIRECTED:
				return graph.getNeighbors(v);
				
		}
		return null;
	}
	

	class PriorityComparator implements Comparator<V>{

		private Map<V,Double> dist;
		
		public PriorityComparator(Map<V,Double> dist){
			this.dist = dist;
		}
		
		@Override
		public int compare(V v1, V v2) {
			return dist.get(v1).compareTo(dist.get(v2));
		}
		
	}

	/**
	 * Build graph from the "previous" structure. According to Wikipedia:
	 * When the algorithm completes, previous[] data structure will actually
	 * describe a graph that is a subset of the original graph with some
	 * edges removed. Its key property will be that if the algorithm was run
	 * with some starting node, then every path from that node to any other
	 * node in the new graph will be the shortest path between those nodes
	 * in the original graph, and all paths of that	length from the original
	 * graph will be present in the new graph. Then to actually find all
	 * these shortest paths between two given nodes we would use a path
	 * finding algorithm on the new graph, such as depth-first search.
	 * 
	 * To optimize the process of finding a path from source to target,
	 * I use the idea above and reverse the edges, i.e., I make them point 
	 * from the targets towards the source. This speeds up future searches
	 * a lot, because in the original graph every node is reachable from s. 
	 * However, by reversing the edges, only a few nodes will be reachable
	 * for each target.
	 * source
	 * @param graph 
	 * @param previousMap 
	 */
	private DirectedWeightedGraph<V,E> buildPathRecoveryGraph(
			WeightedGraph<V, E> graph, Map<V, Set<V>> previousMap){
		
		DirectedWeightedGraph<V,E> pathRecGraph = 
				new DirectedWeightedSparseGraph<>();
		
		for(Entry<V,Set<V>> entry : previousMap.entrySet()){
			V v = entry.getKey();
			for(V u : entry.getValue()){
				E e = getEdge(graph, u, v);
				Double weight = graph.getEdgeWeight(e);
				
				//Important step: reversing the edge from the original graph
				pathRecGraph.addEdge(e, v, u);
				pathRecGraph.setEdgeWeight(e, weight);
			}
		}

		return pathRecGraph;
	}

	public static void main(String[] args) {
				
		/**
		
		Sample graph:
		
		         1
		        / \
		       /   \
		7 - - 2- -- 3 - - 5
		|      \   /
		|       \ /
		- -- - - 4 - - 6
		
		*/
		
		
		UndirectedWeightedGraph<Integer,Integer> sample = 
				new UndirectedWeightedSparseGraph<>();
	
		sample.addEdge(1, 1, 2);
		sample.addEdge(2, 1, 3);
		sample.addEdge(3, 2, 7);
		sample.addEdge(4, 2, 3);
		sample.addEdge(5, 3, 5);
		sample.addEdge(6, 7, 4);
		sample.addEdge(7, 2, 4);
		sample.addEdge(8, 3, 4);
		sample.addEdge(9, 4, 6);
		
		
		DijkstraAllShortestPath<Integer,Integer> dijkstra = 
				new DijkstraAllShortestPath<Integer,Integer>();
		
		DijkstraResult<Integer,Integer> result = 
				dijkstra.compute(sample, 1, 7, 4);
		
		System.out.println(result.getShortestPathsTo(7));
		System.out.println(result.getShortestPathsTo(4));
		
		
		/**
		IntegerEdgeFactory edgeFactory = new IntegerEdgeFactory(1, 1);
		
		UndirectedWeightedGraph<String, Integer> graph = 
				new UndirectedWeightedSparseGraph<>();
				
		Integer edge = edgeFactory.getNewEdge();
		graph.addEdge(edge, "v1", "v2");
		graph.setEdgeWeight(edge, 8);
		
		edge = edgeFactory.getNewEdge();
		graph.addEdge(edge, "v1", "v3");
		graph.setEdgeWeight(edge, 9);
		
		edge = edgeFactory.getNewEdge();
		graph.addEdge(edge, "v2", "v5");
		graph.setEdgeWeight(edge, 5);
		
		edge = edgeFactory.getNewEdge();
		graph.addEdge(edge, "v3", "v4");
		graph.setEdgeWeight(edge, 8);
		
		edge = edgeFactory.getNewEdge();
		graph.addEdge(edge, "v1", "u1");
		graph.setEdgeWeight(edge, 2);
		
		edge = edgeFactory.getNewEdge();
		graph.addEdge(edge, "v1", "u3");
		graph.setEdgeWeight(edge, 2);
		
		edge = edgeFactory.getNewEdge();
		graph.addEdge(edge, "v2", "u1");
		graph.setEdgeWeight(edge, 2);
		
		edge = edgeFactory.getNewEdge();
		graph.addEdge(edge, "v2", "u4");
		graph.setEdgeWeight(edge, 8);
		
		edge = edgeFactory.getNewEdge();
		graph.addEdge(edge, "v3", "u2");
		graph.setEdgeWeight(edge, 4);
		
		edge = edgeFactory.getNewEdge();
		graph.addEdge(edge, "v3", "u3");
		graph.setEdgeWeight(edge, 8);
		
		edge = edgeFactory.getNewEdge();
		graph.addEdge(edge, "v4", "u2");
		graph.setEdgeWeight(edge, 3);
		
		edge = edgeFactory.getNewEdge();
		graph.addEdge(edge, "v5", "u2");
		graph.setEdgeWeight(edge, 5);
		
		edge = edgeFactory.getNewEdge();
		graph.addEdge(edge, "v5", "u4");
		graph.setEdgeWeight(edge, 8);
		
		edge = edgeFactory.getNewEdge();
		graph.addEdge(edge, "u1", "u2");
		graph.setEdgeWeight(edge, 1);
		
		DijkstraAllShortestPath<String,Integer> dijkstra = 
				new DijkstraAllShortestPath<>();
		
		Set<WeightedPath<String,Integer>> result = 
				dijkstra.compute(graph, "v1", "v2");
		
		System.out.println(result);
		*/
	}	
}