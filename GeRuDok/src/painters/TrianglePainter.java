package painters;

import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import elements.DiagramElement;
import elements.TriangleElement;

public class TrianglePainter extends DevicePainter
{

	public TrianglePainter(DiagramElement device) {
		super(device);
		
		TriangleElement triangle = (TriangleElement)device;
		
		shape = new GeneralPath();
		
		((GeneralPath)shape).moveTo(triangle.getPosition().getX(),triangle.getPosition().getY() + triangle.getSize().height);
		
		((GeneralPath)shape).lineTo(triangle.getPosition().getX() + triangle.getSize().width/2
				, triangle.getPosition().getY() - triangle.getSize().height/3);
		
		((GeneralPath)shape).lineTo(triangle.getPosition().getX() + triangle.getSize().width
				, triangle.getPosition().getY() + triangle.getSize().height);
		
		((GeneralPath)shape).closePath();
	}
}
