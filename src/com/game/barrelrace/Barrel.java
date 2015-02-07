package com.game.barrelrace;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Defines the position and bounds of a barrel
 * It also checks the progress/course of ride around a barrel  
 * @author Prashanth Govindaraj - pxg142030
 *	
 */
public class Barrel {
	
	int index;
	public float centreX,centreY,top,left,bottom,right,refTx,refTy,refLx,refLy,refBx,refBy,refRx,refRy,inAreaIndX,inAreaIndY;
	public int size;
	public int firstTraversal=-1; //0=top, 1=left, 2=bottom, 3=right
	public boolean flagT=false,flagL=false,flagB=false,flagR=false,isComplete=false;
	
	/**
	 * Used to initialize barrel position and bounds
	 * @param index
	 * @param cx
	 * @param cy
	 * @param s
	 * @param t
	 * @param l
	 * @param b
	 * @param r
	 */
	public Barrel(int index,float cx,float cy,int s,float t,float l,float b,float r) {
		// TODO Auto-generated constructor stub
		centreX=cx;
		centreY=cy;
		size=s;
		top=t;
		left=l;
		bottom=b;
		right=r;
		//coordinates of reference points
		refTx=centreX;
		refTy=centreY-(size/2);
		refLx=centreX-(size/2);
		refLy=centreY;
		refBx=centreX;
		refBy=centreY+(size/2);
		refRx=centreX+(size/2);
		refRy=centreY;
	}
	/**
	 * To check if the horse is within particular area around the barrel whose bounds are defined in the constructor
	 * @param x
	 * @param y
	 * @return
	 */
	public int inArea(float x,float y){
		if(x>left&&x<right&&y>top&&y<bottom)
			return index;
		else
			return -1;
	}
	/**
	 * To check whether the horse has crossed the checkpoints placed around the barrel
	 * @param x
	 * @param y
	 * @param lastX
	 * @param lastY
	 */
	
	public void checkTraversal(float x,float y,float lastX,float lastY){
		
		if(inArea(x, y)>-1){  //is it in area of this barrel
			
			if(isInBetween(centreX, x, lastX)&&(y<centreY)){	//top point crossing
				if(firstTraversal!=1&&isComplete==false){
					flagT=!flagT;
				}
				if(firstTraversal==-1){
					firstTraversal=1;
				}
			}
			
			if(isInBetween(centreY, y, lastY)&&(x<centreX)){	//left point crossing
				if(firstTraversal!=2&&isComplete==false){
					flagL=!flagL;
				}
				if(firstTraversal==-1){
					firstTraversal=2;
				}
			}
			
			if(isInBetween(centreX, x, lastX)&&(y>centreY)){	//bottom point crossing
				if(firstTraversal!=3&&isComplete==false){
					flagB=!flagB;
				}
				if(firstTraversal==-1){
					firstTraversal=3;
				}
			}
			
			if(isInBetween(centreY, y, lastY)&&(x>centreX)){	//right point crossing
				if(firstTraversal!=4&&isComplete==false){
					flagR=!flagR;
				}
				if(firstTraversal==-1){
					firstTraversal=4;
				}
			}

			isComplete=flagT&&flagL&&flagB&&flagR;
		}
		else{		//going out of this barrel's area without completing a round
			if(!isComplete)
				flagT=flagL=flagB=flagR=false;
				firstTraversal=-1;
		}
	}
	
	/**
	 * Updates the UI to inform the player's progress around a barrel.
	 * It requires the size of each UI element and a Canvas to be passed from the calling View object.
	 * @param canvas
	 * @param mYellowRing
	 * @param mGreenRing
	 * @param mDot
	 * @param dotSize
	 * @param x
	 * @param y
	 */
	
	public void drawFeedback(Canvas canvas,Bitmap mYellowRing,Bitmap mGreenRing,Bitmap mDot,int dotSize,float x,float y){
		
		if(inArea(x, y)>-1){
			canvas.drawBitmap(mYellowRing, centreX-(size/2), centreY-(size/2), null);
		}
		if(flagT){
			canvas.drawBitmap(mDot, refTx-(dotSize/2), refTy-(dotSize/2), null);
		}
		if(flagL){
			canvas.drawBitmap(mDot, refLx-(dotSize/2), refLy-(dotSize/2), null);
		}
		if(flagB){
			canvas.drawBitmap(mDot, refBx-(dotSize/2), refBy-(dotSize/2), null);
		}
		if(flagR){
			canvas.drawBitmap(mDot, refRx-(dotSize/2), refRy-(dotSize/2), null);
		}
		if(isComplete){
			canvas.drawBitmap(mGreenRing, centreX-(size/2), centreY-(size/2), null);
		}
	}

	boolean isInBetween(float value,float a,float b){
		float lower,upper;
		if(a>b){
			lower=b;
			upper=a;
			}
		else{
			lower=a;
			upper=b;
		}
		
		if((value>lower)&&(value<upper)){
			return true;
		}
		else{
			return false;
		}
	}
}
