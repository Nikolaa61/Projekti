package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import gui.PopupMenu;

public class PopupTriggerListener extends MouseAdapter{
	PopupMenu menu = new PopupMenu();
	
	public void mousePressed(MouseEvent ev) {
        if (ev.isPopupTrigger()) {
          menu.show(ev.getComponent(), ev.getX(), ev.getY());
        }
      }

      public void mouseReleased(MouseEvent ev) {
        if (ev.isPopupTrigger()) {
          menu.show(ev.getComponent(), ev.getX(), ev.getY());
        }
      }

      public void mouseClicked(MouseEvent ev) {
      }

	
      
      
    
}
