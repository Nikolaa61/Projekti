package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import gui.MainWindow;
import view.DocumentView;
import view.PageView;

public class ResizeAction extends AbstractGEDAction
{
	
	public ResizeAction() 
	{
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON,loadIcon("resizeAction.png"));
		putValue(NAME,"");
		putValue(SHORT_DESCRIPTION, "Menja velicinu selektovanog objekta");
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
		pw.startResizeState();

	}
	
}
