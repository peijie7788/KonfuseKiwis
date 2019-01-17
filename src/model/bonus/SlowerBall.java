package model.bonus;

import model.BreakOut;
import model.brick.Brick;

/**
 * Erbt von der Vaterklasee Bonus. Macht den Ball langsamer.
 * @author Niklas
 *
 */
public class SlowerBall extends Bonus {

	public SlowerBall(Brick sourceBrick, BreakOut breakOut) {
		super(sourceBrick, breakOut);
	}

	/**
	 * Verringert die aktuelle und die zu erreichende Geschwindigkeit des Balles, falls diese größer als 2 ist. 
	 */
	@Override
	public void applyBonus() {
		if (breakOut.getBall().getSpeed() > 2.0) {
			this.breakOut.getBall().setSpeed(breakOut.getBall().getSpeed() - 2.0);
			this.breakOut.getBall().setTargetSpeed(this.breakOut.getBall().getTargetSpeed()-2.0);
		}
	}

	/**
	 * Setzt die Geschwindigkeit des Balles auf den Stadndartwert zurueck.
	 */
	@Override
	public void reset() {
		this.breakOut.getBall().setSpeed(this.breakOut.getBall().getDefaultSpeed());
	}

}
