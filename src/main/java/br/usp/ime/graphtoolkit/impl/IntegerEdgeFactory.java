package br.usp.ime.graphtoolkit.impl;

public class IntegerEdgeFactory {

	private Integer start;
	private Integer i;
	private Integer increment;
	
	public IntegerEdgeFactory(Integer start, Integer increment){
		this.start = start;
		this.i = start;
		this.increment = increment;
	}
	
	public Integer getNewEdge(){
		Integer edge = i;
		i += increment;
		return edge;
	}
	
	public void reset(){
		i = start;
	}
	
	public static void main(String[] args) {
		IntegerEdgeFactory edgeFactory = new IntegerEdgeFactory(1, 1);
		System.out.println(edgeFactory.getNewEdge());
		System.out.println(edgeFactory.getNewEdge());
		System.out.println(edgeFactory.getNewEdge());
		System.out.println(edgeFactory.getNewEdge());
	}
}
