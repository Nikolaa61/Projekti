package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JDesktopPane;
import javax.swing.KeyStroke;

import gui.MainWindow;
import view.DocumentView;
import view.PageView;
public class RectangleAction extends AbstractGEDAction{

	public RectangleAction() {
		
		
		
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("rectangleIcon.png"));
		putValue(SHORT_DESCRIPTION, "Crtaj pravougaonik");
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		PageView pageView = null;
		pageView = (PageView) ((DocumentView)MainWindow.getInstance().getCenterPane().getTabbedPane().getSelectedComponent()).getSelectedFrame();
		
		if(pageView != null)
		{
			pageView.getPage().getDocument().getProject().setChanged(true);
			pageView.startRectangleState();
		}
		
		
	}
	
}