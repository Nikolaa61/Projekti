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
import gui.MainWindow;
import view.DocumentView;
import view.PageView;

public class SaveASProjectAction extends AbstractGEDAction{
	public SaveASProjectAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("saveProjectAs.png"));
		putValue(NAME, "Sacuvaj projekat kao");
		putValue(SHORT_DESCRIPTION, "Sacuvaj projekat kao");

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new PageFileFilter());
		
		if (MainWindow.getInstance().getWorkspaceTree().isSelectionEmpty()) {
			return;
		}

		if ((PageView) ((DocumentView)MainWindow.getInstance().getCenterPane().getTabbedPane().getSelectedComponent()).getSelectedFrame()==null) {
			return;
		}
		
		Project project=MainWindow.getInstance().getWorkspaceTree().getCurrentProject();
		
		if(project == null)
		{
			JOptionPane.showMessageDialog(null, "Selektuj Projekat", "Greska", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		File projectFile=project.getProjectFile();
		
		
		if (projectFile==null){
			if(jfc.showSaveDialog(MainWindow.getInstance())==JFileChooser.APPROVE_OPTION){
		                   projectFile=jfc.getSelectedFile();           	 
		                   //trebalo bi u isto vreme da je i Save i SaveAs jer jedan projekat ima polje File i ako je null pozvace dijalog inace ce samo da sacuva u taj fajl...
		                   //Kad se otvara Project file treba da se setuje File polje? Ali kako od readObject
				}else{
		        	return; 
		        }
		         
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

