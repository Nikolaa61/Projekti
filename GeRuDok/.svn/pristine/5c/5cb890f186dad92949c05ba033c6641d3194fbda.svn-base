package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import gui.MainWindow;

public class CloseTab extends AbstractGEDAction
{
	public CloseTab(){
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON,loadIcon("closeOneTab.png"));
		putValue(NAME,"Ugasi tab");
		putValue(SHORT_DESCRIPTION, "Gasi selektovan tab");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		MainWindow.getInstance().getCenterPane().getTabbedPane().remove(MainWindow.getInstance().getCenterPane().getTabbedPane().getSelectedComponent());
		
	}
	
}
