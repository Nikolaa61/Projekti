package painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import elements.DiagramDevice;
import elements.DiagramElement;

public class DevicePainter extends ElementPainter
{
	protected Shape shape;
	
	public DevicePainter(DiagramElement device){
		super(device);
	}
	
	@Override
	public void paint(Graphics2D g, DiagramElement element, double rotation) 
	{
		AffineTransform old = g.getTransform();

		g.setPaint(Color.BLACK);
		g.setStroke(element.getStroke());
		DiagramDevice diagDevice = (DiagramDevice) element;
		g.rotate(-element.getRotate(), diagDevice.getSize().getWidth()/2 + diagDevice.getPosition().getX(),	diagDevice.getSize().getHeight()/2 + diagDevice.getPosition().getY());
		g.draw(getShape());
		g.setPaint(element.getPaint());
		g.rotate(0);
		g.fill(getShape());
		
		if (element instanceof DiagramDevice){
			g.setPaint(Color.BLACK);
			DiagramDevice device=(DiagramDevice )element;
			g.drawString(device.getName(), (int)device.getPosition().getX()+10, 
										   (int)device.getPosition().getY()+10);
		}
		
		g.setTransform(old);
		
	}
	
	public boolean elementAt(DiagramElement element, Point pos){
		return getShape().contains(pos);
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	@Override
	public boolean isElementAt(Point point) {
		return getShape().contains(point);
	}
}
