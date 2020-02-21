package commands;

import java.awt.geom.Point2D;

import elements.CircleElement;
import elements.DiagramDevice;
import elements.DiagramElement;
import elements.RectangleElement;
import elements.TriangleElement;
import gerudok.model.workspace.DiagramSelectionModel;
import gerudok.model.workspace.Page;

import view.PageView;



public class AddDeviceCommand extends AbstractCommand{

	Page model; 
	Point2D lastPosition;
	DiagramDevice device = null; 
	DiagramSelectionModel selectionModel;
	int deviceType;
	
	
	public AddDeviceCommand(Page model, DiagramSelectionModel selectionModel, Point2D lastPosition,int deviceType) {
		this.model = model;
		this.lastPosition = lastPosition;
		this.selectionModel = selectionModel;
		this.deviceType=deviceType;
		
		
	}
	
	
	@Override
	public void doCommand() { /// Doda element na Page /// uslov je zbog REDO ako device != null REDO je
		if (device==null) { /// uslov da se napravi samo ako nije bio napravljen, ako je bio napravljen dodace se
			if (deviceType==PageView.CIRCLE){
				device=CircleElement.createDefault(lastPosition,model.getElementCount());
				
			}else if (deviceType==PageView.RECTANGLE){
				device=RectangleElement.createDefault(lastPosition,model.getElementCount());
				
			}else if(deviceType==PageView.TRIANGLE) {
				device=TriangleElement.createDefault(lastPosition, model.getElementCount());
				
			}	
			
		}
		selectionModel.removeAllFromSelectionList();
		model.addDiagramElement(device);
		selectionModel.addToSelectionList(device);
		
		
		
		
		
		
	}

	@Override
	public void undoCommand() { /// Samo sklanja element sa Page-a
		selectionModel.removeAllFromSelectionList();
		model.removeElement(device);
	}
	
}
