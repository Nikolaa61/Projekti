package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import gerudok.model.workspace.Project;
import gui.MainWindow;
import gui.TabbedPane;

public class TabbedPaneListener extends MouseAdapter
{
	TabbedPane tp;
	private int count = 0;
	
	public void isDoubleClick(EventObject arg0) 
	{
		if (arg0 instanceof MouseEvent) 
		{
			if(((MouseEvent)arg0).getClickCount()==2 && MainWindow.getInstance().getWorkspaceTree().getLastSelectedPathComponent() instanceof Project)
			{
				Object obj = MainWindow.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
				
				tp = MainWindow.getInstance().getCenterPane().getTabbedPane();

				for(ProjectView pw : tp.getProjectViews())
				{
					if(tp.getProjectViews() == null)
					{
						ProjectView projectView = new ProjectView((Project) obj);
						tp.getProjectViews().add(projectView);
					}
					else if(pw.getProject().equals((Project) obj))
					{
						this.count = 1;
						
						for(DocumentView documentView : pw.getDcViewList())
						{
							int numOfTabs = tp.getTabCount();
							
							if(numOfTabs == 0)
							{
								for(DocumentView docView : pw.getDcViewList())
								{
									tp.add(docView);
								}
							}
							
							for(int i = 0; i < numOfTabs; i++)
							{
								if(!tp.getComponentAt(i).equals(documentView))
								{
									tp.add(documentView);
								}
							}
						}
					}
				}
				
				if(count == 0)
				{
					ProjectView projectView = new ProjectView((Project) obj);
					tp.getProjectViews().add(projectView);					
				}
				
				this.count = 0;
			}
		}
	}
}
