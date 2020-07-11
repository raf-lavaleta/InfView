package infview.events;

import infview.database.state.DatabaseState;

public interface DBStateChangeListener {
	public void update(DatabaseState dbState);
}
