package infview.app;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.json.JSONTokener;

import infview.actions.ActionManager;
import infview.controller.OtvaranjeSchema;
import infview.controller.Validation;
import infview.gui.LeftSide;
import infview.gui.MSEditor;
import infview.gui.MenuBar;
import infview.gui.RightSide;
import infview.gui.ToolBar;
import infview.model.NodeModel;
import infview.state.StateManager;
import infview.view.WorkspaceTree;
import infview.workspace.Workspace;


public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static MainFrame instance = null;
	private WorkspaceTree workspaceTree;
	private NodeModel workspaceModel;
	private ActionManager actionManager;
	private RightSide desniPanel;
	private MSEditor mseditor;
	private OtvaranjeSchema otvaranjeSchema;
	private Validation validation;
	private StateManager stateManager;
	private Boolean pernamently;
	private String filePath = "";
	private HashMap<String, String> tabela;
	private JTree indexTree;
	public static MainFrame getInstance() {
		if (instance == null) {
			instance = new MainFrame();
			instance.Initialise();
		}
		return instance;
	}
	private void Initialise() {
		actionManager = new ActionManager();
		initialiseWorkspaceTree();
		initialiseGUI();
		validation = new Validation();
		tabela = new HashMap<>();
		stateManager = new StateManager();
		stateManager.addListener(desniPanel);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			System.out.println(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initialiseWorkspaceTree() {	
		workspaceTree = new WorkspaceTree();
		workspaceModel = new NodeModel();
		workspaceTree.setModel(workspaceModel);
		workspaceModel.setRoot(new Workspace(null, "Workspace"));
		SwingUtilities.updateComponentTreeUI(workspaceTree);
	}
	
	private void initialiseGUI() {
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		int ScreenH = kit.getScreenSize().height;
		int ScreenW = kit.getScreenSize().width;
		
		ToolBar toolbar = new ToolBar(0);
		MenuBar menubar = new MenuBar();
		
		desniPanel = new RightSide(JSplitPane.VERTICAL_SPLIT);
		desniPanel.setDividerLocation(ScreenH/3);
		this.setJMenuBar(menubar);
		this.add(toolbar, BorderLayout.NORTH);
		setLocationRelativeTo(null);
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, workspaceTree, desniPanel);
		sp.setDividerLocation(ScreenW/3);
		this.add(sp, BorderLayout.CENTER);
		//otvaranjeSchema = new OtvaranjeSchema("fakultetMetaModel.json");
		setSize(ScreenW, ScreenH);
		setTitle("InfView");
		setLocationRelativeTo(null);
	}

	public WorkspaceTree getWorkspaceTree() {
		return workspaceTree;
	}

	public void setWorkspaceTree(WorkspaceTree workspaceTree) {
		this.workspaceTree = workspaceTree;
	}

	public NodeModel getWorkspaceModel() {
		return workspaceModel;
	}

	public void setWorkspaceModel(NodeModel workspaceModel) {
		this.workspaceModel = workspaceModel;
	}

	public ActionManager getActionManager() {
		return actionManager;
	}

	public void setActionManager(ActionManager actionManager) {
		this.actionManager = actionManager;
	}

	public RightSide getDesniPanel() {
		return desniPanel;
	}
	
	public MSEditor getMseditor() {
		return mseditor;
	}

	public void setMseditor(MSEditor mseditor) {
		this.mseditor = mseditor;
	}


	public OtvaranjeSchema getOtvaranjeSchema() {
		return otvaranjeSchema;
	}

	public void setOtvaranjeSchema(OtvaranjeSchema otvaranjeSchema) {
		this.otvaranjeSchema = otvaranjeSchema;
	}

	public Validation getValidation() {
		return validation;
	}

	public void setValidation(Validation validation) {
		this.validation = validation;
	}

	public StateManager getStateManager() {
		return stateManager;
	}

	public void setStateManager(StateManager stateManager) {
		this.stateManager = stateManager;
	}

	public Boolean getPernamently() {
		return pernamently;
	}

	public void setPernamently(Boolean pernamently) {
		this.pernamently = pernamently;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public HashMap<String, String> getTabela() {
		return tabela;
	}

	public void setTabela(HashMap<String, String> tabela) {
		this.tabela = tabela;
	}

	public JTree getIndexTree() {
		return indexTree;
	}

	public void setIndexTree(JTree indexTree) {
		this.indexTree = indexTree;
	}
	
}
