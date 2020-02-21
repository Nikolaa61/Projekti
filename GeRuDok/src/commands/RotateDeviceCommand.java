package commands;

import java.awt.Point;
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

public class RotateDeviceCommand extends AbstractCommand
{
	private Page model; 
	private ArrayList<DiagramDevice> selectedDevices; 
	private DiagramSelectionModel selectionModel;
	private Point clickPoint;
	private Point draggedPoint;
	private Point devicePosition;
	private ArrayList<Double> oldAngles = new ArrayList<Double>();
	private double angle = 0;

	public RotateDeviceCommand(Page page, ArrayList<DiagramDevice> selectedDevices, DiagramSelectionModel selectionModel, 
																		Point clickPoint, Point draggedPoint, Point devicePosition, ArrayList<Double> oldAngles) 
	{
		this.model = page;
		this.selectedDevices = (ArrayList<DiagramDevice>) selectedDevices.clone();
		this.selectionModel = selectionModel;
		this.clickPoint = clickPoint;
		this.draggedPoint = draggedPoint;
		this.devicePosition = devicePosition;
		this.oldAngles = (ArrayList<Double>) oldAngles.clone();
	}

	@Override
	public void doCommand() 
	{
		if(selectedDevices != null)
		{
			for(DiagramDevice diagramDevice : selectedDevices)
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

	@Override
	public void undoCommand()
	{
		for(int i = 0; i < selectedDevices.size(); i++)
		{
			selectedDevices.get(i).setRotate(oldAngles.get(i));
			
			if(selectedDevices.get(i) instanceof CircleElement)
			{
				selectedDevices.get(i).setElementPainter(new CirclePainter(selectedDevices.get(i)));
			}
			else if(selectedDevices.get(i) instanceof RectangleElement)
			{
				selectedDevices.get(i).setElementPainter(new RectanglePainter(selectedDevices.get(i)));
			}
			else if(selectedDevices.get(i) instanceof TriangleElement)
			{
				selectedDevices.get(i).setElementPainter(new TrianglePainter(selectedDevices.get(i)));
			}
		}
		
	}
	
}
