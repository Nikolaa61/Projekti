package gerudok.model.workspace;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class Workspace extends DefaultMutableTreeNode implements Serializable{
	private ArrayList<Project> projects = new ArrayList<Project>();
	private File file;
	private boolean changed;
	
	public Workspace(){
		super();
		this.file = null;
		this.changed = false;
	}

	public String toString(){
		return ((changed?  "* " : "") + "Workspace");
	}
	
	public TreeNode getChildAt(int pos){
		return projects.get(pos);
	}
	

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public int getChildCount() {
		return projects.size();
	}

	
	public TreeNode getParent() {
		return null;
	}

	public int getIndex(TreeNode pos){
		return projects.indexOf((Project) pos);
	}

	public boolean getAllowsChildren(){
		return true;
	}

	public boolean isLeaf(){
		return false;
	}

	public Enumeration<Project> children(){
		return (Enumeration<Project>) projects;
	}

	@Override
	public void add(MutableTreeNode project){
		projects.add((Project) project);
		((Project) project).setName("Projekat " + String.valueOf(getChildCount()));
	}

	public ArrayList<Project> getProjects() {
		return projects;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	
}
