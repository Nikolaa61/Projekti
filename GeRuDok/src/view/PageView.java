package view;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import commands.CommandManager;
import elements.CircleElement;
import elements.DiagramDevice;
import elements.DiagramElement;
import elements.RectangleElement;
import elements.TriangleElement;
import gerudok.model.workspace.DiagramElementSelection;
import gerudok.model.workspace.Page;
import gui.MainWindow;
import observer.IListener;
import painters.CirclePainter;
import painters.ElementPainter;
import painters.RectanglePainter;
import painters.TrianglePainter;
import state.StateManager;


public class PageView extends JInternalFrame implements IListener, Cloneable {	
	static int openFrameCountPage = 0;
	
	static final int xOffsetPage = 40;
	static final int yOffsetPage = 40;
	
	private Page page;
	private String name;
	
	private JPanel framework;
	
	StateManager stateManager = new StateManager(this);
	
	//tacka koja nam za sada sluzi za lasso
	private Point2D lastPosition=null;
	private Rectangle2D selectionRectangle=new Rectangle2D.Double();
	
	
	private CommandManager commandManager = new CommandManager();

	private AffineTransform transformation = new AffineTransform();
	
	public static final int OR=1;
	public static final int AND=2;
	public static final int INPUT=3;
	public static final int CIRCLE=4;
	public static final int RECTANGLE=5;
	public static final int TRIANGLE=6; /// ja sam dodao
	
	public enum Direction{
		Up, Down, Left, Right
	}
	
	static final int handleSize = 7;
	
	public PageView(Page pageModel) {
		
		super("", true, false, true, true);
		
		this.page = pageModel;
		this.page.addObserver(this);
		this.name = page.getDocument().getName() + " -> " + page.getName();
		
		framework = new Framework();
		framework.setCursor(new Cursor(Cursor.HAND_CURSOR));
		framework.setBackground(Color.WHITE);
		getContentPane().add(framework,BorderLayout.CENTER);

		PageController c=new PageController();
		framework.addMouseListener(c);
		framework.addMouseMotionListener(c);
		
		setTitle(name);
		openFrameCountPage += 1;
		setLocation(xOffsetPage*openFrameCountPage, yOffsetPage);
		setIconifiable(true);
		setSize(500,200);
 	    setVisible(true);
	}
	
	public Page getPage() 
	{
		return page;
	}	
	
	public String getName() {
		return name;
	}
	
	public enum Handle {
		North, South, East, West, SouthEast, SouthWest, NorthEast, NorthWest
	}
	

	@Override
	public void update(Object event) 
	{
		if(event instanceof String)
		{
			setTitle(page.getDocument().getName() + " -> " + (String)event.toString());
		}
		if(event instanceof DiagramElement)
		{
			repaint();
		}
		if(event instanceof DiagramDevice)
		{
			repaint();
		}
	}
	
	private class PageController extends MouseAdapter implements MouseMotionListener{

		public void mousePressed(MouseEvent e) 
		{
			   lastPosition = e.getPoint();
				//System.out.println(page + " " + page.getStateManager() + " " + page.getStateManager().getCurrentState());
			   getStateManager().getCurrentState().mousePressed(e);
		}

		public void mouseReleased(MouseEvent e) {
			   getStateManager().getCurrentState().mouseReleased(e);
		}
		
		
		public void mouseDragged(MouseEvent e ){
			   getStateManager().getCurrentState().mouseDragged(e);
		}
		
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
			
			paintSelectionHandles(g2);
			
			repaint();
			
