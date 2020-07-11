package infview.utility;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.swing.DefaultRowSorter;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import infview.app.MainFrame;
import infview.model.MyTableModel;
import infview.model.file.IVAbstractFile;
import infview.model.file.IVSEKFile;
import infview.state.SortState;
import infview.workspace.Attribute;
import infview.workspace.Entity;

public class ExternalSekSorter {

	private IVAbstractFile sorting;
	private Entity tableType;
	private ArrayList<File> sortPartitions = new ArrayList<>();
	public ExternalSekSorter(IVAbstractFile sorting, Entity tableType) {
		super();
		this.sorting = sorting;
		this.tableType = tableType;
	}
	
	public void sort() {
		try {
			RandomAccessFile raf = new RandomAccessFile(sorting.getFilePath() + File.separator + sorting.getFileName(), "r");
			System.out.println("sortiram");
			long i = 0;
			while(i<sorting.getRECORD_NUM()) {
				sortPartition(i, raf);
				i = i + (i+1000> sorting.getRECORD_NUM() ? sorting.getRECORD_NUM() - i : 1000);
			}
			mergePartitions();
			for (File f : sortPartitions) {
				f.delete();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sortPartition(long currPoint, RandomAccessFile raf) throws IOException {
		File file = new File(sorting.getFilePath() +File.separator+  "tmpSortFile" + currPoint/1000 + sorting.getFileName().substring(0, sorting.getFileName().length() - 4) + ".txt");

		if (file.exists()) file = new File(sorting.getFilePath() +File.separator+  "tmpSortFile" + (currPoint/1000 + 1) + 
				sorting.getFileName().substring(0, sorting.getFileName().length() - 4) + ".txt");
		file.createNewFile();
		//Zbog ovoga mi ne radi kada datoteka ima manje od 1000 linija podataka, posto kada krecem od pocetka uvek size stavljam na 1000
		long dataSize = (currPoint == 0) ? 1000 : sorting.getRECORD_NUM()-currPoint > 1000 ? 1000 : sorting.getRECORD_NUM() - currPoint;
		String[][] fileData = new String[(int)dataSize][sorting.getFields().size()];
		String s;
		ArrayList<Attribute> fields = sorting.getFields();
		int i = 0;
		int j = 0;
		int entriesPerFile = 1000;
		while(entriesPerFile>0 && (s = raf.readLine())!=null) {

			int k = 0;
			while (k<s.length()) {
				fileData[i][j] = s.substring(k, k+fields.get(j).getLength());
				k+=fields.get(j).getLength();
				j++;
			}
			j = 0;
			i++;
			entriesPerFile--;
		}
		
		MyTableModel myModel = new MyTableModel(fileData, fields);
		JTable myTable = new JTable(myModel);
		TableRowSorter<MyTableModel> sorter = new TableRowSorter<MyTableModel>((MyTableModel)myTable.getModel());
		SortState state = (SortState)(MainFrame.getInstance().getStateManager().getCurrState());
		ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
		int curr = -1;
		for (Attribute a : sorting.getFields()) {
			if (!a.isSort()) continue;
			SortOrder order = a.isAsc() ? SortOrder.ASCENDING : SortOrder.DESCENDING;
			sortKeys.add(new RowSorter.SortKey(sorting.getFields().indexOf(a), order));
		}
		sorter.setSortKeys(sortKeys);
		myTable.setRowSorter(sorter);
		DefaultRowSorter sorter1 = ((DefaultRowSorter)myTable.getRowSorter());
		sorter1.sort();
		FileWriter fw = new FileWriter(file);

		for(j=0; j<myTable.getRowCount();j++) {
			for(int t=0;t<myTable.getColumnCount();t++) {
				fw.write((String)myTable.getValueAt(j, t));
			}
			fw.write(System.lineSeparator());
		}
		fw.close();
		sortPartitions.add(file);
	}
	
	private void mergePartitions() throws IOException {
		ArrayList<RandomAccessFile> rafPartitions = new ArrayList<>();
		for (File file : sortPartitions) {
			rafPartitions.add(new RandomAccessFile(file, "r"));
		}
		ArrayList<Attribute> fields = sorting.getFields();
		String[][] fileData = new String[rafPartitions.size()][fields.size()];
		ArrayList<String> unsortedData = new ArrayList<>();
		for (RandomAccessFile raf : rafPartitions) {
			String s = raf.readLine();
			unsortedData.add(s);
			int k = 0;
			int j = 0;
			while (k<s.length()) {
				fileData[rafPartitions.indexOf(raf)][j] = s.substring(k, k+fields.get(j).getLength());
				k+=fields.get(j).getLength();
				j++;
			}
		}
		MyTableModel myModel = new MyTableModel(fileData, fields);
		JTable myTable = new JTable(myModel);
		TableRowSorter<MyTableModel> sorter = new TableRowSorter<MyTableModel>((MyTableModel)myTable.getModel());
		SortState state = (SortState)(MainFrame.getInstance().getStateManager().getCurrState());
		ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
		int curr = -1;
		for (Attribute a : sorting.getFields()) {
			if (!a.isSort()) continue;
			SortOrder order = a.isAsc() ? SortOrder.ASCENDING : SortOrder.DESCENDING;
			sortKeys.add(new RowSorter.SortKey(sorting.getFields().indexOf(a), order));
		}
		sorter.setSortKeys(sortKeys);
		myTable.setRowSorter(sorter);
		DefaultRowSorter sorter1 = ((DefaultRowSorter)myTable.getRowSorter());

		sorter1.sort();
		String fileName = sorting.getFileName();
		String filePath = sorting.getFilePath();
		File sortResult = new File(filePath + File.separator + fileName.substring(0, fileName.length()-5) + "Sorted.txt");
		int fileNr = 0;
		while(sortResult.exists()) {
			fileNr++;
			sortResult = new File(filePath + File.separator + fileName.substring(0, fileName.length()-5) + "Sorted" + fileNr + ".txt");
		}
		sortResult.createNewFile();
		int pass = 0;
		FileWriter fw = new FileWriter(sortResult);
		while(true) {
			String str = "";
			for (int i = 0; i<fields.size(); i++) {
				str = str.concat((String)myTable.getValueAt(0, i));
				//System.out.println(str);
			}
			int index = unsortedData.indexOf(str);

//			while (index < rafPartitions.size()) {
//				String modelStr = "";
//				for (int i = 0; i<fields.size(); i++) {
//					modelStr = modelStr.concat((String)myTable.getModel().getValueAt(index, i));
//				}
//				if (modelStr.equals(str)) break;
//				index++;
//			}
			fw.write(str);
			fw.write(System.lineSeparator());
			unsortedData.remove(index);
			int k=0;
			if ((str = rafPartitions.get(index).readLine()) ==null) {
				rafPartitions.get(index).close();
				rafPartitions.remove(index);
				String[][] tmpData = new String[rafPartitions.size()][fields.size()];
				int t = 0;
				for(int j=0;j<rafPartitions.size(); j++) {
					if (j==index) t++;
					for (int i = 0; i<fields.size();i++) {
						tmpData[j][i] = fileData[t][i];
					}
					t++;
				}
				fileData = new String[rafPartitions.size()][fields.size()];
				for(int j=0;j<rafPartitions.size(); j++) {
					for (int i = 0; i<fields.size();i++) {
						fileData[j][i] = tmpData[j][i];
					}
				}
			}else { 
				unsortedData.add(index, str);
				for (int i = 0; i<fields.size();i++) {
					fileData[index][i] = str.substring(k, k+fields.get(i).getLength());
					k+=fields.get(i).getLength();
				}
			}
			myTable.setModel(new MyTableModel(fileData, fields));
			sorter = new TableRowSorter<MyTableModel>((MyTableModel)myTable.getModel());
			sorter.setSortKeys(sortKeys);
			myTable.setRowSorter(sorter);
			sorter1 = ((DefaultRowSorter)myTable.getRowSorter());

			sorter1.sort();
//			int doneFiles = 0;
//			for (RandomAccessFile raf : rafPartitions) {
//				if (raf.getFilePointer() >= raf.length()) doneFiles++;
//			}
//			if (doneFiles == rafPartitions.size()) break;
			if (rafPartitions.size() == 0) break;
		}
		fw.close();
		File storageFileSER = new File(sortResult.getPath().substring(0, sortResult.getPath().length() - 3) + "ser");
		storageFileSER.createNewFile();
		FileWriter fwSER = new FileWriter(storageFileSER);
		BufferedReader bread = new BufferedReader(new FileReader(new File(filePath + File.separator + sorting.getHeaderName())));
		String s = "path/"+sortResult.getName();
		bread.readLine();
		fwSER.write(s);
		fwSER.write(System.lineSeparator());
		while ((s = bread.readLine())!=null) {
			fwSER.write(s);
			fwSER.write(System.lineSeparator());
		}
		fwSER.close();
		bread.close();
	}
	
}
