package elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.text.AbstractDocument.Content;

public abstract class DiagramDevice extends DiagramElement{
	protected Dimension size;
	protected Point2D position;
	protected Point2D oldPosition;
	
	public DiagramDevice(Point2D position, Dimension size, Stroke stroke, Paint paint,Color  strokeColor){
		super(stroke, paint, strokeColor);
		this.size = size;
		//ovo omoguÄ‡ava translaciju taÄ�aka tako da se element kreira u centru
		position.setLocation(position.getX()-size.getWidth()/2,position.getY()-size.getHeight()/2);
		this.position = position;
		this.strokeColor=strokeColor;
	}
	
	public DiagramDevice(DiagramDevice device)
	{
		super(device);
		this.size=device.size;
		this.position=device.position;
	}
	
	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Dimension getSize() {
		return size;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}
	
	public Dimension getInitSize(){
		return size;
	}

	public Point getOldPosition() {
		return (Point) oldPosition;
	}

	public void setOldPosition(Point2D oldPosition) {
		this.oldPosition = oldPosition;
	}
}
