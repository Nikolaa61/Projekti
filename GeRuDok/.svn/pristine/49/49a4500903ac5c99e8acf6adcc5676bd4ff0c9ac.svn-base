package gerudok.model.workspace;

import java.util.ArrayList;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

public class WorkspaceModel extends DefaultTreeModel{
	public WorkspaceModel() {
		super(new Workspace());
	}
	
	public void addProject(Project project) {
		((Workspace)getRoot()).add((MutableTreeNode) project);
	}
	
	public ArrayList<Project> getProject() {
		return ((Workspace)getRoot()).getProjects();
	}
}
