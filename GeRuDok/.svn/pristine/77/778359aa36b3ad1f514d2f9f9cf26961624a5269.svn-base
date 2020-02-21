package elements;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.io.Serializable;

import painters.ElementPainter;
import serijalizacija.SerializableStrokeAdapter;

public abstract class DiagramElement implements Serializable
{
	protected Paint paint;
	protected SerializableStrokeAdapter stroke;
	protected Color strokeColor;
	protected double rotate;
	
	protected String name;
	protected String description;

	protected ElementPainter elementPainter;
	
	protected SlotType slotType;
	
	public DiagramElement(Stroke stroke, Paint paint, Color strokeColor){
		setStroke(stroke);
		this.paint = paint;
		this.strokeColor = strokeColor;
	}
	/**Copy konstruktor klase

	 * 
	 */
	public DiagramElement(DiagramElement element){
		this.stroke=element.stroke;
		this.paint=element.paint;
		this.strokeColor=element.strokeColor;
		this.name=element.name;
		this.description=element.description;
		this.elementPainter=element.elementPainter;
		this.rotate = 0;
	}
	
	abstract public DiagramElement clone();
	
	public Color getStrokeColor() {
		return strokeColor;
	}

	public String getDescription() {
		return description;
	}

	public ElementPainter getElementPainter() {
		return elementPainter;
	}

	public SlotType getSlotType() {
		return slotType;
	}
	public void setSlotType(SlotType slotType) {
		this.slotType = slotType;
	}
	
	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}



	public void setDescription(String description) {
		this.description = description;
	}

	public double getRotate() {
		return rotate;
	}
	public void setRotate(double rotate) {
		this.rotate = rotate;
	}
	
	public void setElementPainter(ElementPainter elementPainter) {
		this.elementPainter = elementPainter;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ElementPainter getPainter() {
		return elementPainter;
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public Stroke getStroke() {
		return stroke;
	}

	public void setStroke(Stroke stroke) {
		this.stroke = new SerializableStrokeAdapter(stroke);
	}

}
