package infview.database.state;

import java.util.ArrayList;

import javax.swing.JPanel;

import infview.events.DBStateChangeListener;
import infview.workspace.Entity;

public class DatabaseStateManager{
	
	private DatabaseState currState;
	private DBUpdateState dbUpdateState;
	private DBSortState dbSortState;
	private DBRelationState dbRelationState;
	private DBAvgState dbAvgState;
	private DBCountState dbCountState;
	private DBAddRecordState dbAddRecordState;
	private DBFindFilterState dbFindFilterState;
	
	private ArrayList<DBStateChangeListener> listeners = new ArrayList<>();
	
	public DatabaseStateManager() {

		dbUpdateState = new DBUpdateState();
	
		dbSortState = new DBSortState();
		
		dbRelationState = new DBRelationState();
		
		dbAvgState = new DBAvgState();
		
		dbCountState = new DBCountState();
		
		dbAddRecordState = new DBAddRecordState(); 
		
		dbFindFilterState = new DBFindFilterState();
	}
	
	public void setDBUpdateState(Entity entity) {
		currState = dbUpdateState;
		currState.drawState(entity);
		alertListeners();
	}

	public void setDBSortState(Entity entity) {
		currState = dbSortState;
		currState.drawState(entity);
		alertListeners();
	}
	
	public void setDBRelationState(Entity entity) {
		currState = dbRelationState;
		currState.drawState(entity);
		alertListeners();
	}
	
	public void setDBAvgState(Entity entity) {
		currState = dbAvgState;
		currState.drawState(entity);
		alertListeners();
	}
	
	public void setDBCountState(Entity entity) {
		currState = dbCountState;
		currState.drawState(entity);
		alertListeners();
	}
	
	public void setDBAddRecordState(Entity entity) {
		currState = dbAddRecordState;
		currState.drawState(entity);
		alertListeners();
	}
	
	public void setDbFindFilterState(Entity entity) {
		currState = dbFindFilterState;
		currState.drawState(entity);
		alertListeners();
	}
	
	public DatabaseState getCurrState() {
		return currState;
	}

	
	
	public ArrayList<DBStateChangeListener> getListeners() {
		return listeners;
	}

	public void setListeners(ArrayList<DBStateChangeListener> listeners) {
		this.listeners = listeners;
	}
	
	private void alertListeners() {
		for (DBStateChangeListener listener : listeners) {
			listener.update(currState);
		}
	}
}
