package state;

import java.awt.Point;
import java.awt.event.MouseEvent;

import commands.AddDeviceCommand;
import elements.CircleElement;
import elements.DiagramDevice;
import gerudok.model.workspace.Page;

import view.PageView;


public class CircleState extends State{
	private PageView med; 
	
	public CircleState(PageView md) {
	    med = md;
	}
	
	public void mousePressed(MouseEvent e) {
		Point position = e.getPoint();
		
		if (e.getButton()==MouseEvent.BUTTON1){
			 if (med.getPage().getDeviceAtPosition(position)==-1){
				 med.getCommandManager().addCommand(new AddDeviceCommand(med.getPage(),med.getPage().getSelectionModel(),position,PageView.CIRCLE));
			 }
		}
	}
}
