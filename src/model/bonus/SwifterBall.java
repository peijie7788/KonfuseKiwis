package model.bonus;

import model.BreakOut;
import model.brick.Brick;

/**
 * Erbt von der Vaterklasse Bonus. Erhoeht die Geschwindigkeit des Balls.
 * @author Niklas
 *
 */
public class SwifterBall extends Bonus {

	public SwifterBall(Brick sourceBrick, BreakOut breakOut) {
		super(sourceBrick, breakOut);
	}

	/**
	 * Erhoeht die Geschwindigkeit des Balls, wenn diese kleiner als 10 ist.
	 */
	@Override
	public void applyBonus() {
		if (breakOut.getBall().getSpeed() < 10.0) {
			this.breakOut.getBall().setSpeed(breakOut.getBall().getSpeed() + 2.0);
			this.breakOut.getBall().setTargetSpeed(this.breakOut.getBall().getTargetSpeed());
		}
	}

	/**
	 * Setzt die Geschwindigkeit des Balls auf den Standartwert zurueck.
	 */
	@Override
	public void reset() {
		this.breakOut.getBall().setSpeed(this.breakOut.getBall().getDefaultSpeed());
	}

}
