package infview.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import infview.SQL_proxy.SQLRequestManager;
import infview.app.MainFrame;
import infview.controller.DBTreeMouseController;
import infview.controller.OtvaranjeSchema;
import infview.controller.TreeMouseController;
import infview.view.WorkspaceTree;
import infview.workspace.Attribute;
import infview.workspace.Entity;
import infview.workspace.Relation;
import infview.workspace.Resource;
import infview.workspace.Workspace;

public class OpenDBSchemaAction extends IWAbstractAction{

	public OpenDBSchemaAction() {
		super();
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.ALT_MASK));
		putValue(SMALL_ICON, new ImageIcon("images/Open.png"));
		putValue(SHORT_DESCRIPTION, "Load workspace");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new FileNameExtensionFilter("json","json"));
		int i = jfc.showOpenDialog(null);
		File dir = jfc.getSelectedFile();
		if (i == JFileChooser.CANCEL_OPTION) return;
		
		
		String schema = dir.toString();
		
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(schema)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return;
		}
		try {
				Gson gson = new Gson();
				
				JsonObject metasema = gson.fromJson(br, JsonObject.class);
				
				String dbName = metasema.get("name").getAsString();
				String desc = metasema.get("description").getAsString();
				
				String type = metasema.get("type").getAsString();
				//Nije mssql database staviti ovde warning JOptionPane
				if (!type.equals("mssql")) return;
				
				String username = metasema.get("username").getAsString();
				String password = metasema.get("password").getAsString();
				String connection = metasema.get("connection").getAsString();
				
				//TREBA DA SE STARTUJE KONEKCIJA
				
				JsonArray entities = metasema.getAsJsonArray("entities");
				Resource resurs = new Resource(dbName, desc);
				for (Object jsonobj: entities) {
					JsonObject tmp = (JsonObject)jsonobj;
					String  entityName = tmp.get("title").getAsString();
					
					Entity entity = new Entity(entityName);
					
					JsonObject attributes = tmp.getAsJsonObject("properties");
					
					Set<String> entries = attributes.keySet();
					
					for (String currEntry: entries) {
						JsonObject tmpObj = attributes.getAsJsonObject(currEntry);
						
						Set<String> entryProperties = tmpObj.keySet();
						
						Attribute attribute = new Attribute(currEntry);
						
						boolean key = false;
						
						for (String currProperty: entryProperties) {
							if (currProperty.equals("type"))
								attribute.setType(tmpObj.get("type").getAsString());
							if(currProperty.equals("maxLength"))
								attribute.setLength(tmpObj.get("maxLength").getAsInt());
							if(currProperty.equals("primaryKey")) key = true;
						}
						attribute.setPrimaryKey(key);
						entity.addAttribute(attribute);
					}
					resurs.addEntity(entity);
				}
				try {
						JsonArray relations = metasema.getAsJsonArray("relations");
						for(Object obj:relations) {
							System.out.println("Previmo relacije xD");
							JsonObject jObj = (JsonObject)obj;
							
							String relName = jObj.get("relationName").getAsString();
							String relType = jObj.get("type").getAsString();
							
							String relFromAtr = jObj.get("fromAttribute").getAsString();
							String relToAtr = jObj.get("toAttribute").getAsString();
							
							String relFromEnt = jObj.get("from").getAsString();
							String relToEnt = jObj.get("to").getAsString();
							
							Entity toEnt = resurs.getEntity(relToEnt);
							Entity fromEnt = resurs.getEntity(relFromEnt);
							
							Attribute toAttribute = toEnt.getAttribute(relToAtr);
							Attribute fromAttribute = fromEnt.getAttribute(relFromAtr);
							
							Relation newRel = new Relation(fromEnt, toEnt, fromAttribute, toAttribute, relType, relName);
							toEnt.addRelation(newRel);
							fromEnt.addRelation(newRel);
						}
				}
				catch(Exception e) {
					System.out.println("Relacije nisu dodate");
				}
				
				Object root = MainFrame.getInstance().getWorkspaceModel().getRoot();
				
				((Workspace)root).addWorkNode(resurs);
				SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getWorkspaceTree());
			
				SQLRequestManager.initialiseConnection(connection, username, password);
				WorkspaceTree workspaceTree = MainFrame.getInstance().getWorkspaceTree();
				for (MouseListener ml : workspaceTree.getMouseListeners()) {
					if (ml instanceof TreeMouseController) workspaceTree.removeMouseListener(ml);
				}
				workspaceTree.addMouseListener(new DBTreeMouseController());
				System.out.println("Changed listener");
			}
		catch(Exception e){
			OtvaranjeSchema os = new OtvaranjeSchema(schema);
		}
	}
}
