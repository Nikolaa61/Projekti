package gui;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;


public class Palette extends JToolBar
{
	public Palette() 
	{
		super(JToolBar.VERTICAL);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		ButtonGroup group = new ButtonGroup();


		JToggleButton btn2=new JToggleButton(MainWindow.getInstance().getActionManager().getpHandCursorAction());
		group.add(btn2);
		add(btn2);
		
		addSeparator();

		JToggleButton btn3=new JToggleButton(MainWindow.getInstance().getActionManager().getRectangleAction());
		group.add(btn3);
		add(btn3);
	
		addSeparator();
		
		JToggleButton btn4=new JToggleButton(MainWindow.getInstance().getActionManager().getCircleAction());
		group.add(btn4);
		add(btn4);

		addSeparator();
		
		JToggleButton btn5=new JToggleButton(MainWindow.getInstance().getActionManager().getTriangleAction());
		group.add(btn5);
		add(btn5);
		
		addSeparator();

		JToggleButton btn6=new JToggleButton(MainWindow.getInstance().getActionManager().getResizeAction());
		group.add(btn6);
		add(btn6);
		
		addSeparator();
		
		JToggleButton btn7=new JToggleButton(MainWindow.getInstance().getActionManager().getMoveAction());
		group.add(btn7);
		add(btn7);
		
		JToggleButton btn8=new JToggleButton(MainWindow.getInstance().getActionManager().getRotateAction());
		group.add(btn8);
		add(btn8);
		
		JToggleButton btn9=new JToggleButton(MainWindow.getInstance().getActionManager().getDeleteElementAction());
		group.add(btn9);
		add(btn9);
	}
}
