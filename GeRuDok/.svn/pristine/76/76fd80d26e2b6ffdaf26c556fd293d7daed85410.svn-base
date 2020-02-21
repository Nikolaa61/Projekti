package gerudok.model.workspace;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import elements.CircleElement;
import elements.DiagramDevice;
import elements.RectangleElement;
import elements.TriangleElement;
import event.UpdateEvent;
import event.UpdateListener;
import gui.MainWindow;
import observer.IListener;
import observer.IObserver;
import painters.CirclePainter;
import painters.RectanglePainter;
import painters.TrianglePainter;

public class Project extends DefaultMutableTreeNode implements UpdateListener, IObserver, Serializable{
	
	transient private Workspace workspace;
	
	private ArrayList<Document> documents = new ArrayList<Document>();
	transient private ArrayList<IListener> listeners = new ArrayList<IListener>();
	private String name;
	private File projectFile;
	private transient boolean changed;
	transient private Clipboard clipboard = new Clipboard("Klipbord1");
	
	
	public Project( Workspace wp){
		this.workspace = wp;
		this.changed = false;
		this.projectFile = null;
	}
	
	@Override
	public void add(MutableTreeNode document){
		documents.add((Document) document);
		notifyObservers(document);
	}	
	
	public String toString(){
		return ((changed?  "* " : "") +name);
	}	
	
	public Workspace getWorkspace() {
		return workspace;
	}

	public Document getDocument(int index){
		return documents.get(index);
	}
	
	public int getDocumentIndex(Document diagram){
		return documents.indexOf(diagram);
	}
	
	public int getDocumentCount(){
		return documents.size();
	}	
	
	public boolean isLeaf() {
		return false;
	}	
	
	public void setName(String name){
		this.name=name;
		notifyObservers(name);
	}
	
	public String getName() {
		return name;
	}

	public TreeNode getChildAt(int arg0) {
		return getDocument(arg0);
	}

	public int getChildCount() {
		return getDocumentCount();
	}

	public TreeNode getParent(){
		return null;
	}

	public int getIndex(TreeNode arg0){
		return getDocumentIndex((Document)arg0);
	}

	public boolean getAllowsChildren() {
		return false;
	}

	public Enumeration children() {
		return (Enumeration) documents;
	}
	
	public ArrayList<Document> getDocuments() {
		return documents;
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
		if(event == null || this.listeners == null || this.listeners.isEmpty()){
			return;
		}
		
		for(IListener listener : listeners){
			listener.update(event);
		}
	}
	
	

	public File getProjectFile() {
		return projectFile;
	}

	public boolean isChanged() {
		return changed;
	}

	
	
	public void setWorkspace(Workspace workspace) {
		this.workspace = workspace;
	}

	public void setProjectFile(File projectFile) {
		this.projectFile = projectFile;
	}

	public void setChanged(boolean changed) {
		if(this.changed!=changed) {
			this.changed = changed;
			notifyObservers(changed);
		}
	}

	@Override
	public void UpdatePerformed(UpdateEvent e) {
		setChanged(true);
	}

	public void shareIn() {
		Transferable clipboardContent = KlipbordDeljenja.getInstance().getClipboard().getContents(KlipbordDeljenja.getInstance()); 
		
		if((clipboardContent != null) && (clipboardContent.isDataFlavorSupported(DocumentSelection.documentFlavor))) {
			try {
				Document document = (Document) clipboardContent.getTransferData(DocumentSelection.documentFlavor);;
		 		documents.add(document);
		 		notifyObservers(document);
			}
			catch (Exception ex) {
		 		ex.printStackTrace ();
		 	}
		}
	}
	
	public void pasteDocument()
	{
		Transferable clipboardContent = KlipbordDeljenja.getInstance().getClipboard().getContents(KlipbordDeljenja.getInstance()); 
		
		if((clipboardContent != null) && (clipboardContent.isDataFlavorSupported(DocumentSelection.documentFlavor))) 
		{
			try
			{
				Document document = (Document) clipboardContent.getTransferData(DocumentSelection.documentFlavor);;
		 		DocumentCopy documentcopy = new DocumentCopy(document, this);
		 		documents.add(documentcopy);
		 		notifyObservers(documentcopy);
			}
			catch (Exception ex)
			{
		 		ex.printStackTrace ();
		 	}
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

