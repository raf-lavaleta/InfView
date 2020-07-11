package infview.database.state;

import java.awt.Checkbox;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import infview.SQL_proxy.SQLRequestManager;
import infview.SQL_proxy.SQLRequestManager.transactionTypes;
import infview.app.MainFrame;
import infview.gui.MyTabbedPane;
import infview.model.MyTableModel;
import infview.view.DBView;
import infview.workspace.Entity;

public class DBCountState extends DatabaseState {

	JPanel broji;
	ArrayList<JCheckBox> checkboxovi;
	String zaIzvoz;
	String nizImena[];
	JButton BROJ;
	@Override
	public void drawState(Entity entity) {
		
		removeAll();
		
		
		
		String qveri = "SELECT * FROM "+ entity.getName();
		
		//System.out.println(qveri);
		
		//Object[][] objekti = SQLRequestManager.executeTransaction(qveri, transactionTypes.COUNT, null);
		
		JScrollPane sp = new JScrollPane();
		BROJ = new JButton("BROJI");
		
		
		checkboxovi = new ArrayList<>();
		JComboBox<String> imena = new JComboBox<>();
		Label countkolona = new Label("Kolona po kojoj ce se brojati");
		broji = new JPanel();
		ResultSet rs = SQLRequestManager.fetchRequest(qveri);
		broji.add(countkolona);
		broji.add(imena);
		
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			nizImena = new String[rsmd.getColumnCount()];
			broji.setLayout(new GridLayout(rsmd.getColumnCount()+2,3,2,2));
			
			for(int i = 1; i <= rsmd.getColumnCount(); i++) {
				broji.add(new Label(rsmd.getColumnName(i)));
				imena.addItem(rsmd.getColumnName(i));
				JCheckBox chb = new JCheckBox();
				checkboxovi.add(chb);
				broji.add(chb);
				nizImena[i-1] = rsmd.getColumnName(i);
			}
			
			BROJ.addActionListener(new ActionListener() {
				
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String group = "GROUP BY ";
					boolean prvi = true;
					ArrayList<String> priv = new ArrayList<>();
				
					BROJ.setEnabled(false);
					int i = 0;
					String selektovi = "";
					for(JCheckBox chb : checkboxovi) {
						
						if(chb.isSelected()) {
							try {
								priv.add(rsmd.getColumnName(checkboxovi.indexOf(chb)+1));
								if(prvi) {
									prvi = false;
									group += rsmd.getColumnName(checkboxovi.indexOf(chb)+1);
									selektovi += rsmd.getColumnName(checkboxovi.indexOf(chb)+1);
									continue;
								}
								group += "," + rsmd.getColumnName(checkboxovi.indexOf(chb)+1);
								selektovi += "," + rsmd.getColumnName(checkboxovi.indexOf(chb)+1); 
								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
					zaIzvoz = "select " + selektovi;
							
							
							
					zaIzvoz += ", count("+imena.getSelectedItem()+") from "+entity.getName()+" " + group;		 

					priv.add("count("+imena.getSelectedItem()+") from "+entity.getName()+" " + group);
					System.out.println(priv.toString());
					
					Object[][] niz = SQLRequestManager.executeTransaction(zaIzvoz, transactionTypes.COUNT, null);
					
					
					
					MyTableModel nesto = new MyTableModel(niz, priv.toArray());
					
					DBView selectedTab = (DBView)MainFrame.getInstance().getDesniPanel().getTopTabPane().getSelectedComponent();

					selectedTab.getMyTable().setModel(nesto);
	
					group = "";
					prvi = true;
					BROJ.setEnabled(true);
				}
				
				
				
				
			});
			
			
			
			broji.add(BROJ);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		add(broji);
	
		revalidate();
		repaint();
		/*SELECT COUNT(column_name) FROM table_name WHERE condition;
		
		*/
		
		
		
	}

}
