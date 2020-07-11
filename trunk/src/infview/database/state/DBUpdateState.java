package infview.database.state;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.table.TableModel;
import javax.swing.text.TableView.TableRow;

import infview.SQL_proxy.SQLRequestManager;
import infview.app.MainFrame;
import infview.model.MyTableModel;
import infview.model.file.IVSEKFile;
import infview.model.file.IVSERFile;
import infview.view.DBView;
import infview.workspace.Attribute;
import infview.workspace.Entity;

public class DBUpdateState extends DatabaseState{

	
	private HashMap<String, JTextArea> searchParam = new HashMap<>();

	private ArrayList<String> nameParams = new ArrayList<>();
	private ArrayList<String> pkValues = new ArrayList<>();
	@Override
	public void drawState(Entity entity) {
		// TODO Auto-generated method stub
		removeAll();
		
		DBView currDisplay = (DBView) MainFrame.getInstance().getDesniPanel().getTopTabPane().getSelectedComponent();
		
		int selectedRow = currDisplay.getMyTable().getSelectedRow();
		TableModel dataModel = currDisplay.getMyTable().getModel();
		searchParam = new HashMap<>();
		pkValues = new ArrayList<>();
		
		setLayout(new GridLayout(entity.getAttributes().size() +1, 1));
		ArrayList<JPanel> boxes=new ArrayList<JPanel>();
	
		for (int i=0; i<entity.getAttributes().size(); i++) {
			String name = entity.getAttributes().get(i).getName();
			JLabel tmpLbl = new JLabel(name);
			nameParams.add(name);
			searchParam.put(name, new JTextArea());
			searchParam.get(name).setColumns(20);
			searchParam.get(name).setName(name);
			if (dataModel.getValueAt(selectedRow, i)!= null)
					searchParam.get(name).setText(dataModel.getValueAt(selectedRow, i).toString());
			else searchParam.get(name).setText("");
			if (entity.getAttributes().get(i).isPrimaryKey()) {
				pkValues.add(dataModel.getValueAt(selectedRow, i).toString());
				System.out.println("Dodat " + dataModel.getValueAt(selectedRow, i).toString());
			}
			boxes.add(new JPanel(new FlowLayout(FlowLayout.LEFT)));
			boxes.get(i).add(tmpLbl);
			boxes.get(i).add(searchParam.get(name));
			
			
			add(boxes.get(i));
		}
		
		Box boxC = new Box(BoxLayout.X_AXIS);
		JButton okBtn = new JButton("OK");
		
		okBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String request = "UPDATE "+ entity.getName() + " SET";
				
				for (String key : searchParam.keySet()) {
					request +=" " + key + " = ?, ";
				}
				request = request.substring(0, request.length() - 2) + " WHERE ";
				for (Attribute a: entity.getAttributes()) {
					if (a.isPrimaryKey()) {
						request += a.getName();
						if (a.getType().equals("string")) request += " = '" + pkValues.get(entity.getAttributes().indexOf(a))+ "' AND ";
						else request += " = " + pkValues.get(entity.getAttributes().indexOf(a)) + " AND ";
					}
				}
				request = request.substring(0, request.length() - 5);
				System.out.println(request);
				PreparedStatement preparedStatement = SQLRequestManager.prepareStatement(request);

				if (preparedStatement == null) return;
				int tmpCount = 1;
				
					try {
						for (Attribute a : entity.getAttributes()) {
							
							if (a.getType().equals("number"))
								preparedStatement.setInt(tmpCount, Integer.parseInt(searchParam.get(a.getName()).getText()));
							else if (a.getType().equals("string")) preparedStatement.setString(tmpCount, searchParam.get(a.getName()).getText());
							else if (a.getType().equals("datetime")) {
								  SimpleDateFormat formatter=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");  
								  Date date = formatter.parse(searchParam.get(a.getName()).getText());
								  java.sql.Date sqlDate = new java.sql.Date(date.getTime());
								  preparedStatement.setDate(tmpCount, sqlDate);
							}
							tmpCount++;
						}
						preparedStatement.executeUpdate();
						preparedStatement.close();
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					ResultSet requestedData = SQLRequestManager.fetchRequest("SELECT * FROM " + entity.getName());
					ArrayList<String> dataList = new ArrayList<>();
					try {
						while (requestedData.next()) {
							for (int i = 0; i<entity.getAttributes().size(); i++) { 
								dataList.add(requestedData.getString(i+1));
							}
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					int columns = entity.getAttributes().size();
					int rows = dataList.size()/columns;
					String data[][] = new String[rows][columns];
					for (int i = 0; i<rows; i++)
						for (int j = 0; j<columns; j++) {
							data[i][j] = dataList.get(j+columns*i);
							//System.out.println();
						}
					MyTableModel tableModel = new MyTableModel(data, entity.getAttributes());
					
					DBView selectedTab = (DBView)MainFrame.getInstance().getDesniPanel().getTopTabPane().getSelectedComponent();

					selectedTab.getMyTable().setModel(tableModel);
					tableModel.fireTableDataChanged();
					selectedTab.getMyTable().repaint();
				}
		});
		
		
		
		
		boxC.add(Box.createHorizontalGlue());
		boxC.add(okBtn);

		add(boxC);
		revalidate();
		repaint();
	}
}