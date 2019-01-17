package model.bonus;

import model.BreakOut;
import model.brick.Brick;

/**
 * Erbt von der Vaterklasse Bonus. Verkleinert die Breite des Schlaegers.
 * @author Niklas
 *
 */
public class NarrowerPad extends Bonus {

	public NarrowerPad(Brick sourceBrick, BreakOut breakOut) {
		super(sourceBrick, breakOut);
	}

	/**
	 * Verkleinert die Breite des Schlaegers.
	 */
	@Override
	public void applyBonus() {
		breakOut.getBat().setWidth(90);
		breakOut.getBat().setWidthBat(90);
	}

	/**
	 * Setzt die Schlaegerbreite auf den standartwert zurueck.
	 */
	@Override
	public void reset() {
		breakOut.getBat().setWidthBat(breakOut.getBat().getDEFAULT_WIDTH());
		breakOut.getBat().setWidth(breakOut.getBat().getDEFAULT_WIDTH());
	}

}