			//iscrtavanje pravougaonika za lasso
			g2.setPaint(Color.BLACK);
			g2.setStroke(new BasicStroke((float)1, BasicStroke.CAP_SQUARE, 
					BasicStroke.JOIN_BEVEL, 1f, new float[]{(float)3, (float)6}, 0 ));
			g2.draw(selectionRectangle);
		}
	}
	
	
	private void paintSelectionHandles(Graphics2D g2) {
		
/**		U ovoj metodi prolazi se kroz sve selektovane elemente i za
//		svaki selektovani element se iscrtava isprekidani pravougaonik oko elementa i za svaki od 8
//		Handl-ova poziva se metoda paintSelectionHandle() koja iscrtava puni pravougaonik na mestu
		zadatog Handl-a
*/		
		Iterator<DiagramDevice> it = page.getSelectionModel().getSelectionListIterator();
		while(it.hasNext()){
			DiagramElement element =  it.next();
			if (element instanceof DiagramDevice){
				DiagramDevice device=(DiagramDevice)element;
				// Iscrtavanje pravougaonika sa isprekidanom linijom
				g2.setStroke(new BasicStroke((float)1, BasicStroke.CAP_SQUARE, 
						BasicStroke.JOIN_BEVEL, 1f, new float[]{3f, 6f}, 0 ));
				g2.setPaint(Color.BLACK);
			
				g2.drawRect((int)device.getPosition().getX(), (int)device.getPosition().getY(),
						(int)device.getSize().getWidth(), (int)device.getSize().getHeight());
			
				// 	Iscrtavanje hendlova
				for(Handle e: Handle.values()){
					paintSelectionHandle(g2, getHandlePoint(device.getPosition(), device.getSize(), e));
				}
			
			
			}else {
				//isrtavanje handlova za link

			}

		}
	}
	
	private void paintSelectionHandle(Graphics2D g2, Point2D position){
		double size = handleSize;
		g2.fill(new Rectangle2D.Double(position.getX()-size/2, position.getY()-size/2, 
				size, size));	
	}
	
	private Point2D getHandlePoint(Point2D topLeft, Dimension2D size, Handle handlePosition ){
		double x=0, y=0;
		
		// DoreÄ‘ivanje y koordinate
		
		// Ako su gornji hendlovi
		if(handlePosition == Handle.NorthWest || handlePosition == Handle.North || handlePosition == Handle.NorthEast){
			y = topLeft.getY();
		}
		// Ako su centralni po y osi
		if(handlePosition == Handle.East || handlePosition == Handle.West){
			y = topLeft.getY()+size.getHeight()/2;
		}
		//Ako su donji
		if(handlePosition == Handle.SouthWest || handlePosition == Handle.South || handlePosition == Handle.SouthEast){
			y = topLeft.getY() + size.getHeight();
		}

		// OdreÄ‘ivanje x koordinate
		
		// Ako su levi
		if(handlePosition == Handle.NorthWest || handlePosition == Handle.West || handlePosition == Handle.SouthWest){
			x = topLeft.getX();
		}
		// ako su centralni po x osi
		if(handlePosition == Handle.North || handlePosition == Handle.South){
			x = topLeft.getX() + size.getWidth()/2;
		}
		// ako su desni
		if(handlePosition == Handle.NorthEast || handlePosition == Handle.East || handlePosition == Handle.SouthEast){
			x = topLeft.getX() + size.getWidth();
		}
		
		return new Point2D.Double(x,y);
		
	}
	
	public void setMouseCursor(Point2D point){
		
		Handle handle = getDeviceAndHandleForPoint(point);

		if(handle != null){
			Cursor cursor = null;
			
			switch(handle){
			case North: cursor = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);break;
			case South: cursor = Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);break;
			case East: cursor = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);break;
			case West: cursor = Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);break;
			case SouthEast: cursor = Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);break;
			case NorthWest: cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);break;
			case SouthWest: cursor = Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);break;
			case NorthEast: cursor = Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);break;
			}
			framework.setCursor(cursor);
		}
		else
			framework.setCursor(Cursor.getDefaultCursor());
	}
	
	public Handle getDeviceAndHandleForPoint(Point2D point){
		DiagramElement element;
		
		Iterator<DiagramDevice> it = page.getSelectionModel().getSelectionListIterator();
		while(it.hasNext()){
			element = it.next();
			return getHandleForPoint(element, point);
		}
		return null;
	}
	
	/**
	 * Za zadatu taÄ�ku i ureÄ‘aj vraÄ‡a hendl.
	 * @param device
	 * @param point
	 * @return Hendl ukoliko je "pogoÄ‘en", u suprotnom vraÄ‡a null
	 */
	private Handle getHandleForPoint(DiagramElement element, Point2D point){
		for(Handle h: Handle.values()){
			if(isPointInHandle(element, point, h)){
				return h;
			}
		}
		return null;
	}
	
	/**
	 * Za zadati ureÄ‘aj, taÄ�ku i hendl odreÄ‘uje da li je taÄ�ka unutar hendla
	 * @param device
	 * @param point
	 * @param handle
	 * @return
	 */
	private boolean isPointInHandle(DiagramElement element, Point2D point, Handle handle){
		if (element instanceof DiagramDevice){
			DiagramDevice device=(DiagramDevice)element;
			Point2D handleCenter = getHandlePoint(device.getPosition(), device.getSize(), handle);
			return ( (Math.abs(point.getX()-handleCenter.getX())<=(double)handleSize/2) && 
					(Math.abs(point.getY()-handleCenter.getY())<=(double)handleSize/2) );
		}else 
			return false;
	}

	public void paste ()
	{
		Transferable clipboardContent = MainWindow.getInstance().getClipboard().getContents(MainWindow.getInstance()); 
		
		if((clipboardContent != null) && (clipboardContent.isDataFlavorSupported(DiagramElementSelection.elementFlavor))) 
		{
			try 
			{
				DiagramDevice device = null;
				ArrayList<DiagramDevice> tempElements = (ArrayList<DiagramDevice>) clipboardContent.getTransferData(DiagramElementSelection.elementFlavor);					
		 		for(int i=0;i<tempElements.size();i++)
		 		{
		 			if(tempElements.get(i) instanceof DiagramDevice)
		 			{
		 				device=(DiagramDevice) tempElements.get(i).clone();
			 			Point2D newLocation=(Point2D) device.getPosition().clone();
			 			newLocation.setLocation(device.getPosition().getX()+40,device.getPosition().getY()+40);
			 			device.setPosition(newLocation);
			 			
			 			if(device instanceof CircleElement)
			 			{
			 				device.setElementPainter(new CirclePainter(device));
			 			}
			 			else if(device instanceof RectangleElement)
			 			{
			 				device.setElementPainter(new RectanglePainter(device));
			 			}
			 			else if(device instanceof TriangleElement)
			 			{
			 				device.setElementPainter(new TrianglePainter(device));
			 			}
			 			
			 			page.addDiagramElement(device);
			 			
			 			System.out.println("Paste Completed");
		 			}
		 		}
			}
			catch (Exception ex) 
			{
		 		ex.printStackTrace ();
		 	}
		}
	}
	
