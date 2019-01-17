package controller;

import javafx.animation.AnimationTimer;
import model.BreakOut;
import model.bonus.Bonus;

/**
 * Bonus wird nach gewisser Zeit entfernt, DecayTimer wird gestartet, wenn Bonus
 * beruehrt wird, stoppt wenn 300 frames erreicht
 * 
 * @author peijie
 *
 */
public class BonusDecayTimer extends AnimationTimer {
	BreakOut breakOut;
	long counter = 0;
	Bonus bonus;

	public BonusDecayTimer(Bonus bonus) {
		this.bonus = bonus;
	}

	/**
	 * wird immer wieder aufgerufen, so lange Bonus noch vorhanden ist
	 */
	@Override
	public void handle(long now) {
		counter++;
		if (counter == 300) {
			bonus.reset();
			this.stop();
		}
	}

}
