package commands;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import elements.CircleElement;
import elements.DiagramDevice;
import elements.DiagramElement;
import elements.RectangleElement;
import elements.TriangleElement;
import gerudok.model.workspace.DiagramSelectionModel;
import gerudok.model.workspace.Page;
import painters.CirclePainter;
import painters.RectanglePainter;
import painters.TrianglePainter;

public class ResizeDeviceCommand extends AbstractCommand{

	private Page model; 
	private ArrayList<DiagramDevice> selectedDevices; 
	private ArrayList<DiagramDevice> diagramElementi = new ArrayList<>();
	private Point oldPoint;
	private Point newPoint;
	private Dimension dim = null;
	private ArrayList<Dimension> oldDimensions = new ArrayList<>();
	private ArrayList<Dimension> dimenzije = new ArrayList<>();
	
	public ResizeDeviceCommand(Page model,ArrayList<Dimension> oldDimensions, Point oldPoint, Point newPoint, ArrayList<DiagramDevice> selectedDevices, ArrayList<Dimension> dimenzije) {
		this.model = model;
		this.selectedDevices = (ArrayList<DiagramDevice>) selectedDevices.clone();
		this.oldPoint = oldPoint;
		this.newPoint = newPoint;
		this.oldDimensions = (ArrayList<Dimension>) oldDimensions.clone();
		this.dimenzije = (ArrayList<Dimension>) dimenzije.clone();
	
	}

	@Override
	public void doCommand() {
		
		if (selectedDevices == null) {
			return;
		}
			ArrayList<DiagramDevice> selectedElements = selectedDevices;
			for (int i = 0; i<selectedElements.size(); i++) {
					dim = dimenzije.get(i); 
					selectedElements.get(i).setSize(dim);
					if(selectedElements.get(i) instanceof CircleElement){
						selectedElements.get(i).setElementPainter(new CirclePainter(selectedElements.get(i)));
					}
					else if(selectedElements.get(i) instanceof RectangleElement){
						selectedElements.get(i).setElementPainter(new RectanglePainter(selectedElements.get(i)));
					}
					else if(selectedElements.get(i) instanceof TriangleElement){
						selectedElements.get(i).setElementPainter(new TrianglePainter(selectedElements.get(i)));
					}
				// Ako je prazna lista diagramElementi treba da se napravi nova dimenzija inace da se prosledi samo dim od diagramElementi.getSize();
			}
}

		
	

	@Override
	public void undoCommand() {
		
			
//			ArrayList<DiagramDevice> selectedElements = model.getSelectionModel().getSelectionList();
			
//			for( int i = 0; i<reserveDevices.size(); i++) {
//				reserveDevices.get(i).setSize(reserveDevices.get(i).getSize());
//			}
			if(selectedDevices != null){
				
				for (int i=0; i<selectedDevices.size(); i++) {
					
					dim = new Dimension(oldDimensions.get(i)); 
					if(selectedDevices.get(i) instanceof CircleElement){
						selectedDevices.get(i).setSize(dim);
						selectedDevices.get(i).setElementPainter(new CirclePainter(selectedDevices.get(i)));
					}
					else if(selectedDevices.get(i) instanceof RectangleElement){
						selectedDevices.get(i).setSize(dim);
						selectedDevices.get(i).setElementPainter(new RectanglePainter(selectedDevices.get(i)));
					}
					else if(selectedDevices.get(i) instanceof TriangleElement){
						selectedDevices.get(i).setSize(dim);
						selectedDevices.get(i).setElementPainter(new TrianglePainter(selectedDevices.get(i)));
					}
					
					
				}
				
				
				
			}
		
	}
	
}