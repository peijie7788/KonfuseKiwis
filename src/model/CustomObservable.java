package model;

import java.util.Observable;

import model.brick.Brick;

/**
 * Die eigenerstellte Klasse, wird von der View observiert. Teilt der View mit,
 * wenn ein Brick zerstoert ist
 * 
 * @author peijie
 *
 */
public class CustomObservable extends Observable {

	private Brick brick;
	private int brickValue;
	private char brickType;

	/**
	 * wird aufgerufen, wenn ein Brick zerstoert wird notifiziert den Observer bzw.
	 * der View, dass die auch den entsprechenden Brick entfernen muss
	 * 
	 * @param brick
	 *            der Brick, der gerade zerstoert worden ist
	 * @param brickValue
	 *            der Tye des Bricks
	 */
	public void execute(Brick brick, int brickValue) {
		this.brick = brick;
		this.brickValue = brickValue;
		this.brickType = brick.getBrickType();
		setChanged();
		notifyObservers();
	}

	public int getBrickValue() {
		return this.brickValue;
	}

	public void setBrickValue(int brickValue) {
		this.brickValue = brickValue;
	}

	public void setBrickType(char brickType) {
		this.brickType = brickType;
	}

	public char getBrickType() {
		return this.brickType;
	}

	public Brick getBrick() {
		return this.brick;
	}
}
