package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

import gerudok.model.workspace.Workspace;
import gui.MainWindow;

public class SaveWSAction extends AbstractGEDAction{

	public SaveWSAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON,loadIcon("saveWorkspace.png"));
		putValue(NAME,"saveWorkspace");
		putValue(SHORT_DESCRIPTION, "Sacuvaj workspace");

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Workspace workspace = (Workspace) MainWindow.getInstance().getWorkspaceModel().getRoot();
		
		if(workspace.isChanged()) {
			File workspaceFile = workspace.getFile();
			
			if (workspaceFile==null) {
				
				JFileChooser jfc = new JFileChooser();
				jfc.setFileFilter(new PageFileFilter());
				if(jfc.showSaveDialog(MainWindow.getInstance())==JFileChooser.APPROVE_OPTION){
	                workspaceFile=jfc.getSelectedFile();           	 
				}else{
					return; 
				}
				
			} 

			ObjectOutputStream os;
			try {
				os = new ObjectOutputStream(new FileOutputStream(workspaceFile));
				os.writeObject(workspace);
				workspace.setFile(workspaceFile);
				workspace.setChanged(false);
				os.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
	}
}

	

