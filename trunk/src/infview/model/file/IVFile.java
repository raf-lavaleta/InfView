package infview.model.file;

import java.io.IOException;
import java.util.ArrayList;

public interface IVFile {

	
	public void readHeader() throws IOException;
	public void fetchNextBlock() throws IOException;
	public boolean addRecord(ArrayList<String> record) throws IOException;
	public boolean updateRecord(ArrayList<String> record)throws IOException;
	public int findRecord(ArrayList<String> searchRec);
	public boolean deleteRecord(ArrayList<String> searchRec);
	
	
}
