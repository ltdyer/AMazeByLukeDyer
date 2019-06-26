package edu.wm.cs.cs301.amazebylukedyer.generation;

public class Tree {
	
	private Tree parent = null;
	
	public Tree() {
		
	}
	
	public Tree root() {
		if (parent != null) {
			return parent.root();
		}
		else {
			return this;
		}
	}
	
	public boolean connected(Tree tree) {
		return this.root() == tree.root();
	}
	
	public void connect(Tree tree) {
		tree.root().setParent(this);
	}
	
	public void setParent(Tree parent) {
		this.parent = parent;
	}
}
