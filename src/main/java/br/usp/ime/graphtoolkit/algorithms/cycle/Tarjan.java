package br.usp.ime.graphtoolkit.algorithms.cycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import br.usp.ime.graphtoolkit.impl.DirectedWeightedSparseGraph;
import edu.uci.ics.jung.graph.DirectedGraph;

public class Tarjan<V,E> {

   private Map<V, Integer> indexMap;
   private Map<V, Integer> lowLinkMap;
   
   private int index = 0;
   private Stack<V> stack = new Stack<V>();
   
   private List<List<V>> sccs = new ArrayList<List<V>>();

   public Tarjan(){
	   
   }
   
   public List<List<V>> calculate(
		   DirectedGraph<V,E> graph){
	   
	   indexMap = new HashMap<V, Integer>();
	   lowLinkMap = new HashMap<V, Integer>();
	   
	   //Initialize the maps
	   for(V v : graph.getVertices()){
		   indexMap.put(v, -1);
		   lowLinkMap.put(v, -1);
	   }
	   
	   //Initialize the maps
	   for(V v : graph.getVertices()){
		  if(indexMap.get(v) == -1){
			  calculate(graph,v);
		  }
	   }
	   
	   return sccs;
   }
   
   private void calculate(DirectedGraph<V,E> graph, V v){
	   	   
	   indexMap.put(v, index);
	   lowLinkMap.put(v, index);
	   
       index++;
       stack.push(v);
       
       for(V w : graph.getSuccessors(v)){
    	   
    	   if(indexMap.get(w) == -1){
    		   calculate(graph, w);    		   
    		   int min = Math.min(lowLinkMap.get(v), lowLinkMap.get(w));    		   
	           lowLinkMap.put(v, min);
	       }else if(stack.contains(w)){
	    	   int min = Math.min(lowLinkMap.get(v), indexMap.get(w));
	    	   lowLinkMap.put(v, min);
	       }
       }
       
       if(lowLinkMap.get(v).intValue() == indexMap.get(v).intValue()){
                 	   
           V w;
           ArrayList<V> scc = new ArrayList<V>();
           do{
               w = stack.pop();
               scc.add(w);
           }while(!w.equals(v));
           sccs.add(scc);
       }
     
   }
   
   public static void main(String[] args) {
	   
	   DirectedGraph<String, Integer> g = 
			   new DirectedWeightedSparseGraph<>();
	   
	   g.addVertex("a");
	   g.addVertex("b");
	   g.addVertex("c");
	   g.addVertex("d");
	   g.addVertex("e");
	   g.addVertex("f");
	   g.addVertex("g");
	   g.addVertex("h");
	   
	   g.addEdge(1, "a", "b");
	   g.addEdge(2, "b", "c");
	   g.addEdge(3, "c", "d");
	   g.addEdge(4, "d", "c");
	   
	   g.addEdge(5, "e", "a");
	   g.addEdge(6, "b", "e");
	   g.addEdge(7, "b", "f");
	   g.addEdge(8, "c", "g");
	   g.addEdge(9, "d", "h");
	   g.addEdge(10, "h", "d");
	   
	   g.addEdge(11, "e", "f");
	   g.addEdge(12, "f", "g");
	   g.addEdge(13, "g", "f");
	   g.addEdge(14, "h", "g");
	   
	   
	   g.addVertex("i");
	   g.addVertex("j");
	   g.addVertex("k");
	   g.addVertex("l");
	   g.addVertex("m");
	   g.addVertex("n");
	   g.addVertex("o");
	   g.addVertex("p");
	   g.addVertex("q");
	   g.addVertex("r");
	   g.addVertex("s");
	   g.addVertex("t");
	   
	   g.addEdge(15, "i", "m");
	   g.addEdge(16, "j", "i");
	   g.addEdge(17, "k", "j");
	   g.addEdge(18, "k", "l");
	   
	   g.addEdge(19, "l", "k");
	   g.addEdge(20, "m", "j");
	   g.addEdge(21, "n", "m");
	   g.addEdge(22, "n", "j");
	   g.addEdge(23, "n", "o");
	   g.addEdge(24, "o", "n");
	   
	   g.addEdge(25, "o", "k");
	   g.addEdge(26, "p", "o");
	   g.addEdge(27, "p", "p");
	   
	   g.addEdge(28, "p", "q");
	   g.addEdge(29, "q", "r");
	   g.addEdge(30, "r", "s");
	   g.addEdge(31, "s", "t");
	 
	   Tarjan t = new Tarjan();
	   System.out.println(t.calculate(g));
	   
	   
	   /**
	   JDX jdx = new JDX();
	   DependencyReport dependencyReport = jdx.calculateDepsFrom(
			   "c:/tmp/ase2015/9/workspace1", true, "*.java", new JavaAPIMatcher(), true);
	   
	   DirectedWeightedGraph<CompUnit, CompUnitMetaDependency> compUnitDepGraph = 
				new DirectedWeightedSparseGraph<>();
				
		for(CompUnitMetaDependency cuMetaDep : dependencyReport.getCompUnitMetaDependencies()){
			CompUnit client = cuMetaDep.getClient();
			CompUnit supplier = cuMetaDep.getSupplier();
			int strength = cuMetaDep.getStrength();
			
			compUnitDepGraph.addEdge(cuMetaDep, client, supplier);
			compUnitDepGraph.setEdgeWeight(cuMetaDep, strength);				
		}
	   
		int totalNodes = 0;
		int max = 0;
		Tarjan<CompUnit,CompUnitMetaDependency> t = new Tarjan<>();
		for(List<CompUnit> scc : t.calculate(compUnitDepGraph)){
			totalNodes += scc.size();
			if(scc.size() > max) max = scc.size();
		}
	    System.out.println(totalNodes);
	    System.out.println(max);
	    */
   }
}