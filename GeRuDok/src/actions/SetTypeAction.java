package actions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.print.attribute.standard.JobHoldUntil;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import elements.DiagramDevice;
import elements.GraphicsType;
import elements.SlotType;
import elements.TextType;
import gui.GraphicWindow;
import gui.MainWindow;
import gui.TextWindow;
import view.DocumentView;
import view.PageView;

public class SetTypeAction extends AbstractGEDAction{
	public SetTypeAction(){
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		putValue(MNEMONIC_KEY, KeyEvent.VK_X);
		putValue(SMALL_ICON, loadIcon("izaberiTip.png"));
		putValue(NAME, "Izaberi tip elementa");
		putValue(SHORT_DESCRIPTION, "Izaberi tip elementa");
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		final JDialog window = new JDialog();
		JButton btn1 = new JButton("Tekstualni");
		JButton btn2 = new JButton("Graficki");
		
		window.setLayout(new BorderLayout());
		
		window.add(btn1, BorderLayout.WEST);
		window.add(btn2, BorderLayout.EAST);
		
		window.setTitle("Izaberi tip elementa");

		
		window.setSize(200, 200);
		window.setResizable(false);
		window.setLocationRelativeTo(null);	
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		if (MainWindow.getInstance().getWorkspaceTree().isSelectionEmpty()) {
			return;
		}

		if ((PageView) ((DocumentView)MainWindow.getInstance().getCenterPane().getTabbedPane().getSelectedComponent()).getSelectedFrame()==null) {
			return;
		}
		
		PageView pw = (PageView) ((DocumentView)MainWindow.getInstance().getCenterPane().getTabbedPane().getSelectedComponent()).getSelectedFrame();
		
		if(pw.getPage().getSelectionModel().getSelectionList().size() == 1){
			
			if(pw.getPage().getSelectionModel().getSelectionList().get(0).getSlotType() != null){
				TextWindow txt = new TextWindow();
				txt.setText(pw.getPage().getSelectionModel().getSelectionList().get(0).getSlotType().getText());
			}
			else{
				pw.getPage().getSelectionModel().getSelectionList().get(0).setSlotType(new TextType());
				
				btn1.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e){
						TextWindow txt = new TextWindow();
						window.setVisible(false);
					}
				});
				
				btn2.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						GraphicWindow gpW = new GraphicWindow();
						window.setVisible(false);
					}
				});
				window.setVisible(true);
			}
		}
	}
}
