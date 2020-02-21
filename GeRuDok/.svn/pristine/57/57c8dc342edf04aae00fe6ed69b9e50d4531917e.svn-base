package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import gui.MainWindow;
import view.DocumentView;
import view.PageView;

public class MoveAction extends AbstractGEDAction
{
	public MoveAction() 
	{
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_X);
		putValue(SMALL_ICON, loadIcon("moveIcon.png"));
		putValue(NAME, "");
		putValue(SHORT_DESCRIPTION, "Move");
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
		pw.startMoveState();
	}

}
