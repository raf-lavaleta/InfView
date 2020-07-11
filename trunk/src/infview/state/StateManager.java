package infview.state;

import java.util.ArrayList;

import javax.swing.JPanel;

import org.w3c.dom.Attr;

import infview.app.MainFrame;
import infview.events.StateChangeListener;
import infview.gui.MyTabbedPane;
import infview.model.file.IVAbstractFile;
import infview.model.file.IVFile;
import infview.workspace.Attribute;

public class StateManager extends JPanel{
		
		private State currState;
		
		private SortState sortState;
		private FindState findState;
		private RelationState relationState;
		private ArrayList<StateChangeListener> listeners = new ArrayList<>();
		public StateManager() {
			// TODO Auto-generated constructor stub
			sortState = new SortState();
			findState = new FindState();
			relationState = new RelationState();
		}

		public State getCurrState() {
			return currState;
		}

		public void setSortState(IVFile file, ArrayList<Attribute> fields) {
			currState = sortState;
			sortState.drawState(file, fields);
			alert();
		}

		public void setFindState(IVFile file, ArrayList<Attribute> fields) {
			currState = findState;
			findState.drawState(file, fields);
			alert();
		}
		
		public void setRelationState(IVAbstractFile file, ArrayList<Attribute> fields) {
			currState = relationState;
			relationState.drawState(file, fields);
			alert();
		}
		
		public void addListener(StateChangeListener scl) {
			listeners.add(scl);
		}
		public void alert() {
			for (StateChangeListener scl : listeners) {
				scl.update(currState);
			}
		}
}
