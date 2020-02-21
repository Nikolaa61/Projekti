package state;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import commands.RotateDeviceCommand;
import elements.CircleElement;
import elements.DiagramDevice;
import elements.RectangleElement;
import elements.TriangleElement;
import gerudok.model.workspace.DiagramSelectionModel;
import gerudok.model.workspace.Page;
import painters.CirclePainter;
import painters.RectanglePainter;
import painters.TrianglePainter;
import view.PageView;

public class RotateState extends State{
	private PageView med; 
	private Point clickPoint = new Point();
	private Point draggedPoint = new Point();
	private Point devicePosition = new Point();
	private double angle = 0;
	private ArrayList<Double> oldAngles = new ArrayList<Double>();
	
	public RotateState(PageView md) {
	    med = md;
	}
	
	public void mousePressed(MouseEvent e) {
		clickPoint = e.getPoint();
		oldAngles.clear();
		for(DiagramDevice diagramDevice : med.getPage().getSelectionModel().getSelectionList())
		{
			oldAngles.add(diagramDevice.getRotate());
			devicePosition = (Point) diagramDevice.getPosition();
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		
		draggedPoint = e.getPoint();
		
		if(med.getPage().getSelectionModel().getSelectionList() != null)
		{
			for(DiagramDevice diagramDevice : med.getPage().getSelectionModel().getSelectionList())
			{
				angle = Math.atan2(devicePosition.getY() - clickPoint.getY(), devicePosition.getX() - clickPoint.getX()) - 
						Math.atan2(devicePosition.getY() - draggedPoint.getY(), devicePosition.getX() - draggedPoint.getX());
				
				diagramDevice.setRotate(angle);
				
				if(diagramDevice instanceof CircleElement)
				{
					diagramDevice.setElementPainter(new CirclePainter(diagramDevice));
				}
				else if(diagramDevice instanceof RectangleElement)
				{
					diagramDevice.setElementPainter(new RectanglePainter(diagramDevice));
				}
				else if(diagramDevice instanceof TriangleElement)
				{
					diagramDevice.setElementPainter(new TrianglePainter(diagramDevice));
				}
			}
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		draggedPoint.setLocation(e.getX(), e.getY());
		
		ArrayList<DiagramDevice> selectedElements = med.getPage().getSelectionModel().getSelectionList();
		
		med.getCommandManager().addCommand(new RotateDeviceCommand(med.getPage(), selectedElements, med.getPage().getSelectionModel(), 
				clickPoint, draggedPoint, devicePosition, oldAngles));
	}
}
