package infview.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import infview.app.MainFrame;
import infview.view.DBView;
import infview.workspace.Entity;

public class DBTreeMouseController implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getClickCount() != 2) return;
		
		Object tmpEntity = MainFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
		if (! (tmpEntity instanceof Entity)) return;
		Entity entity = (Entity) tmpEntity;
		DBView dbView = new DBView(entity);
		MainFrame.getInstance().getDesniPanel().getTopTabPane().add(dbView, entity.getName());
		MainFrame.getInstance().getDesniPanel().getTopTabPane().setSelectedComponent(dbView);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
