package gui;

import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;

import controler.WorkspaceTreeControler;
import gerudok.model.workspace.Project;
import gerudok.model.workspace.Document;
import gerudok.model.workspace.WorkspaceModel;
import view.PopupTriggerListener;
import view.TabbedPaneListener;
import view.WorkspaceTreeCellRendered;
import view.WorkspaceTreeEditor;
import javax.swing.tree.*;
public class WorkSpaceTree extends JTree{
	
	public WorkSpaceTree() {
		addTreeSelectionListener(new WorkspaceTreeControler());
		setCellEditor(new WorkspaceTreeEditor(this, new DefaultTreeCellRenderer()));
		setCellRenderer(new WorkspaceTreeCellRendered());
		setEditable(true);
		addMouseListener(new PopupTriggerListener());
	}
	

	public void addProject(Project project) {
		((WorkspaceModel)getModel()).addProject(project);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public Project getCurrentProject() {
		TreePath path = getSelectionPath();
		for(int i=0; i<path.getPathCount(); i++){
			if(path.getPathComponent(i) instanceof Project){
				return (Project)path.getPathComponent(i);
			}
		}
		return null;
	}
	
	public ArrayList<Document> getCurrentDocuments(){
		TreePath[] paths = getSelectionPaths();
		ArrayList<Document> dokumenti = new ArrayList<>(); 
		for (int i =0; i < paths.length; i++) {
			for (int j=0; j< paths[i].getPathCount(); j++) {
				if (paths[i].getPathComponent(j) instanceof Document) {
					dokumenti.add((Document) paths[i].getPathComponent(j));
				}
			}
		}
		return dokumenti;
	}
}
