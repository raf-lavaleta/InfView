package infview.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import infview.app.MainFrame;
import infview.gui.MSEditor;
import infview.view.FileView;

public class OtvoriMSEAction extends IWAbstractAction{

	public OtvoriMSEAction() {
		// TODO Auto-generated constructor stub
				putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				        KeyEvent.VK_O, ActionEvent.ALT_MASK));
				putValue(SMALL_ICON, new ImageIcon("images/Close.png"));
				putValue(SHORT_DESCRIPTION, "Load workspace");
		
	}
	
	@Override
		public void actionPerformed(ActionEvent e) {
			
		//System.out.println(((FileView)MainFrame.getInstance().getDesniPanel().getTopTabPane().getSelectedComponent()).getMyTable());
			
		}
	

}
