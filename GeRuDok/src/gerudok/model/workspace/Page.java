package gerudok.model.workspace;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import elements.DiagramDevice;
import elements.DiagramElement;
import observer.IListener;
import observer.IObserver;
import state.StateManager;

public class Page extends DefaultMutableTreeNode implements IObserver, Serializable, Cloneable{
	
	private String name;
	private Document document;
	private static int count = 0;
	transient private DiagramDevice selectedDevice;
	
	
	transient private ArrayList<IListener> listeners = new ArrayList<IListener>();
	protected ArrayList<DiagramDevice> diagramElements = new ArrayList<DiagramDevice>();

	
	private DiagramSelectionModel selectionModel;
	
	public Page(Document d) {
		this.document = d;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public int getDeviceAtPosition(Point point) {
		for(int i=getElementCount()-1;i>=0;i--){
			DiagramElement device = getDeviceAt(i);
			if(device.getPainter().isElementAt(point)){
				return i;
			}
		}
		return -1;
	}

	public String getName() {
		return name;
	}
	
	

	public DiagramDevice getSelectedDevice() {
		return selectedDevice;
	}

	public void setSelectedDevice(DiagramDevice selectedDevice) {
		this.selectedDevice = selectedDevice;
	}

	public void setName(String name) {
		this.name = name;
		notifyObservers(name);
	}

	

	@Override
	public TreeNode getChildAt(int arg0) {
		return null;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	public TreeNode getParent() {
		
		return null;
	}

	@Override
	public int getIndex(TreeNode node) {
		return 0;
	}
	
	public DiagramElement getDeviceAt(int i){
		return diagramElements.get(i);
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}
	
	

	public ArrayList<DiagramDevice> getDiagramElements() {
		return diagramElements;
	}

	public void setDiagramElements(ArrayList<DiagramDevice> diagramElements) {
		this.diagramElements = diagramElements;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public Enumeration children() {
		return null;
	}

	public Document getDocument() {
		return document;
	}

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		Page.count = count;
	}
	
	public int getElementCount(){
		return diagramElements.size();
	}
	
	public Iterator<DiagramDevice> getDeviceIterator(){
		return diagramElements.iterator();
	}
	
	public void addDiagramElements(DiagramDevice device){
		diagramElements.add(device);
		notifyObservers(device);
	}	
	
	public void removeDiagramElement(DiagramDevice device) {
		diagramElements.remove(device);
		notifyObservers(device);
	}
		
	public ArrayList<IListener> getListeners() {
		return listeners;
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
	public void removeObserver(IListener listener){
		if(listener == null || this.listeners == null || !this.listeners.contains(listener)){
			return;
		}
			
		this.listeners.remove(listener);
	}

	@Override
	public void notifyObservers(Object event){
		if(event == null || this.listeners == null || this.listeners.isEmpty())
		{
			return;
		}
		
		for(IListener listener : listeners)
		{
			listener.update(event);
		}
	}
	
	public DiagramSelectionModel getSelectionModel() {
		if(selectionModel == null)
			selectionModel = new DiagramSelectionModel();
		return selectionModel;
	}
	
	public void addDiagramElement(DiagramDevice element){
		diagramElements.add(element);
		notifyObservers(element);
	}	
	public void removeElement(DiagramDevice element){
		
		diagramElements.remove(element);
		notifyObservers(element);
	}

	@Override
	public Object clone()
	{
	    return super.clone();
	}
}