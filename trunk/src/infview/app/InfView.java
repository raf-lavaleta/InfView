package infview.app;

import javax.swing.JFrame;

public class InfView {

	public static void main(String[] args) {
		MainFrame frame = MainFrame.getInstance();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
