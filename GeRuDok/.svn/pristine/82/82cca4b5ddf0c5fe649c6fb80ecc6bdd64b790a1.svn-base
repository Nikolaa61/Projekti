package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import gui.MainWindow;
import view.DocumentView;
import view.PageView;

public class TriangleAction extends AbstractGEDAction{

	public TriangleAction() {
		
		
		
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("triangleIcon.png"));
		putValue(SHORT_DESCRIPTION, "Crtaj trougao");
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
		
	
	
		
		if(pw != null){	
			pw.getPage().getDocument().getProject().setChanged(true);
			pw.startTriangleState(); 
		}
	}
}