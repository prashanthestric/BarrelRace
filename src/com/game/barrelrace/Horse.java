package com.game.barrelrace;

import android.util.Log;

/**
 * Defines the "horse" object.
 * @author Prashanth Govindaraj - pxg142030
 * @author Kamesh Santhanam - kxs142530
 */
public class Horse {
	public float dt = 0.5f;
    public float mPosX;
    public float mPosY;
    private PositionQueue pq=new PositionQueue(0.0f, 0.0f);
    public boolean collidedT=false;
    public boolean collidedL=false;
    public boolean collidedR=false;
    public boolean collidedB=false;
    
    /**
     * To accelerate the horse in the direction and speed denoted by the accelerometer readings.
     * @param sx
     * @param sy
     * @param sz
     * @param timestamp
     */
    public void updatePosition(float sx, float sy, float sz) {    	
    	mPosX-=sx*dt;
    	mPosY-=sy*dt;    	
    }
    
    /**
     * Contains the horse within the specified boundaries and sets flags that indicate collision with fences.
     * @param mHorizontalBound
     * @param mVerticalBound
     */
    public void resolveCollisionWithBounds(float mHorizontalBound, float mVerticalBound, int ballsizeBy2,float fenceThickness,float screenW,float screenH) {

    	int ballSize=ballsizeBy2*2;
    	if(inPartition(screenW, screenH, fenceThickness, ballsizeBy2) && (mPosX<screenW/2-ballSize*2 || mPosX>screenW/2+ballSize*2)){
    		mPosY=pq.lastY;
    		collidedB=true;
    	}
    	else{
    		pq.enQueue(mPosX, mPosY);
    		collidedB=false;
    	}
    	if (mPosX > (mHorizontalBound-fenceThickness)) {
            mPosX = mHorizontalBound-fenceThickness;
            collidedR=true;
        } 
        else if (mPosX < (0+ballsizeBy2+fenceThickness)) {
            mPosX = 0+ballsizeBy2+fenceThickness;
            if(mPosY>((0.1*screenH)+ballsizeBy2)){
            	collidedL=true;
            }
        }
        else{
        	collidedL=false;
        	collidedR=false;
        }
    	if (mPosY > (mVerticalBound-fenceThickness)) {
            mPosY = mVerticalBound-fenceThickness;
            collidedT=true;
        }   	
    	else if (mPosY < (0+ballsizeBy2)) {
            mPosY = 0+ballsizeBy2;
            collidedT=false;
        }
    	else{
    		collidedT=false;
    	}
    	
    }
    
    /**
     * Resolves collision with the fence that contains the gate(in order to allow horse to pass through the gate alone).
     * @param screenW
     * @param screenH
     * @param fenceThickness
     * @param ballsizeBy2
     * @return
     */
    boolean inPartition(float screenW,float screenH,float fenceThickness,int ballsizeBy2){
    	if(mPosY>((0.15*screenH)-fenceThickness-ballsizeBy2) && mPosY<((0.15*screenH)+ballsizeBy2)){
    		return true;
    	}
    	else{
    		return false;
    	}		
    }
    
    /**
     * Returns true if a collision with fence has occurred.
     * @return
     */
    boolean collided(){
    		return collidedL||collidedT||collidedB||collidedR;
    }
}
