package controller;

import javafx.animation.AnimationTimer;
import model.BreakOut;
import model.bonus.Bonus;
import view.ShapeBonus;

/**
 * Die Bonusstuecke wird hierdurch bewegt, bis die vom Spieler beruehrt werden
 * 
 * @author peijie
 *
 */
public class BonusTimer extends AnimationTimer {

	private Bonus bonus;
	private ShapeBonus shapeBonus;

	public BonusTimer(Bonus bonus, BreakOut breakOut) {
		super();
		this.bonus = bonus;
	}

	/**
	 * setzt die Position des Bonusstuecks nach unten
	 */
	@Override
	public void handle(long now) {
		bonus.translate();
		shapeBonus.setTranslateY(shapeBonus.getTranslateY() + bonus.getSpeed());
	}

	public ShapeBonus getShapeBonus() {
		return shapeBonus;
	}

	public void setShapeBonus(ShapeBonus shapeBonus) {
		this.shapeBonus = shapeBonus;
	}

}
