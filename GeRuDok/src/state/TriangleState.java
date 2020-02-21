package state;

import java.awt.Point;
import java.awt.event.MouseEvent;

import commands.AddDeviceCommand;
import elements.DiagramDevice;
import elements.TriangleElement;
import gerudok.model.workspace.Page;
import view.PageView;


public class TriangleState extends State{
	private PageView med; 
	public TriangleState(PageView md) {
	    med = md;
	}
	
	public void mousePressed(MouseEvent e) {
		Point position = e.getPoint();
		if (e.getButton()==MouseEvent.BUTTON1){

				 if (med.getPage().getDeviceAtPosition(position)==-1){
					//	 DiagramDevice device = CircleElement.createDefault(position,med.getComponentCount());
						 med.getCommandManager().addCommand(new AddDeviceCommand(med.getPage(),med.getPage().getSelectionModel(),position,PageView.TRIANGLE));
						// med.addDiagramElements(device);
			   	}
			 }
		}
}

