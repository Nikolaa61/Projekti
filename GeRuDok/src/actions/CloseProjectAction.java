package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import gerudok.model.workspace.Document;
import gerudok.model.workspace.Page;
import gerudok.model.workspace.Project;
import gui.MainWindow;
import gui.TabbedPane;
import view.DocumentView;
import view.PageView;
import view.ProjectView;

public class CloseProjectAction extends AbstractGEDAction{

	public CloseProjectAction(){
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON,loadIcon("deleteNode.png"));
		putValue(NAME,"Ugasi projekat");
		putValue(SHORT_DESCRIPTION, "Gasi projekat");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = MainWindow.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
		
		if(obj instanceof Project) {
			
			((Project)obj).getWorkspace().getProjects().remove(obj);
			
			ArrayList<Document> documents = ((Project)obj).getDocuments();
	
			TabbedPane tp = MainWindow.getInstance().getCenterPane().getTabbedPane();
			
			for(ProjectView projectView : tp.getProjectViews())
			{
				if(projectView.getProject().equals(((Project)obj)))
				{
					for(DocumentView documentView : projectView.getDcViewList())
					{
						tp.remove(documentView);
					}
					
					tp.getProjectViews().remove(projectView);
					
					MainWindow.getInstance().getWorkspaceTree().setSelectionPath(null);
					break;
				}
			}
			
			documents.clear();
		}
		
		if (obj instanceof Document) {
			
			Document document = (Document) obj;
			
			document.getProject().getDocuments().remove(document);
			
			ArrayList<Page> pages = (document).getPages();
			ArrayList<DocumentView> helpList = new ArrayList<DocumentView>();
			
			TabbedPane tp = MainWindow.getInstance().getCenterPane().getTabbedPane();
			
			for(ProjectView projectView : tp.getProjectViews())
			{
				if(projectView.getProject().equals(document.getProject()))
				{
					for(DocumentView documentView : projectView.getDcViewList())
					{
						if(documentView.getDocument().equals(document))
						{
							tp.remove(documentView);
							
							helpList.add(documentView);
						}
					}
				}
			}
			
			for(ProjectView projectView : tp.getProjectViews())
			{
				for(DocumentView documentView : helpList)
				{
					projectView.getDcViewList().remove(documentView);
				}
			}
			
			MainWindow.getInstance().getWorkspaceTree().setSelectionPath(null);
			pages.clear();
			
		}
		
		if (obj instanceof Page) {
			((Page)obj).getDocument().getPages().remove(obj);
			
			TabbedPane tp = MainWindow.getInstance().getCenterPane().getTabbedPane();
			
			for(ProjectView projectView : tp.getProjectViews())
			{
				for(DocumentView documentView : projectView.getDcViewList())
				{
					for(PageView pageView : documentView.getPgViewList())
					{
						if(pageView.getPage().equals((Page)obj))
						{
							try 
							{
								pageView.setClosed(true);
								
								MainWindow.getInstance().getWorkspaceTree().setSelectionPath(null);
							} 
							catch (PropertyVetoException e1) 
							{
								e1.printStackTrace();
							}
						}
					}
				}
			}
		}

		SwingUtilities.updateComponentTreeUI(MainWindow.getInstance().getWorkspaceTree());
		
	}
	
}
