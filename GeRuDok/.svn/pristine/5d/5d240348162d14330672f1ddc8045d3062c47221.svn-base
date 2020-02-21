package gui;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class PopupMenu extends JPopupMenu{
	
	
	public PopupMenu(){
		JMenuItem item = new JMenuItem("Dodaj projekat");
	    
	    
	    item = new JMenuItem("Izbrisi projekat");
	    item.addActionListener(MainWindow.getInstance().getActionManager().getCloseProjectAction());
	    add(item);
	    
	    item = new JMenuItem("Promeni ime");
	    item.addActionListener(MainWindow.getInstance().getActionManager().getRenameAction());
	    add(item);
	    
	    item = new JMenuItem("Sacuvaj projekat");
	    item.addActionListener(MainWindow.getInstance().getActionManager().getSaveProjectAction());
	    add(item);
	    
	    item = new JMenuItem("Otvori projekat");
	    item.addActionListener(MainWindow.getInstance().getActionManager().getOpenProjectAction());
	    add(item);
	}
}
