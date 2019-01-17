package model.brick;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.BreakOut;
import util.CoordPair;

/**
 * Vaterklasse für alle Bricks.
 * @author Niklas
 *
 */
public class Brick extends Rectangle{
	
	protected int removedCounter = 0;
	protected boolean unDestroyable = false;
	protected boolean zombieBrick = false;

	private int brickWidth;
	private int brickHeight;
	private char brickType = ' ';
	protected BreakOut breakOut;
	
	//Beschreibt die Koordinaten der Vier Ecken eines Bricks.
	private CoordPair[] coordPairs = new CoordPair[4];

	/**
	 * Konstruiert einen Brick mit einer X- und Y-Postion.
	 * @param xkoordinate des Bricks
	 * @param ykoordinate des Bricks
	 */
	public Brick(double xkoordinate, double ykoordinate, BreakOut breakOut) {
		super(xkoordinate, ykoordinate, 0, 0);
		this.setWidth(breakOut.getBrickWidth());
		this.setHeight(breakOut.getBrickHeight());
		
		coordPairs[0] = new CoordPair(xkoordinate, ykoordinate);
		coordPairs[1] = new CoordPair(xkoordinate + breakOut.getBrickWidth(), ykoordinate);
		coordPairs[2] = new CoordPair(xkoordinate + breakOut.getBrickWidth(), ykoordinate + breakOut.getBrickHeight());
		coordPairs[3] = new CoordPair(xkoordinate, ykoordinate + breakOut.getBrickHeight());
		
		setStroke(Color.BLACK);	
	}
	
	public int getBrickWidth() {
		return brickWidth;
	}

	public void setBrickWidth(int brickWidth) {
		this.brickWidth = brickWidth;
	}

	public int getBrickHeight() {
		return brickHeight;
	}

	public void setBrickHeight(int brickHeight) {
		this.brickHeight = brickHeight;
	}

	public int getRemovedCounter() {
		return removedCounter;
	}
	
	public void setRemovedCounter(int count) {
		this.removedCounter += count;
	}
	
	public void setUnDestroyable(boolean unDestroyable) {
		this.unDestroyable = unDestroyable;
	}
	
	public boolean isUnDestroyable() {
		return unDestroyable;
	}

	public boolean isZombieBrick() {
		return zombieBrick;
	}

	public void setZombieBrick(boolean zombieBrick) {
		this.zombieBrick = zombieBrick;
	}
	
	public char getBrickType() {
		return brickType;
	}
	public CoordPair[] getCoordPairs() {
		return coordPairs;
	}

	public void setCoordPairs(CoordPair[] coordPairs) {
		this.coordPairs = coordPairs;
	}
	
	
}
