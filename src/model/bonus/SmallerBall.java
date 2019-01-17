package model.bonus;

import model.BreakOut;
import model.brick.Brick;

/**
 * Erbt von der Vaterklasee Bonus. Verringert die Groeße des Balls.
 * @author Niklas
 *
 */
public class SmallerBall extends Bonus {

	public SmallerBall(Brick sourceBrick, BreakOut breakOut) {
		super(sourceBrick, breakOut);
	}

	/**
	 * Verringert den Radius des Balls, wenn der Radius groeßer als 5 ist. 
	 */
	@Override
	public void applyBonus() {
		if (breakOut.getBall().getRadius()>5) {
			this.breakOut.getBall().setRadius(breakOut.getBall().getRadius()-5);
		}
	}

	/**
	 * Setzt den Radius des Balles auf den Standartwert zurueck.
	 */
	@Override
	public void reset() {
		this.breakOut.getBall().setRadius(this.breakOut.getBall().getDEFAULT_SIZE());
	}

}
