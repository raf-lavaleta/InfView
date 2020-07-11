package infview.model.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.FileHandler;

import infview.app.MainFrame;
import infview.state.FindState;
import infview.workspace.Attribute;
import model.tree.KeyElement;
import model.tree.Node;
import model.tree.NodeElement;
import model.tree.Tree;


//Fetch je identican kao kod SER

public class IVSEKFile extends IVAbstractFile{

	public IVSEKFile(String filePath, String headerName) {
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
			BUFFER_SIZE = (int) (RECORD_NUM*RECORD_SIZE - FILE_POINTER);
		}
		
		buffer = new byte[BUFFER_SIZE];
		data = new String[(int) BUFFER_SIZE/RECORD_SIZE][fields.size()];
		raf.seek(FILE_POINTER);
		raf.read(buffer);
		
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
		System.out.println("idemo");
		try {
			RandomAccessFile raf = new RandomAccessFile(filePath + File.separator + fileName, "r");
			FindState findState = (FindState)(MainFrame.getInstance().getStateManager().getCurrState());
			if (!findState.isFromStart()) raf.seek(FILE_POINTER);
			else raf.seek(0);
			ArrayList<ArrayList<String>> tmpStorage = new ArrayList<ArrayList<String>>();
			
			int i = 0;
			int count;
			if (findState.isMultiFind()) count = -1;
			else count = 1;
			while (count != 0 && raf.length()>raf.getFilePointer()) {
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
				
				//data = new String[150][fields.size()];
				tmpStorage.add(new ArrayList<String>());
				for (int j=0; j<fields.size(); j++) {
					String polje = null;
					polje = record.substring(k, k+fields.get(j).getLength());
					//data[i][j] = polje;
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
					fileNr++;
					storageFile = new File(filePath + File.separator + fileName.substring(0, fileName.length()-5) + "SearchResult" + fileNr + ".stxt");
				}
				storageFile.createNewFile();
				FileWriter fw = new FileWriter(storageFile);
				BufferedWriter bw = new BufferedWriter(fw);
				for(int j=0; j<data.length;j++) {
					for(t=0;t<data[j].length;t++) {
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
			//broj nadjenih Recorda
			return i;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return 0;
	}

	public int binaryFind(ArrayList<String> searchRec) {
		System.out.println("Binaran sine");
		FILE_POINTER = RECORD_NUM/2 * RECORD_SIZE;
		long recNum = RECORD_NUM/2; //////////////////// + 1 dodato
		try {
			RandomAccessFile raf = new RandomAccessFile(filePath + File.separator + fileName, "r");
			FindState findState = (FindState)(MainFrame.getInstance().getStateManager().getCurrState());
			raf.seek(FILE_POINTER);
			File fileHere = new File(filePath + File.separator + fileName.substring(0, fileName.length()-5) + "PomocniFajl.txt");
			PrintWriter pw = new PrintWriter(fileHere);
			int count;
			String gradiString = "";
			boolean flag = false;
			long left = 0;
			long right = RECORD_NUM; 
			long mid = left + (right - left) / 2;;
			recNum = mid;
			while(left <= right) {
				
				mid = left + (right - left) / 2; // tu sam izgleda
				
				byte[] tmp = new byte[RECORD_SIZE];
				raf.read(tmp);
				String record = new String(tmp);
				
				int k = 0;
				HashMap<String, String> attributeMap = new HashMap<>();
				String recordKey = "";
				String foundKey = "";
				
				for (Attribute a: fields) {
					if(a.isPrimaryKey()) {
						attributeMap.put(a.getName(), record.substring(k, k+a.getLength()).trim());
						recordKey+=record.substring(k, k+a.getLength());
						k+=a.getLength();
					}
				}
				for(String str: findState.getNameParams()) {
					for(String pom: searchRec) {
						if(str.equals(findState.getSearchParam().get(pom).getName())
								&& attributeMap.containsKey(str)) {
							foundKey+=findState.getSearchParam().get(pom).getText();
						}
					}
				}
				
				recordKey = recordKey.trim();
				
				if(recordKey == "") continue;
				if(recordKey.equals(foundKey)) {
					gradiString+=record;
					System.out.println("Pogodak!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					System.out.println(recordKey + " ----- " + foundKey + " ----- File pointer " + recNum);
					break;
				}
				
				
				int a = recordKey.compareTo(foundKey);
				
				String string;
				
				if(a>0) {
					string = "Levo";
				} else {
					string = "Desno";
				}
				
				if(a>0) {
					right = mid-1;
					
					mid = left + (right - left) / 2;
					recNum = mid;
					raf.seek(recNum * RECORD_SIZE);
					System.out.println(recordKey + " ----- " + foundKey + " " + string + " LEFT=" + left + " RIGHT=" + right + " MID= " + mid);
				} else if(a<0) {
					left = mid+1;
					
					mid = left + (right - left) / 2;
					recNum = mid;
					raf.seek(recNum * RECORD_SIZE);
					System.out.println(recordKey + " ----- " + foundKey + " " + string + " LEFT=" + left + " RIGHT=" + right + " MID= " + mid);
				}

			}
			pw.write(gradiString);
			pw.close();
			
			
			File auxFileSEK = new File(filePath + File.separator + headerName.substring(0, headerName.length()-5) + "AUX.sek");
			
			FileWriter fwSEK = new FileWriter(auxFileSEK);
			BufferedReader bread = new BufferedReader(new FileReader(new File(filePath + File.separator + headerName)));
			String s = "path/"+fileHere.getName();
			bread.readLine();
			fwSEK.write(s);
			fwSEK.write(System.lineSeparator());
			while ((s = bread.readLine())!=null) {
				fwSEK.write(s);
				fwSEK.write(System.lineSeparator());
			}
			
			fwSEK.close();
			bread.close();
			
			notifyViewListeners(this);
			
			raf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public void makeINDFile() throws IOException {
		makeTree();
		
		// Nisam siguran da li to drvo treba i da se prikaze
		// Ako treba onda bi taj kod isao ovde
		// Samo bi kao i na drugim mestima napravili FileView i njemu prosledili Tree
		// U specifikaciji se ne pominje prikazivanje drveta vec samo njegova izgradnja i serijalizacija
	}
	
	public void makeTree() throws IOException {
		try {
			FILE_POINTER = 0;
			List<Node> listNodes=new ArrayList<Node>(); 
			
			Tree tree = null;
			long filePoint = 0;
			
			int brojBlokova = 0;
			
			while(FILE_POINTER<FILE_SIZE) {
				filePoint = FILE_POINTER;
				fetchNextBlock();
				
				List<KeyElement> listKeyElements = new ArrayList<KeyElement>();
				List<NodeElement> listNodeElements = new ArrayList<NodeElement>();
				
				for (int i=0;i<fields.size();i++){
					if (fields.get(i).isPrimaryKey()){
						KeyElement keyElement=new KeyElement(fields.get(i).getType(),data[0][i]);
						listKeyElements.add(keyElement);
					}
				}
				
				NodeElement nodeElement=new NodeElement((int) filePoint/RECORD_SIZE, listKeyElements);
				listNodeElements.add(nodeElement);
				
				filePoint = FILE_POINTER;
				
				fetchNextBlock();
				
				try {
					String s = data[0][0];
				} catch (Exception e) {
					Node node = new  Node(listNodeElements);
					listNodes.add(node);
					break;
				}
				
				listKeyElements = new ArrayList<KeyElement>();
				
				for (int i=0;i<fields.size();i++){
					
					if (fields.get(i).isPrimaryKey()){
						KeyElement keyElement=new KeyElement(fields.get(i).getType(),data[0][i]);
						listKeyElements.add(keyElement);
					}
				}
				
				
				nodeElement=new NodeElement((int) filePoint/RECORD_SIZE, listKeyElements);
				listNodeElements.add(nodeElement);
				
				Node node = new  Node(listNodeElements);
				listNodes.add(node);
				
			}
			
			int i = 0;
			while(i < listNodes.size()) {
				Node parentNode = new Node();
				
				List<NodeElement> elements = new ArrayList<>();
				List<Node> children = new ArrayList<>();
				
				elements.add(listNodes.get(i).getData().get(0));
				children.add(listNodes.get(i++));
				
				if(i == listNodes.size()) {
					break;
				} else {
					elements.add(listNodes.get(i).getData().get(0));
					children.add(listNodes.get(i++));
					
					parentNode.data.addAll(elements);
					parentNode.children.addAll(children);
					
					listNodes.add(parentNode);
				}
			}
			
			Node root = listNodes.get(listNodes.size()-1);
			tree = new Tree();
			tree.setRootElement(root);
			
			FILE_POINTER = 0;
			
			ObjectOutputStream os;
			String treeFileName = headerName.replaceAll(".sek", ".tree");
			
			try {
				File pom = new File(filePath + File.separator + treeFileName);
				os = new ObjectOutputStream(new FileOutputStream(pom));
				os.writeObject(tree);
			} catch (Exception e) {
				System.out.println("Greska u serijalizaciji");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean deleteRecord(ArrayList<String> searchRec) {
		// TODO Auto-generated method stub
		return false;
	}  
	
	private boolean isRowEqual(String [] aData, ArrayList<String> searchRec){
		   boolean result=true;
		   
			
		     for (int col=0;col<fields.size();col++){
		    	 if (!searchRec.get(col).trim().equals("")){
		    		  if (!aData[col].trim().equals(searchRec.get(col).trim())){
		    			  result=false;
		    			  return result;
		    		  }
		    	 }

		   
	   }	   
	   
	   return result;
 }
 

}
