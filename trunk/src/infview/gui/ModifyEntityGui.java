//package infview.gui;
//
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.Insets;
//import java.awt.Window;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.nio.channels.FileChannel;
//import java.util.ArrayList;
//
//import javax.swing.Box;
//import javax.swing.BoxLayout;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//import javax.swing.border.EmptyBorder;
//
//
//
//import infview.app.MainFrame;
//import infview.workspace.Entity;
//import infview.workspace.WorkNode;
//import infview.workspace.Workspace;
//
//public class ModifyEntityGui extends JFrame{
//
//	JButton edit;
//	JButton add;
//	JButton delete;
//	
//	
//	public ModifyEntityGui() {
//
//		edit = new JButton("Edit Entity");
//		edit.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				editGui();
//				
//			}
//		});
//		add = new JButton("Add Entity");
//		add.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				addGui();
//				
//			}
//		});
//		delete = new JButton("Delete Entity");
//		delete.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				deleteGui();
//				
//			}
//		});
//		
//		 JFrame.setDefaultLookAndFeelDecorated(true);
//	        JFrame frame = new JFrame("Layout");
//	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	         
//	        JPanel panel = new JPanel();
//	        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//	        panel.setBorder(new EmptyBorder(new Insets(150, 200, 150, 200)));
//	        panel.add(edit);
//	        panel.add(Box.createRigidArea(new Dimension(0, 60)));     
//	        panel.add(add);
//	        panel.add(Box.createRigidArea(new Dimension(0, 60)));
//	        panel.add(delete);
//	        
//	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//	        
//	        frame.setSize(300, 300);
//	        frame.add(panel);
//	        frame.pack();
//	        frame.setVisible(true);
//	
//	        
//	    add.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				addGui();
//			}
//		});    
//	        
//	
//	}
//	
//	public void addGui() {
//		
////		String inStr = JOptionPane.showInputDialog(null, "Enter the Entity name",
////	            "Input Dialog", JOptionPane.PLAIN_MESSAGE);
////		JSONObject ent  = new JSONObject();
////		ent.append("name", inStr);
////		
////		BufferedReader br = null;
////        try {
////            br = new BufferedReader(new InputStreamReader(new FileInputStream("meta model.json")));
////        } catch (FileNotFoundException e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////        }
////
////        JSONTokener tokener = new JSONTokener(br);
////        JSONObject o = new JSONObject(tokener);
////		
////        JSONArray entiteti = o.getJSONArray("entities");
////        entiteti.put(entiteti.length(), ent);
////        
////        for(int i = 0; i < entiteti.length(); i++) {
////            System.out.println(entiteti.getJSONObject(i));
////        }
////	}
//	
//	
//	public void editGui() {
//		BufferedReader br = null;
//		try {
//			br = new BufferedReader(new InputStreamReader(new FileInputStream("meta model.json")));
//		} catch (FileNotFoundException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}	
//		JSONTokener tokener = new JSONTokener(br);
//		JSONObject o = new JSONObject(tokener);
//		
//		JSONArray entiteti = o.getJSONArray("entities");
//			
//			JComboBox<String> jcb = new JComboBox<>();
//			
//			for(int i = 0; i < entiteti.length(); i++) {
//				jcb.addItem(entiteti.getJSONObject(i).getString("name"));
//			}
//			
//			
//			JButton editdugme = new JButton("edit");
//			
//			
//			JFrame.setDefaultLookAndFeelDecorated(true);
//	        JFrame frame = new JFrame("Layout");
//	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	         
//	        JPanel panel = new JPanel();
//	        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//	        panel.setBorder(new EmptyBorder(new Insets(50, 100, 50, 100)));
//	        
//	        panel.add(Box.createRigidArea(new Dimension(0, 60)));     
//	        panel.add(jcb);
//	        panel.add(Box.createRigidArea(new Dimension(0, 60)));
//	        panel.add(editdugme);
//	        
//	        editdugme.addActionListener(new ActionListener() {
//				
//				@Override
//				public void actionPerformed(ActionEvent e) {
////					JFrame.setDefaultLookAndFeelDecorated(true);
////			        JFrame frame = new JFrame("Layout");
////			        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////			         
////			        JPanel panel = new JPanel();
////			        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
////			        panel.setBorder(new EmptyBorder(new Insets(50, 100, 50, 100)));
////			        
////			        panel.add(Box.createRigidArea(new Dimension(0, 60)));     
////			        
////			        panel.add(Box.createRigidArea(new Dimension(0, 60)));
////
////			        
////			        frame.setSize(100, 100);
////			        frame.add(panel);
////			        frame.pack();
////			        frame.setVisible(true);
//					
//					for(int i = 0; i < entiteti.length(); i++) {
//						if(entiteti.getJSONObject(i).getString("name").equals(jcb.getSelectedItem())) {
//							
//					
//						
//					}
//					
//				}
//				}});
//
//	        
//	        frame.setSize(100, 100);
//	        frame.add(panel);
//	        frame.pack();
//	        frame.setVisible(true);
//		
//	}
//	
//	public void deleteGui() {
//		
//		Object root = MainFrame.getInstance().getWorkspaceModel().getRoot();
//		
//		ArrayList<WorkNode> nodovi = ((Workspace)root).getDeca();
//		
//		
//		BufferedReader br = null;
//		try {
//			br = new BufferedReader(new InputStreamReader(new FileInputStream("meta model.json")));
//		} catch (FileNotFoundException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}	
//		JSONTokener tokener = new JSONTokener(br);
//		JSONObject o = new JSONObject(tokener);
//		
//		JSONArray entiteti = o.getJSONArray("entities");
//			
//			JComboBox<String> jcb = new JComboBox<>();
//			
//			for(int i = 0; i < entiteti.length(); i++) {
//				jcb.addItem(entiteti.getJSONObject(i).getString("name"));
//			}
//			
//			
//			
//			JButton deletedugme = new JButton("delete");
//			
//			
//			JFrame.setDefaultLookAndFeelDecorated(true);
//	        JFrame frame = new JFrame("Layout");
//	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	         
//	        JPanel panel = new JPanel();
//	        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//	        panel.setBorder(new EmptyBorder(new Insets(150, 200, 150, 200)));
//	        
//	        panel.add(Box.createRigidArea(new Dimension(0, 60)));     
//	        panel.add(jcb);
//	        panel.add(Box.createRigidArea(new Dimension(0, 60)));
//	        panel.add(deletedugme);
//	        
//	        
//
//	        
//	        frame.setSize(100, 100);
//	        frame.add(panel);
//	        frame.pack();
//	        frame.setVisible(true);
//	
//	        
//	        
//	        
//		deletedugme.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Object root = MainFrame.getInstance().getWorkspaceModel().getRoot();
//				
//				ArrayList<WorkNode> nodovi = ((Workspace)root).getDeca();
//				
//				//FileChannel src;
//				
//				
//				//Kopiranje meta modela u temp.json kako bi na njemu moglo da se vrsi brisanje i validacija
//				
//				try {
//					PrintWriter writer = new PrintWriter(new File("temp.json"));
//					writer.print("");
//					writer.close();
//
//					FileChannel src = new FileInputStream("meta model.json").getChannel();
//					FileChannel dest = new FileOutputStream(new File("temp.json")).getChannel();
//					dest.transferFrom(src, 0, src.size());
//					dest.close();
//					src.close();
//				} catch (FileNotFoundException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				
//				
//				BufferedReader br = null;
//				try {
//					br = new BufferedReader(new InputStreamReader(new FileInputStream("temp.json")));
//				} catch (FileNotFoundException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
//				
//				
//				//Parsiranje meta modela u tokener 
//				JSONTokener tokener = new JSONTokener(br);
//				JSONObject o = new JSONObject(tokener);
//
//				JSONArray entiteti = o.getJSONArray("entities");
//				
//				
//				//Brisanje i-tog entiteta
//				for(int i = 0; i < entiteti.length(); i++) {
//					if(entiteti.getJSONObject(i).getString("name").equals(jcb.getSelectedItem())) {
//						entiteti.remove(i);
//						o.put("entitites", entiteti);
//					}
//				}
//				
//				
////				//Ispis entiteta test
////				for(int i = 0; i < entiteti.length(); i++) {
////					System.out.println(entiteti.getJSONObject(i).getString("name"));
////				}
////				
//				
//				/*
//				 * Pokusaj validacije temp.json, u slucaju da prodje meta model se stavlja na null i prepisuje se temp.json na meta model.json
//				 * 
//				 */
//				boolean test = MainFrame.getInstance().getValidation().validiraj("temp.json");
//				System.out.println(test);
//				if(test) {
//					System.out.println("*****************************");
//					try {
//						/*PrintWriter writer = new PrintWriter(new File("meta model.json"));
//						writer.print("");
//						writer.close();
//						*/
//						
//						FileWriter fw = new FileWriter("temp.json");
//						
//						fw.write(o.toString());
//						fw.close();
//						
//						
//						FileChannel src1 = new FileInputStream("temp.json").getChannel();
//						FileChannel dest = new FileOutputStream("meta model.json").getChannel();
//						dest.transferFrom(src1, 0, src1.size());
//						dest.close();
//						src1.close();
//				}catch (Exception e4) {
//					// TODO: handle exception
//				}
//				}
//				for(int i = 0; i < entiteti.length(); i++) {
//					System.out.println(entiteti.getJSONObject(i).getString("name"));
//				}
//			}
//		});
//		
//		System.out.println("\n");
//		
//		
//		
//		
//		
//		
//	}
//	
//}
