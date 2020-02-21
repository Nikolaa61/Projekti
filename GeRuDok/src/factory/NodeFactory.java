package factory;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class NodeFactory 
{
	public abstract DefaultMutableTreeNode createNode(DefaultMutableTreeNode obj);
}
