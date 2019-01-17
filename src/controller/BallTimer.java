package controller;

import javafx.animation.AnimationTimer;
import model.BreakOut;

/**
 * Timer, der den Ball bewegt
 * @author peijie
 *
 */
public class BallTimer extends AnimationTimer {

	private BreakOut breakOut;

	/**
	 * Konstruktor des BallTimers
	 * 
	 * @param breakOut
	 */
	public BallTimer(BreakOut breakOut) {
		this.breakOut = breakOut;
	}

	/**
	 * handle, wird solange der BallTimer läuft, immer wieder aufgerufen und ruft
	 * jedes mal die Bewegungsmethode moveBall1 des Balles auf um diesen zu bewegen.
	 */
	@Override
	public void handle(long now) {

		if (!breakOut.getBall().isSticked()) {
			breakOut.getBall().moveBall1();
		} else {
			breakOut.getBall().followBat();
		}
	}

}
