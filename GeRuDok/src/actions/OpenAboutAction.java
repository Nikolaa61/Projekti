package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import gui.AboutInfo;

public class OpenAboutAction extends AbstractGEDAction
{
	
	public OpenAboutAction() 
	{
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON,loadIcon("about.png"));
		putValue(NAME,"About");
		putValue(SHORT_DESCRIPTION, "Vise informacija o proizvodu");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		AboutInfo aboutInfo = new AboutInfo();
	}
	
}
