package br.usp.ime.graphtoolkit.algorithms.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.iterators.ReverseListIterator;

import br.usp.ime.graphtoolkit.algorithms.path.DijkstraAllShortestPath;
import br.usp.ime.graphtoolkit.algorithms.path.DijkstraResult;
import br.usp.ime.graphtoolkit.algorithms.traversal.NonRecursiveDFS;
import br.usp.ime.graphtoolkit.api.UndirectedWeightedGraph;
import br.usp.ime.graphtoolkit.api.WeightedPath;
import br.usp.ime.graphtoolkit.impl.IntegerEdgeFactory;
import br.usp.ime.graphtoolkit.impl.UndirectedWeightedSparseGraph;
import edu.uci.ics.jung.algorithms.shortestpath.MinimumSpanningForest;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.graph.Tree;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;

/**
 * Implementation of Steiner Minimal Tree Algorithm
 * found in http://www.csie.ntu.edu.tw/~kmchao/tree07spr/Steiner.pdf
 * 
 * @author Gustavo Ansaldi Oliva {@link goliva@ime.usp.br}
 *
 * @param <V>
 * @param <E>
 */
public class SteinerMinimalTreeCalculator<V,E> {
	
	/**
	Algorithm: MST-Steiner
	Input: A graph G = (V, E, w) and a terminal set L C V.
	Output: A Steiner tree T.
	1: Construct the metric closure G_L on the terminal set L.
	2: Find an MST T_L on G_L.
	3: T <- Empty
	4: for each edge e = (u, v) belonging to E(T_L) in a DFS order of T_L do 
	4.1: Find a shortest path P from u to v on G.
	4.2: if P contains less than two vertices in T then
	         Add P to T;
	     else
	         Let p_i and p_j be the first and the last vertices already in T;
	         Add subpaths from u to p_i and from p_j to v to T.
	5: Output T.
	*/
	public UndirectedWeightedGraph<V,E> mstSteiner(UndirectedWeightedGraph<V,E> graph, 
			Set<V> terminals){
		
		DijkstraAllShortestPath<V, E> dsp = new DijkstraAllShortestPath<>();
		
		//1:
		UndirectedWeightedGraph<V,Integer> metricClosure = 
				buildMetricClosure(graph, terminals);
		
		//2:
		UndirectedGraph<V,Integer> mstFromMetricClosure = 
				getMinimumSpanningTree(metricClosure);
		
		//3:
		UndirectedWeightedGraph<V,E> steinerTree = 
				new UndirectedWeightedSparseGraph<>();

		//4:
		NonRecursiveDFS<V, Integer> dfs = new NonRecursiveDFS<>();
		dfs.traverse(mstFromMetricClosure);
		for(Integer edge : dfs.getVisitedEdges()){
			
			Pair<V> vertices = mstFromMetricClosure.getEndpoints(edge);
			
			V source = vertices.getFirst();
			V target = vertices.getSecond();
			
			//4.1:			
			Set<WeightedPath<V,E>> shortestPaths = 
					dsp.compute(graph, source, target);
			
			WeightedPath<V,E> smallestShortestPath = 
					getSmallestPath(shortestPaths);
			
			//4.2:
			Collection<V> intersection = CollectionUtils.intersection(
					smallestShortestPath.getVertices(), steinerTree.getVertices());
			
			if(intersection.size() < 2){
				addPathToSteinerTree(smallestShortestPath, steinerTree);
			}
			else{
				addSubpathsToSteinerTree(graph, smallestShortestPath, steinerTree);
			}
		}
		
		return steinerTree;
	}

	private void addSubpathsToSteinerTree(UndirectedWeightedGraph<V, E> graph,
			WeightedPath<V,E> shortestPath, UndirectedWeightedGraph<V, E> steinerTree) {
		
		List<V> vertices = shortestPath.getVertices();
		V u = vertices.get(0);
		V v = vertices.get(vertices.size() - 1);
		
		//Let's now find pi, which is the first vertex from shortestPath 
		//that is already in the tree
		V pi = findPi(shortestPath, steinerTree);
				
		//Let's now find pj, which is the last vertex from shortestPath 
		//that is already in the tree
		V pj = findPj(shortestPath, steinerTree);
		
		//Ok, now we obtain the subpaths
		WeightedPath<V,E> fromUtoPi = shortestPath.getSubpath(u, pi);
		WeightedPath<V,E> fromPjToV = shortestPath.getSubpath(pj, v);
		
		//Let's now add both subpaths to Steiner tree
		addPathToSteinerTree(fromUtoPi, steinerTree);
		addPathToSteinerTree(fromPjToV, steinerTree);
		
	}

	private V findPi(WeightedPath<V, E> shortestPath, 
			UndirectedWeightedGraph<V, E> steinerTree) {
	
		V pi = null;
		for(V w : shortestPath.getVertices()){
			
			//Found pi
			if(steinerTree.containsVertex(w)) {
				pi = w;
				break;
			}
		}
		
		return pi;
	}
	
	private V findPj(WeightedPath<V, E> shortestPath, 
			UndirectedWeightedGraph<V, E> steinerTree) {
			
		V pj = null;
		ReverseListIterator<V> iterator = 
				new ReverseListIterator<>(shortestPath.getVertices());
		
		while(iterator.hasNext()){
			V w = iterator.next();
			
			//Found pj
			if(steinerTree.containsVertex(w)){
				pj = w;
				break;
			}
		}
		
		return pj;
	}
	
