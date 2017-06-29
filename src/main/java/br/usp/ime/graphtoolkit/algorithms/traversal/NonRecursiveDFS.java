package br.usp.ime.graphtoolkit.algorithms.traversal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import edu.uci.ics.jung.graph.Graph;

/**
 * Non-recursive Depth-First Search (DFS)
 *
 * @param <V> Vertex type
 * @param <E> Edge type
 */
public class NonRecursiveDFS<V,E> {
	
	private List<V> discoveredNodes = new LinkedList<>();
	private List<V> visitedNodes = new LinkedList<>();
	private List<E> visitedEdges = new LinkedList<>();
	private List<E> backEdges = new LinkedList<>();
	
	public void traverse(Graph<V,E> graph){
		
		for(V v : graph.getVertices()){
			
			if (!visitedNodes.contains(v)){
				newTraversal(v);
				traverse(graph, v);
			}
		}
	}


	public void traverse(Graph<V,E> graph, V root){
			
		Map<V,V> previousMap = new HashMap<>();
				
		Stack<V> stack = new Stack<V>();
		stack.push(root);
		
		while(!stack.isEmpty()){
			
			V v = stack.pop();
			
			visitNode(root,v);
			visitedNodes.add(v);
			
			//Recover the edge
			if(previousMap.containsKey(v)){
				V w = previousMap.get(v);
				E e = graph.findEdge(w, v);
				
				visitEdge(root,e);
				visitedEdges.add(e);
			}
			
			if(!discoveredNodes.contains(v)){
				
				discoverNode(root, v);
				discoveredNodes.add(v);
				
				for (V w : graph.getSuccessors(v)){
					
					//Back edge if w is an already visited node and w is not the "parent" of v 
					if(discoveredNodes.contains(w) && w != previousMap.get(v)){
						E e = graph.findEdge(v, w);
						visitBackEdge(root,e);
						backEdges.add(e);
					}
					
					stack.push(w);
					previousMap.put(w, v);
				}	
			}			
		}
	}
	
	//FIXME: Replace by logger.debug
	protected void newTraversal(V v) {
		System.out.println("New traversal starting at " + v);
		
	}

	protected void visitNode(V root, V v) {
		System.out.println("Visiting node " + v + " in traversal that started from " + root);
		
	}

	protected void visitEdge(V root, E e) {
		System.out.println("Visiting edge " + e + " in traversal that started from " + root);
		
	}

	protected void discoverNode(V root, V v) {
		System.out.println("Discovering node " + v + " in traversal that started from " + root);
		
	}

	protected void visitBackEdge(V root, E e) {
		System.out.println("Visiting back edge " + e + " in traversal that started from " + root);
		
	}
	
	
	public List<V> getDiscoveredNodes(){
		return discoveredNodes;
	}
	
	public List<V> getVisitedNodes(){
		return visitedNodes;
	}
	
	public List<E> getVisitedEdges(){
		return visitedEdges;
	}
	
	public List<E> getBackEdges(){
		return backEdges;
	}
	
	public void reset() {
		this.discoveredNodes = new LinkedList<>();
		this.visitedNodes = new LinkedList<>();
		this.visitedEdges = new LinkedList<>();
		this.backEdges = new LinkedList<>();
	}
	
	public static void main(String[] args) {
		
		/**
		DirectedGraph<String, Integer> graph = 
				new DirectedSparseGraph<String, Integer>();
		
		String u = new String("u");
		String v = new String("v");
		String w = new String("w");
		String x = new String("x");
		String y = new String("y");
		String z = new String("z");
		
		graph.addEdge(1, u, v);
		graph.addEdge(2, u, x);
		graph.addEdge(3, v, y);
		graph.addEdge(4, w, y);
		graph.addEdge(5, w, z);
		graph.addEdge(6, x, v);
		graph.addEdge(7, y, x);
		graph.addEdge(8, z, z);
		*/
		
		/**
		String v1 = new String("v1");
		String v2 = new String("v2");
		String v3 = new String("v3");
		String v4 = new String("v4");
		String v5 = new String("v5");
		
		graph.addEdge(1,v1,v4);
		graph.addEdge(2,v1,v2);
		graph.addEdge(3,v2,v3);
		graph.addEdge(4,v2,v5);
		*/
		
		/**
		UndirectedWeightedGraph<String, Integer> graph = new UndirectedWeightedSparseGraph<>();
		
		graph.addEdge(1,"org.apache.tomcat.util.http.ServerCookie.maybeQuote2(int, StringBuffer, String)", "org.apache.tomcat.util.http.ServerCookie.alreadyQuoted(String)"); 
		graph.addEdge(35,"org.apache.tomcat.util.http.ServerCookie.escapeDoubleQuotes(String, int, int)", "org.apache.tomcat.util.http.ServerCookie.isToken2(String)");
		graph.addEdge(33,"org.apache.tomcat.util.http.ServerCookie.escapeDoubleQuotes(String, int, int)", "org.apache.catalina.connector.Request.parseCookies()");
		graph.addEdge(3,"org.apache.tomcat.util.http.ServerCookie.escapeDoubleQuotes(String, int, int)", "org.apache.tomcat.util.http.ServerCookie.maybeQuote2(int, StringBuffer, String)"); 
		graph.addEdge(65,"org.apache.tomcat.util.http.ServerCookie.appendCookieValue(StringBuffer, int, String, String, String, String, String, int, boolean)", "org.apache.catalina.connector.Response.addCookieInternal(Cookie)"); 
		graph.addEdge(5,"org.apache.tomcat.util.http.ServerCookie.maybeQuote2(int, StringBuffer, String)", "org.apache.tomcat.util.http.ServerCookie.containsCTL(String, int)");
		graph.addEdge(52,"org.apache.catalina.connector.Request.parseCookies()", "org.apache.catalina.connector.Request.unescape(String)");
		graph.addEdge(22,"org.apache.tomcat.util.http.ServerCookie.escapeDoubleQuotes(String, int, int)", "org.apache.tomcat.util.http.Cookies.processCookieHeader(byte[], int, int)"); 
		graph.addEdge(37,"org.apache.tomcat.util.http.ServerCookie.maybeQuote(int, StringBuffer, String)", "org.apache.tomcat.util.http.ServerCookie.escapeDoubleQuotes(String, int, int)"); 
		graph.addEdge(11,"org.apache.tomcat.util.http.ServerCookie.maybeQuote2(int, StringBuffer, String)", "org.apache.tomcat.util.http.ServerCookie.appendCookieValue(StringBuffer, int, String, String, String, String, String, int, boolean)"); 
		graph.addEdge(44,"org.apache.tomcat.util.http.ServerCookie.isToken(String)", "org.apache.tomcat.util.http.ServerCookie.maybeQuote(int, StringBuffer, String)");
		
		NonRecursiveDFS<String, Integer> dfs = 
				new NonRecursiveDFS<String, Integer>();
		
		dfs.traverse(graph);
		
		System.out.println(dfs.getVisitedNodes());
		System.out.println(dfs.getVisitedEdges());
		*/
	}


	
}