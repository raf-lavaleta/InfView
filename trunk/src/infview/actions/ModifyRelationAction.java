package infview.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public class ModifyRelationAction extends IWAbstractAction{

	public ModifyRelationAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		         KeyEvent.VK_O, ActionEvent.ALT_MASK));
		putValue(SMALL_ICON, new ImageIcon("images/Switch.png"));
		putValue(SHORT_DESCRIPTION, "Modify Relation");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
