package by.bsu.ti.bean;

public class Node implements Comparable<Node> {

	private double value;

	private Node parent;

	private Node leftChild;

	private Node rightChild;

	public Node(double value) {
		this.value = value;
	}

	@Override
	public int compareTo(Node o) {
		return Double.compare(value, o.value);
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}

	@Override
	public String toString() {
		return "Node [value=" + value + ", parent=" + parent + "]";
	}

}