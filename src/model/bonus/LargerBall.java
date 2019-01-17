package model.bonus;

import model.BreakOut;
import model.brick.Brick;

/**
 * Erbt von der Vaterklasse Bonus. Erhoeht den Radius des Balls.
 * @author Niklas
 *
 */
public class LargerBall extends Bonus {

	public LargerBall(Brick sourceBrick, BreakOut breakOut) {
		super(sourceBrick, breakOut);
	}

	/**
	 * Erhoeht den Radius des Balles, wenn der Radius des Balles unter 25 liegt.
	 */
	@Override
	public void applyBonus() {
		if (breakOut.getBall().getRadius()<25) {
			this.breakOut.getBall().setRadius(breakOut.getBall().getRadius()+5);
		}
	}

	/**
	 * Setzt den Radius des Balles auf den Standartwert zurück.
	 */
	@Override
	public void reset() {
		this.breakOut.getBall().setRadius(this.breakOut.getBall().getDEFAULT_SIZE());
	}

}
