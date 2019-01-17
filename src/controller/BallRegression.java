package controller;

import javafx.animation.AnimationTimer;
import model.BreakOut;

/**
 * Wenn der ball vom Schlaeger beschleunigt ist, muss die Geschwindigkeit des
 * Balls hier wieder nach TargetSpeed allmaehlich zurueckgesetzt werden
 * 
 * @author peijie
 *
 */
public class BallRegression extends AnimationTimer {

	BreakOut breakOut;

	public BallRegression(BreakOut breakOut) {
		super();
		this.breakOut = breakOut;
	}

	/**
	 * Immer wieder aufgerufen, solange die Geschwindigkeit hoeher als TargetSpeed
	 * ist setzt die Geschwindigkeit zurueck
	 */
	@Override
	public void handle(long now) {

		if (breakOut.getBall().getSpeed() < breakOut.getBall().getTargetSpeed()
				&& breakOut.getBall().getSpeed() > breakOut.getBall().getTargetSpeed() - 0.5) {
			breakOut.getBall().setSpeed(breakOut.getBall().getTargetSpeed());
			this.stop();

		}

		breakOut.getBall().setSpeed(breakOut.getBall().getSpeed() - 0.1);
	}

}
