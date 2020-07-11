package infview.model;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeModel;

import infview.app.MainFrame;
import infview.workspace.WorkNode;
import infview.workspace.Workspace;

public class NodeModel extends DefaultTreeModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NodeModel() {
		super(new Workspace(null, "Workspace"));
		SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getWorkspaceTree());
	}
	
	public void addWorkNode(WorkNode n) {
		((WorkNode)getRoot()).addWorkNode(n);
	}
}
