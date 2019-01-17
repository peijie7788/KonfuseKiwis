package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.bonus.Bonus;

/**
 * Bonus als Rechteck in der graphischen Ebene
 * @author peijie
 *
 */
public class ShapeBonus extends Rectangle{
	private Bonus bonus;
	
	public ShapeBonus(Bonus bonus){
		super(48,20);
		setStroke(Color.BLACK);
		setFill(Color.WHITE);
		this.bonus = bonus;
		
	}
	
}
