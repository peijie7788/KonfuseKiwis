package model.brick;

import javafx.scene.paint.Color;
import model.BreakOut;

/**
 * Erbt von Brick und ist eine spezielle Brickart die 5 mal getroffen werden
 * muss, um zertört zu werden.
 * @author Niklas
 *
 */
public class BrickE extends Brick{
	
	private char brickType = 'E';

	public BrickE(double xkoordinate, double ykoordinate, BreakOut breakOut) {
		super(xkoordinate, ykoordinate, breakOut);
		this.removedCounter = 5;
		setFill(Color.BLUE);
	}

	public char getBrickType() {
		return brickType;
	}
}
