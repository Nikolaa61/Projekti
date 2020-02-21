package elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import painters.CirclePainter;
import painters.TrianglePainter;

public class TriangleElement extends DiagramDevice
{

	public TriangleElement(Point2D position, Dimension size, Stroke stroke, Paint paint, Color strokeColor) {
		super(position, size, stroke, paint, strokeColor);
		
		elementPainter = new TrianglePainter(this);
	}
	
	public TriangleElement(TriangleElement triangle) 
	{
		super(triangle);
		setName("kopija");
		elementPainter = new TrianglePainter(this);
	}
	
	public static DiagramDevice createDefault(Point2D pos, int elemNo){
	   Point2D position = pos;
		
       Paint fill = Color.WHITE;
       TriangleElement triangleElement = new TriangleElement(position, 
    		   									new Dimension(60, 60),
    		  									new BasicStroke((float)(2), BasicStroke.CAP_SQUARE,BasicStroke.JOIN_BEVEL),
    		   									fill,  
    		   									Color.BLACK);
        triangleElement.setName("Triangle" + elemNo);
		return triangleElement;
	}

	@Override
	public DiagramElement clone() 
	{
		return new TriangleElement(this);
	}	
	
}
