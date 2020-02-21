package painters;

import java.awt.geom.GeneralPath;

import elements.CircleElement;
import elements.DiagramElement;

public class CirclePainter extends DevicePainter
{

	public CirclePainter(DiagramElement device) {
		super(device);
		CircleElement or = (CircleElement) device;

		shape=new GeneralPath();
		
		((GeneralPath)shape).moveTo(or.getPosition().getX()+or.getSize().getWidth()/2,or.getPosition().getY());
		
		((GeneralPath)shape).quadTo(or.getPosition().getX()+or.getSize().getWidth(), or.getPosition().getY(), 
									or.getPosition().getX()+or.getSize().getWidth(), or.getPosition().getY()+or.getSize().getHeight()/2);
		
		((GeneralPath)shape).quadTo(or.getPosition().getX()+or.getSize().getWidth(), or.getPosition().getY()+or.getSize().getHeight(),
									or.getPosition().getX()+or.getSize().getWidth()/2, or.getPosition().getY()+or.getSize().getHeight());
		
		((GeneralPath)shape).quadTo(or.getPosition().getX(), or.getPosition().getY()+or.getSize().getHeight(),
				or.getPosition().getX(), or.getPosition().getY()+or.getSize().getHeight()/2);


		((GeneralPath)shape).quadTo(or.getPosition().getX(), or.getPosition().getY(),
				or.getPosition().getX()+or.getSize().getWidth()/2,or.getPosition().getY());

		

		
	}


}
