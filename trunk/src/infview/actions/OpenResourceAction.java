package infview.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactoryLoader;
import javax.xml.validation.Validator;
import javax.xml.validation.ValidatorHandler;

import infview.app.MainFrame;
import infview.controller.OtvaranjeSchema;
import infview.workspace.Attribute;
import infview.workspace.Entity;
import infview.workspace.Relation;
import infview.workspace.Resource;
import infview.workspace.Workspace;

public class OpenResourceAction extends IWAbstractAction{

	public OpenResourceAction() {
		// TODO Auto-generated constructor stub
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.ALT_MASK));
		putValue(SMALL_ICON, new ImageIcon("images/Open.png"));
		putValue(SHORT_DESCRIPTION, "Load workspace");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new FileNameExtensionFilter("json","json"));
		int i = jfc.showOpenDialog(null);
		File dir = jfc.getSelectedFile();
		if (i == JFileChooser.CANCEL_OPTION) return;

		OtvaranjeSchema os = new OtvaranjeSchema(dir.toString()); 
		
	}
}
