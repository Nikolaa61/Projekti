package commands;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import elements.CircleElement;
import elements.DiagramDevice;
import elements.DiagramElement;
import elements.RectangleElement;
import elements.TriangleElement;
import gerudok.model.workspace.DiagramSelectionModel;
import gerudok.model.workspace.Page;
/// URADITI ISTO KAO ZA addDeviceCommand
import view.PageView;




public class DeleteDeviceCommand extends AbstractCommand{

	Page model; 
	ArrayList<DiagramDevice> selectedDevices; 
	ArrayList<DiagramDevice> reserveDevices;
	
	
	DiagramSelectionModel selectionModel;
	
	public DeleteDeviceCommand(Page model, DiagramSelectionModel selectionModel,ArrayList<DiagramDevice> selectedElements) {
		this.model = model;
		this.selectionModel = selectionModel;
		this.selectedDevices = selectedElements;
		
		
	}

	@Override
	public void doCommand() {
		if (selectedDevices == null) {
			return;
		}
		
				
		for (DiagramElement diagramElement : selectedDevices) {
			model.removeDiagramElement((DiagramDevice) diagramElement);
		}
		
		/// ideja je da pre uklanjanja sve sacuvamo u pomocnoj listi koju cemo u undoCommand prikazati
		reserveDevices = (ArrayList<DiagramDevice>)selectedDevices.clone();
		
		selectionModel.removeAllFromSelectionList(); 	
	}

	@Override
	public void undoCommand() {
		// kad se vratimo sa undo na ovu komandu on ce u listi comandi na tom mestu imati tacan izgled reserveDevices
		for (DiagramElement diagramElement : reserveDevices) {
			DiagramDevice diagramDevice = (DiagramDevice) diagramElement;
			// ne treba da se prave novi elementi jer vec postoje u listi, samo treba da ih dodamo
			model.addDiagramElement(diagramDevice);
		}
		selectionModel.removeAllFromSelectionList();
		selectionModel.addAllToSelectionList(reserveDevices);
		
	}
	
}
