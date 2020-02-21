package state;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import elements.DiagramDevice;
import elements.DiagramElement;
import gerudok.model.workspace.Page;
import view.PageView;
import view.PageView.Handle;

public class SelectState extends State{
	private PageView med;
	
	
	//indeks elementa koji je selektovan
	private int elementInMotion = -1;
		
	private int mouseButton=0;
	
	private Handle handleInMotion = null;
	
	public SelectState(PageView md) {
		med = md;
	}
	
	
	public void mousePressed(MouseEvent e) {
		
		mouseButton=e.getButton();
		Point position = e.getPoint();
		
		//ukoliko je pritisnut taster CTRL vrÅ¡i se multiselekcija
		//ako nije oÄ�isti trenutnu selekciju
		
	    if (mouseButton == MouseEvent.BUTTON1){
				if(!e.isControlDown()){
					med.getPage().getSelectionModel().removeAllFromSelectionList();
				}				
				elementInMotion = med.getPage().getDeviceAtPosition(position);
				if(elementInMotion != -1){
					//pogodjen je element, ukoliko je selektovan treba ga ukloniti iz liste,
					//ako nije treba ga dodati u listu
					DiagramDevice element=(DiagramDevice) med.getPage().getDeviceAt(elementInMotion);
					
					if (med.getPage().getSelectionModel().isElementSelected(element)){
						med.getPage().getSelectionModel().removeFromSelectionList(element);
					}else{
						med.getPage().getSelectionModel().addToSelectionList(element);
					}	
					
				}else{
					//nije pogodjen nijedan element
					
				}
			}else{
			}
			
		
		
		if (e.getButton()==MouseEvent.BUTTON1){
			
			for (DiagramDevice dd : med.getPage().getDiagramElements()) {
				
				if (dd.getPainter().isElementAt(position)) {
					 med.getPage().setSelectedDevice(dd);
				}
			}
		}
		
		
	}
	
	
	
	public void mouseMoved(MouseEvent e) {
		// Promena pokazivaÄ�a miÅ¡a u zavisnosti od toga iznad Ä�ega se nalazi
		Point2D position = e.getPoint();

		med.setMouseCursor(position);
	}	

	public void mouseDragged(MouseEvent e) {
		if(mouseButton == MouseEvent.BUTTON1){

			med.startLassoState();	
				
			}
		}
}
