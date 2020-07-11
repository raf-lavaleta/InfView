package infview.workspace;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class WorkNode implements MutableTreeNode{
	
	private ArrayList<WorkNode> deca = new ArrayList<WorkNode>();
	private WorkNode parent;
	private String name;
	public WorkNode() {
		// TODO Auto-generated constructor stub
	}
	public WorkNode(WorkNode parent, String name) {
		this.name = name;
		this.parent = parent;
	}
	@Override
	public Enumeration children() {
		// TODO Auto-generated method stub
		return (Enumeration<WorkNode>)deca;
	}

	@Override
	public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		// TODO Auto-generated method stub
		return deca.get(childIndex);
	}

	@Override
	public int getChildCount() {
		// TODO Auto-generated method stub
		return deca.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		// TODO Auto-generated method stub
		return parent.getIndex((TreeNode)this);
	}

	@Override
	public TreeNode getParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return deca.isEmpty();
	}

	@Override
	public void insert(MutableTreeNode child, int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(MutableTreeNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFromParent() {
		// TODO Auto-generated method stub
		if (parent!=null)
			parent.getDeca().remove(this);
		this.parent = null;
	}

	@Override
	public void setParent(MutableTreeNode newParent) {
		// TODO Auto-generated method stub
		this.parent =(WorkNode)newParent;
	}

	@Override
	public void setUserObject(Object object) {
		// TODO Auto-generated method stub
		
	}
	
	public void addWorkNode(WorkNode child) {
		deca.add(child);
	}

	public ArrayList<WorkNode> getDeca() {
		return deca;
	}

	public void setDeca(ArrayList<WorkNode> deca) {
		this.deca = deca;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
