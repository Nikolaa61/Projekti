package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import gui.MainWindow;
import view.DocumentView;
import view.PageView;

public class CircleAction extends AbstractGEDAction{

	public CircleAction() {
		
		
		
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("circleIcon.png"));
		putValue(SHORT_DESCRIPTION, "Crtaj krug");
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		PageView pw = null;
	
		
		pw = (PageView) ((DocumentView)MainWindow.getInstance().getCenterPane().getTabbedPane().getSelectedComponent()).getSelectedFrame();
		
		if(pw != null)
		{
			pw.getPage().getDocument().getProject().setChanged(true);
			pw.startCircleState();
		}
		
	
	}
	
}