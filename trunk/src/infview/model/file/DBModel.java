package infview.model.file;

import java.util.ArrayList;

import infview.SQL_proxy.SQLRequestManager;
import infview.events.DBModelListener;
import infview.workspace.Attribute;
import infview.workspace.Entity;
import infview.workspace.Relation;

public class DBModel extends Entity{
	
		Object[][] data;
	
		ArrayList<DBModelListener> listeners;
		
		String refreshRequest;
		
		Object[] currentColumns;
		
		public DBModel(String request, Entity entity) {
	
			super(entity.getName());
			refreshRequest = request;
			data = SQLRequestManager.executeTransaction(request, SQLRequestManager.transactionTypes.FETCH, null);
			for (Attribute a : entity.getAttributes()) {
				addAttribute(a);
			}
			for (Relation r : entity.getRelations()) {
				addRelation(r);
			}
			listeners = new ArrayList<>();
			currentColumns = getAttributes().toArray();
		}

		public Object[][] getData() {
			return data;
		}

		public void setData(Object[][] data) {
			this.data = data;
			alert();
		}
		
		public void refreshData() {
			data = SQLRequestManager.executeTransaction(refreshRequest, SQLRequestManager.transactionTypes.FETCH, null);
			currentColumns = getAttributes().toArray();
			alert();
		}
		
		public ArrayList<DBModelListener> getListeners() {
			return listeners;
		}
		
		public void addListener(DBModelListener listener) {
			listeners.add(listener);
		}
		
		public void removeListener(DBModelListener listener) {
			if (listeners.contains(listener)) listeners.remove(listener);
		}
		
		public void getAverage(String request, Object[] title) {
			data = SQLRequestManager.executeTransaction(request, SQLRequestManager.transactionTypes.AVERAGE, null);
			currentColumns = title;
			alert();
		}
		
		public void sortDB(String request) {
			data = SQLRequestManager.executeTransaction(request, SQLRequestManager.transactionTypes.AVERAGE, null);
			currentColumns = getAttributes().toArray();
			alert();
		}
		
		private void alert() {
			for (DBModelListener listener : listeners) {
				listener.databaseModelChanged(data, currentColumns);
			}
		}
}
