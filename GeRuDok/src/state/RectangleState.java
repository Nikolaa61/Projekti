package state;

import java.awt.Point;
import java.awt.event.MouseEvent;

import elements.DiagramDevice;
import elements.RectangleElement;
import gerudok.model.workspace.Page;
import view.PageView;
import commands.*;

public class RectangleState extends State{
	private PageView med; 
	public RectangleState(PageView md) {
      	med = md;
	}

	public void mousePressed(MouseEvent e) {
		Point position = e.getPoint();
		if (e.getButton()==MouseEvent.BUTTON1){
			if (med.getPage().getDeviceAtPosition(position)==-1){
//				 DiagramDevice device = RectangleElement.createDefault(position,med.getComponentCount());
				 med.getCommandManager().addCommand(new AddDeviceCommand(med.getPage(),med.getPage().getSelectionModel(),position,PageView.RECTANGLE));
				// med.addDiagramElements(device);
				}
		}
	}
}