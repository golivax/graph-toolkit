package br.usp.ime.graphtoolkit.algorithms.path;

import java.util.Set;

import br.usp.ime.graphtoolkit.api.DirectedWeightedGraph;
import br.usp.ime.graphtoolkit.api.WeightedPath;

public class DijkstraResult<V,E> {

	private V source;
	private DirectedWeightedGraph<V, E> pathRecoveryGraph;

	public DijkstraResult(V source, DirectedWeightedGraph<V,E> pathRecoveryGraph){
		this.source = source;
		this.pathRecoveryGraph = pathRecoveryGraph;
	}
	
	public Set<WeightedPath<V,E>> getShortestPathsTo(V target) {
				
		PathFinder<V, E> pathFinder = new PathFinder<>();
				
		//Edges are reversed in the path recovery graph, so we search for paths
		//from the target to the source
		Set<WeightedPath<V, E>> paths = 
				pathFinder.findPathsFrom(target,source,pathRecoveryGraph);
				
		//Paths back to normal
		for(WeightedPath<V,E> path : paths){
			path.reverse();
		}

		return paths;
	}
	
	public V getSource(){
		return source;
	}
	
}
