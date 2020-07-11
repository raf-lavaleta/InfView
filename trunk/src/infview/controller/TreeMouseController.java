package infview.controller;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import infview.app.MainFrame;
import infview.gui.MyTabbedPane;
import infview.model.MyTableModel;
import infview.model.file.IVAbstractFile;
import infview.model.file.IVINDFile;
import infview.model.file.IVSEKFile;
import infview.model.file.IVSERFile;
import infview.view.FileView;
import infview.view.MyPanel;
import infview.view.MyTable;
import infview.workspace.Attribute;
import infview.workspace.Entity;

public class TreeMouseController implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		Object tmp = MainFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
		if (!(tmp instanceof Entity)) return;
		
		
//		MyTableModel mtblModel = new MyTableModel();
//		MyTable mtbl = new MyTable(mtblModel);
//		mtbl.setName(((Entity)tmp).toString());
//		for (Attribute a: ((Entity)tmp).getAttributes()) {
//			mtblModel.addColumn(a.toString());
//		}
//		MyPanel tmpPan = new MyPanel();
//		tmpPan.setRoditelj((Entity)tmp);
//		tmpPan.add(mtbl.getTableHeader(), BorderLayout.PAGE_START);
//		tmpPan.add(mtbl);
//		MyTabbedPane tmpPane = MainFrame.getInstance().getDesniPanel().getTopTabPane();
//		
//		for (int i = 0; i<tmpPane.getTabCount();i++) {
//			if (((MyPanel)(tmpPane.getComponent(i))).getRoditelj().equals((Entity)tmp)) {
//				tmpPane.setSelectedIndex(i);
//				return;
//			}
//		}
//		
//		tmpPane.add(tmpPan, mtbl.getName());
//		tmpPane.setSelectedComponent(tmpPan);
		
		JFileChooser jfc = new JFileChooser();
		int i = jfc.showOpenDialog(null);
		if (i == JFileChooser.CANCEL_OPTION) return;
		File dir = jfc.getSelectedFile();
		
		
		IVAbstractFile ivFile;
		System.out.println(dir.getPath());
		if (dir.getPath().contains(".ser")) ivFile = new IVSERFile(dir.getParent(), dir.getName());
		else if (dir.getPath().contains(".sek")) ivFile = new IVSEKFile(dir.getParent(), dir.getName());
		else if (dir.getPath().contains(".ind")) ivFile = new IVINDFile(dir.getParent(), dir.getName());
		else return;
		System.out.println(dir.getParent() + "  " + dir.getName());
		FileView fw = new FileView(ivFile, ((Entity)tmp));
		MyTabbedPane tmpPane = MainFrame.getInstance().getDesniPanel().getTopTabPane();
		MainFrame.getInstance().getDesniPanel().setDividerLocation(800);
		for (i = 0; i<tmpPane.getTabCount();i++) {
			if (((FileView)(tmpPane.getComponent(i))).getRoditelj().equals((Entity)tmp)) {
				tmpPane.setSelectedIndex(i);
				return;
			}
		}
		tmpPane.add(fw, dir.getName());
		tmpPane.setSelectedComponent(fw);
		
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
