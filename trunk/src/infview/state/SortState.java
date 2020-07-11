package infview.state;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import infview.app.MainFrame;
import infview.model.file.IVAbstractFile;
import infview.model.file.IVFile;
import infview.model.file.IVSEKFile;
import infview.utility.ExternalSekSorter;
import infview.view.FileView;
import infview.workspace.Attribute;

public class SortState extends State{

	private HashMap<String, JCheckBox> isSortedBy = new HashMap<String, JCheckBox>();
	private HashMap<String, JComboBox> sortingType = new HashMap<String, JComboBox>();
	private ArrayList<Attribute> fields = new ArrayList<>();
	
	public SortState() {
		// TODO Auto-generated constructor stub
		super();
		
		
	}
	
	public void drawState(IVFile file, ArrayList<Attribute> fields) {
		removeAll();
		System.out.println("Sklonio sve");
		this.fields = fields;
		setLayout(new GridLayout(fields.size()+1,1));
		ArrayList<JPanel> boxes=new ArrayList<JPanel>();
		for (int i=0; i<fields.size(); i++) {
			String[] types = {"Ascending", "Descending"};
			String name = fields.get(i).getName();
			isSortedBy.put(name, new JCheckBox());
			isSortedBy.get(name).setActionCommand(name);
			isSortedBy.get(name).setSelected(fields.get(i).isSort());
			//Postavlja combo boxeve na visible/invisible kada se klikne check box
			isSortedBy.get(fields.get(i).getName()).addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					sortingType.get(arg0.getActionCommand()).setVisible(isSortedBy
							.get(arg0.getActionCommand()).isSelected());
					
					}
				});
			sortingType.put(fields.get(i).getName(), new JComboBox(types));
			sortingType.get(fields.get(i).getName()).setVisible(fields.get(i).isSort());
			sortingType.get(fields.get(i).getName()).setSelectedItem(fields.get(i).isAsc()?"Ascending":"Descending");
			
			
			boxes.add(new JPanel(new FlowLayout(FlowLayout.LEFT)));
			boxes.get(i).add(new JLabel(" "+fields.get(i).getName()));
			boxes.get(i).add(isSortedBy.get(fields.get(i).getName()));
			boxes.get(i).add(sortingType.get(fields.get(i).getName()));
			
	
 	 	    add(boxes.get(i));
		}
		Box boxC=new Box(BoxLayout.X_AXIS);
		JButton btnOK=new JButton("ok");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i=0;i<fields.size();i++){
					fields.get(i).setSort(isSortedBy.get(fields.get(i).getName()).isSelected());
					fields.get(i).setAsc(sortingType.get(fields.get(i).getName()).getSelectedIndex()==0);
				}
				FileView fw = ((FileView)(MainFrame.getInstance().getDesniPanel().getTopTabPane().getSelectedComponent()));
				ExternalSekSorter sekSorter = new ExternalSekSorter((IVAbstractFile)file, fw.getRoditelj());
				sekSorter.sort();
			}		
		});
		boxC.add(Box.createHorizontalGlue());
		boxC.add(btnOK);
		add(boxC);
		revalidate();
		repaint();
	}

	public HashMap<String, JCheckBox> getIsSortedBy() {
		return isSortedBy;
	}

	public HashMap<String, JComboBox> getSortingType() {
		return sortingType;
	}

	public ArrayList<Attribute> getFields() {
		return fields;
	}
	
	
}
