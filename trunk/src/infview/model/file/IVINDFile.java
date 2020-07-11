package infview.model.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.StringTokenizer;

import infview.workspace.Attribute;
import model.tree.Tree;

public class IVINDFile extends IVAbstractFile{
	
	private Tree tree;
	
	private String treeFileName;
	
	private String overZoneFileName;
	
	
		
	public IVINDFile(String filePath, String headerName) {
		super(filePath, headerName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
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
			        	 RECORD_SIZE=RECORD_SIZE+fieldLenght;
			        	 boolean fieldPK=new Boolean(tokens.nextToken());
			        	 Attribute field=new Attribute(fieldName,fieldType,fieldLenght,fieldPK); 
			        	 fields.add(field);
			         }else if (data[col].equals("path")){
			        	    fileName=tokens.nextToken();
			         }else if (data[col].equals("tree")) {
			        	 treeFileName = tokens.nextToken();
			         }else if (data[col].equals("overZone")) {
			        	 overZoneFileName = tokens.nextToken();
			         }
			      }
			   }
			   RECORD_SIZE=RECORD_SIZE+2;	   
			   RandomAccessFile afile=new RandomAccessFile(filePath+File.separator+fileName,"r");
			   FILE_SIZE=afile.length();
			   RECORD_NUM = FILE_SIZE/RECORD_SIZE;
				
				BLOCK_NUM =(int) (RECORD_NUM/BLOCK_FACTOR);
				if (RECORD_NUM % BLOCK_FACTOR !=0) BLOCK_NUM++;
			   afile.close();
			   
			   openTree(filePath + File.separator + treeFileName);
	}
	
	public void fetchNextBlock(int pointer) throws IOException{
		
		
		FILE_POINTER = pointer;
		RandomAccessFile raf = new RandomAccessFile(filePath + File.separator + fileName,  "r");	
		
		FILE_SIZE = raf.length();
		
		RECORD_NUM = FILE_SIZE/RECORD_SIZE;
		
		BLOCK_NUM =(int) (RECORD_NUM/BLOCK_FACTOR);
		if (RECORD_NUM % BLOCK_FACTOR !=0) BLOCK_NUM++;
		
		if (FILE_POINTER/RECORD_SIZE + BLOCK_FACTOR < RECORD_NUM) {
			BUFFER_SIZE = (int)(RECORD_SIZE * BLOCK_FACTOR);
		} else {
			//Prvo je velicina celog fajla, drugo dokle smo stigli
			BUFFER_SIZE = (int) (RECORD_NUM*RECORD_SIZE - FILE_POINTER);
		}
		
		buffer = new byte[BUFFER_SIZE];
		data = new String[(int) BUFFER_SIZE/RECORD_SIZE][fields.size()];
		raf.seek(FILE_POINTER);
		raf.read(buffer);
		
		//procitali smo blok, sad ide obrada toga
		
		String sadrzaj = new String(buffer);
		
		for (int i=0; i<data.length; i++) {
			String line = sadrzaj.substring(i*RECORD_SIZE, (i+1)*RECORD_SIZE);
			
			int k = 0;
			for (int j=0; j<fields.size(); j++) {
				String polje = null;
				polje = line.substring(k, k+fields.get(j).getLength());
				data[i][j] = polje;
				k+=fields.get(j).getLength();
			}
		}
		
		FILE_POINTER = raf.getFilePointer();
		raf.close();
		fireUpdateBlockPerformed(); 
	}

	
	public void openTree(String treeFilePath){

		ObjectInputStream os=null;
		try {
			os = new ObjectInputStream(new FileInputStream(treeFilePath));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		  
		try {
			tree = (Tree) os.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean addRecord(ArrayList<String> record) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean updateRecord(ArrayList<String> record) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int findRecord(ArrayList<String> searchRec) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean deleteRecord(ArrayList<String> searchRec) {
		// TODO Auto-generated method stub
		return false;
	}

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	public String getTreeFileName() {
		return treeFileName;
	}

	public void setTreeFileName(String treeFileName) {
		this.treeFileName = treeFileName;
	}

	public String getOverZoneFileName() {
		return overZoneFileName;
	}

	public void setOverZoneFileName(String overZoneFileName) {
		this.overZoneFileName = overZoneFileName;
	}

	@Override
	public void fetchNextBlock() throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	
}
