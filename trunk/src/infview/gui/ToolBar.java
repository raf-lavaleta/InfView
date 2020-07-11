package infview.gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import infview.actions.OtvoriMSEAction;
import infview.app.MainFrame;

public class ToolBar extends JToolBar{

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	//private JButton openRescource;
	private JButton mseAction;
	private JButton restoreBtn;
	private JButton openDBRescource;
	
	private JButton btnModifyEntity;
	private JButton btnModifyAttribute;
	private JButton btnModifyRelations;
	
	private JButton saveMSEAction;
	
	public ToolBar(int a) {
//		openRescource = new JButton("open");
//		openRescource.setAction(MainFrame.getInstance().getActionManager().getOpenResourceAction());
//		add(openRescource);
//		
//		mseAction = new JButton("mse");
//		mseAction.setAction(MainFrame.getInstance().getActionManager().getMseAction());
//		add(mseAction);
		if(a == 0) {
			MainBar();
		}
		if(a == 1) {
			MSEToolBar();
		}
	}
	
	private void MainBar() {
//		openRescource = new JButton("open");
//		openRescource.setAction(MainFrame.getInstance().getActionManager().getOpenResourceAction());
//		add(openRescource);
		
		mseAction = new JButton("mse");
		mseAction.setAction(MainFrame.getInstance().getActionManager().getMseAction());
		add(mseAction);
		
		restoreBtn = new JButton("Prestore");
		restoreBtn.setAction(MainFrame.getInstance().getActionManager().getReadSerilaAction());
		add(restoreBtn);
		
		openDBRescource = new JButton("open");
		openDBRescource.setAction(MainFrame.getInstance().getActionManager().getOpenDBSchemaAction());
		add(openDBRescource);
	}
	
	private void MSEToolBar() {
		btnModifyEntity = new JButton("Modify entity");
		btnModifyEntity.setAction(MainFrame.getInstance().getActionManager().getModifyEntityAction());
		add(btnModifyEntity);
		btnModifyAttribute = new JButton("Modify Attribute");
		btnModifyAttribute.setAction(MainFrame.getInstance().getActionManager().getModifyAttributeAction());
		add(btnModifyAttribute);
		btnModifyRelations = new JButton("Modify Relations");
		btnModifyRelations.setAction(MainFrame.getInstance().getActionManager().getModifyRelationAction());
		add(btnModifyRelations);
		
		saveMSEAction = new JButton("Save");
		saveMSEAction.setAction(MainFrame.getInstance().getActionManager().getSaveMSEAction());
		add(saveMSEAction);
		
		
	}
}
