package model.bonus;

import model.BreakOut;
import model.brick.Brick;

/**
 * Erbt von der Vaterklasse Bonus. Macht den Schlaeger klebrig.
 * @author Niklas
 *
 */
public class StickyPad extends Bonus {

	public StickyPad(Brick sourceBrick, BreakOut breakOut) {
		super(sourceBrick, breakOut);
	}

	/**
	 * Macht den Schlaeger zu einem klebrigen Schlaeger.
	 */
	@Override
	public void applyBonus() {
		this.breakOut.getBat().setSticky(true);
	}

	@Override
	public void reset() {
		
	}

}
