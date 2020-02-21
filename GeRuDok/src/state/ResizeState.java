package state;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import commands.AddDeviceCommand;
import commands.ResizeDeviceCommand;
import elements.CircleElement;
import elements.DiagramDevice;
import elements.DiagramElement;
import elements.RectangleElement;
import elements.TriangleElement;
import painters.CirclePainter;
import painters.RectanglePainter;
import painters.TrianglePainter;
import view.PageView;

public class ResizeState extends State{

	private PageView med; 
	private Point oldPoint = new Point();
	private Dimension dim;
	private ArrayList<Dimension> oldDimensions = new ArrayList<>();
	private ArrayList<Dimension> dimenzije = new ArrayList<>();
	public ResizeState(PageView md) {
	    med = md;
	}
	
	public void mousePressed(MouseEvent e) {
		oldPoint.setLocation(e.getPoint());
		
		ArrayList<DiagramDevice> selectedElements = med.getPage().getSelectionModel().getSelectionList();
		oldDimensions.clear();
		
		for (DiagramDevice diagramDevice : selectedElements) {
			oldDimensions.add(diagramDevice.getSize()); 
		}
		
	}
	
	public void mouseDragged(MouseEvent e) {
		Point point = e.getPoint(); 
		
		if(med.getPage().getSelectionModel().getSelectionList() != null){
		
			ArrayList<DiagramDevice> selectedElements = med.getPage().getSelectionModel().getSelectionList();
			dimenzije.clear();
			for(DiagramDevice diagramDevice : selectedElements) {
				
				dim = new Dimension((int) (((DiagramDevice) diagramDevice).getSize().width + ((int)point.getX() - oldPoint.getX())/25), 
						(int) (((DiagramDevice) diagramDevice).getSize().height + ((int)point.getY() - oldPoint.getY())/25));
				diagramDevice.setSize(dim);
				if(diagramDevice instanceof CircleElement){
					diagramDevice.setElementPainter(new CirclePainter(diagramDevice));
				}
				else if(diagramDevice instanceof RectangleElement){
					diagramDevice.setElementPainter(new RectanglePainter(diagramDevice));
				}
				else if(diagramDevice instanceof TriangleElement){
					diagramDevice.setElementPainter(new TrianglePainter(diagramDevice));
				}
				dimenzije.add(dim);
				
				
			}	
		}
	}
	public void mouseReleased(MouseEvent e) {
		Point point = e.getPoint();
		
		ArrayList<DiagramDevice> selectedElements = med.getPage().getSelectionModel().getSelectionList();
		
		med.getCommandManager().addCommand(new ResizeDeviceCommand(med.getPage(), oldDimensions, oldPoint,point,selectedElements, dimenzije));

	}
}