package gerudok.model.workspace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import observer.IListener;
import observer.IObserver;

public class DocumentCopy extends Document implements IObserver, Serializable{

	private String name;
	private Project project;
	
	private ArrayList<Page> pages = new ArrayList<Page>();
	transient private ArrayList<IListener> listeners = new ArrayList<IListener>();

	public DocumentCopy(Document d, Project p) {
		super(p);
		this.project = p;
		this.name =d.getName();
		this.pages = d.getPages();
		//pages.clear();
		
		System.out.println(d.getPages().size());
		
		for(int i = 0; i < d.getPages().size(); i++)
		{
			doclone(d, i);
		}
		
		System.out.println(this.getPages().size());
//	
//		
//		pages = (ArrayList<Page>) d.getPages().clone();
//		
//		
//		
//		this.pages = pages;
	}
	
	public String toString()
	{
		return name;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
		notifyObservers(name);
	}

	public TreeNode getChildAt(int arg0)
	{
		return getPage(arg0);
	}
	public Page getPage(int index) {
		return pages.get(index);
	}

	public int getChildCount() 
	{
		return pages.size();
	}

	public TreeNode getParent() 
	{
		return null;
	}
	
	public int getIndex(TreeNode arg0) {
		return getPageIndex((Page) arg0);
	}
	
	public int getPageIndex(Page page) {
		return pages.indexOf(page);
	}


	public boolean getAllowsChildren() 
	{
		return false;
	}

	public boolean isLeaf() 
	{
		return false;
	}

	public Enumeration children() 
	{
		return (Enumeration)pages;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project p) {
		this.project = p;
	}

	public ArrayList<Page> getPages() {
		return pages;
	}
	
	@Override
	public void add(MutableTreeNode page)
	{
		pages.add((Page) page);
		((Page) page).setName("Strana" + String.valueOf(pages.size()));
		notifyObservers(page);
	}

	@Override
	public void addObserver(IListener listener) 
	{
		if(listener == null)
		{
			return;
		}

		if(this.listeners ==null)
		{
			this.listeners = new ArrayList<>();
		}
			
		if(!this.listeners.contains(listener))
		{
			this.listeners.add(listener);
		}
	}

	@Override
	public void removeObserver(IListener listener)
	{
		if(listener == null || this.listeners == null || !this.listeners.contains(listener))
		{
			return;
		}
			
		this.listeners.remove(listener);
	}

	@Override
	public void notifyObservers(Object event)
	{
		if(event == null || this.listeners == null || this.listeners.isEmpty())
		{
			return;
		}
		
		for(IListener listener : listeners)
		{
			listener.update(event);
		}
	}	
	
	@Override
	public Object clone() {
		return super.clone();
	}
	
	public void doclone(Document cloned, int i)
	{
		//cloned = (Document)super.clone();
	    this.add((Page)cloned.getPage(i).clone());   
	}
}
