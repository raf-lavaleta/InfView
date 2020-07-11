package infview.controller;

import javax.swing.JOptionPane;

public class ErrorHandler extends JOptionPane{

	public ErrorHandler(String error) {
		
		this.showMessageDialog(null, error, "Alo bre", JOptionPane.ERROR_MESSAGE);

		
		setVisible(true);
		
	}
	
	
}
