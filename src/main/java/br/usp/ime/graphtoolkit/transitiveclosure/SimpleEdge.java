package br.usp.ime.graphtoolkit.transitiveclosure;

/**
 * A simple graph edge implementation
 * @author gustavo.ansaldi.oliva@hp.com
 *
 */
public class SimpleEdge{

	private String label;
	
	public SimpleEdge(String label){
		this.label = label;
	}
	
	public SimpleEdge(int label){
		this.label = "" + label;
	}

	public String getLabel() {
		return label;
	}
	
	public boolean equals(Object o){
		SimpleEdge otherEdge = (SimpleEdge)o;
		return label.equals(otherEdge.getLabel());
	}
	
	public String toString(){
		return label;
	}
	
}
