package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import gui.Main;
import gui.MainWindow;
import view.DocumentView;
import view.PageView;

public class PHandCursorAction extends AbstractGEDAction{

	public PHandCursorAction() {
		
		
		
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("selectIcon.png"));
		putValue(SHORT_DESCRIPTION, "Select mode");
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		PageView pw = null;
		pw = (PageView) ((DocumentView)MainWindow.getInstance().getCenterPane().getTabbedPane().getSelectedComponent()).getSelectedFrame();
		if(pw != null)
		{
			pw.startSelectState();
		}
	}
}
