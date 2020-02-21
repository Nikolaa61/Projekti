package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import gerudok.model.workspace.Project;
import gui.MainWindow;
import view.DocumentView;
import view.PageView;

public class PasteDocumentAction extends AbstractGEDAction
{
	
	public PasteDocumentAction() 
	{
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("pasteDocument.png"));
		putValue(NAME,"PasteDoc");
		putValue(SHORT_DESCRIPTION, "PasteDoc");
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Project project = null;
		
		if (MainWindow.getInstance().getWorkspaceTree().isSelectionEmpty()) {
			return;
		}

		if ((PageView) ((DocumentView)MainWindow.getInstance().getCenterPane().getTabbedPane().getSelectedComponent()).getSelectedFrame()==null) {
			return;
		}
		
		Object obj = MainWindow.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
		if (obj instanceof Project)
		{
			project = (Project) obj;
			project.pasteDocument();
		}
		
		SwingUtilities.updateComponentTreeUI(MainWindow.getInstance().getWorkspaceTree());
	}
	
}
