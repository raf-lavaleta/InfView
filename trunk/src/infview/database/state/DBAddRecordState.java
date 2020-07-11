package infview.database.state;

import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;

import infview.SQL_proxy.SQLRequestManager;
import infview.SQL_proxy.SQLRequestManager.transactionTypes;
import infview.app.MainFrame;
import infview.controller.ErrorHandler;
import infview.gui.MyTabbedPane;
import infview.model.MyTableModel;
import infview.view.DBView;
import infview.workspace.Entity;

public class DBAddRecordState extends DatabaseState {
	
	JPanel dodaj;
	ArrayList<JTextField> textfildovi;
	String zaIzvoz;
	String nizImena[];
	JButton dugmeDodaj;
	@Override
	public void drawState(Entity entity) {
		
		removeAll();
		
		
		
		String qveri = "SELECT * FROM "+ entity.getName();
		
		//System.out.println(qveri);
		
		//Object[][] objekti = SQLRequestManager.executeTransaction(qveri, transactionTypes.COUNT, null);
		
		JScrollPane sp = new JScrollPane();
		dugmeDodaj = new JButton("DODAJ");
		
		
		textfildovi = new ArrayList<>();
		
		Label countkolona = new Label("Dodavanje recorda");
		Label druga = new Label("");
		dodaj = new JPanel();
		ResultSet rs = SQLRequestManager.fetchRequest(qveri);
		dodaj.add(countkolona);
		dodaj.add(druga);
		
		
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			nizImena = new String[rsmd.getColumnCount()];
			dodaj.setLayout(new GridLayout(rsmd.getColumnCount()+2,3,2,2));
			
			for(int i = 1; i <= rsmd.getColumnCount(); i++) {
				dodaj.add(new Label(rsmd.getColumnName(i)));

				JTextField text = new JTextField(20);
				textfildovi.add(text);
				dodaj.add(text);
				nizImena[i-1] = rsmd.getColumnName(i);
			}
			
			dugmeDodaj.addActionListener(new ActionListener() {
				
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String insert = "";
					boolean prvi = true;
					dugmeDodaj.setEnabled(false);
					String podaci = "";
					String[] podacizaizvoz = null;
					try {
						podacizaizvoz = new String[rsmd.getColumnCount()];
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					int i = 1;
					for(JTextField text : textfildovi) {
						
						if(text.getText().length() == 0) {
							ErrorHandler er = new ErrorHandler("Nisu popunjena sva polja"); 
							dugmeDodaj.setEnabled(true);
							return;
						}
							
							try {
								if(prvi) {
									prvi = false;
									insert += nizImena[i-1];
									podaci += "\'" + text.getText()+ "\'";
									podacizaizvoz[i-1] = text.getText();
									i++;
									continue;
								}
								insert += "," + nizImena[i-1];
								podaci += "," + "\'" + text.getText()+ "\'";
								podacizaizvoz[i-1] = text.getText();
								i++;
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						
					}
					
					zaIzvoz = "insert into "+ entity.getName() + " (" +insert +")" + " values (" + podaci + ")";   ;		 

					
					try {
						PreparedStatement preparedstat = SQLRequestManager.prepareStatement(zaIzvoz);
						i = 1;
						ResultSetMetaData rsmd = preparedstat.getMetaData();
						
						for(Object obj : podacizaizvoz) {
							
							String tip = entity.getAttributes().get(i-1).getType();
							
							if(tip == "number") {
								preparedstat.setInt(i, (int)obj);
							}
							if(tip == "string") {
								preparedstat.setString(i, (String)obj);
							}
							if(tip =="datetime") {
								SimpleDateFormat formatter=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");  
								  Date date;
								try {
									date = (Date) formatter.parse((String)obj);
									 Date sqlDate = new Date(date.getTime());
									  preparedstat.setDate(i, sqlDate);
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								 
							}
							i++;
						
						}
						preparedstat.executeUpdate();
						
					} catch (SQLException e1) {
						ErrorHandler error = new ErrorHandler(e1.getMessage());
						//e1.printStackTrace();
					}
					
					ResultSet requestedData = SQLRequestManager.fetchRequest("SELECT * FROM " + entity.getName());
					ArrayList<String> dataList = new ArrayList<>();
					try {
						while (requestedData.next()) {
							for ( i = 0; i<entity.getAttributes().size(); i++) { 
								dataList.add(requestedData.getString(i+1));
							}
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					int novododani = 0;
					boolean flag = true;
					int columns = entity.getAttributes().size();
					int rows = dataList.size()/columns;
					String data[][] = new String[rows][columns];
					for ( i = 0; i<rows; i++) {
						flag = true;
						for (int j = 0; j<columns; j++) {
							data[i][j] = dataList.get(j+columns*i);
							if(!(data[i][j].equals(podacizaizvoz[j]))) {
								flag=false;
								
							}
							
							//System.out.println();
					
						}
					if(flag) novododani = i; 
					}
					System.out.println(zaIzvoz);
					
					//Object[][] niz = SQLRequestManager.executeTransaction(zaIzvoz, transactionTypes.INSERT, podacizaizvoz);
					
					
					
					MyTableModel nesto = new MyTableModel(data, nizImena);
					
					
					
					
					DBView selectedTab = (DBView)MainFrame.getInstance().getDesniPanel().getTopTabPane().getSelectedComponent();

					selectedTab.getMyTable().setModel(nesto);
					
					
					nesto.fireTableDataChanged();
					selectedTab.getMyTable().repaint();
					
					int count = selectedTab.getMyTable().getRowCount();
					selectedTab.getMyTable().setRowSelectionInterval(novododani, novododani);
					
					JViewport viewport = (JViewport)selectedTab.getMyTable().getParent();

			      
			      
			        Rectangle rect = selectedTab.getMyTable().getCellRect(novododani, 0, true);

			      
			        Point pt = viewport.getViewPosition();

			      
			        rect.setLocation(rect.x-pt.x, rect.y-pt.y);

			        selectedTab.getMyTable().scrollRectToVisible(rect);
					
					
					prvi = true;
					dugmeDodaj.setEnabled(true);
					
				
					
				}
				
				
				
				
			});
			
			
			
			dodaj.add(dugmeDodaj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		JComboBox<String>Nesto = new JComboBox<>();
	
		add(dodaj);
	
		revalidate();
		repaint();
		/*SELECT COUNT(column_name) FROM table_name WHERE condition;
		
		*/
		
		
		
	}

}
