package infview.state;

import infview.app.MainFrame;
import infview.model.file.IVFile;
import infview.model.file.IVSEKFile;
import infview.model.file.IVSERFile;
import infview.workspace.Attribute;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FindState extends State{

//	private HashMap<String,JCheckBox> sortFields=new HashMap<String,JCheckBox>();
//	private HashMap<String,JComboBox> typeSort=new HashMap<String,JComboBox>();
	
	private HashMap<String, JTextArea> searchParam = new HashMap<>();
	private HashMap<String, JComboBox> choiceParams = new HashMap<>();
	private ArrayList<String> nameParams = new ArrayList<>();
	private ArrayList<Attribute> fields;
	private boolean fromStart = true;
	private boolean multiFind = false;
	private boolean fileStore = true;
	public FindState() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@Override
	public void drawState(IVFile file, ArrayList<Attribute> fields) {

		this.fields = fields;
		removeAll();
		searchParam = new HashMap<>();
		choiceParams = new HashMap<>();
		setLayout(new GridLayout(fields.size() +1, 1));
		ArrayList<JPanel> boxes=new ArrayList<JPanel>();
		
		for (int i=0; i<fields.size(); i++) {
			String name = fields.get(i).getName();
			JLabel tmpLbl = new JLabel(name);
			nameParams.add(name);
			searchParam.put(name, new JTextArea());
			searchParam.get(name).setColumns(20);
			searchParam.get(name).setName(name);
			
			boxes.add(new JPanel(new FlowLayout(FlowLayout.LEFT)));
			boxes.get(i).add(tmpLbl);
			boxes.get(i).add(searchParam.get(name));
			
			
			add(boxes.get(i));
		}
		String[] tipovi = {"prvi", "svi"};
		choiceParams.put("Amount", new JComboBox(tipovi));
		choiceParams.get("Amount").addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int i = choiceParams.get("Amount").getSelectedIndex();
				if (i==0) multiFind = false;
				else multiFind = true;
			}
		});
		String[] tipovi2 = {"pocetak", "trenutna pozicija"};
		choiceParams.put("StartPoint", new JComboBox(tipovi2));
		choiceParams.get("StartPoint").addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int i = choiceParams.get("StartPoint").getSelectedIndex();
				if (i==0) fromStart = true;
				else fromStart = false;
			}
		});
		
		String[] tipovi3 = {"Cuvaj u datoteci", "Cuvaj u memoriji"};
		choiceParams.put("Storage", new JComboBox(tipovi3));
		choiceParams.get("Storage").addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int i = choiceParams.get("Storage").getSelectedIndex();
				if (i==0) fileStore = true;
				else fileStore = false;
			}
		});
		
		JPanel tmpPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tmpPan.add(new JLabel("Broj elementata"));
		tmpPan.add(choiceParams.get("Amount"));
		JPanel tmpPan2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tmpPan2.add(new JLabel("Pocetak pretrage"));
		tmpPan2.add(choiceParams.get("StartPoint"));
		JPanel tmpPan3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tmpPan3.add(new JLabel("Nacin cuvanja rezultata"));
		tmpPan3.add(choiceParams.get("Storage"));
		
		add(tmpPan);
		add(tmpPan2);
		add(tmpPan3);
		
		
		Box boxC = new Box(BoxLayout.X_AXIS);
		JButton okBtn = new JButton("OK");
		
		okBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int choose = JOptionPane.showConfirmDialog(MainFrame.getInstance(), "Da li zelite binarno pretrazivanje ili ne?",
						"Pretrazi", 0, 3);
				
				ArrayList<String> stuff = new ArrayList<>();
				for (String a : searchParam.keySet()) {
					stuff.add(a);
				}
				if(choose == 1) {
					((file)).findRecord(stuff);
				} else if(choose == 0) {
					((IVSEKFile)(file)).binaryFind(stuff);
				}
				
				// TODO Auto-generated method stub
//				ArrayList<String> stuff = new ArrayList<>();
//				for (String a : searchParam.keySet()) {
//					stuff.add(a);
//				}
				
//				((IVSEKFile)(file)).findRecord(stuff);
//				((IVSERFile)(file)).findRecord(stuff);
			}
		});
		
		JButton eraseBtn = new JButton("Obrisi");
		
		eraseBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int choose = JOptionPane.showConfirmDialog(MainFrame.getInstance(), "Da li zelite da pernamentno obrisete ili ne?",
						"Brisanje", 0, 3);
				if(choose == 0) {
					MainFrame.getInstance().setPernamently(true);
					ArrayList<String> stuff = new ArrayList<>();
					for (String a : searchParam.keySet()) {
						stuff.add(a);
					}
					((IVSERFile)(file)).deleteRecord(stuff);
					
				} else if(choose == 1) {
					MainFrame.getInstance().setPernamently(false);
					ArrayList<String> stuff = new ArrayList<>();
					for (String a : searchParam.keySet()) {
						stuff.add(a);
					}
					((IVSERFile)(file)).deleteRecord(stuff);
				} else {
				}
			}
		});
		
		JButton addBtn = new JButton("Dodaj");
		
		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> stuff = new ArrayList<>();
				for (String a : searchParam.keySet()) {
					stuff.add(a);
				}
				try {
					((IVSERFile)(file)).addRecord(stuff);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("addBtn try catch");
				}
				
			}
		});
		
		boxC.add(Box.createHorizontalGlue());
		boxC.add(okBtn);
		boxC.add(eraseBtn);
		boxC.add(addBtn);
		add(boxC);
		revalidate();
		repaint();
	}

	public boolean isFromStart() {
		return fromStart;
	}

	public void setFromStart(boolean fromStart) {
		this.fromStart = fromStart;
	}

	public boolean isMultiFind() {
		return multiFind;
	}

	public void setMultiFind(boolean multiFind) {
		this.multiFind = multiFind;
	}

	public boolean isFileStore() {
		return fileStore;
	}

	public void setFileStore(boolean fileStore) {
		this.fileStore = fileStore;
	}

	public HashMap<String, JTextArea> getSearchParam() {
		return searchParam;
	}
	
	public ArrayList<String> getNameParams() {
		return nameParams;
	}
	
	public ArrayList<Attribute> getFields() {
		return fields;
	}
}
