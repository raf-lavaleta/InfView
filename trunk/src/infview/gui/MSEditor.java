package infview.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class MSEditor extends JFrame{
		
	
	private TextArea textArea;
	Toolkit kit = Toolkit.getDefaultToolkit();
	int ScreenH = kit.getScreenSize().height;
	int ScreenW = kit.getScreenSize().width;
	

	public MSEditor() {
		
		
		JPanel splitp = new JPanel();
		
		textArea = new TextArea();
		//splitp.setTopComponent(textArea);
		textArea.setMaximumSize(new Dimension(3*ScreenW/4, 3*ScreenH/4));
		
		setLayout(new BorderLayout());
		
		splitp.add(textArea);
		
		
		add(splitp);
			
		setSize(ScreenW/3, 3*ScreenH/6);
		
		
		
		JButton exit = new JButton("Exiiiiiiit");
		
		ToolBar toolbar = new ToolBar(1);
		
		add(toolbar, BorderLayout.NORTH);
		setTitle("Meta sheme ");
		
		
		setVisible(true);
		
		
		
		
		
	}


	public TextArea getTextArea() {
		return textArea;
	}


	public void setTextArea(TextArea textArea) {
		this.textArea = textArea;
	}
	
}
