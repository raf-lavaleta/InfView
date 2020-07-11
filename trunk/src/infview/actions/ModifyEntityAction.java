package infview.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

//import infview.gui.ModifyEntityGui;

public class ModifyEntityAction extends IWAbstractAction{

	public ModifyEntityAction() {
		// TODO Auto-generated constructor stub
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		         KeyEvent.VK_O, ActionEvent.ALT_MASK));
		putValue(SMALL_ICON, new ImageIcon("images/Open.png"));
		putValue(SHORT_DESCRIPTION, "Modify Entity");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//ModifyEntityGui meg = new ModifyEntityGui(); 
	}

	
}