//	public PageView clone()
//	{
//		return new PageView(this.getPage());
//	}
	
	public Object clone()
	{  
	    try
	    {  
	        return super.clone();  
	    }
	    catch(Exception e)
	    { 
	        return null; 
	    }
	}
	
	public CommandManager getCommandManager() {
		return commandManager;
	}

	public void setCommandManager(CommandManager commandManager) {
		this.commandManager = commandManager;
	}
	
	public void startCircleState() {
		stateManager.setCircleState();
	}

    public void startSelectState() {
    	stateManager.setSelectState();
	}
    
    public void startRectangleState(){
    	stateManager.setRectangleState();
    }
    
    public void startResizeState(){
    	stateManager.setResizeState();
    }
	
    public void startRotateState()
    {
    	stateManager.setRotateState();
    }
    
	public StateManager getStateManager() {
		return stateManager;
	}

	public void setStateManager(StateManager stateManager) {
		this.stateManager = stateManager;
	}
	
	public void startTriangleState() {
		stateManager.setTriangleState();
	}
	public Point2D getLastPosition() {
		return lastPosition;
	}
	public void setSelectionRectangle(Rectangle2D selectionRectangle) {
		this.selectionRectangle = selectionRectangle;
	}

	public void startLassoState() {
		stateManager.setLassoState();
		
	}

	public void startMoveState() {
		stateManager.setMoveState();
		
	}
	
}
