package model.brick;

import javafx.scene.paint.Color;
import model.BreakOut;

/**
 * Erbt von Brick und ist eine spezielle Brickart die 1 mal getroffen werden
 * muss, um zertört zu werden.
 * @author Niklas
 *
 */
public class BrickA extends Brick{
	
	private char brickType = 'A';

	public BrickA(double xkoordinate, double ykoordinate, BreakOut breakOut) {
		super(xkoordinate, ykoordinate, breakOut);
		super.removedCounter = 1;
		setFill(Color.RED);
//		breakOut.getBrickView().setColor(this, super.removedCounter);
	}

	public char getBrickType() {
		return brickType;
	}
}
