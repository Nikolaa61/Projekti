package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import elements.DiagramDevice;
import gerudok.model.workspace.DiagramElementSelection;
import gui.MainWindow;
import view.DocumentView;
import view.PageView;

public class CutAction extends AbstractGEDAction
{
	public CutAction() 
	{
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_X);
		putValue(SMALL_ICON, loadIcon("cutIcon.png"));
		putValue(NAME, "Cut");
		putValue(SHORT_DESCRIPTION, "Cut");
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
		
		if(pw.getPage().getSelectionModel().getSelectionList() != null)
		{
			for(DiagramDevice device : pw.getPage().getSelectionModel().getSelectionList())
			{
				pw.getPage().removeDiagramElement(device);
			}
		}
		
	}

}
