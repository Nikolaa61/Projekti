package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;

import gerudok.model.workspace.Document;
import gerudok.model.workspace.Page;
import gerudok.model.workspace.Project;
import gui.MainWindow;
import gui.TabbedPane;
public class WorkspaceTreeEditor extends DefaultTreeCellEditor implements ActionListener{
	
	private TabbedPane tp;
	private Object stavka = null;
	private JTextField edit = null;
	private int count = 0;
	
	public WorkspaceTreeEditor(JTree tree, DefaultTreeCellRenderer renderer) {
		super(tree, renderer);	
	}

	public Component getTreeCellEditorComponent(JTree arg0, Object arg1, boolean arg2, boolean arg3, boolean arg4, int arg5) {
		stavka = arg1;
		
		edit = new JTextField(arg1.toString());
		edit.addActionListener(this);
		
		return edit;
	}
	
	public boolean isCellEditable(EventObject arg0) {
		if (arg0 instanceof MouseEvent) {
			
			if (((MouseEvent)arg0).getClickCount()==3) 
			{
				return true;
			}

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
				
//				_________________________________________ScrollPane______________________________________________
				
//				tp = MainWindow.getInstance().getCenterPane().getTabbedPane();
//				
//				DocumentView selectedDocumentView = (DocumentView) tp.getSelectedComponent();
//				
//				for(ProjectView projectView : tp.getProjectViews())
//				{
//					for(DocumentView documentView : projectView.getDcViewList())
//					{
//						if(documentView.equals(selectedDocumentView))
//						{
//							MainWindow.getInstance().getCenterPane().getScrollMini().removeAll();
//							
//							for(PageView pageView : documentView.getPgViewList())
//							{
//								PageView pageViewClone;
//								pageViewClone = (PageView) pageView.clone();
//								
//								pageViewClone.setClosable(false);
//								pageViewClone.setResizable(false);
//								pageViewClone.setEnabled(false);
//								pageViewClone.setMaximizable(false);
//										
//										
//								pageViewClone.setSize(50, 50);
//								
//								MainWindow.getInstance().getCenterPane().getScrollMini().add(pageViewClone);
//							}
//							break;
//						}
//					}
//				}
			}
//				_________________________________________ScrollPane______________________________________________
			
			if(((MouseEvent)arg0).getClickCount()==2 && MainWindow.getInstance().getWorkspaceTree().getLastSelectedPathComponent() instanceof Document)
			{
				Object obj = MainWindow.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
				
				tp = MainWindow.getInstance().getCenterPane().getTabbedPane();
				
				for(int i = 0; i < tp.getTabCount(); i++)
				{
					Component com = tp.getComponentAt(i);
					
					if(com instanceof DocumentView)
					{
						if(((DocumentView) com).getDocument().equals((Document)obj))
						{
							tp.setSelectedIndex(i);
						}
					}
					
				}
			}
		}
		if(arg0 == null)
		{
			return true;
		}
		return false;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (stavka instanceof Project) {
			((Project)stavka).setName(e.getActionCommand());
		}else if(stavka instanceof Document) {
			((Document)stavka).setName(e.getActionCommand());
		}else {
			((Page)stavka).setName(e.getActionCommand());
		}
		SwingUtilities.updateComponentTreeUI(MainWindow.getInstance().getWorkspaceTree());
	}
}
