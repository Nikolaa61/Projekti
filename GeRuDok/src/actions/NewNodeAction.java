package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import factory.DocumentNodeFactory;
import factory.NodeFactory;
import factory.PageNodeFactory;
import factory.ProjectNodeFactory;
import gerudok.model.workspace.Document;
import gerudok.model.workspace.Page;
import gerudok.model.workspace.Project;
import gerudok.model.workspace.Workspace;
import gui.MainWindow;

public class NewNodeAction extends AbstractGEDAction{

	public NewNodeAction() 
	{
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON,loadIcon("addNode.png"));
		putValue(NAME,"Novi element");
		putValue(SHORT_DESCRIPTION, "Dodaje novi element ne odgovarajuce mesto");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		DefaultMutableTreeNode obj = (DefaultMutableTreeNode) MainWindow.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
		
		if(obj == null)
		{
			JOptionPane.showMessageDialog(null, "Selektuj cvor", "Greska", JOptionPane.ERROR_MESSAGE);
		}
		else if (obj instanceof Page) {
			JOptionPane.showMessageDialog(null, "Ne mozes da pravis novi cvor iz strane", "Greska", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			NodeFactory factory = returnNodeType(obj);
			
			obj.add(factory.createNode(obj));
			
			if(obj instanceof Workspace)
			{
				((Workspace)obj).setChanged(true);
			}
			if(obj instanceof Project)
			{
				((Project)obj).setChanged(true);
			}
			
			SwingUtilities.updateComponentTreeUI(MainWindow.getInstance().getWorkspaceTree());
		}
	}
	
	private static NodeFactory returnNodeType(DefaultMutableTreeNode obj)
	{
		if(obj instanceof Workspace)
		{
			return new ProjectNodeFactory();
		}
		else if(obj instanceof Project)
		{
			return new DocumentNodeFactory();
		}
		else if(obj instanceof Document)
		{
			return new PageNodeFactory();
		}
		
		return null;
	}
}
