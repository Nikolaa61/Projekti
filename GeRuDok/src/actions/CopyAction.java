package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import gerudok.model.workspace.DiagramElementSelection;
import gui.MainWindow;
import view.DocumentView;
import view.PageView;

public class CopyAction extends AbstractGEDAction
{
	public CopyAction() 
	{
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_C);
		putValue(SMALL_ICON, loadIcon("copyIcon.png"));
		putValue(NAME, "Copy");
		putValue(SHORT_DESCRIPTION, "Copy");
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		PageView pw = null;

		if (MainWindow.getInstance().getWorkspaceTree().isSelectionEmpty()) {
			return;
		}

		if ((PageView) ((DocumentView)MainWindow.getInstance().getCenterPane().getTabbedPane().getSelectedComponent()).getSelectedFrame()==null) {
			return;
		}
		
		pw = (PageView) ((DocumentView)MainWindow.getInstance().getCenterPane().getTabbedPane().getSelectedComponent()).getSelectedFrame();
	
		if(pw.getPage().getSelectionModel().getSelectionListSize() != 0)
		{
			DiagramElementSelection dielsel = new DiagramElementSelection(pw.getPage().getSelectionModel().getSelected());
			MainWindow.getInstance().getClipboard().setContents(dielsel, MainWindow.getInstance());
		}
	}

}
