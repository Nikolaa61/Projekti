package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import gerudok.model.workspace.Document;
import gerudok.model.workspace.Project;
import gerudok.model.workspace.Workspace;
import gui.MainWindow;
import gui.TabbedPane;
import view.DocumentView;
import view.ProjectView;

public class SwitchWSAction extends AbstractGEDAction {

	public SwitchWSAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON,loadIcon("switchWorkspace.png"));
		putValue(NAME,"Promeni workspace");
		putValue(SHORT_DESCRIPTION, "Menja workspace");
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object[] options = {"Da, Želim da sačuvam", "Ne, ne Želim da sačuvam"};
		
        int answer = JOptionPane.showOptionDialog(MainWindow.getInstance(), "Da li želite da sačuvate radni prostor ","Quit:Continue", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                     null, options,options[1]);
        
        if(answer == JOptionPane.YES_OPTION){
            MainWindow.getInstance().getActionManager().getSaveWSAction();
        }
        
        MainWindow.getInstance().getWorkspaceModel().getProject().clear();
        
		TabbedPane tp = MainWindow.getInstance().getCenterPane().getTabbedPane();
		
		for(ProjectView projectView : tp.getProjectViews()){
			
				for(DocumentView documentView : projectView.getDcViewList()){
					tp.remove(documentView);
				}
				
				
		}
		tp.getProjectViews().clear();
		
		// Otvara workspace file chooser
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new PageFileFilter());
		
		if(jfc.showOpenDialog(MainWindow.getInstance())==JFileChooser.APPROVE_OPTION){
			try {
				ObjectInputStream os = new ObjectInputStream(new FileInputStream(jfc.getSelectedFile()));
				  
				Workspace workspace=null;
				try {
					workspace = (Workspace) os.readObject();
					workspace.setFile(jfc.getSelectedFile());
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			      MainWindow.getInstance().getWorkspaceModel().setRoot(workspace);
				  os.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} 
			
		}
		
		
		SwingUtilities.updateComponentTreeUI(MainWindow.getInstance().getWorkspaceTree());
	}
	
}
