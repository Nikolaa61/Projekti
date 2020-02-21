package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import gui.MainWindow;
import view.DocumentView;
import view.PageView;

public class RotateAction extends AbstractGEDAction
{

	public RotateAction() 
	{
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON,loadIcon("rotateIcon.png"));
		putValue(NAME,"");
		putValue(SHORT_DESCRIPTION, "Rotira selektovani objekta");
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
		pw.getPage().getDocument().getProject().setChanged(true);
		pw.startRotateState();
	}
	
}
