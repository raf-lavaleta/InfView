package infview.view;

import javax.swing.JPanel;

import infview.workspace.Entity;

public class MyPanel extends JPanel{
	private Entity roditelj;

	public Entity getRoditelj() {
		return roditelj;
	}

	public void setRoditelj(Entity roditelj) {
		this.roditelj = roditelj;
	}
	
	
}
