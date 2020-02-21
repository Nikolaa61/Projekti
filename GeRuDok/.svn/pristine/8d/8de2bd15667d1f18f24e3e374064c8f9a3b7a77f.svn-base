package state;

import java.io.Serializable;

import gerudok.model.workspace.Page;
import view.PageView;

public class StateManager implements Serializable{
	private State currentState;
	
	
	CircleState circleState; 
	RectangleState rectangleState;
	TriangleState triangleState;
	SelectState selectState;
	ResizeState resizeState;
	LassoState lassoState;
	MoveState moveState;
	RotateState rotateState;
	DeleteState deleteState;
	
	public StateManager(PageView med)
	{
		circleState = new CircleState(med); 
		rectangleState=new RectangleState(med);
		triangleState = new TriangleState(med);
     	selectState = new SelectState(med);
     	resizeState = new ResizeState(med);
     	rotateState = new RotateState(med);
     	lassoState = new LassoState(med);
     	moveState = new MoveState(med);
     	deleteState = new DeleteState(med);
     	currentState = selectState;
	}
	
	public void setCircleState() { currentState = circleState; }
	public void setRectangleState(){ currentState = rectangleState; }
	public void setSelectState(){ currentState = selectState; }
	public void setTriangleState() {currentState = triangleState;}
	public void setResizeState() {currentState = resizeState;}
	public void setMoveState() {currentState = moveState;}
	public void setLassoState() {currentState = lassoState;}
	public void setRotateState() {currentState = rotateState;}
	public void setDeleteState() {currentState = deleteState;}
	
	public State getCurrentState() {
		return currentState;
	}
}

