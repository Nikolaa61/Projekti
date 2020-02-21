package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.tree.TreeSelectionModel;

import actions.ActionManager;
import gerudok.model.workspace.WorkspaceModel;

public class MainWindow extends JFrame implements ClipboardOwner{		
	private static MainWindow instance;
	
	private ActionManager actionManager;
	private ToolBar toolBar;
	private Palette palette;
	
	private MenuBar menuBar;
	private CenterPane centerPane;
	
	private WorkSpaceTree workspaceTree;
	private WorkspaceModel workspaceModel;

	private Clipboard clipboard = new Clipboard("Klipbord");	
	
	private MainWindow() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent evt){
                    
                	Object[] options = {"Da, Zelim da sacuvam", "Ne, ne zelim da sacuvam"};

                    int answer = JOptionPane.showOptionDialog(MainWindow.this, "Da li zelite da sacuvate radni prostor ","Quit:Continue", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                 null, options,options[1]);
                    
                    if(answer == JOptionPane.YES_OPTION){
                        actionManager.getSaveWSAction();
                    }
                    System.exit(0);
                }
        });
	}
	
	private void initialise() {
		actionManager = new ActionManager();
		
		initialiseWorkspaceTree();
		initialiseGUI();
	}
	
	private void initialiseGUI() {
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenD = tk.getScreenSize();
		int sHeight = screenD.height;
		int sWidth = screenD.width;
		setSize(sWidth, sHeight);
		
		setLayout(new BorderLayout());
		
		toolBar = new ToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		palette = new Palette();
		getContentPane().add(palette, BorderLayout.EAST);
		
		menuBar = new MenuBar(); 
		setJMenuBar(menuBar);    
		
		centerPane = new CenterPane();
		
		add(centerPane, BorderLayout.CENTER);
		
		setLocationRelativeTo(null);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setExtendedState(getExtendedState()|JFrame.MAXIMIZED_BOTH );
	}
	
	private void initialiseWorkspaceTree() {
		workspaceTree = new WorkSpaceTree();
		workspaceModel = new WorkspaceModel();
		workspaceTree.setModel(workspaceModel);
	}
	
	
	public static MainWindow getInstance(){
		if (instance == null) {
            instance = new MainWindow();
            instance.initialise();
		}
		return instance;		
	}
	
	public CenterPane getCenterPane() {
		return centerPane;
	}

	public ActionManager getActionManager() {
		return actionManager;
	}

	public WorkSpaceTree getWorkspaceTree() {
		return workspaceTree;
	}

	public WorkspaceModel getWorkspaceModel() {
		return workspaceModel;
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) 
	{
	}

	public  Clipboard getClipboard() {
		return clipboard;
	}
}
