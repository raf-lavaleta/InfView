package infview.workspace;

public class Workspace extends WorkNode{
	public Workspace(WorkNode parent, String name) {
		// TODO Auto-generated constructor stub
		super(parent,name);
	}

	@Override
	public String toString() {
		return this.getName();
	}
	
	
}
