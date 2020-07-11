package infview.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import infview.SQL_proxy.SQLRequestManager;
import infview.app.MainFrame;
import infview.database.state.DatabaseStateManager;
import infview.events.DBModelListener;
import infview.model.MyTableModel;
import infview.model.file.DBModel;
import infview.workspace.Attribute;
import infview.workspace.Entity;

public class DBView extends FileView implements DBModelListener{
	private MyTable myTable;
	private DBModel databaseModel;
	private DatabaseStateManager stateManager;

	public DBView(Entity roditelj) {
		stateManager = new DatabaseStateManager();
		stateManager.getListeners().add(MainFrame.getInstance().getDesniPanel());
		initialise(roditelj);
	}
	
	private void initialise(Entity entity) {
			
			String request = "SELECT * FROM " + entity.getName();
			databaseModel = new DBModel(request, entity);
			databaseModel.addListener(this);
			MyTableModel tableModel = new MyTableModel(databaseModel.getData(), databaseModel.getAttributes());
			myTable =new MyTable(tableModel);
			JScrollPane scr = new JScrollPane(myTable);
			setLayout(new BorderLayout());
			JButton updateButton = new JButton("Update");
			updateButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					stateManager.setDBUpdateState(databaseModel);
				}
			});
			JButton sortButton = new JButton("Sort");
			sortButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					stateManager.setDBSortState(databaseModel);
				}
			});
			
			JButton avgButton = new JButton("Average");
			avgButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					stateManager.setDBAvgState(databaseModel);
				}
			});
			
			JButton countButton = new JButton("Count");
			countButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					stateManager.setDBCountState(databaseModel);
					
				}
			});
			
			JButton refreshButton = new JButton("Refresh");
			refreshButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					databaseModel.refreshData();
					
				}
			});
			
			JButton addRecordButton = new JButton("Add Record");
			addRecordButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					stateManager.setDBAddRecordState(databaseModel);
					
				}
			});
			
			JButton addFilterButton = new JButton("Filter");
			addFilterButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					stateManager.setDbFindFilterState(databaseModel);
					
				}
			});
			updateButton.setBorder(BorderFactory.createBevelBorder(0));
			updateButton.setBackground(Color.red);
			updateButton.setOpaque(true);
			
			sortButton.setBorder(BorderFactory.createBevelBorder(0));
			sortButton.setBackground(Color.red);
			sortButton.setOpaque(true);
			
			avgButton.setBorder(BorderFactory.createBevelBorder(0));
			avgButton.setBackground(Color.red);
			avgButton.setOpaque(true);
			
			countButton.setBorder(BorderFactory.createBevelBorder(0));
			countButton.setBackground(Color.red);
			countButton.setOpaque(true);
			
			refreshButton.setBorder(BorderFactory.createBevelBorder(0));
			refreshButton.setBackground(Color.red);
			refreshButton.setOpaque(true);
			
			addRecordButton.setBorder(BorderFactory.createBevelBorder(0));
			addRecordButton.setBackground(Color.red);
			addRecordButton.setOpaque(true);
			
			addFilterButton.setBorder(BorderFactory.createBevelBorder(0));
			addFilterButton.setBackground(Color.red);
			addFilterButton.setOpaque(true);
			
			JToolBar jtb = new JToolBar();
			//jtb.setLayout(new FlowLayout());
			jtb.add(updateButton);
			jtb.addSeparator();
			jtb.add(sortButton);
			jtb.addSeparator();
			jtb.add(avgButton);
			jtb.addSeparator();
			jtb.add(countButton);
			jtb.addSeparator();
			jtb.add(refreshButton);
			jtb.addSeparator();
			jtb.add(addRecordButton); 
			jtb.addSeparator();
			jtb.add(addFilterButton); 
			add(jtb, BorderLayout.NORTH);
			//add
			add(scr, BorderLayout.CENTER);
	}

	public MyTable getMyTable() {
		return myTable;
	}

	public void setMyTable(MyTable myTable) {
		this.myTable = myTable;
		myTable.repaint();
	}

	public DatabaseStateManager getStateManager() {
		return stateManager;
	}

	public DBModel getDatabaseModel() {
		return databaseModel;
	}

	@Override
	public void databaseModelChanged(Object[][] data, Object[] columns) {
		// TODO Auto-generated method stub
		MyTableModel newModel = new MyTableModel(data, columns);
		myTable.setModel(newModel);
		newModel.fireTableDataChanged();
	}
	
	
}
