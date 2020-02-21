package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import gerudok.model.workspace.Document;
import gerudok.model.workspace.Project;
import gui.MainWindow;
import gui.ScrollPane;
import gui.TabbedPane;
import observer.IListener;

public class ProjectView extends JTabbedPane implements IListener
{
	private Project project;
	private String name;
	
	
	private ScrollPane scrollMini;
	private JSplitPane splitPaneMini;
	
	private ArrayList<DocumentView> dcViewList = new ArrayList<DocumentView>();
	
	public ProjectView(Project project)
	{
		super();
		this.name = project.getName();
		this.project = project;
		this.project.addObserver(this);
		addDocuments();
		putDocuments();
//
//		setLayout(new BorderLayout());
//		
//		scrollMini = new ScrollPane();
//		splitPaneMini = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollMini, MainWindow.getInstance().getCenterPane().getTabbedPane());
//		
//		add(splitPaneMini, BorderLayout.CENTER);
		
		//splitPaneMini.setDividerLocation(250);
	}
	
	private void addDocuments()
	{
		for(Document document : project.getDocuments())
		{
			DocumentView documentView = new DocumentView(document);
			
			dcViewList.add(documentView);
		}
	}

	private void putDocuments()
	{
		TabbedPane tp = MainWindow.getInstance().getCenterPane().getTabbedPane();
		
		for(DocumentView documentView : dcViewList)
		{
			tp.add(documentView);
		}
	}
	
	public Project getProject() {
		return project;
	}

	public String getName() {
		return name;
	}

	public ArrayList<DocumentView> getDcViewList() {
		return dcViewList;
	}

	private boolean inList(Document document, Project project)
	{
		for(Document doc : project.getDocuments())
		{
			if(doc.equals(document))
			{
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void update(Object event) {
		if (event instanceof String) {
			
			setName((String) event);
			
			TabbedPane tp = MainWindow.getInstance().getCenterPane().getTabbedPane();
			
			int totalTabs = tp.getTabCount();
			
			for(int i = 0; i < totalTabs; i++)
			{
				Component comp = tp.getComponentAt(i);

				if(inList(((DocumentView) comp).getDocument(), this.getProject()))
				{
					tp.setTitleAt(i, (String) event + " -> " + ((DocumentView) comp).getDocument().getName());
				}
			}
		}
		
		if (event instanceof Document)
		{
			DocumentView documentView = new DocumentView((Document) event);
			
			dcViewList.add(documentView);
			
			TabbedPane tp = MainWindow.getInstance().getCenterPane().getTabbedPane();
			
			tp.add(documentView);
		}
		
		if(event instanceof Boolean)
		{
			SwingUtilities.updateComponentTreeUI(MainWindow.getInstance().getWorkspaceTree());
		}
	}
}
