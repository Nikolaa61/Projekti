package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import gui.MainWindow;

public class CloseAllTabs extends AbstractGEDAction
{
	public CloseAllTabs(){
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON,loadIcon("closeAllTabs.png"));
		putValue(NAME,"Ugasi sve tabove");
		putValue(SHORT_DESCRIPTION, "Gasi sve tabove");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		MainWindow.getInstance().getCenterPane().getTabbedPane().removeAll();
		
	}
	
}
