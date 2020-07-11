package infview.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import infview.app.MainFrame;
import infview.controller.RightPanelTopTableController;
import infview.database.state.DatabaseState;
import infview.database.state.DatabaseStateManager;
import infview.events.DBStateChangeListener;
import infview.events.FileViewListener;
import infview.events.StateChangeListener;
import infview.model.file.IVAbstractFile;
import infview.state.FindState;
import infview.state.State;
import infview.view.DBView;
import infview.view.FileView;
import infview.workspace.Entity;

public class RightSide extends JSplitPane implements StateChangeListener, FileViewListener, DBStateChangeListener{
	
	private MyTabbedPane topTabPane = new MyTabbedPane();
	private MyTabbedPane bottomTabPane = new MyTabbedPane();
	private JPanel bottomPanel = new JPanel();
	public RightSide() {
		// TODO Auto-generated constructor stub		
	}
	public RightSide(int split) {
		super(split, null, null);
		this.setTopComponent(topTabPane);
		this.setBottomComponent(bottomTabPane);
		topTabPane.addMouseListener(new RightPanelTopTableController());
	}
	public MyTabbedPane getTopTabPane() {
		return topTabPane;
	}
	public void setTopTabPane(MyTabbedPane topTabPane) {
		this.topTabPane = topTabPane;
	}
	public MyTabbedPane getBottomTabPane() {
		return bottomTabPane;
	}
	public void setBottomTabPane(MyTabbedPane bottomTabPane) {
		this.bottomTabPane = bottomTabPane;
	}
	
	public void update(State s) {
		System.out.println("Promenio state");
		setBottomComponent(s);
	}
	@Override
	public void update(IVAbstractFile ivFile) {
		Entity table = ((FileView)(topTabPane.getSelectedComponent())).getRoditelj();
		FileView newView = new FileView(ivFile, table);
		topTabPane.add(newView, ivFile.getName());
		topTabPane.setSelectedComponent(newView);
	}
	@Override
	public void update(DatabaseState dbState) {
			setBottomComponent(dbState);
	}
}
