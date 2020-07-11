package infview.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public class ModifyAttributeAction extends IWAbstractAction{

	public ModifyAttributeAction() {
		// TODO Auto-generated constructor stub
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		         KeyEvent.VK_O, ActionEvent.ALT_MASK));
		putValue(SMALL_ICON, new ImageIcon("images/Close.png"));
		putValue(SHORT_DESCRIPTION, "Modify Attribute");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
