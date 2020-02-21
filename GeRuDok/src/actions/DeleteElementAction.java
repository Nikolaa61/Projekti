package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.KeyStroke;

import commands.AddDeviceCommand;
import commands.DeleteDeviceCommand;
import elements.DiagramDevice;
import elements.DiagramElement;
import gui.MainWindow;
import view.DocumentView;
import view.PageView;

public class DeleteElementAction extends AbstractGEDAction{

	public DeleteElementAction(){
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON,loadIcon("deleteElementIcon.png"));
		putValue(NAME,"");
		putValue(SHORT_DESCRIPTION, "Brise element/slot");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		PageView pw = null;
		
		if (MainWindow.getInstance().getWorkspaceTree().isSelectionEmpty()) {
			return;
		}
		
		if ((PageView) ((DocumentView)MainWindow.getInstance().getCenterPane().getTabbedPane().getSelectedComponent()).getSelectedFrame()==null) {
			return;
		}
		
		pw = (PageView) ((DocumentView)MainWindow.getInstance().getCenterPane().getTabbedPane().getSelectedComponent()).getSelectedFrame();
		
		ArrayList<DiagramDevice> selectedElements = pw.getPage().getSelectionModel().getSelectionList();

		pw.getCommandManager().addCommand(new DeleteDeviceCommand(pw.getPage(),pw.getPage().getSelectionModel(),selectedElements));

	}
	
}
