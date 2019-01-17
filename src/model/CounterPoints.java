package model;

import java.util.Observable;

public class CounterPoints extends Observable{
	
	//Aktuelle Punkte
	private int pointsCounter = 0;
	
	/**
	 * notifiziert den Observer, dass sich der livesCounter geaendert hat
	 */
	private void publish() {
		this.setChanged();
	    this.notifyObservers();
	}
	
	public int getPointsCounter() {
		return pointsCounter;
	}
	
	public void setPointsCounter(int value) {
		pointsCounter += value;
		this.publish();
	}
	
	
}
