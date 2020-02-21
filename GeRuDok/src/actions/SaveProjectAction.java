package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import gerudok.model.workspace.Project;
import gerudok.model.workspace.Workspace;
import gui.MainWindow;


public class SaveProjectAction extends AbstractGEDAction{

	public SaveProjectAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("saveProject.png"));
		putValue(NAME, "Sacuvaj projekat");
		putValue(SHORT_DESCRIPTION, "Sacuvaj projekat");

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = MainWindow.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
		
		
		
		if (obj instanceof Project) {
			
			Project project=MainWindow.getInstance().getWorkspaceTree().getCurrentProject();
			
			if (project.getProjectFile()!=null) {
				File projectFile=project.getProjectFile();
				
				if (!project.isChanged()){
					return;
				}
					      
				         
			    ObjectOutputStream os;
				try {
					os = new ObjectOutputStream(new FileOutputStream(projectFile));
					os.writeObject(project);
					project.setProjectFile(projectFile);
					project.setChanged(false);
					os.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Selektuj Projekat", "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
