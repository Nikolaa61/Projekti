package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import gerudok.model.workspace.DiagramElementSelection;
import gerudok.model.workspace.Document;
import gerudok.model.workspace.DocumentSelection;
import gerudok.model.workspace.KlipbordDeljenja;
import gui.MainWindow;
import view.DocumentView;
import view.PageView;

public class ShareDocumentAction extends AbstractGEDAction{

	public ShareDocumentAction() {
		
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("deliDokument.png"));
		putValue(NAME,"Deli/Kopiraj dokument");
		putValue(SHORT_DESCRIPTION, "Deli dokument");
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (MainWindow.getInstance().getWorkspaceTree().isSelectionEmpty()) {
			return;
		}

		if ((PageView) ((DocumentView)MainWindow.getInstance().getCenterPane().getTabbedPane().getSelectedComponent()).getSelectedFrame()==null) {
			return;
		}
		
		Object obj = MainWindow.getInstance().getWorkspaceTree().getLastSelectedPathComponent();
		
		if (obj instanceof Document) {
			DocumentSelection docsel = new DocumentSelection((Document)obj);
			KlipbordDeljenja.getInstance().getClipboard().setContents(docsel, KlipbordDeljenja.getInstance());
			System.out.println("Kopirano");
		}
	}
	
}
