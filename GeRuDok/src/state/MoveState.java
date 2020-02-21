package state;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import commands.AddDeviceCommand;
import commands.MoveDeviceCommand;
import elements.CircleElement;
import elements.DiagramDevice;
import elements.RectangleElement;
import elements.TriangleElement;
import painters.CirclePainter;
import painters.RectanglePainter;
import painters.TrianglePainter;
import view.PageView;


public class MoveState extends State{

	private PageView med; 
	private Point clickPoint = new Point();
	private ArrayList<Point> oldPositions = new ArrayList<>();
	 // racunamo u command
	private ArrayList<Point> draggedPoints = new ArrayList<Point>();
	
	public MoveState(PageView md) {
		med = md;
	}
	
	public void mousePressed(MouseEvent e) 
	{
		oldPositions.clear();

		for(DiagramDevice device : med.getPage().getSelectionModel().getSelectionList())
		{
			device.setOldPosition((Point) device.getPosition());

			oldPositions.add((Point) device.getPosition()) ;
		}
		clickPoint.setLocation(e.getPoint());
	}
	
	public void mouseDragged(MouseEvent e) 
	{
		draggedPoints.clear();
		if (med.getPage().getSelectionModel().getSelectionList() != null)
		{
			for(DiagramDevice diagramDevice : med.getPage().getSelectionModel().getSelectionList()) 
			{
				Point newPoint = new Point();
				
				draggedPoints.add(e.getPoint());
				newPoint.setLocation(e.getX() - clickPoint.getX(), e.getY() - clickPoint.getY()); // racunamo u command
				// moramo da pokupimo e koordinate i da ih prosledimo za racunanje newPointa u command
				Point locPoint = new Point();
				locPoint.setLocation(diagramDevice.getOldPosition().getX() + newPoint.getX(), diagramDevice.getOldPosition().getY() + newPoint.getY());
				diagramDevice.setPosition(locPoint);
				
				if(diagramDevice instanceof CircleElement){
					diagramDevice.setElementPainter(new CirclePainter(diagramDevice));
				}
				else if(diagramDevice instanceof RectangleElement){
					diagramDevice.setElementPainter(new RectanglePainter(diagramDevice));
				}
				else if(diagramDevice instanceof TriangleElement){
					diagramDevice.setElementPainter(new TrianglePainter(diagramDevice));
				}
			}
		}
	}
	public void mouseReleased(MouseEvent e) {

		med.getCommandManager().addCommand(new MoveDeviceCommand(med.getPage(),med.getPage().getSelectionModel(), clickPoint, draggedPoints, med.getPage().getSelectionModel().getSelectionList(), oldPositions));
	}
}
