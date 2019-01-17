package model.brick;

import javafx.scene.paint.Color;
import model.BreakOut;

/**
 * Erbt von Brick und ist eine spezielle Brickart die 6 mal getroffen werden
 * muss, um zertört zu werden.
 * @author Niklas
 *
 */
public class BrickF extends Brick{

	private char brickType = 'F';
	
	public BrickF(double xkoordinate, double ykoordinate, BreakOut breakOut) {
		super(xkoordinate, ykoordinate, breakOut);
		this.removedCounter = 6;
		setFill(Color.PURPLE);
	}

	public char getBrickType() {
		return brickType;
	}
}
