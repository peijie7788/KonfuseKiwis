package model.brick;

import javafx.scene.paint.Color;
import model.BreakOut;

/**
 * Erbt von Brick und ist eine spezielle Brickart die 2 mal getroffen werden
 * muss, um zertört zu werden. * @author Niklas
 *
 */
public class BrickB extends Brick{
	
	private char brickType = 'B';

	public BrickB(double xkoordinate, double ykoordinate, BreakOut breakOut) {
		super(xkoordinate, ykoordinate,breakOut);
		
		// TODO Auto-generated constructor stub
		this.removedCounter = 2;
		setFill(Color.ORANGE);
	}

	public char getBrickType() {
		return brickType;
	}
}
