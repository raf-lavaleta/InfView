package infview.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.SwingUtilities;

//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.json.JSONTokener;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import infview.app.MainFrame;
import infview.workspace.Attribute;
import infview.workspace.Entity;
import infview.workspace.Relation;
import infview.workspace.Resource;
import infview.workspace.Workspace;

public class OtvaranjeSchema {
	private int i;
	
	
	public OtvaranjeSchema(String dir) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dir)));
			//JSONTokener tokener = new JSONTokener(br);
			//JSONObject o = new JSONObject(tokener);
			
			Gson gson = new Gson();
			
			JsonObject o = gson.fromJson(br, JsonObject.class);
			
			
			String name = o.get("name").getAsString();
			String desc = (String)o.get("description").getAsString();
			
			Resource resurs = new Resource(name, desc);
			
			
			//JSONArray packages = o.getJSONArray("packages");
			
		//	System.out.println(packages.getJSONObject(0).toString());
			
			//JSONArray tables = o.getJSONArray("entities");
			
			JsonArray tables = o.getAsJsonArray("entities");
			JsonArray relations = o.getAsJsonArray("relations");
			//JSONArray relations = o.getJSONArray("relations");
			
			for (Object jsonobj: tables) {
				JsonObject tmp = (JsonObject)jsonobj;
				
				String  entityName = tmp.get("name").getAsString();
				
				Entity entity = new Entity(entityName);
				
				JsonArray attributes = tmp.getAsJsonArray("attributes");
				
				for (i = 0; i<attributes.size(); i++) {
					JsonObject tmpObj = attributes.get(i).getAsJsonObject();
					
					Attribute attribute = new Attribute(tmpObj.get("name").getAsString());
					
					attribute.setType(tmpObj.get("type").getAsString());
					attribute.setLength(Integer.parseInt(tmpObj.get("length").getAsString()));
					String pk = tmpObj.get("primaryKey").getAsString();
					
					if (pk.equals("true")) attribute.setPrimaryKey(true);
					else attribute.setPrimaryKey(false);
					
					entity.addAttribute(attribute);
				}
				resurs.addEntity(entity);
				
			}
			
			for (i = 0; i<relations.size();i++) {
				JsonObject tmpObj = relations.get(i).getAsJsonObject();
				
				String type = tmpObj.get("type").getAsString();
				String fromEntity = tmpObj.get("from").getAsString();
				String toEntity = tmpObj.get("to").getAsString();
				String fromAttribute = tmpObj.get("fromAttribute").getAsString();
				String toAttribute = tmpObj.get("toAttribute").getAsString();
				String relationName = tmpObj.get("relationName").getAsString();
				
				Entity fromE = resurs.getEntity(fromEntity);
				
				Entity toE = resurs.getEntity(toEntity);
				System.out.println(fromEntity + i);
				Attribute fromA = fromE.getAttribute(fromAttribute);
				Attribute toA = fromE.getAttribute(toAttribute);
				
				Relation rel = new Relation(fromE, toE, fromA, toA, type, relationName);
				
				if (resurs.containsRelation(rel)) continue;
				
				System.out.println("Pravim relaciju sa imenom  " + relationName);
				
				resurs.addRelation(rel);
				
				fromE.addRelation(rel);
				if (fromE.equals(toE)) continue;
				toE.addRelation(rel);
	
			}
			
			for(i = 0; i < tables.size(); i++) {
				
			}
			
			for (Relation rel : resurs.getRelacije()) {
				System.out.println(rel.getName() + " :"+rel.getFromEntity() + "- " +rel.getToEntity());
			}
			
			Object root = MainFrame.getInstance().getWorkspaceModel().getRoot();
			
			((Workspace)root).addWorkNode(resurs);
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {
			SwingUtilities.updateComponentTreeUI(MainFrame.getInstance().getWorkspaceTree());
		}
	}
	
}
