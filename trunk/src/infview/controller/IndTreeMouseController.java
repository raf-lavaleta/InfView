package infview.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import infview.app.MainFrame;
import infview.model.file.IVINDFile;
import infview.view.FileView;
import model.tree.Node;

public class IndTreeMouseController implements MouseListener {

	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		Node tmp = (Node) MainFrame.getInstance().getIndexTree().getLastSelectedPathComponent();
		
		
			
			IVINDFile ivFile =(IVINDFile) ((FileView)MainFrame.getInstance().getDesniPanel().getTopTabPane().getSelectedComponent()).getIvFile();
			
			try {
				ivFile.fetchNextBlock(tmp.getData().get(0).getBlockAddress()*ivFile.getRECORD_SIZE());
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		
			
			
			
			
			
			
			
			
			
			
			
		}
		 
		
		
		
		
	

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
