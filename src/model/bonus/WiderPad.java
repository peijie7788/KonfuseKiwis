package model.bonus;

import model.BreakOut;
import model.brick.Brick;

/**
 * Erbt von der Vaterklasse Bonus. Vergrießert die Breite des Schlaegers.
 * @author Niklas
 *
 */
public class WiderPad extends Bonus{

	public WiderPad(Brick sourceBrick, BreakOut breakOut) {
		super(sourceBrick, breakOut);
	}

	/**
	 * Vergroeßert die Breite des Schlaegers.
	 */
	@Override
	public void applyBonus() {
		breakOut.getBat().setWidth(160);
		breakOut.getBat().setWidthBat(160);
	}

	/**
	 * Setzt die Breite des Schlaegers auf den Standartwert zurueck.
	 */
	@Override
	public void reset() {
		breakOut.getBat().setWidthBat(breakOut.getBat().getDEFAULT_WIDTH());
		breakOut.getBat().setWidth(breakOut.getBat().getDEFAULT_WIDTH());
	}
	
}
