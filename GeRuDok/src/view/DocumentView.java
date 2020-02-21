package view;

import java.awt.BorderLayout;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import gerudok.model.workspace.Document;
import gerudok.model.workspace.Page;
import gui.MainWindow;
import gui.ScrollPane;
import gui.TabbedPane;
import observer.IListener;

public class DocumentView extends JDesktopPane implements IListener
{
	
	private Document document;
	private String name;
	
	private ArrayList<PageView> pgViewList = new ArrayList<>();
	
	public DocumentView(Document documentModel) 
	{
		super();
		this.document = documentModel;
		this.document.addObserver(this);
		this.name = document.getProject().getName() + " -> " + document.getName();
		addPages();
		putPages();
	}

	private void addPages()
	{
		for(Page page : document.getPages())
		{
			pgViewList.add(new PageView(page));
		}
	}
	
	private void putPages()
	{	
		for(PageView pageView : pgViewList)
		{
			this.add(pageView);
		}
	}
	
	public Document getDocument() 
	{
		return document;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<PageView> getPgViewList() {
		return pgViewList;
	}

	@Override
	public void update(Object event)
	{
		if(event instanceof String)
		{
			this.setName(document.getProject().getName() + " -> " + (String)event.toString());
			
			TabbedPane tp = MainWindow.getInstance().getCenterPane().getTabbedPane();
			
			int totalTabs = tp.getTabCount();
			
			for(int i = 0; i < totalTabs; i++)
			{
				if(this.equals(tp.getComponentAt(i)))
				{
					tp.setTitleAt(i, document.getProject().getName() + " -> " + (String)event.toString());
				}  
			}
			
			for(PageView pageView : pgViewList)
			{
				if(pageView.getPage().getDocument().equals(this.getDocument()))
				{
					pageView.setTitle((String)event.toString() + " -> " + pageView.getPage().getName());
				}
			}
		}
			
		if(event instanceof Page)
		{
			PageView pageView = new PageView((Page) event);
			
			pgViewList.add(pageView);
			
			this.add(pageView);
			
//			_____________________
			
			PageView miniPageView;
			
			miniPageView = (PageView) pageView.clone();
			
			MainWindow.getInstance().getCenterPane().getScrollMini().add(miniPageView);
			
			miniPageView.setClosable(false);
			miniPageView.setResizable(false);
			miniPageView.setEnabled(false);
			miniPageView.setMaximizable(false);
			miniPageView.setSize(200, 200);
			
			repaint();
		}
	}
}
