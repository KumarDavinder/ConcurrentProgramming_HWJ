package it.uniroma3.hwj.base;


public class BinaryNode implements Node{
	private Node sx;
	private Node dx;
	private int value;

	public BinaryNode(Node sx, Node dx, int value) {
		this.sx = sx;
		this.dx = dx;
		this.value = value;
	}
	//get
	@Override
	public Node getSx() {
		return this.sx;
	}
	
	@Override
	public Node getDx() {
		return this.dx;
	}
	
	@Override
	public int getValue() {
		return this.value;
	}
	//set
	public void setSx(Node sx) {
		this.sx = sx;
	}
	
	public void setDx(Node dx) {
		this.dx = dx;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		String s = null;
		if(this.getSx() != null){
			s = "NodeSx " + this.getSx().toString();
		}
		s = s + "valueNode " + String.valueOf(this.getValue());
		if(this.getDx() != null){
			s = s + "NodeDx" + this.getDx().toString();
		}
		return s;
	}
}
