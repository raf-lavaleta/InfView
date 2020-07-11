package infview.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;
import javax.swing.tree.TreeModel;

import infview.controller.RightPanelTopTableController;
import infview.model.MyTableModel;

public class MyTable extends JTable{
	public MyTable(MyTableModel model) {
		// TODO Auto-generated constructor stub
		super(model);
		addMouseListener(new RightPanelTopTableController());
	}
	
	public void changeModel( MyTableModel model) {
		this.setModel(model);
		model.fireTableDataChanged();
	}
}
