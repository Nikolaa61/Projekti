package gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import view.ProjectView;

public class CenterPane extends JPanel
{
	private JScrollPane scroll;
	private ScrollPane scrollMini;

	private TabbedPane tabbedPane;
	
	private JSplitPane splitPane;
	private JSplitPane splitPaneMini;
	
	
	public CenterPane()
	{
		scroll = new JScrollPane(MainWindow.getInstance().getWorkspaceTree());
		
		scrollMini = new ScrollPane();
		
		setLayout(new BorderLayout());
		
		tabbedPane = new TabbedPane();

//		splitPaneMini = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollMini, tabbedPane);
//		splitPaneMini.setDividerLocation(250);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, tabbedPane);
		splitPane.setDividerLocation(150);
		
		add(splitPane, BorderLayout.CENTER);		
	}

	public TabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public ScrollPane getScrollMini() {
		return scrollMini;
	}
}
