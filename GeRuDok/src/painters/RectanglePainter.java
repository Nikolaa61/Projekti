package painters;

import java.awt.geom.GeneralPath;

import elements.DiagramElement;
import elements.RectangleElement;

public class RectanglePainter extends DevicePainter{

	public RectanglePainter(DiagramElement device) {
		super(device);
		RectangleElement rectangle = (RectangleElement) device;

		shape=new GeneralPath();
		
		((GeneralPath)shape).moveTo(rectangle.getPosition().getX(),rectangle.getPosition().getY());
		
		((GeneralPath)shape).lineTo(rectangle.getPosition().getX()+rectangle.getSize().width,rectangle.getPosition().getY());
		
		((GeneralPath)shape).lineTo(rectangle.getPosition().getX()+rectangle.getSize().width,rectangle.getPosition().getY()+rectangle.getSize().height);
		
		((GeneralPath)shape).lineTo(rectangle.getPosition().getX(),rectangle.getPosition().getY()+rectangle.getSize().height);
		
		((GeneralPath)shape).closePath();

		
	}

}
