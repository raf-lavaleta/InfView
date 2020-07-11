package infview.model.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.event.EventListenerList;

import infview.events.FileViewListener;
import infview.events.UpdateBlockEvent;
import infview.events.UpdateBlockListener;
import infview.workspace.Attribute;
import infview.workspace.Entity;


public abstract class IVAbstractFile extends Entity implements IVFile{

	protected long BLOCK_FACTOR=20;
	protected int RECORD_SIZE=0;
	protected int BUFFER_SIZE=0;
	protected int BLOCK_NUM=0;
	protected long RECORD_NUM=0;
	protected long FILE_POINTER=0;
	protected long FILE_SIZE=0;
	
	protected FileMode MODE = FileMode.BROWSE_MODE;
	
	
	protected String filePath;
	protected String headerName;
	protected String fileName;
	
	protected ArrayList<Attribute> fields = new ArrayList<>();
	private ArrayList<FileViewListener> viewListeners = new ArrayList<>();


	protected byte[] buffer;
	protected String[][] data=null;
	
	EventListenerList listenerBlockList = new EventListenerList();
	UpdateBlockEvent updateBlockEvent = null;
	
	public IVAbstractFile(String filePath, String headerName) {
		super();
		this.filePath = filePath;
		this.headerName = headerName;
		this.fileName = headerName;
	}
	
	
	
	//Kopirano je i prepravljeno, nisam bas siguran kako sta, ako nije ok gledaj ovde
	
	public void readHeader() throws IOException{
		   String delimiter = "\\/";
		   ArrayList<String> headRec= new ArrayList<String>();
		   RandomAccessFile headerFile=null;
		   Object data[]=null;
		   
			
			headerFile = new RandomAccessFile(filePath+File.separator+headerName,"r");
			while (headerFile.getFilePointer()<headerFile.length() )
             	headRec.add(headerFile.readLine());
			
				headerFile.close();
	         
			   for (String record : headRec) {
			      StringTokenizer tokens = new StringTokenizer(record,delimiter);
			 
			      int cols = tokens.countTokens();
			      data = new String[cols];  
			      int col = 0;
			      while (tokens.hasMoreTokens()) {
			         data[col] = tokens.nextToken();
			         if (data[col].equals("field")){
			        	 String fieldName=tokens.nextToken();
			        	 String fieldType=tokens.nextToken();
			        	 int fieldLenght=Integer.parseInt(tokens.nextToken());
			        	 //velicina sloga jednaka je zbiru velicine pojedinacnih polja
			        	 RECORD_SIZE=RECORD_SIZE+fieldLenght;
			        	 boolean fieldPK=new Boolean(tokens.nextToken());
			        	 Attribute field=new Attribute(fieldName,fieldType,fieldLenght,fieldPK); 
			        	 fields.add(field);
			         }else if (data[col].equals("path")){
			        	    fileName=tokens.nextToken();
			         }   
			      }
			   }
			   //jos 2 bajta za oznaku novog reda u liniji datoteke
			   RECORD_SIZE=RECORD_SIZE+2;	   
			   //postavljanje atributa datoteke
			   RandomAccessFile afile=new RandomAccessFile(filePath+File.separator+fileName,"r");
			   FILE_SIZE=afile.length();
			   RECORD_NUM = FILE_SIZE/RECORD_SIZE;
				
				BLOCK_NUM =(int) (RECORD_NUM/BLOCK_FACTOR);
				if (RECORD_NUM % BLOCK_FACTOR !=0) BLOCK_NUM++;
			   afile.close();
	}

	public void addBlockListener(UpdateBlockListener listener) {
		listenerBlockList.add(UpdateBlockListener.class, listener);
	}
	
	public void removeBlockListener(UpdateBlockListener listener) {
		listenerBlockList.remove(UpdateBlockListener.class, listener);
	}
	
	protected void fireUpdateBlockPerformed() {

 	 Object[] listeners = listenerBlockList.getListenerList();
 	 int i = 0;
		for (Object o : listeners) {
		 i++;
		 if (i%2 == 1) continue;
		 if (updateBlockEvent == null) {
		 	 updateBlockEvent = new UpdateBlockEvent(this);
			 }
		 ((UpdateBlockListener)o).updateBlockPerformed(updateBlockEvent);
		}
	}
	
	public long getBLOCK_FACTOR() {
		return BLOCK_FACTOR;
	}



	public int getRECORD_SIZE() {
		return RECORD_SIZE;  
	}



	public int getBUFFER_SIZE() {
		return BUFFER_SIZE;
	}



	public int getBLOCK_NUM() {
		return BLOCK_NUM;
	}



	public long getRECORD_NUM() {
		return RECORD_NUM;
	}



	public long getFILE_POINTER() {
		return FILE_POINTER;
	}



	public long getFILE_SIZE() {
		return FILE_SIZE;
	}



	public FileMode getMODE() {
		return MODE;
	}



	public String getFilePath() {
		return filePath;
	}



	public String getHeaderName() {
		return headerName;
	}



	public String getFileName() {
		return fileName;
	}



	public ArrayList<Attribute> getFields() {
		return fields;
	}



	public byte[] getBuffer() {
		return buffer;
	}



	public String[][] getData() {
		return data;
	}



	@Override
	public String toString() {
		return fileName;
	}



	public void setMODE(FileMode mODE) {
		MODE = mODE;
	}



	public void setBLOCK_FACTOR(long bLOCK_FACTOR) {
		BLOCK_FACTOR = bLOCK_FACTOR;
	}	
	
	
	public ArrayList<FileViewListener> getViewListeners() {
		return viewListeners;
	}
	
	protected void notifyViewListeners (IVAbstractFile file) {
		for (FileViewListener fwl : getViewListeners()) {
			fwl.update(file);
		}
	}
}
