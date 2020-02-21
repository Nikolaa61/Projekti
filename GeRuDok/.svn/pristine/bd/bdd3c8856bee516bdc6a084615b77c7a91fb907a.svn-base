package view;

import java.awt.Component;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import gerudok.model.workspace.Document;
import gerudok.model.workspace.Page;
import gerudok.model.workspace.Project;
import gerudok.model.workspace.Workspace;

public class WorkspaceTreeCellRendered extends DefaultTreeCellRenderer
{
	public WorkspaceTreeCellRendered()
	{
		
	}
	
	 public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, 
			 							boolean expanded, boolean leaf, int row, boolean hasFocus) 
	 {
		 super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
                  
             
         if(value instanceof Document) 
         {
        	 URL imageURL = getClass().getResource("document.png");
             Icon icon = null;
             
             if(imageURL != null) 
             {
            	 icon = new ImageIcon(imageURL);
             }
             
             setIcon(icon);
 
         } 
         else if(value instanceof Project)
         {
        	 URL imageURL = getClass().getResource("projectPic.png");
             Icon icon = null;
             
             if(imageURL != null)  
             {
            	 icon = new ImageIcon(imageURL);
             }
                     
             setIcon(icon);
         }
         else if(value instanceof Workspace)
         {
        	 URL imageURL = getClass().getResource("workspace.png");
             Icon icon = null;
             
             if(imageURL != null)  
             {
            	 icon = new ImageIcon(imageURL);
             }
                     
             setIcon(icon);
         }
         else if(value instanceof Page)
         {
        	 URL imageURL = getClass().getResource("page.png");
             Icon icon = null;
             
             if(imageURL != null)  
             {
            	 icon = new ImageIcon(imageURL);
             }
                     
             setIcon(icon);
         }
     
         return this;
	 }
}
