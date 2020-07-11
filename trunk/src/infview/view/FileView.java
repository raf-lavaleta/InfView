package infview.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.tree.DefaultTreeModel;

import infview.app.MainFrame;
import infview.controller.IndTreeMouseController;
import infview.events.UpdateBlockEvent;
import infview.events.UpdateBlockListener;
import infview.gui.FileViewToolbar;
import infview.model.MyTableModel;
import infview.model.file.FileMode;
import infview.model.file.IVAbstractFile;
import infview.model.file.IVFile;
import infview.model.file.IVINDFile;
import infview.model.file.IVSEKFile;
import infview.workspace.Entity;
import model.tree.Node;
import model.tree.TreeCellRendered;

public class FileView extends JPanel implements UpdateBlockListener{
	
	private FileViewToolbar fileViewToolbar = new FileViewToolbar();
	private IVAbstractFile ivFile;
	private MyTable myTable;
	private Entity roditelj;
	private JTextArea blockSizeArea;
	private JTree indexTree;
	private JTable overZoneTable;
	private ArrayList<TextField> tfovi;
	public FileView() {
		super();
	}
	public FileView(IVAbstractFile ivFile, Entity roditelj) {
		// TODO Auto-generated constructor stub
		super();
		this.roditelj = roditelj;
		setLayout(new BorderLayout());
		this.ivFile = ivFile;
		try {
			this.ivFile.readHeader();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		ivFile.getViewListeners().add(MainFrame.getInstance().getDesniPanel());
		JButton btnFetch = new JButton("Fetch");
		
		btnFetch.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				ivFile.setMODE(FileMode.BROWSE_MODE);
				try {
					if (!blockSizeArea.getText().equals(""))
					ivFile.setBLOCK_FACTOR(Integer.parseInt(blockSizeArea.getText()));
					ivFile.fetchNextBlock();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
			
		});
		blockSizeArea = new JTextArea();
		blockSizeArea.setColumns(10);
		blockSizeArea.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				char c = arg0.getKeyChar();
				if (c<'0' && c>'9') arg0.consume();
			}
		});
		JButton btnSort = new JButton("Sort");
		btnSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ivFile.setMODE(FileMode.SORT_MODE);
				MainFrame.getInstance().getStateManager().setSortState(ivFile, ivFile.getFields());
			}	
		});
		JButton btnFind = new JButton("Find");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ivFile.setMODE(FileMode.FIND_MODE);
				MainFrame.getInstance().getStateManager().setFindState(ivFile, ivFile.getFields());
			}	
		});
		
		fileViewToolbar.add(btnFetch);
		fileViewToolbar.add(blockSizeArea);
		fileViewToolbar.add(btnSort);
		fileViewToolbar.add(btnFind);
		
		this.ivFile.addBlockListener(this);
		add(fileViewToolbar, BorderLayout.NORTH);
		myTable = new MyTable(new MyTableModel(this.ivFile.getData(), this.ivFile.getFields()));
		myTable.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {

				int cols = myTable.getColumnCount();
				int row = myTable.getSelectedRow();
				String[] s = new String[cols];
				HashMap<String, String> tabela = new HashMap<>();
				for(int i = 0; i < cols; i++ ) {
					tabela.put(myTable.getColumnName(i), (String) myTable.getValueAt(row, i));
					
				}
				
				
				MainFrame.getInstance().setTabela(tabela);
				MainFrame.getInstance().getStateManager().setRelationState(ivFile,ivFile.getFields());
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		if(ivFile.getHeaderName().contains(".sek")) {
			JButton makeTreeBtn = new JButton("Make.ind");
			fileViewToolbar.add(makeTreeBtn);
			makeTreeBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						((IVSEKFile) ivFile).makeINDFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		
		
	
		JScrollPane scr = new JScrollPane(myTable);
		//add(scr, BorderLayout.CENTER);
		
		if (ivFile.getHeaderName().contains(".ind")){
			//zona prekoracenja u novoj tabeli, ista struktura kao i osnovni fajl
			
			
			
			this.setName(ivFile.getHeaderName());
			JPanel searchPanel = new JPanel();
			
		    tfovi = new ArrayList<>();
		    
			for(int i = 0; i < ivFile.getFields().size(); i++) {
				if(ivFile.getFields().get(i).isPrimaryKey()) {
				tfovi.add(new TextField(20));
				searchPanel.add(new Label(ivFile.getFields().get(i).getName()));
				searchPanel.add(tfovi.get(i));
				}
			}
			
			
			
			Button search = new Button("Pretrazi");
			
			
			search.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Node root = (Node) MainFrame.getInstance().getIndexTree().getModel().getRoot();
					System.out.println(root.getData().get(0).getKeyValue());
					System.out.println();
					Node trenutni;
					boolean nasao = false;
					System.out.println(ivFile.getFields().size());
					int primKeys = 0;
					for(int i = 0; i < ivFile.getFields().size(); i++) {
						if(ivFile.getFields().get(i).isPrimaryKey()) primKeys++;
					}
					int counter = 0;
					System.out.println(primKeys);
					while(!nasao){
						
						counter++;
						System.out.println(counter);
						
						if(root.getChildCount() == 0) {
							System.out.println(root.getData());
							nasao = true;
							IVINDFile ivFile =(IVINDFile) ((FileView)MainFrame.getInstance().getDesniPanel().getTopTabPane().getSelectedComponent()).getIvFile();
							try {
								ivFile.fetchNextBlock(root.getData().get(0).getBlockAddress()*ivFile.getRECORD_SIZE());
								System.out.println(root.getData().get(0).getBlockAddress());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
						else if(root.getChildCount() == 1) {
							root = root.getChildren().get(0);
						}
						
						else {
							System.out.println(root.getData());
							System.out.println(root.getData().get(1));
							for(int i = 0; i < primKeys; i++) {
								try {
									if(root.getData().get(1).getKeyValue().get(i).toString().compareTo(tfovi.get(i).getText()) < 0) {
										
										root = (Node) root.getChildren().get(1);
										break;
									}
									
									else if(root.getData().get(1).getKeyValue().get(i).toString().compareTo(tfovi.get(i).getText()) >	 0) {
										root = (Node) root.getChildren().get(0);
										break;
									}
									else if(i == primKeys-1) {
										//System.out.println(root.getData());
										//nasao = true;
									}
									
								}catch (IndexOutOfBoundsException e1) {
									break;
								}
							}
						}
						
					}
				}
			});
			
			
			searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			
			searchPanel.add(search);
			
			
		    
		    DefaultTreeModel treeModel=new DefaultTreeModel(((IVINDFile)ivFile).getTree().getRootElement());
		    
		    indexTree=new JTree(treeModel);
		    TreeCellRendered rendered=new TreeCellRendered();
		    indexTree.setCellRenderer(rendered);
		    
		    MainFrame.getInstance().setIndexTree(indexTree);
		    
		    indexTree.addMouseListener(new IndTreeMouseController());
			JScrollPane scTree=new JScrollPane(indexTree);
			JSplitPane splitHor=new JSplitPane(JSplitPane.VERTICAL_SPLIT,scr,searchPanel);
			JSplitPane splitVer=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,scTree,splitHor);
		    splitVer.setDividerLocation(400);
			
			splitHor.setDividerLocation(300);
			add(splitVer,BorderLayout.CENTER);
			
			
		}else{
			add(scr,BorderLayout.CENTER);
		} 
		
	}
	
	
	
	@Override
	public void updateBlockPerformed(UpdateBlockEvent e) {
		// TODO Auto-generated method stub
		myTable.setModel(new MyTableModel(ivFile.getData(), ivFile.getFields()));
	}


	public FileViewToolbar getFileViewToolbar() {
		return fileViewToolbar;
	}


	public IVAbstractFile getIvFile() {
		return ivFile;
	}


	public MyTable getMyTable() {
		return myTable;
	}


	public Entity getRoditelj() {
		return roditelj;
	}


	public void setRoditelj(Entity roditelj) {
		this.roditelj = roditelj;
	}


	public ArrayList<TextField> getTfovi() {
		return tfovi;
	}


	public void setTfovi(ArrayList<TextField> tfovi) {
		this.tfovi = tfovi;
	}
	
	

}
