package model.brick;

import javafx.scene.paint.Color;
import model.BreakOut;

/**
 * Erbt von Brick und ist eine spezielle Brickart die 4 mal getroffen werden
 * muss, um zertört zu werden.
 * @author Niklas
 *
 */
public class BrickD extends Brick{
	
	private char brickType = 'D';

	public BrickD(double xkoordinate, double ykoordinate, BreakOut breakOut) {
		super(xkoordinate, ykoordinate, breakOut);
		// TODO Auto-generated constructor stub
		this.removedCounter = 4;
		setFill(Color.GREEN);
	}

	public char getBrickType() {
		return brickType;
	}
}
