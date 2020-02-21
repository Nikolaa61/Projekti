package commands;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import elements.CircleElement;
import elements.DiagramDevice;
import elements.RectangleElement;
import elements.TriangleElement;
import gerudok.model.workspace.DiagramSelectionModel;
import gerudok.model.workspace.Page;
import painters.CirclePainter;
import painters.RectanglePainter;
import painters.TrianglePainter;

public class MoveDeviceCommand extends AbstractCommand{
	
	private Page model; 
	private Point2D lastPosition;
	private DiagramDevice device = null; 
	private DiagramSelectionModel selectionModel;
	private ArrayList<Point> oldPositions;
	private ArrayList<DiagramDevice> selectedElements = new ArrayList<DiagramDevice>();
	private Point clickPoint;
	private ArrayList<Point> draggedPoints = new ArrayList<Point>();
	
	public MoveDeviceCommand(Page model, DiagramSelectionModel selectionModel, Point clickPoint, ArrayList<Point> draggedPoints, ArrayList<DiagramDevice> selectedElements, ArrayList<Point> oldPosition) {
		this.model = model;
		this.selectionModel = selectionModel;
		this.selectedElements = (ArrayList<DiagramDevice>) selectedElements.clone();
		this.clickPoint = clickPoint;
		this.draggedPoints = (ArrayList<Point>) draggedPoints.clone();
		this.oldPositions = (ArrayList<Point>) oldPosition.clone();
	}
	
	@Override
	public void doCommand() {
		if (selectedElements != null)
		{
			for(int i =0; i<selectedElements.size(); i++) 
			{
				Point newPoint = new Point();
				newPoint.setLocation(draggedPoints.get(i).getX() - clickPoint.getX(),draggedPoints.get(i).getY() - clickPoint.getY()); // racunamo u command
				
				Point locPoint = new Point();
				locPoint.setLocation(selectedElements.get(i).getOldPosition().getX() + newPoint.getX(), selectedElements.get(i).getOldPosition().getY() + newPoint.getY());
				selectedElements.get(i).setPosition(locPoint);
				
				if(selectedElements.get(i) instanceof CircleElement){
					selectedElements.get(i).setElementPainter(new CirclePainter(selectedElements.get(i)));
				}
				else if(selectedElements.get(i) instanceof RectangleElement){
					selectedElements.get(i).setElementPainter(new RectanglePainter(selectedElements.get(i)));
				}
				else if(selectedElements.get(i) instanceof TriangleElement){
					selectedElements.get(i).setElementPainter(new TrianglePainter(selectedElements.get(i)));
				}
			}
		}
	}

	@Override
	public void undoCommand() {
		for(int i = 0; i<selectedElements.size(); i++) 
		{
			selectedElements.get(i).setPosition(oldPositions.get(i));
			
			if(selectedElements.get(i) instanceof CircleElement){
				selectedElements.get(i).setElementPainter(new CirclePainter(selectedElements.get(i)));
			}
			else if(selectedElements.get(i) instanceof RectangleElement){
				selectedElements.get(i).setElementPainter(new RectanglePainter(selectedElements.get(i)));
			}
			else if(selectedElements.get(i) instanceof TriangleElement){
				selectedElements.get(i).setElementPainter(new TrianglePainter(selectedElements.get(i)));
			}
			
		}
	}
	
}
