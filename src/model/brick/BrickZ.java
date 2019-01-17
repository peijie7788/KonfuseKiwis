package model.brick;

import javafx.scene.paint.Color;
import model.BreakOut;

/**
 * Erbt von Brick und ist eine spezielle Brickart die unzerstoerbar ist.
 * @author Niklas
 *
 */
public class BrickZ extends Brick{
	
	private char brickType = 'Z';

	public BrickZ(double xkoordinate, double ykoordinate, BreakOut breakOut) {
		super(xkoordinate, ykoordinate, breakOut);
		this.setUnDestroyable(true);
		this.removedCounter = 8;
		setFill(Color.IVORY);
	}

	public char getBrickType() {
		return brickType;
	}
}
