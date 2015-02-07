package com.game.barrelrace;

/**
 * Data Model to store a player's high score.
 * @author Prashanth Govindaraj - pxg142030
 *
 */
public class HighScore {
	String name;
	long milliSeconds;
	
	public HighScore(String name,long l) {
		// TODO Auto-generated constructor stub
		this.name=name;
		this.milliSeconds=l;
	}
	public String getName() {
		return name;
	}
	public long getMilliSeconds() {
		return milliSeconds;
	}
}
