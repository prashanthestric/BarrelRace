package com.game.barrelrace;

/**
 * A FIFO queue of capacity 2, to remember the horse's last position.
 * @author Prashanth Govindarag - pxg142030
 *
 */
public class PositionQueue {
	
	public float currentX,currentY,lastX,lastY;
	
	public PositionQueue(float lx,float ly) {
		// TODO Auto-generated constructor stub
		currentX=0.0f;
		currentY=0.0f;
		lastX=lx;
		lastY=ly;		
	}
	
	public void enQueue(float x,float y){
		lastX=currentX;
		lastY=currentY;
		currentX=x;
		currentY=y;
	}

}
