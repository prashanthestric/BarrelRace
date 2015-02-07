package com.game.barrelrace;

/**
 * Performs the function of stop clock.
 * @author Kamesh Santhanam - kxs142530
 *
 */
public class StopClock { 

	private long startTime = 0,start;;
	private boolean running = false;
	private long currentTime = 0;

	public void start() {
	    this.startTime = System.currentTimeMillis();
	    this.running = true;
	}

	public void stop() {
	    this.running = false;
	}

	public long pause() {
	    this.running = false;
	    currentTime = System.currentTimeMillis() - startTime;
	    return currentTime;
	}
	public void resume() {
	    this.running = true;
	    this.startTime = System.currentTimeMillis() - currentTime;
	}
	public void addPenalty()
	{
		this.running = false;
	    currentTime = System.currentTimeMillis()- startTime;
	    this.running = true;
	    this.startTime = System.currentTimeMillis() - currentTime-5000;
	}
	/**
	 * Returns the current stop clock reading in milli seconds
	 * @return
	 */
	public long getElapsedTimeMillis() {
	    long elapsed = 0;
	    if (running) {
	         elapsed =((System.currentTimeMillis() - startTime))  ;
	    }
	    return elapsed;
	}
} 