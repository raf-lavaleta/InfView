package infview.model.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import infview.app.MainFrame;
import infview.state.FindState;
import infview.workspace.Attribute;

public class IVSERFile extends IVAbstractFile{

	public IVSERFile(String filePath, String headerName) {
		super(filePath, headerName);
		// TODO Auto-generated constructor stub
	}
	
	
	public void fetchNextBlock() throws IOException{
		
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


	@Override
	public boolean addRecord(ArrayList<String> record) throws IOException {
		String newRecord="\r\n"; 
		System.out.println("upisujem valjda");
//		for (int i=0;i<record.size();i++){
//			 newRecord=newRecord+record.get(i);
//		} 
		FindState findState = (FindState)(MainFrame.getInstance().getStateManager().getCurrState());
		
		
		for(String str:findState.getNameParams()) {
			String string = findState.getSearchParam().get(str).getText();
			System.out.println(findState.getSearchParam().get(str).getName() + "  --------  Ovo je u prvom for-u");
			
			newRecord += string;
			for(Attribute att:findState.getFields()) {
				System.out.println(att.getName() + "  --------  Ovo je u atributu");
				if(att.getName().equals(findState.getSearchParam().get(str).getName())) {
					System.out.println(att.getName());
					for(int i = 0; i < att.getLength()-string.length(); i++) {
						newRecord += " ";
					}
				}
			}
		}
		
		RandomAccessFile afile=new RandomAccessFile(filePath+File.separator+fileName,"rw"); 
		afile.seek(afile.length()); 
		afile.writeBytes(newRecord);
		afile.close();
		
		JOptionPane.showMessageDialog(MainFrame.getInstance(), "Uspesno dodavanje u fajl");
		return true;
	}


	@Override
	public boolean updateRecord(ArrayList<String> record) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int findRecord(ArrayList<String> searchRec) {
		System.out.println("Pls radi");
		
		try {
			RandomAccessFile raf = new RandomAccessFile(filePath + File.separator + fileName, "r");
			FindState findState = (FindState)(MainFrame.getInstance().getStateManager().getCurrState());
			if (!findState.isFromStart()) raf.seek(FILE_POINTER);
			else raf.seek(0);
			ArrayList<ArrayList<String>> tmpStorage = new ArrayList<ArrayList<String>>();
			
			int i = 0;
			int count;
			
			if(findState.isMultiFind()) count = -1;
			else count = 1;
			
			while(count != 0 && raf.length()>raf.getFilePointer()) {
				byte[] tmp = new byte[RECORD_SIZE];
				raf.read(tmp);
				String record = new String(tmp);
				int k = 0;
				HashMap<String, String> attributeMap = new HashMap<>();
				
				for (Attribute a: fields) {
					attributeMap.put(a.getName(), record.substring(k, k+a.getLength()).trim());
					k+=a.getLength();
				}
				boolean found = true;
				for (String s : searchRec) {
					String lookingFor = findState.getSearchParam().get(s).getText();
					String have = attributeMap.get(s);
					if (!have.equals(lookingFor) && !lookingFor.equals("")) {
						found = false;
						break;
					}
				}
				if (!found) continue;
				k = 0;
				count--;
				tmpStorage.add(new ArrayList<String>());
				for (int j=0; j<fields.size(); j++) {
					String polje = null;
					polje = record.substring(k, k+fields.get(j).getLength());
					tmpStorage.get(i).add(polje);
					k+=fields.get(j).getLength();
					
				}
				i++;
			}
			int t=0;
			data = new String[i][fields.size()];
			
			for (ArrayList<String> al:tmpStorage) {
				for (int j=0;j<al.size();j++) {
					data[t][j] = al.get(j);
				}
				t++;
			}
			if (!findState.isFileStore()) {
				fireUpdateBlockPerformed(); 
				FILE_POINTER = raf.getFilePointer();
				raf.close();
			}
			else {
				File storageFile = new File(filePath + File.separator + fileName.substring(0, fileName.length()-5) + "SearchResult.stxt");
				int fileNr = 0;
				while(storageFile.exists()) {
					storageFile = new File(filePath + File.separator + fileName.substring(0, fileName.length()-5) + "SearchResult" + fileNr + ".stxt");
				}
				storageFile.createNewFile();
				FileWriter fw = new FileWriter(storageFile);
				BufferedWriter bw = new BufferedWriter(fw);
				for(int j=0; j<data.length;j++) {
					for(t=0;t<data[j].length;t++) {
						//String formats = "%"+"s" + fields.get(t).getLength();
						//fw.write(String.format(formats, data[j][t]));
						fw.write(data[j][t]);
					}
					fw.write(System.lineSeparator());
				}
				fw.close();
				long filePointer = FILE_POINTER;
				FILE_POINTER = 0;
				File storageFileSEK = new File(storageFile.getParent() + File.separator + 
						storageFile.getName().substring(0, fileName.length()-5) + "SearchResult" + (fileNr!=0 ? fileNr:"") + ".sek");
				storageFileSEK.createNewFile();
				FileWriter fwSEK = new FileWriter(storageFileSEK);
				BufferedReader bread = new BufferedReader(new FileReader(new File(filePath + File.separator + headerName)));
				String s = "path/"+storageFile.getName();
				bread.readLine();
				fwSEK.write(s);
				fwSEK.write(System.lineSeparator());
				while ((s = bread.readLine())!=null) {
					fwSEK.write(s);
					fwSEK.write(System.lineSeparator());
				}
				fwSEK.close();
				bread.close();
				FILE_POINTER = filePointer;
				notifyViewListeners(this);
				raf.close();
			}
			return i;
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return 0;
	}
	
	@Override
	public boolean deleteRecord(ArrayList<String> searchRec) {
		
		
		try {
			MainFrame.getInstance().setFilePath(filePath + "''..'" + fileName + "''..'" + headerName);
			RandomAccessFile raf = new RandomAccessFile(filePath + File.separator + fileName, "rw");
			FindState findState = (FindState)(MainFrame.getInstance().getStateManager().getCurrState());
			raf.seek(0);
			File storageFile = new File(filePath + File.separator + fileName.substring(0, fileName.length()-4) + ".stxt");
			FileWriter fw = new FileWriter(storageFile);
			BufferedWriter bw = new BufferedWriter(fw);
//			storageFile.createNewFile();
			String pomocniString = "";
			long broj = 0;
			while(raf.length()>raf.getFilePointer()) {
				byte[] tmp = new byte[RECORD_SIZE];
				raf.read(tmp);
				String record = new String(tmp);
				boolean chack = true;
				
				int k=0;
				HashMap<String, String> attributeMap = new HashMap<>();
				for (Attribute a: fields) {
					attributeMap.put(a.getName(), record.substring(k, k+a.getLength()).trim());
					k+=a.getLength();
				}
				for(String s: searchRec) {
					String lookingFor = findState.getSearchParam().get(s).getText();
					String have = attributeMap.get(s);
					if(lookingFor.equals(have) && !lookingFor.equals("")) {
						chack = false;
					}
				}
				if(chack) {
					pomocniString+=record;
				}
				bw.write(record);
				broj++;
			}
			bw.close();
			fw.close();
			System.out.println(broj);
			PrintWriter pw1 = new PrintWriter(new File(filePath + File.separator + fileName));
			pw1.write("");
			pw1.close();
			PrintWriter pw2 = new PrintWriter(new File(filePath + File.separator + fileName));
			pw2.write(pomocniString);
			
			File fileSEK = new File(filePath + File.separator + headerName);
			
			notifyViewListeners(this);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
