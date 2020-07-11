package infview.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MenuBar() {
		
		JMenu jmFile = new JMenu("File");
		JMenu jmView = new JMenu("View");
		
		this.add(jmFile);
		this.add(jmView);
		
		
		// TODO Auto-generated constructor stub
	}
	
}
