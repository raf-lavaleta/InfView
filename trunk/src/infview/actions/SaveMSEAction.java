package infview.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
//import org.json.JSONObject;
//import org.json.JSONTokener;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import infview.app.MainFrame;
import infview.gui.MSEditor;

public class SaveMSEAction extends IWAbstractAction{
	
	public SaveMSEAction() {
		
				putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				        KeyEvent.VK_O, ActionEvent.ALT_MASK));
				putValue(SMALL_ICON, new ImageIcon("images/Save.png"));
				putValue(SHORT_DESCRIPTION, "Load workspace");
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MSEditor mseditor = MainFrame.getInstance().getMseditor();
		
		try {
			
			//InputStream input = new FileInputStream(new File("meta schema.json"));
			
			BufferedReader br = new  BufferedReader(new InputStreamReader(new FileInputStream(new File("meta schema.json"))));
			
			Gson gson = new Gson();
			
			JsonObject rawSchema = gson.fromJson(br, JsonObject.class);
			
			
			
			
			//Schema schema = SchemaLoader.load(rawSchema);
			
			//Schema schema = SchemaLoader.load(schemaJson)
			
			//JsonObject rawSchema = new JsonObject(input);
			//Schema schema = SchemaLoader.load(rawSchema);
			//
			//InputStream inputmodel = new FileInputStream(new File("meta model.json"));
			//schema.validate(new JSONObject(new JSONTokener(inputmodel))); 
			
			}catch (ValidationException e1) {
				System.out.println(e1.getMessage());
				  e1.getCausingExceptions().stream()
				      .map(ValidationException::getMessage)
				      .forEach(System.out::println);
				  
				  
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		
	}
	
		
	
	
}
