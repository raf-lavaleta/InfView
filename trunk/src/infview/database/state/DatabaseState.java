package infview.database.state;

import javax.swing.JPanel;

import infview.workspace.Entity;

public abstract class DatabaseState extends JPanel{
	
	abstract public void drawState(Entity entity);
}
