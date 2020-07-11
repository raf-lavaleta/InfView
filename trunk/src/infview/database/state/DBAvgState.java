package infview.database.state;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import infview.SQL_proxy.SQLRequestManager;
import infview.app.MainFrame;
import infview.model.MyTableModel;
import infview.model.file.IVAbstractFile;
import infview.utility.ExternalSekSorter;
import infview.view.DBView;
import infview.view.FileView;
import infview.workspace.Attribute;
import infview.workspace.Entity;

public class DBAvgState extends DatabaseState{
	
	private HashMap<String, JCheckBox> isGroupedBy = new HashMap<String, JCheckBox>();
	private JComboBox<Attribute> selekcijaKolona = new JComboBox<>();
	private Entity entity;
	
	@Override
	public void drawState(Entity entity) {
		// TODO Auto-generated method stub
		removeAll();
		
		this.entity = entity;
		for (Attribute a : entity.getAttributes()) {
			if (a.getType().equals("number"))
					selekcijaKolona.addItem(a);
		}
		setLayout(new GridLayout(entity.getAttributes().size()+2,1));
		ArrayList<JPanel> boxes=new ArrayList<JPanel>();
		JPanel selectedColumnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		selectedColumnPanel.add(new JLabel("Average for column: "));
		selectedColumnPanel.add(selekcijaKolona);
		add(selectedColumnPanel);
		for (int i=0; i<entity.getAttributes().size(); i++) {
			String name = entity.getAttributes().get(i).getName();
			isGroupedBy.put(name, new JCheckBox());
			isGroupedBy.get(name).setActionCommand(name);
		
			boxes.add(new JPanel(new FlowLayout(FlowLayout.LEFT)));
			boxes.get(i).add(new JLabel("Grouped by "+entity.getAttributes().get(i).getName()));
			boxes.get(i).add(isGroupedBy.get(entity.getAttributes().get(i).getName()));
			
	
 	 	    add(boxes.get(i));
		}
		Box boxC=new Box(BoxLayout.X_AXIS);
		JButton btnOK=new JButton("ok");
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String request = "";
				String title = "";
				if (selekcijaKolona.getSelectedItem() == null) return;
				request += "SELECT AVG("+selekcijaKolona.getSelectedItem().toString()+") " + "FROM " + entity.getName();
				title += "AVG("+selekcijaKolona.getSelectedItem().toString()+") ";
				int grouping = 0;
				
				for (String key : isGroupedBy.keySet()) {
					if (isGroupedBy.get(key).isSelected()) {
						if (grouping == 0) {
							grouping++;
							request+= " GROUP BY";
							title += "GROUPED BY";
						}
						request += " " + key + ",";
						title += " " + key + ",";
					}
				}
				if (grouping != 0) request = request.substring(0, request.length() - 1);
				title = title.substring(0, title.length() - 1);
				DBView selectedTab = (DBView)MainFrame.getInstance().getDesniPanel().getTopTabPane().getSelectedComponent();
				 
				Object[] tmpList = {title};
				
				selectedTab.getDatabaseModel().getAverage(request, tmpList);
			}
		});
		boxC.add(Box.createHorizontalGlue());
		boxC.add(btnOK);
		add(boxC);
		revalidate();
		repaint();
	}

}
