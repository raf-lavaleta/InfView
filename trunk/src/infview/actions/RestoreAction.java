package infview.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import infview.app.MainFrame;
import infview.gui.MyTabbedPane;
import infview.model.file.IVSEKFile;
import infview.view.FileView;
import infview.workspace.Entity;

public class RestoreAction extends IWAbstractAction{

	public RestoreAction() {
		
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.ALT_MASK));
		putValue(SMALL_ICON, new ImageIcon("images/Save.png"));
		putValue(SHORT_DESCRIPTION, "Load serial");

}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(!MainFrame.getInstance().getFilePath().equals("")) {
			
			System.out.println("Poziva se restore action");
			
			String[] str = MainFrame.getInstance().getFilePath().split("''..'");
			
			String filePath = str[0] + File.separator + str[1];
			String headerName = str[2];
		
			System.out.println(filePath);
			System.out.println(filePath.substring(0, filePath.length()-4) + ".stxt" + " - - - - - ovo je ovako xD");
			File mainFile = new File(filePath);
			File auxFile = new File(filePath.substring(0, filePath.length()-4) + ".stxt");

			try {
				PrintWriter pw = new PrintWriter(mainFile);
				pw.write("");
				
				FileChannel src = new FileInputStream(auxFile).getChannel();
				FileChannel dest = new FileOutputStream(mainFile).getChannel();
				dest.transferFrom(src, 0, src.size());
				
				
				pw.close();
				src.close();
				dest.close();
				
				File fileSEK = new File(str[0] + File.separator + str[2]); ///// ovde ima neka fina greska ali sam sad previse umoran da bi ista radio tako da cu da idem sad da spavam pa cu sutra da nastavim. Nadam se da cu sutra uspeti, ipak cu biti odmorniji, nadam se da ce to pomoci. Laku noc, lepo spavaj i kOd ne sanjaj.
				System.out.println(fileSEK.getPath());
				
				
				Object tmp = MainFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
				IVSEKFile tmpFile = new IVSEKFile(fileSEK.getParent(), fileSEK.getName());
				MyTabbedPane tmpPane = MainFrame.getInstance().getDesniPanel().getTopTabPane();
				MainFrame.getInstance().getDesniPanel().setDividerLocation(800);
				Entity tmpParent = ((FileView)(tmpPane.getSelectedComponent())).getRoditelj();
				FileView fv = new FileView(tmpFile, tmpParent);
				tmpPane.add(fv, ((Entity)tmp).getName());
				tmpPane.setSelectedComponent(fv);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
}
