package gerudok.model.workspace;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultBoundedRangeModel;
import javax.swing.DefaultSingleSelectionModel;
import javax.swing.event.EventListenerList;

import elements.DiagramDevice;
import elements.DiagramElement;
import observer.IListener;
import observer.IObserver;



public class DiagramSelectionModel extends DefaultSingleSelectionModel implements IObserver{
	/**
	 * Lista sa elementima koji su selektovani.
	 */
	private ArrayList<DiagramDevice> selectionList = new ArrayList<DiagramDevice>();
	
	transient private ArrayList<IListener> listeners = new ArrayList<IListener>();
	//UpdateEvent updateEvent = null;	
	
	/**
	 * Metoda dodaje element u listu selekcije.
	 * 
	 */
	public void addToSelectionList(DiagramDevice reserveDevices) {
		selectionList.add(reserveDevices);
		notifyObservers(reserveDevices);
	}
	
	
	/**
	 * Metoda dodaje listu elemenata u selekcionu listu.
	 */
	public void addAllToSelectionList(ArrayList<DiagramDevice> list) {
		selectionList.addAll(list);
		notifyObservers(list);
	}
	
	
	/**
	 * Vraca broj elemenata u selekcionoj listi.
	 */
	public int getSelectionListSize() {
		return selectionList.size();
	}
	
	
	/**
	 * Vraca element iz selekcione liste koji se nalazi na indeksu koji se navodi
	 * kao argument.
	 */
	public DiagramDevice getElementFromSelectionListAt(int index) {
		return (DiagramDevice)selectionList.get(index);
	}
	
	
	/**
	 * Vraca indeks zadatog elementa u selekcionoj listi.
	 * @param element - element za koji se trazi indeks u listi
	 * @return - indeks elementa u listi ili -1

	 */
	public int getIndexByObject(DiagramDevice element) {
		return selectionList.indexOf(element);
	}
	
	
	/**
	 * Uklanja element iz selekcione liste na datom indeksu.
	 * @param index - indeks elementa koji se uklanja iz selekcione liste.
	 */
	public void removeFromSelectionList(DiagramDevice element) {
		selectionList.remove(element);
		notifyObservers(element);
	}
	
	
	/**
	 * Uklanja sve elemente iz selekcione liste.
	 */
	public void removeAllFromSelectionList() {
		selectionList.clear();
		notifyObservers(selectionList);
	}
	
	
	/**
	 * Vraca objekat selekcione liste.
	 * @return - objekat selekcione liste

	 */
	public ArrayList<DiagramDevice>  getSelectionList() {
		return selectionList;
	}
	
	public Iterator<DiagramDevice> getSelectionListIterator(){
		return selectionList.iterator();
	}
	
	public boolean isElementSelected(DiagramElement element){
		return selectionList.contains(element);
		
	}
	/**Metoda selektuje sve elemente koji se seku ili su obuhvaceni pravougaonikom
	 * @param rec - selekcioni pravougaonik
	 * @param elements - niz elemenata iz modela
	 *  
	 */
	public void selectElements(Rectangle2D rec,ArrayList<DiagramDevice> elements){
		Iterator<DiagramDevice> it = elements.iterator();  /// U zagradi je bio DiagramElement
		while(it.hasNext()){
			DiagramElement element=it.next();
			if (element instanceof DiagramDevice){
				DiagramDevice device=(DiagramDevice)element;
				if(rec.intersects(device.getPosition().getX(), device.getPosition().getY(),
						device.getSize().getWidth(), device.getSize().getHeight())){
					if(!isElementSelected(device))
						addToSelectionList(device);
				}
		}
	}

			
	}
	
	public ArrayList<DiagramDevice> getSelected() {
		ArrayList<DiagramDevice> selected=new ArrayList<DiagramDevice>();
		selected.addAll(selectionList);
		
		return selected;
	}


	@Override
	public void addObserver(IListener listener) {
		if(listener == null){
			return;
		}

		if(this.listeners ==null){
			this.listeners = new ArrayList<>();
		}
			
		if(!this.listeners.contains(listener)){
			this.listeners.add(listener);
		}
		
	}


	@Override
	public void removeObserver(IListener listener) {
		if(listener == null || this.listeners == null || !this.listeners.contains(listener)){
			return;
		}
			
		this.listeners.remove(listener);
		
	}


	@Override
	public void notifyObservers(Object event) {
		if(event == null || this.listeners == null || this.listeners.isEmpty())
		{
			return;
		}
		
		for(IListener listener : listeners)
		{
			listener.update(event);
		}
		
	}
	




}
