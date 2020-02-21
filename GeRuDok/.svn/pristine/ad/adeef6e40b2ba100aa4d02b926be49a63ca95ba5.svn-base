package controler;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import gerudok.model.workspace.Document;

public class WorkspaceTreeControler implements TreeSelectionListener{

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		
		
		TreePath path = e.getPath();
		for (int i=0; i<path.getPathCount(); i++) {
			if (path.getPathComponent(i) instanceof Document) {
				Document d = (Document) path.getPathComponent(i);
			}
		}
		
	}
	
}
