package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;
import gui.MainWindow;
import view.WorkspaceTreeEditor;
import gerudok.model.workspace.*;


public class RenameAction extends AbstractGEDAction{
	
	WorkspaceTreeEditor wte;
	
	public RenameAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON,loadIcon("rename.png"));
		putValue(NAME,"Promeni ime");
		putValue(SHORT_DESCRIPTION, "Menja ime");
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = MainWindow.getInstance().getWorkspaceTree().getLeadSelectionPath();
		
		if(obj == null) {
			return;
		}
		
		Object objekat = ((TreePath)obj).getLastPathComponent();
		
		if (objekat == null || objekat instanceof Workspace) {
			return;
		}
		
		MainWindow.getInstance().getWorkspaceTree().startEditingAtPath((TreePath)obj);		
	}

}
