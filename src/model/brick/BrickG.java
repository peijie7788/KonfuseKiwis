package model.brick;

import javafx.scene.paint.Color;
import model.BreakOut;

/**
 * Erbt von Brick und ist eine spezielle Brickart 
 * @author Niklas
 *
 */
public class BrickG extends Brick{
	
	private char brickType = 'G';

	public BrickG(double xkoordinate, double ykoordinate, BreakOut breakOut) {
		super(xkoordinate, ykoordinate, breakOut);
		this.removedCounter = 7;
		this.zombieBrick = true;
		setFill(Color.SLATEGREY);
	}

	public char getBrickType() {
		return brickType;
	}
}
