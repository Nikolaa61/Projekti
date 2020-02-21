package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import gerudok.model.workspace.Project;
import gui.MainWindow;
import view.DocumentView;
import view.PageView;

public class ShareInProjectAction extends AbstractGEDAction{
	public ShareInProjectAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("deliUProjekat.png"));
		putValue(NAME,"Deli u projekat ");
		putValue(SHORT_DESCRIPTION, "Deli u projekat dokument sa klipborda");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Project project = null;
		
		if (MainWindow.getInstance().getWorkspaceTree().isSelectionEmpty()) {
			return;
		}

		if ((PageView) ((DocumentView)MainWindow.getInstance().getCenterPane().getTabbedPane().getSelectedComponent()).getSelectedFrame()==null) {
			return;
		}
		
		Object obj = MainWindow.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
		if (obj instanceof Project) {
			project = (Project) obj;
			project.shareIn();
		}
	}
	
	
}
