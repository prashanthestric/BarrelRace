package com.game.barrelrace;

/**
 * Event listener to handle game completion.
 * @author Kamesh Santhanam - kxs142530
 *
 */
public interface GameCompletionListener {
	/**
	 * Override this method with handling code for game completion event.
	 * @param elapsedTime
	 */
	public void onGameComplete(long elapsedTime);
}
