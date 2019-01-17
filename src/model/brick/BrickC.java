package model.brick;

import javafx.scene.paint.Color;
import model.BreakOut;

/**
 * Erbt von Brick und ist eine spezielle Brickart die 3 mal getroffen werden
 * muss, um zertört zu werden.
 * @author Niklas
 *
 */
public class BrickC extends Brick{
	
	private char brickType = 'C';

	public BrickC(double xkoordinate, double ykoordinate, BreakOut breakOut) {
		super(xkoordinate, ykoordinate, breakOut);
		// TODO Auto-generated constructor stub
		this.removedCounter = 3;
		setFill(Color.YELLOW);
	}

	public char getBrickType() {
		return brickType;
	}
}
