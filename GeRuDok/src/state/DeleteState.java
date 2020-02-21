package state;

import java.awt.Point;
import java.awt.event.MouseEvent;

import commands.AddDeviceCommand;
import commands.DeleteDeviceCommand;
import view.PageView;

public class DeleteState extends State{
	private PageView med; 
	
	public DeleteState(PageView md) {
	    med = md;
	}
	
	public void mousePressed(MouseEvent e) {
		Point position = e.getPoint();
		if (e.getButton()==MouseEvent.BUTTON1){
			 if (med.getPage().getDeviceAtPosition(position)!=-1){
				 med.getCommandManager().addCommand(new DeleteDeviceCommand(med.getPage(), med.getPage().getSelectionModel(), med.getPage().getSelectionModel().getSelectionList()));
			 }
		}
	}
}
