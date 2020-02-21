package view;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Iterator;

import javax.swing.JPanel;

import elements.DiagramDevice;
import elements.DiagramElement;
import gerudok.model.workspace.Page;
import observer.IListener;
import painters.ElementPainter;


public class MiniPageView extends JPanel implements IListener
{
	private Page page;
	
	private JPanel framework;
	
	public MiniPageView(Page page) 
	{
		this.page = page;
		this.page.addObserver(this);
		
		framework = new Framework();
		framework.setCursor(new Cursor(Cursor.HAND_CURSOR));
		framework.setBackground(Color.WHITE);
		
		setLayout(new BorderLayout());
		add(framework, BorderLayout.CENTER);
		
		setSize(10, 10);
	}
	
	private class Framework extends JPanel
	{
		protected void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
			
			Graphics2D g2 = (Graphics2D) g;
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Iterator<DiagramDevice> it = page.getDeviceIterator();
			
			while(it.hasNext())
			{
				DiagramDevice d = (DiagramDevice) it.next();
				ElementPainter painter = d.getPainter();
				painter.paint(g2, d, 0);
			}
			
			repaint();
		}
	}

	@Override
	public void update(Object event) 
	{
		if(event instanceof DiagramElement)
		{
			repaint();
		}
		if(event instanceof DiagramDevice)
		{
			repaint();
		}
		
	}
}
