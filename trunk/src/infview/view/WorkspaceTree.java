package infview.view;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeCellRenderer;

import infview.controller.TreeMouseController;
import infview.model.NodeModel;
import infview.workspace.WorkNode;

public class WorkspaceTree extends JTree{
	
	public WorkspaceTree() {
		// TODO Auto-generated constructor stub	
		addMouseListener(new TreeMouseController());
		setCellEditor(new WorkspaceTreeEditor(this,new DefaultTreeCellRenderer()));
		setCellRenderer(new WorkspaceTreeCellRenderer());
		setEditable(true);
	}

	public void addWorkNode(WorkNode r) {
		((NodeModel)getModel()).addWorkNode(r);
		SwingUtilities.updateComponentTreeUI(this);
	}
}
