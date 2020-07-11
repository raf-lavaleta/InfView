package infview.database.state;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import infview.SQL_proxy.SQLRequestManager;
import infview.SQL_proxy.SQLRequestManager.transactionTypes;
import infview.app.MainFrame;
import infview.gui.MyTabbedPane;
import infview.model.MyTableModel;
import infview.view.DBView;
import infview.workspace.Attribute;
import infview.workspace.Entity;
import infview.workspace.Relation;
public class DBRelationState extends DatabaseState{
        private Entity entity;
        @Override
        public void drawState(Entity entity) {
                // TODO Auto-generated method stub
        	removeAll();
        	System.out.println("Relation State");
        	this.entity = entity;
        	DBView selectedTab = (DBView)MainFrame.getInstance().getDesniPanel().getTopTabPane().getSelectedComponent();
//        	selectedTab.getRoditelj().get
        	ArrayList<String> attValue = new ArrayList<>();
        	HashMap<String, String> hMap = new HashMap<>();
        	MyTabbedPane newPane = new MyTabbedPane();
        	GridLayout gr = new GridLayout();
        	MainFrame.getInstance().getDesniPanel().setDividerLocation(400);
        	try {
				entity.getRelations().size();
				if(selectedTab.getMyTable().getSelectedColumn() == -1) {
	        		for(Relation rel:entity.getRelations()) {
	            		String requestSQL = "SELECT * FROM ";
	            		Entity otherEntity;
	            		Attribute otherAttribute;
	            		Attribute thisAttribute;
	            		String relName;
	            		
	            		if(rel.getFromEntity().equals(entity)) {
	            			otherEntity = rel.getToEntity();
	    	                relName = rel.getName();
	    	                otherAttribute = rel.getToAttribute();
	    	                thisAttribute = rel.getFromAttribute();
	            		} else {
	            			otherEntity = rel.getFromEntity();
	                        relName = rel.getName();
	                        otherAttribute = rel.getFromAttribute();
	                        thisAttribute = rel.getToAttribute();
	            		}
	            		String str = hMap.get(thisAttribute.getName());
	            		requestSQL += otherEntity;
	            		
	         
//	            		MyTableModel newTable = new MyTableModel(arg0, arg1);
	            		
//	            		MainFrame.getInstance().getDesniPanel().getBottomTabPane().add(newTab);
	            		System.out.println("ovo je kao proslo xD");
	            	}
	        	} else {
//	        		System.out.println("Columb = " + selectedTab.getMyTable().getColumnCount());
//	        		System.out.println("Row = " + selectedTab.getMyTable().getRowCount());
	        		
	        		for(int i = 0; i < selectedTab.getMyTable().getColumnCount(); i++) {
//	            		attValue.add(selectedTab.getMyTable().getModel().getValueAt(0, selectedTab.getMyTable().getSelectedColumn()).toString());
	            		String s1 = entity.getAttributes().get(i).getName();
	            		int j = selectedTab.getMyTable().getSelectedRow();
	            		String s2 = selectedTab.getMyTable().getModel().getValueAt(j, i).toString();
	            		System.out.println(s1 + " " + s2);
	            		hMap.put(s1, s2);
	            	}
	        		
	        		
	        		
	        		for(Relation rel:entity.getRelations()) {
	            		String requestSQL = "SELECT * FROM ";
	            		Entity otherEntity;
	            		Attribute otherAttribute;
	            		Attribute thisAttribute;
	            		String relName;
	            		
	            		if(!rel.getFromEntity().equals(entity)) {
	            			otherEntity = rel.getToEntity();
	    	                relName = rel.getName();
	    	                otherAttribute = rel.getToAttribute();
	    	                thisAttribute = rel.getFromAttribute();
	            		} else {
	            			otherEntity = rel.getFromEntity();
	                        relName = rel.getName();
	                        otherAttribute = rel.getFromAttribute();
	                        thisAttribute = rel.getToAttribute();
	            		}
	            		String str = hMap.get(thisAttribute.getName());
	            		if(!otherAttribute.getType().equals("Like")) {
	            			requestSQL += otherEntity + " WHERE " + otherAttribute.getName() + " = '" + str + "'";
	            		} else {
	            			requestSQL += otherEntity + " WHERE " + otherAttribute.getName() + " = " + str;
	            		}
	            		
	            		System.out.println(requestSQL);
	            		
	            		Object[][] obj = SQLRequestManager.executeTransaction(requestSQL, transactionTypes.COUNT, null);
	            		
	            		
	            		MyTableModel newTable = new MyTableModel(obj, entity.getAttributes());
	            		JTable jtable = new JTable(newTable);
//	            		newPane.add(jtable);
	            		JScrollPane jsp = new JScrollPane(jtable);
	            		jsp.getHorizontalScrollBar().setVisible(false);
	            		newPane.add(relName, jsp);
	            		add(newPane);
	            		repaint();
	            		revalidate();
	            		setLayout(gr);
//	            		MainFrame.getInstance().getDesniPanel().getBottomTabPane().add(relName, jtable);
//	            		MainFrame.getInstance().getDesniPanel().getBottomTabPane().setSelectedComponent(jtable);
//	            		MainFrame.getInstance().getDesniPanel().getBottomTabPane().setVisible(true);
//	            		MainFrame.getInstance().getDesniPanel().getBottomTabPane().add(newTab);
	            		System.out.println("ovo je kao proslo xD      A ime relacije je " + relName);
	            		
//	            		MainFrame.getInstance().getDesniPanel().getBottomTabPane().repaint();
//	            		MainFrame.getInstance().getDesniPanel().getBottomTabPane().revalidate();
	            	}
	        	}
			} catch (Exception e) {
				System.out.println("Nema relacija xD !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				return;
			}
        }
}
