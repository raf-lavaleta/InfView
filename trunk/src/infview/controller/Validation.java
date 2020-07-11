package infview.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
//import org.json.JSONObject;
//import org.json.JSONTokener;

public class Validation {

	
	public Validation() {
		
	
	}
	
	public boolean validiraj(String s) {
		try {
			
			InputStream input = new FileInputStream(new File("meta schema.json"));
			//JSONObject rawSchema = new JSONObject(new JSONTokener(input));
			//Schema schema = SchemaLoader.load(rawSchema);
			
			InputStream inputmodel = new FileInputStream(new File(s));
			//schema.validate(new JSONObject(new JSONTokener(inputmodel))); 
			
			}catch (ValidationException e1) {
				System.out.println(e1.getMessage());
				  e1.getCausingExceptions().stream()
				      .map(ValidationException::getMessage)
				      .forEach(System.out::println);
				  
				 //return false; 
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NullPointerException e) {
				System.out.println("Ovde");
				System.out.println(e.getMessage());
				
				
			}
			
		
		return true;
	}
	
	
}
