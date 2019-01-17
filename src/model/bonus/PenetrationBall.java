package model.bonus;

import model.BreakOut;
import model.brick.Brick;

/**
 * Erbt von der Vaterklasse Bonus. Lässt den Ball durch Bloecke hindurchfliegen.
 * @author Niklas
 *
 */
public class PenetrationBall extends Bonus {

	public PenetrationBall(Brick sourceBrick, BreakOut breakOut) {
		super(sourceBrick, breakOut);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Setzt den Wert Penetrating auf true, was bedeutet das der Ball durch Bloecke hindurchfliegt.
	 */
	@Override
	public void applyBonus() {
		// TODO Auto-generated method stub
		breakOut.getBall().setPenetrating(true);
	}

	/**
	 * Setzt den Wert Penetrating auf false, was bedeutet das der Ball nicht mehr durch Bloecke hindurchfliegen kann.
	 */
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		this.breakOut.getBall().setPenetrating(false);
	}

}
