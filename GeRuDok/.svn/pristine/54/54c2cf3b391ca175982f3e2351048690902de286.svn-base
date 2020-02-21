package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import actions.AbstractGEDAction;
import gui.MainWindow;
import view.DocumentView;
import view.PageView;

public class RedoAction extends AbstractGEDAction{

	RedoAction(){
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
		putValue(SMALL_ICON, loadIcon("redoIcon.png"));
		putValue(NAME, "Redo");
		putValue(SHORT_DESCRIPTION, "Redo");
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		PageView pw = null;

		if (MainWindow.getInstance().getWorkspaceTree().isSelectionEmpty()) {
			return;
		}

		if ((PageView) ((DocumentView)MainWindow.getInstance().getCenterPane().getTabbedPane().getSelectedComponent()).getSelectedFrame()==null) {
			return;
		}
		
		pw = (PageView) ((DocumentView)MainWindow.getInstance().getCenterPane().getTabbedPane().getSelectedComponent()).getSelectedFrame();
		pw.getCommandManager().doCommand();
		
	}
	
}
