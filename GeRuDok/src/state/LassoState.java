package state;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import view.PageView;



public class LassoState extends State {
	Rectangle2D rect=new Rectangle2D.Double();
	


	private PageView med; 
	public LassoState(PageView md) {
	med = md;
	}
	
	
	public void mouseDragged(MouseEvent e) {
			Point2D position=e.getPoint();

			if(!e.isControlDown()){

				med.getPage().getSelectionModel().removeAllFromSelectionList();
			}
			
			double width=position.getX()-med.getLastPosition().getX();
			double height=position.getY()-med.getLastPosition().getY();
			if((width<0)&&(height<0)){
				rect.setRect(position.getX(), position.getY(),Math.abs(width),Math.abs(height));
			}
			else if((width<0)&&(height>=0)){
				rect.setRect(position.getX(), med.getLastPosition().getY(),Math.abs(width),Math.abs(height));
			}
			else if((width>0)&&(height<0)){
				rect.setRect(med.getLastPosition().getX(), position.getY(), Math.abs(width),Math.abs(height));
			}
			else{
				rect.setRect(med.getLastPosition().getX(), med.getLastPosition().getY(),Math.abs(width),Math.abs(height));
			}
			med.getPage().getSelectionModel().selectElements(rect, med.getPage().getDiagramElements());
			med.setSelectionRectangle(rect); 
			
			med.repaint();
		
	}

		public void mouseReleased(MouseEvent e) {		
		med.setSelectionRectangle(new Rectangle2D.Double(0,0,0,0));
		med.repaint();
		med.startSelectState();
	}


}
