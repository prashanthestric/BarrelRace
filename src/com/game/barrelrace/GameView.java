package com.game.barrelrace;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import appsrox.example.accelerometer.R;

/**
 * Custom view object that defines the main game subscreen consisting of the horse, barrels and the race course
 * It also manages the accelerometer readings.
 * @author Prashanth Govindaraj - pxg142030
 * @author Kamesh Santhanam - kxs142530
 *
 */
public class GameView extends View implements SensorEventListener {
	
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private Display mDisplay;
	
	private Bitmap mGrass;
	private Bitmap mBarrel;
	private Bitmap mBitmap;
	private Bitmap mYellowRing;
	private Bitmap mGreenRing;
	private Bitmap mDot;
	private Bitmap mShadow;
	private Bitmap mTopFence;
	private Bitmap mSideFence;
	private Bitmap mBottomFence;
	private static int HORSE_SIZE = 100;
	private static int BARREL_SIZE = 120;
	private static int BARREL_SIZE_BY2 = BARREL_SIZE/2;
	private static int HORSE_SIZE_BY2 = HORSE_SIZE/2;
	private static int DOT_SIZE=20;
	private static int FENCE_THICKNESS=32;
	private static int GATE_X1;
	private static int GATE_X2;
	private static int GATE_Y;
	private int penalities=0;
	private int collisioncheck=0;
	
    private float mXOrigin;
    private float mYOrigin;
    
    private float mHorizontalBound;
    private float mVerticalBound;  
    private float layoutWidth;
    private float courseWidth;
    private float courseHeight;
    
    private float mSensorX;
    private float mSensorY;
    private float mSensorZ;
    
    private boolean isGameRunning;
    private boolean isGameComplete;
    private boolean isGamePaused;
    public StopClock stopClock;
    private PositionQueue posQ;
    private Horse mBall = new Horse();
    private ArrayList<Barrel> barrels;
    
    private CollisionEventListener collisionEventListener;
    private GameCompletionListener gameCompletionListener;
    
    /**
     * parameterized Constructors for game view
     * @param context
     */
	public GameView(Context context) {
		super(context);		
        init(context);
	}
	
	public GameView(Context context, AttributeSet attrs){
           super(context, attrs);
           init(context);
    }
	
