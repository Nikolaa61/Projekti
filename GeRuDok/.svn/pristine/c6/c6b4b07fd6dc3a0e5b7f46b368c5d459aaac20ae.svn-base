package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

import gerudok.model.workspace.Document;
import gerudok.model.workspace.Page;
import gerudok.model.workspace.Project;
import gerudok.model.workspace.*;
import gui.Main;
import gui.MainWindow;


public class OpenProjectAction extends AbstractGEDAction{

	public OpenProjectAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("openProject.png"));
		putValue(NAME, "Otvori projekat");
		putValue(SHORT_DESCRIPTION, "Otvori projekat");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new PageFileFilter());
		
		if(jfc.showOpenDialog(MainWindow.getInstance())==JFileChooser.APPROVE_OPTION){
			try {
				ObjectInputStream os = new ObjectInputStream(new FileInputStream(jfc.getSelectedFile()));
				  
				Project p=null;
				try {
					p = (Project) os.readObject();
					p.setProjectFile(jfc.getSelectedFile());
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				boolean postoji = false;
				for(Project project : MainWindow.getInstance().getWorkspaceModel().getProject()){
					if (p.getProjectFile().equals(project.getProjectFile())) {
						postoji = true;
						break;
					}
				}
				if (!postoji) {
				      MainWindow.getInstance().getWorkspaceTree().addProject(p);
				      p.setWorkspace((Workspace)MainWindow.getInstance().getWorkspaceModel().getRoot());
					  os.close();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} 
			
		
		
	}
	}
	
}
