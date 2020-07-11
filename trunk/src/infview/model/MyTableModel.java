package infview.model;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import infview.workspace.Attribute;

public class MyTableModel extends DefaultTableModel{

	public MyTableModel(Object[][] arg0, ArrayList<Attribute> arg1) {
		super(arg0, arg1.toArray());
		// TODO Auto-generated constructor stub
	}
	public MyTableModel(Object[][] arg0, Object[] arg1) {
		super(arg0, arg1);
	}
	public MyTableModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
}
