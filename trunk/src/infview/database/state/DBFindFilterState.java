package infview.database.state;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import infview.SQL_proxy.SQLRequestManager;
import infview.SQL_proxy.SQLRequestManager.transactionTypes;
import infview.app.MainFrame;
import infview.gui.MyTabbedPane;
import infview.model.MyTableModel;
import infview.view.DBView;
import infview.workspace.Attribute;
import infview.workspace.Entity;

public class DBFindFilterState extends DatabaseState{

	JPanel dodaj;
    ArrayList<JTextField> textfildovi;
    ArrayList<JComboBox<String>> comboBoxdrugi;
    ArrayList<JComboBox<String>> comboBoxheaderi;
    ArrayList<JComboBox<String>> andori;
    String zaIzvoz;
    String nizImena[];
    JButton dugmeDodaj;
    String selectedHeader;
    MyTabbedPane newPane = new MyTabbedPane();
    Entity entity;
    JButton dodajfilter;
    ResultSetMetaData rsmd;
    JTextField field = new JTextField(20);
    JPanel ovadva;
    int counter = 0;
	@Override
	public void drawState(Entity entity) {
		
		removeAll();
		
		
		textfildovi = new ArrayList<>();
		comboBoxdrugi = new ArrayList<>();
		comboBoxheaderi = new ArrayList<>();
		this.entity = entity;

        String qveri = "SELECT * FROM "+ entity.getName();

        JScrollPane sp = new JScrollPane();
        dugmeDodaj = new JButton("Filter");

        textfildovi = new ArrayList<>();
        JComboBox<String>  headerBox = new JComboBox<>();
        JComboBox<String> veceManje = new JComboBox<>();
        andori = new ArrayList<>();
        dodajfilter = new JButton("DoDaJ fIlTer");
//        Label countkolona = new Label("Filter recorda");
//        Label druga = new Label("");
//        Label treca = new Label("");
        dodaj = new JPanel();
        dodaj.setLayout(new FlowLayout(FlowLayout.CENTER));
	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        ResultSet rs = SQLRequestManager.fetchRequest(qveri);
//        dodaj.add(countkolona);
//        dodaj.add(druga);
//        dodaj.add(treca);
        
        try {
            rsmd = rs.getMetaData();
            nizImena = new String[rsmd.getColumnCount()];
            dodaj.setLayout(new FlowLayout());

            for(int i = 1; i <= rsmd.getColumnCount(); i++) {
                String pom = rsmd.getColumnName(i);

                headerBox.addItem(pom);
            }
            dodaj.add(headerBox);
            headerBox.setSelectedIndex(0);
            selectedHeader = (String) headerBox.getSelectedItem();
            filipoVaFunkcija(selectedHeader, veceManje);
            dodaj.add(veceManje);
            dodaj.add(field);
           
            comboBoxdrugi.add(veceManje);
		    comboBoxheaderi.add(headerBox);
		    
		    
		     textfildovi.add(field);
            headerBox.addItemListener(new ItemListener() {
			
				@Override
				public void itemStateChanged(ItemEvent e) {
					selectedHeader = (String)e.getItem();
					filipoVaFunkcija(selectedHeader, veceManje);
					revalidate();
					repaint();
				}
			});
            
            dugmeDodaj.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String requestSQL = "SELECT * FROM " + entity.getName() + " WHERE ";

					

						for(int i = 0; i < comboBoxdrugi.size();i++) {
						
							
							if(i == 0) {
								if(comboBoxdrugi.get(i).getSelectedItem().equals("Like")) {
									requestSQL += comboBoxheaderi.get(i).getSelectedItem() + " " + comboBoxdrugi.get(i).getSelectedItem() + " '" + textfildovi.get(i).getText() + "'";
								} else {
									requestSQL += comboBoxheaderi.get(i).getSelectedItem() + " " + comboBoxdrugi.get(i).getSelectedItem() + " " + textfildovi.get(i).getText();                                 
								}
								continue;
							}
							
							if(comboBoxdrugi.get(i).getSelectedItem().equals("Like")) {
								requestSQL += " " + andori.get(i-1).getSelectedItem() + " " + comboBoxheaderi.get(i).getSelectedItem() + " " + comboBoxdrugi.get(i).getSelectedItem() + " '" + textfildovi.get(i).getText() + "'";
							} else {
								requestSQL += " " + andori.get(i-1).getSelectedItem() + " " + comboBoxheaderi.get(i).getSelectedItem() + " " + comboBoxdrugi.get(i).getSelectedItem() + " " + textfildovi.get(i).getText();                                 
							}
							
							
						
						
						}
						System.out.println(requestSQL);
			
					
					Object[][] obj = SQLRequestManager.executeTransaction(requestSQL, transactionTypes.COUNT, null);
            		
					MyTableModel nesto = new MyTableModel(obj, entity.getAttributes());

                    DBView selectedTab = (DBView)MainFrame.getInstance().getDesniPanel().getTopTabPane().getSelectedComponent();

                    selectedTab.getMyTable().setModel(nesto);
            		
//            		MyTableModel newTable = new MyTableModel(obj, entity.getAttributes());
//            		JTable jtable = new JTable(newTable);
////            		newPane.add(jtable);
//            		newPane.add("Filtrirano", jtable);
//            		add(newPane);
            		repaint();
            		revalidate();
				}
			});
            
            
            dodajfilter.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						if(counter >= rsmd.getColumnCount()-2) dodajfilter.setEnabled(false);
						counter++;
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {
					JPanel dajdo = new JPanel();
					dajdo.setLayout(new FlowLayout(FlowLayout.CENTER));
					remove(ovadva);
					remove(dugmeDodaj);
					remove(dodajfilter);
					Label buffer = new Label("");
					JComboBox<String> andor = new JComboBox<>();
					andor.addItem("And");
					andor.addItem("Or");
					andor.setSelectedIndex(0);
					andori.add(andor);
					dajdo.add(andor);
					 JComboBox<String>  headerBox = new JComboBox<>();
				     JComboBox<String> veceManje = new JComboBox<>();
				     for(int i = 1; i <= rsmd.getColumnCount(); i++) {
			                String pom;
							
								pom = rsmd.getColumnName(i);

			                headerBox.addItem(pom);
			            }
				     filipoVaFunkcija(selectedHeader, veceManje);

				     comboBoxdrugi.add(veceManje);
				     comboBoxheaderi.add(headerBox);
				     JTextField tf = new JTextField(20);
				     textfildovi.add(tf);
				     
				     headerBox.addItemListener(new ItemListener() {
							
							@Override
							public void itemStateChanged(ItemEvent e) {
								selectedHeader = (String)e.getItem();
								filipoVaFunkcija(selectedHeader, veceManje);
								revalidate();
								repaint();
							}
						});
				     	dajdo.add(headerBox);
				     	dajdo.add(veceManje);
				     	dajdo.add(tf);
				     	add(dajdo);
				     	JPanel ovadva = new JPanel();
				        ovadva.setLayout(new BoxLayout(ovadva, BoxLayout.X_AXIS));
				        ovadva.add(dugmeDodaj);
				        
				        ovadva.add(dodajfilter);
			            add(ovadva);
			       //     dodaj.add(buffer);
			            revalidate();
			            repaint();
				     
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			});
            
            
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        add(dodaj);
        
        ovadva = new JPanel();
        ovadva.setLayout(new BoxLayout(ovadva, BoxLayout.X_AXIS));
        ovadva.add(dugmeDodaj);
        
        ovadva.add(dodajfilter);
        add(ovadva);
        revalidate();
        repaint();
	}
	
	public JComboBox<String> filipoVaFunkcija(String selectedHeader, JComboBox<String> veceManje){
		
        JComboBox<String> like = new JComboBox<>();
        like.insertItemAt("Like", 0);
		
		for(Attribute atb: entity.getAttributes()) {
			if(atb.getName().equals(selectedHeader)) {
				if(atb.getType().equals("number")) {
//					System.out.println("Vraca broj");
					veceManje.removeAllItems();
					veceManje.insertItemAt("=", 0);
					veceManje.insertItemAt("<", 1);
					veceManje.insertItemAt(">", 2);
					veceManje.setSelectedIndex(0);
					return null;
				} else {
//					System.out.println("Vraca str");
					veceManje.removeAllItems();
					veceManje.insertItemAt("Like", 0);
					veceManje.insertItemAt("=", 1);
					veceManje.setSelectedIndex(0);
					return null;
				}
			}
		}
		System.out.println("Puklo je XD");
		return null;
	}
}
