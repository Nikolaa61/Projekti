package gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar
{
	private JMenu file;
	private JMenu edit;
	private JMenu help;
	
	public MenuBar()
	{
		file = new JMenu("File");
		edit = new JMenu("Edit");
		help = new JMenu("Help");
		
		this.add(file);
		this.add(edit);
		this.add(help);
		
		file.add(MainWindow.getInstance().getActionManager().getNewNodeAction());
		file.add(MainWindow.getInstance().getActionManager().getOpenProjectAction());
		file.add(MainWindow.getInstance().getActionManager().getSaveProjectAction());
		file.add(MainWindow.getInstance().getActionManager().getSaveASProjectAction());
		file.add(MainWindow.getInstance().getActionManager().getSaveWSAction());
		file.add(MainWindow.getInstance().getActionManager().getCloseProjectAction());
		file.add(MainWindow.getInstance().getActionManager().getCloseAllTabs());
		file.add(MainWindow.getInstance().getActionManager().getCloseProjectAction());
		file.add(MainWindow.getInstance().getActionManager().getSwitchWSAction());
		
		edit.add(MainWindow.getInstance().getActionManager().getRenameAction());
		
		help.add(MainWindow.getInstance().getActionManager().getOpenAboutAction());
		
	}
}
