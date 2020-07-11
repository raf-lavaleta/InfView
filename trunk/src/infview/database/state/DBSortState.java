package infview.database.state;

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
import infview.SQL_proxy.SQLRequestManager.transactionTypes;
import infview.app.MainFrame;
import infview.model.MyTableModel;
import infview.model.file.IVAbstractFile;
import infview.utility.ExternalSekSorter;
import infview.view.DBView;
import infview.view.FileView;
import infview.view.MyTable;
import infview.workspace.Attribute;
import infview.workspace.Entity;

public class DBSortState extends DatabaseState{
	
	private HashMap<String, JCheckBox> isSortedBy = new HashMap<String, JCheckBox>();
	private HashMap<String, JComboBox> sortingType = new HashMap<String, JComboBox>();
	private Entity entity;
	
	
	@Override
	public void drawState(Entity entity) {
		// TODO Auto-generated method stub
		removeAll();
		this.entity = entity;
		setLayout(new GridLayout(entity.getAttributes().size()+1,1));
		ArrayList<JPanel> boxes=new ArrayList<JPanel>();
		for (int i=0; i<entity.getAttributes().size(); i++) {
			String[] types = {"Ascending", "Descending"};
			String name = entity.getAttributes().get(i).getName();
			isSortedBy.put(name, new JCheckBox());
			isSortedBy.get(name).setActionCommand(name);
			isSortedBy.get(name).setSelected(entity.getAttributes().get(i).isSort());
			//Postavlja combo boxeve na visible/invisible kada se klikne check box
			isSortedBy.get(entity.getAttributes().get(i).getName()).addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					sortingType.get(arg0.getActionCommand()).setVisible(isSortedBy
							.get(arg0.getActionCommand()).isSelected());
					
					}
				});
			sortingType.put(entity.getAttributes().get(i).getName(), new JComboBox(types));
			sortingType.get(entity.getAttributes().get(i).getName()).setVisible(entity.getAttributes().get(i).isSort());
			sortingType.get(entity.getAttributes().get(i).getName()).setSelectedItem(entity.getAttributes().get(i).isAsc()?"Ascending":"Descending");
			
			
			boxes.add(new JPanel(new FlowLayout(FlowLayout.LEFT)));
			boxes.get(i).add(new JLabel(" "+entity.getAttributes().get(i).getName()));
			boxes.get(i).add(isSortedBy.get(entity.getAttributes().get(i).getName()));
			boxes.get(i).add(sortingType.get(entity.getAttributes().get(i).getName()));
			
	
 	 	    add(boxes.get(i));
		}
		Box boxC=new Box(BoxLayout.X_AXIS);
		JButton btnOK=new JButton("ok");
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				String request = "SELECT * FROM " + entity.getName();
				int index = 0;
				for (Attribute a: entity.getAttributes()) {
					if (!(isSortedBy.get(a.getName()).isSelected())) continue;
					if (index == 0) {
						index ++;
						request = request + " ORDER BY";
					}
					request = request +" " + a.getName();
					if (sortingType.get(a.getName()).getSelectedIndex() == 0) request = request + " ASC,";
					else  request = request + " DESC,";		
				}
				request = request.substring(0, request.length() - 1);

				DBView selectedTab = (DBView)MainFrame.getInstance().getDesniPanel().getTopTabPane().getSelectedComponent();

				selectedTab.getDatabaseModel().sortDB(request);
			}
		});
		boxC.add(Box.createHorizontalGlue());
		boxC.add(btnOK);
		add(boxC);
		revalidate();
		repaint();
	}
}