	private void addPathToSteinerTree(
			WeightedPath<V, E> shortestPath, UndirectedWeightedGraph<V, E> steinerTree) {
		
		for(E e : shortestPath.getEdges()){
			V source = shortestPath.getSource(e);
			V target = shortestPath.getTarget(e);
			Double weight = shortestPath.getEdgeWeight(e);
			steinerTree.addEdge(e, source, target);
			steinerTree.setEdgeWeight(e, weight);
		}		
	}

	private UndirectedWeightedGraph<V,Integer> buildMetricClosure(
			UndirectedWeightedGraph<V,E> graph, Set<V> terminalsSet) {
		
		List<V> terminalsList = new ArrayList<>(terminalsSet);
		
		UndirectedWeightedGraph<V,Integer> metricClosure = 
				new UndirectedWeightedSparseGraph<>();
				
		IntegerEdgeFactory integerEdgeFactory = new IntegerEdgeFactory(1, 1);
		
		for(int i = 0; i < terminalsList.size() - 1; i++){
				
			V source = terminalsList.get(i);
			List<V> targets = terminalsList.subList(i+1, terminalsList.size());
						
			DijkstraAllShortestPath<V,E> dsp = new DijkstraAllShortestPath<>();
			DijkstraResult<V,E> dijkstraResult = dsp.compute(graph, source, targets);
			
			for(V target : targets){
								
				Set<WeightedPath<V, E>> shortestPaths = 
						dijkstraResult.getShortestPathsTo(target);
				
				/**
				if(shortestPaths.size() > 1){
					System.out.println("Mais que um");
					int size = shortestPaths.iterator().next().getVertexCount();
					for(WeightedPath p : shortestPaths){
						if (p.getVertexCount() != size){
							System.out.println("Fuck!");
						}
					}
				}
				*/
				
				WeightedPath<V, E> smallestShortestPath = 
						getSmallestPath(shortestPaths);
				
				double pathWeight = smallestShortestPath.getWeight();
				
				Integer edge = integerEdgeFactory.getNewEdge();
				metricClosure.addEdge(edge, source, target);
				metricClosure.setEdgeWeight(edge, pathWeight);
			}
		}
		
		return metricClosure;
	}
	
	private UndirectedGraph<V,Integer> getMinimumSpanningTree(
			UndirectedWeightedGraph<V,Integer> metricClosure) {
		
		Forest<V, Integer> forest = 
				new DelegateForest<V, Integer>(
						new DirectedSparseGraph<V,Integer>());
	
		Map<Integer,Double> edgeWeights = metricClosure.getEdgeWeightMap();
		
		MinimumSpanningForest<V, Integer> minimumSpanningForest = 
				new MinimumSpanningForest<V, Integer>(
						metricClosure, forest, null, edgeWeights);
		
		//Don't know if forest is already populated after invoking the 
		//constructor above. In any case, I'm following the API and calling
		//getForest()
		forest = minimumSpanningForest.getForest();
		
		//As the input graph is a complete graph, then we should have a single
		//minimum spanning tree
		Tree<V, Integer> directedMst = forest.getTrees().iterator().next();
		
		//It turns out that Jung outputs a directed tree (i.e., a tree backed up
		//by a directed graph). So we should transform it into an undirected
		//tree (graph)
		UndirectedGraph<V,Integer> undirectedMst = 
				getUndirectedTree(directedMst);
		
		return undirectedMst;
	}
	
	
	private UndirectedGraph<V, Integer> getUndirectedTree(
			Tree<V, Integer> directedMst) {
		
		UndirectedGraph<V,Integer> undirectedTree = 
				new UndirectedSparseGraph<>();
				
		for(Integer edge : directedMst.getEdges()){
			V source = directedMst.getSource(edge);
			V target = directedMst.getDest(edge);
			undirectedTree.addEdge(edge, source, target);
		}
		
		return undirectedTree;
	}

	private WeightedPath<V,E> getSmallestPath(Set<WeightedPath<V,E>> paths){
		
		if(paths.size() > 1){
			//System.out.println("More than 1 shortest path!");
		}
				
		WeightedPath<V,E> smallestPath = null;
		Integer minSize = Integer.MAX_VALUE;
		
		for(WeightedPath<V,E> path : paths){
			
			if(path.getEdgeCount() < minSize){
				minSize = path.getEdgeCount();
				smallestPath = path;
			}
		}
		
		return smallestPath;
	}
	
	public static void main(String[] args) {
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
		
		//Test SteinerMinimalTree
		SteinerMinimalTreeCalculator<String, Integer> smt = 
				new SteinerMinimalTreeCalculator<>();
				
		Set<String> terminals = new HashSet<>();
		terminals.add("v1");
		terminals.add("v2");
		terminals.add("v3");
		terminals.add("v4");
		terminals.add("v5");
		
		UndirectedWeightedGraph<String,Integer> steinerTree = 
				smt.mstSteiner(graph, terminals);
		
		System.out.println(steinerTree);
		System.out.println(steinerTree.getEdgeWeightMap());
		System.out.println(steinerTree.getTotalWeight());
		
	}
}