	public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
	/**
	 * Initializes the accelerometer, stop clock, status flags for game pause and complete.
	 *
	 * @param context
	 */
	private void init(Context context) {
		WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();
        isGameComplete=false;
        isGamePaused=false;
		stopClock=new StopClock();
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);        
        barrels=new ArrayList<Barrel>();
	}
	/**
	 * Set status flag for game paused.
	 * @param paused
	 */
	
	public void setGamePaused(boolean paused){
		isGamePaused=paused;
	}

	public boolean isGamePaused(){
		return isGamePaused;
	}
	/**
	 * Registers the accelerometer, sets the flag of game running true and Starts the stop clock.
	 */
	public void startSimulation() {		
		mSensorManager.registerListener(this, mAccelerometer, 25);
        if(!isGameRunning){
        	stopClock.start();
        }
        isGameRunning=true;
	}
	/**
	 * Unregisters the sensor, sets the flag of game running false and stops the clock.
	 */
	public void stopSimulation() {
		isGameRunning=false;
		mSensorManager.unregisterListener(this);
		stopClock.pause();
	}
	
	/**
	 * Reading the sensor values.
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;
        
        //Log.d("Sensor values",event.values[0]+" "+event.values[1]);
		switch (mDisplay.getRotation()) {
		case Surface.ROTATION_0:
			mSensorX = event.values[0]*10;
			mSensorY = event.values[1]*10;
			break;
		case Surface.ROTATION_90:
			mSensorX = -event.values[1]*10;
			mSensorY = event.values[0]*10;
			break;
		case Surface.ROTATION_180:
			mSensorX = -event.values[0]*10;
			mSensorY = -event.values[1]*10;
			break;
		case Surface.ROTATION_270:
			mSensorX = event.values[1]*10;
			mSensorY = -event.values[0]*10;
			break;
		}
		mSensorZ = event.values[2]*10;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}
	
	/**
	 * Initializes the dimensions of all view elements like barrels, horse, fences, etc with respect to screen size.
	 * 
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {  

		mXOrigin = 0;
        mYOrigin = h; 
        layoutWidth=w;
        
        HORSE_SIZE=(int)w/12;
        HORSE_SIZE_BY2=(int)HORSE_SIZE/2;
        BARREL_SIZE=(int)w/9;
        BARREL_SIZE_BY2=(int)BARREL_SIZE/2;
        DOT_SIZE=(int)w/54;
        FENCE_THICKNESS=(int)(w/34);
        GATE_X1=(int)(w/2-(HORSE_SIZE*2));
        GATE_X2=(int)(w/2+(HORSE_SIZE*2));
        GATE_Y=(int)(0.85*h);
        mBall.dt=(float)w/4000.0f;        
        
        courseWidth=w-(FENCE_THICKNESS);
        courseHeight=(float) (h-((FENCE_THICKNESS*2)+(0.1*h)));
        mHorizontalBound = w-HORSE_SIZE_BY2;
        mVerticalBound = h-HORSE_SIZE_BY2;

        Bitmap grass = BitmapFactory.decodeResource(getResources(), R.drawable.grass);
        mGrass= Bitmap.createScaledBitmap(grass, w, h, true);
        
        Bitmap topFence = BitmapFactory.decodeResource(getResources(), R.drawable.fencetop);
        mTopFence= Bitmap.createScaledBitmap(topFence, w, FENCE_THICKNESS, true);
        
        Bitmap sideFence = BitmapFactory.decodeResource(getResources(), R.drawable.fenceside);
        mSideFence= Bitmap.createScaledBitmap(sideFence, FENCE_THICKNESS, GATE_Y, true);
        
        Bitmap bottomFence = BitmapFactory.decodeResource(getResources(), R.drawable.fencebottomhalf);
        mBottomFence= Bitmap.createScaledBitmap(bottomFence, GATE_X1, FENCE_THICKNESS, true);
        
        Bitmap ball = BitmapFactory.decodeResource(getResources(), R.drawable.horse);
        mBitmap = Bitmap.createScaledBitmap(ball, HORSE_SIZE, HORSE_SIZE, true);
        
        Bitmap hole = BitmapFactory.decodeResource(getResources(), R.drawable.hole);
        mBarrel = Bitmap.createScaledBitmap(hole, BARREL_SIZE, BARREL_SIZE, true);
        
        Bitmap shadow = BitmapFactory.decodeResource(getResources(), R.drawable.shadow);
        mShadow = Bitmap.createScaledBitmap(shadow, (int)(BARREL_SIZE*1.2), (int)(BARREL_SIZE*1.2), true);
                
        Bitmap yring = BitmapFactory.decodeResource(getResources(), R.drawable.yellowring);
        mYellowRing = Bitmap.createScaledBitmap(yring, BARREL_SIZE, BARREL_SIZE, true);
        
        Bitmap gring = BitmapFactory.decodeResource(getResources(), R.drawable.greenring);
        mGreenRing = Bitmap.createScaledBitmap(gring, BARREL_SIZE, BARREL_SIZE, true);
        
        Bitmap dot = BitmapFactory.decodeResource(getResources(), R.drawable.dot);
        mDot = Bitmap.createScaledBitmap(dot, DOT_SIZE, DOT_SIZE, true);
        
		barrels.add(new Barrel(0,(courseWidth/4), (2.2f*courseHeight/3), BARREL_SIZE,courseHeight/2,0,courseHeight,courseWidth/2));
		barrels.add(new Barrel(1,(3*courseWidth/4), (2.2f*courseHeight/3), BARREL_SIZE,courseHeight/2,courseWidth/2,courseHeight,courseWidth));
		barrels.add(new Barrel(2,(courseWidth/2), (0.8f*courseHeight/3), BARREL_SIZE,0,0,courseHeight/2,courseWidth));
		
		posQ=new PositionQueue(mXOrigin-HORSE_SIZE_BY2, mYOrigin-HORSE_SIZE_BY2);
		
	}
	
	/**
	 * Draws all the view elements and update the view according to the player's progress.
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);		
		
		canvas.drawBitmap(mGrass, 0, 0, null);
		drawFences(canvas);
		drawBarrels(canvas);
		if(isGameRunning&&!isGamePaused){
			MainActivity.stopClockText.setText(""+ (new SimpleDateFormat("mm:ss:SSS")).format(new Date(stopClock.getElapsedTimeMillis())));
		}
		if(!isGamePaused){
			mBall.updatePosition(mSensorX, mSensorY, mSensorZ);
			mBall.resolveCollisionWithBounds(mHorizontalBound, mVerticalBound, HORSE_SIZE_BY2,FENCE_THICKNESS,layoutWidth,mYOrigin);
			posQ.enQueue((mXOrigin - HORSE_SIZE_BY2) + mBall.mPosX, (mYOrigin - HORSE_SIZE_BY2) - mBall.mPosY);
		}
		checkTraversalAndDrawFeedback(canvas, posQ);
        if(pitStopVisit(GATE_Y, posQ)==1 && completedAllCircuits()){	//completed!!
        	if(!isGameComplete)
        		gameCompletionListener.onGameComplete(stopClock.getElapsedTimeMillis());
        	stopClock.pause();
        	isGameComplete=true;
        	canvas.drawBitmap(mBitmap, posQ.currentX, posQ.currentY, null);
        	mSensorManager.unregisterListener(this);
        	Log.d("Done", "Game Complete!!!!!!!!!!!!pitstop visit="+pitStopVisit(GATE_Y, posQ));
        }
        getNoOfPenalities();
        //As of now, collision with barrel leads to losing even after completing all 3 rounds. Is that okay??
        if(collidedWithBarrel()){
        	stopClock.pause();
        	canvas.drawBitmap(mBitmap, posQ.currentX, posQ.currentY, null);
        	mSensorManager.unregisterListener(this);
        	GameView.this.collisionEventListener.onCollision();
        }
        else if(!isGameComplete){
        	canvas.drawBitmap(mBitmap, posQ.currentX, posQ.currentY, null);
        	invalidate();
        }
	}
	
	/** 
	 * To find the no. of penalities incurred during the ride.
	 * @return
	 */
	private int getNoOfPenalities(){
		if(mBall.collided()){
			collisioncheck++;
		}
		else{
			collisioncheck=0;
		}
		
		if(collisioncheck==1){
			Log.d("penality","penality!!!!!!!!!!!!!!!!!!!!!!");
			stopClock.addPenalty();
			penalities++;
		}
		return penalities;
	}

	/**
	 * Draws the fences
	 * @param canvas
	 */
	private void drawFences(Canvas canvas) {
		canvas.drawBitmap(mTopFence, 0, 0, null);
		canvas.drawBitmap(mSideFence, 0, 0, null);
		canvas.drawBitmap(mSideFence, layoutWidth-FENCE_THICKNESS, 0, null);
		canvas.drawBitmap(mBottomFence, 0, GATE_Y, null);
		canvas.drawBitmap(mBottomFence, GATE_X2, GATE_Y, null);
	}
	/**
	 * Draws the barrel
	 * @param canvas
	 */
	void drawBarrels(Canvas canvas){		
		for(int i=0;i<barrels.size();i++){
			canvas.drawBitmap(mBarrel, barrels.get(i).centreX-BARREL_SIZE_BY2, barrels.get(i).centreY-BARREL_SIZE_BY2, null);
			canvas.drawBitmap(mShadow, barrels.get(i).centreX-BARREL_SIZE_BY2, barrels.get(i).centreY-BARREL_SIZE_BY2, null);			
		}		
	}
	/**
	 * To check if the player has completed circling all the barrels.
	 * @return
	 */
	boolean completedAllCircuits(){		
		int completedBarrels=0;
		for(int i=0;i<barrels.size();i++){
			if(barrels.get(i).isComplete){
				completedBarrels++;
			}			
		}		
		if(completedBarrels==barrels.size()){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Checks the completion status of circling each barrel and draws feedback to indicate the progress to the player.
	 * @param canvas
	 * @param posQ
	 */
	void checkTraversalAndDrawFeedback(Canvas canvas,PositionQueue posQ){		
		for(int i=0;i<barrels.size();i++){
			Barrel b=barrels.get(i);
			b.checkTraversal(posQ.currentX, posQ.currentY, posQ.lastX, posQ.lastY);
			b.drawFeedback(canvas, mYellowRing, mGreenRing, mDot, DOT_SIZE, posQ.currentX, posQ.currentY);
		}
	}
	
	/**
	 * Checks if the horse has collided with a barrel.
	 * @return
	 */
	boolean collidedWithBarrel(){
		double x=mXOrigin+mBall.mPosX;
		double y=mYOrigin-mBall.mPosY;
		int overlaps=0;
		for(int i=0;i<barrels.size();i++){
			if(circleOverlap(x,y,barrels.get(i).centreX, barrels.get(i).centreY))
				overlaps++;
		}		
		if(overlaps>0)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if the bounding circles of the horse and a barrel overlap.
	 * @param x
	 * @param y
	 * @param a
	 * @param b
	 * @return
	 */
	boolean circleOverlap(double x,double y,double a,double b){
		double r=(double)BARREL_SIZE_BY2+HORSE_SIZE_BY2;
		if((x-a)*(x-a)+(y-b)*(y-b)-(r*r)<0){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Used to check if the horse has entered or exited the gate.
	 * @param pitYbound
	 * @param posQ
	 * @return
	 */
	int pitStopVisit(float pitYbound,PositionQueue posQ){		
		if(posQ.currentY-posQ.lastY>0 && (posQ.lastY<pitYbound && pitYbound<posQ.currentY)){
			return 1;
		}
		else if(posQ.currentY-posQ.lastY<0 && (posQ.lastY>pitYbound && pitYbound>posQ.currentY)){
			return -1;
		}
		else{
			return 0;
		}
	}
	
	public void setCollisionEventListener(CollisionEventListener collisionListener){
		collisionEventListener=collisionListener;
	}
	
	public void setGameCompletionListener(GameCompletionListener gcl){
		gameCompletionListener=gcl;
	}
}
