package infview.state;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.jar.Attributes;

import infview.app.MainFrame;
import infview.gui.MyTabbedPane;
import infview.model.file.IVAbstractFile;
import infview.model.file.IVFile;
import infview.model.file.IVSEKFile;
import infview.view.FileView;
import infview.workspace.Attribute;
import infview.workspace.Entity;
import infview.workspace.Relation;

public class RelationState extends State{

	
	public RelationState() {
		super();
	}
	
	@Override
	public void drawState(IVAbstractFile file, ArrayList<Attribute> fields) {
//		removeAll();
//		//System.out.println("Help");
//		
//		MyTabbedPane tab = MainFrame.getInstance().getDesniPanel().getTopTabPane();
//		Entity selectedTab = ((FileView)tab.getSelectedComponent()).getRoditelj();
//		MyTabbedPane tmpPane = new MyTabbedPane();
//		HashMap<String, String> tabela = MainFrame.getInstance().getTabela();
//		for (Relation rel : selectedTab.getRelations()) {
//			//System.out.println("help");
//			
//			Entity zaRel;
//			setLayout(new BorderLayout());
//			String relacija;
//			if(selectedTab.getName().equals(rel.getToEntity().getName())) {
//				zaRel = rel.getFromEntity();
//				relacija = tabela.get(rel.getToAttribute().getName());
//			}else {
//				zaRel = rel.getToEntity();
//				relacija = tabela.get(rel.getFromAttribute().getName());
//			}
//			
//			String dir;
//			
//			String[] temp = rel.getName().split("-");
//			String name;
//			IVAbstractFile ivFile = null;
//			String fileName;
//			//System.out.println(temp[0] + " = " + file.getFileName().substring(0, file.getFileName().length()-5));
//			if(temp[0].equalsIgnoreCase(file.getFileName().substring(0, file.getFileName().length()-5))) {
//				dir = "datoteke/sekvencijalne";
//				ivFile = new IVSEKFile("datoteke/sekvencijalne",  temp[1] +".sek");
//				fileName = temp[1];
//				
//			}else {
//				dir = "datoteke/sekvencijalne";
//				ivFile = new IVSEKFile("datoteke/sekvencijalne",  temp[0] +".sek");
//				fileName = temp[0];
//
//			}
//		try {
//				ivFile.readHeader();
//				file.readHeader();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//			ArrayList<Attribute> primKeys = new ArrayList<>();
//		
//			int fieldsSize = ivFile.getFields().size();
//			
//			int filesize = fields.size();
//			
////			for(int i = 0; i < primKeys.size(); i++) {
////				System.out.println(ivFile.get(i));
////			}
////			
//			int brojprimarnih = 0;
//			
//			
//			for(int i = 0; i < fieldsSize; i++) {
//				//System.out.println(fields.get(i).toString());
//				//System.out.println(ivFile.getFields().get(i).toString());
//			}
//			//System.out.println(brojprimarnih);
//			
//			//Stavljamo sve zajednicke primarne kljuceve u ArrayList 
//			//Kako bi u dole proveri za podudaranje stavili u hashmapu
//			for(int i = 0; i < fieldsSize; i++) {
//				
//				//if(brojprimarnih == primKeys.size()) break; 
//				
//				if(ivFile.getFields().get(i).isPrimaryKey()) {
//					
//					for(int j = 0; j < filesize; j++) {
//						
//						if(ivFile.getFields().get(i).toString().equals(fields.get(j).toString())) {
//							primKeys.add(ivFile.getFields().get(i));
//							break;
//						}
//						//if(brojprimarnih == primKeys.size()) break; 	
//							
//						//System.out.println(fields.get(j) + " = " + ivFile.getFields().get(i));
//					}
//					
//					
//					//if(brojprimarnih == primKeys.size()) break; 	
//							
//				}
//				
//			}
//			//System.out.println("primKeys =" +primKeys.size());
//			//System.out.println(selectedTab.getName() + " = " + zaRel.getName());
////			for(int i = 0; i < primKeys.size(); i++) {
////				System.out.println(primKeys.get(i).toString());
////			}
////			
//			
//			
//			//System.out.println(zaRel);
//			ArrayList<String> jedan = new ArrayList<>();
//			jedan.add(relacija);
//			
//			
//			//System.out.println("");
//			
//			
//			//System.out.println(dir + File.separator + fileName+".sek");
//			String ceostring = null;
//			boolean jed = false;
//			try {
//				
//				RandomAccessFile raf = new RandomAccessFile(ivFile.getFilePath() + File.separator + ivFile.getFileName(), "r");
//				raf.seek(0);
//				int test = 0;
//			
//				while(raf.length()>raf.getFilePointer()) {
//					
//					byte[] tmp = new byte[ivFile.getRECORD_SIZE()];
//					raf.read(tmp);
//					String record = new String(tmp);
//					//System.out.println(record);
//					HashMap<String, String> attributeMap = new HashMap<>();
//					int k=0;
//					int j = 0;
//					
//					boolean ispis = false;
//					for (Attribute a: ivFile.getFields()) {
//
//						attributeMap.put(a.getName(), record.substring(k, k+a.getLength()).trim());
//						
//						if(primKeys.contains(a)) {	
//							//System.out.println(record.substring(k, k+a.getLength()).trim() + "  =  " + tabela.get(a.getName()).trim());
//								if((record.substring(k, k+a.getLength()).trim()).equals(tabela.get(a.getName()).trim())) {
//									//System.out.println(record.substring(k, k+a.getLength()).trim() + "  =  " + tabela.get(a.getName()));
//									j++;
//									if(j == primKeys.size()) {
//										ispis = true;
//										//System.out.println(j + " = " + primKeys.size());
//										
//
//									}
//								}
//								
//							}
//						
//						k+=a.getLength();
//						
//					}
//					
//					if(ispis) {
////						for (String key1: attributeMap.keySet()){
////				            String key = key1;
////				            String value = attributeMap.get(key1).toString();  
////						} 
//					//	System.out.println(record);
//						
//						if(!jed) {
//							ceostring = record;
//							jed = true;
//						}else ceostring += record;
//					}
////					for(Attribute a: ivFile.getFields()) {
////						
////				
////					}
//
//					
////					if(test == 10) {
////						break;
////					}
////					
//					
//				}
//				//System.out.println("nesto");
//				File storageFile = new File(ivFile.getFilePath() + File.separator + fileName.substring(0, fileName.length()) + "Relacije.stxt");
//			
//				if(storageFile.exists()) {
//					PrintWriter terminator = new PrintWriter(new FileWriter(storageFile));
//					terminator.println("");
//					terminator.close();
//				}
//				
//				storageFile.createNewFile();
//				//FileWriter fw = new FileWriter(storageFile);
//				File Sekfile = new File(ivFile.getFilePath() + File.separator + ivFile.getFileName().substring(0, ivFile.getFileName().length()-5)+"Relacije" + ".sek");
//				
//				
//				
//				Sekfile.createNewFile();
//				
//				//System.out.println(Sekfile.getName());
//				
//			//	System.out.println(ivFile.getFilePath() + File.separator + ivFile.getFileName().substring(0, ivFile.getFileName().length()-4) + ".sek");
//				BufferedReader bf = new BufferedReader(new FileReader(ivFile.getFilePath() + File.separator + ivFile.getFileName().substring(0, ivFile.getFileName().length()-5) + ".sek"));
//				//System.out.println(ivFile.getFilePath() + File.separator + ivFile.getFileName().substring(0, ivFile.getFileName().length()-5) + ".stxt");
//				
//				PrintWriter sekpw = new PrintWriter(new FileWriter(Sekfile));
//				PrintWriter stxtpw = new PrintWriter(new FileWriter(storageFile));
//				bf.readLine();
//				
//				
//				
//				
//				sekpw.println("path/"+storageFile.getName());
//				
//				//System.out.println("path/"+storageFile.getName());
//				String s;
//				
//				while((s=bf.readLine()) != null) {
//					sekpw.println(s);
//				}
//				
//				
//				sekpw.close();
//				bf.close();
//				
//				
//				stxtpw.println(ceostring);
//				
//				
//				stxtpw.close();
//				raf.close();
//				
////				System.out.println("ivFile = " + ivFile.getHeaderName());
////				System.out.println("Sekfile = "+ Sekfile.getName());
//				
//				IVSEKFile zaIzvoz = new IVSEKFile(Sekfile.getParent(), Sekfile.getName());
//				FileView fw = new FileView(zaIzvoz, zaRel);
//				tmpPane.add(fw, zaRel.getName());
//				tmpPane.setSelectedComponent(fw);
//				
//				zaIzvoz.fetchNextBlock();
//			} catch (Exception e) {
//				e.printStackTrace();
//				removeAll();
//			}
//			
//			
//			
//
////			tmpPane.removeAll();
////			tmpPane.revalidate();
////			tmpPane.repaint();
//			MainFrame.getInstance().getDesniPanel().setDividerLocation(800);
//					
//			for (int i = 0; i<tmpPane.getTabCount();i++) {
//				if (((FileView)(tmpPane.getComponent(i))).getRoditelj().equals((Entity)zaRel)) {
//					//tmpPane.setSelectedIndex(i);				
//				}
//			}		
//		}
//		add(tmpPane);	
	}	
}
