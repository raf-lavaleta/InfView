package infview.view;

import java.awt.event.ActionListener;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;

public class WorkspaceTreeEditor extends DefaultTreeCellEditor implements ActionListener{

	public WorkspaceTreeEditor(JTree tree, DefaultTreeCellRenderer renderer) {
		super(tree, renderer);
		// TODO Auto-generated constructor stub
	}

}
