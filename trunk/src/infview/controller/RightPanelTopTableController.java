package infview.controller;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.plaf.TabbedPaneUI;

import infview.app.MainFrame;
import infview.gui.MyTabbedPane;
import infview.model.MyTableModel;
import infview.model.file.IVAbstractFile;
import infview.model.file.IVINDFile;
import infview.model.file.IVSEKFile;
import infview.model.file.IVSERFile;
import infview.view.DBView;
import infview.view.FileView;
import infview.view.MyPanel;
import infview.view.MyTable;
import infview.workspace.Attribute;
import infview.workspace.Entity;
import infview.workspace.Relation;

public class RightPanelTopTableController implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("pouse click xD");
		MyTabbedPane topRightUpPane = MainFrame.getInstance().getDesniPanel().getTopTabPane();
		
		int clickedTab = topRightUpPane.getUI().tabForCoordinate(topRightUpPane, e.getX(), e.getY());
		
//		if (clickedTab == -1) return;
		
		Object selectedView = topRightUpPane.getSelectedComponent();
//		Object selectedView = topRightUpPane.getSelectedComponent();
		
		if (selectedView instanceof DBView) {
			DBView selectedDBView = (DBView) selectedView;
//			System.err.println(selectedDBView.getRoditelj().getName());
			selectedDBView.getStateManager().setDBRelationState(selectedDBView.getDatabaseModel());
		}
		
		IVAbstractFile fileToDispay = ((FileView)selectedView).getIvFile();
		
		//MainFrame.getInstance().getStateManager().setRelationState(fileToDispay, fileToDispay.getFields());
	}
		
		
		
		
//		int tabNr = tab.getUI().tabForCoordinate(tab, e.getX(), e.getY());
//		
//		if (tabNr == -1) return;
//		
//		Entity clicked = ((FileView)tab.getComponent(tabNr)).getRoditelj();
//		
//		MainFrame.getInstance().getDesniPanel().getBottomTabPane().removeAll();
//		
//		for (Relation r: clicked.getRelations()) {
//			Entity tmp;
//			if ((r.getFromEntity()).equals(clicked)) {
//				tmp = r.getToEntity();
//			}
//			else tmp = r.getFromEntity();
//
//			MyTableModel mtblModel = new MyTableModel();
//			MyTable mtbl = new MyTable(mtblModel);
//			mtbl.setName(tmp.toString());
//			for (Attribute a:  tmp.getAttributes()) {
//				mtblModel.addColumn(a.toString());
//			}
//			MyPanel tmpPan = new MyPanel();
//			tmpPan.setRoditelj(tmp);
//			tmpPan.add(mtbl.getTableHeader(), BorderLayout.PAGE_START);
//			tmpPan.add(mtbl);
//
//			MainFrame.getInstance().getDesniPanel().getBottomTabPane().add(tmpPan, mtbl.getName());
//		}
//	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
