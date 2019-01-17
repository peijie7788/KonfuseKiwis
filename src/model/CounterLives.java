package model;

import java.util.Observable;

public class CounterLives extends Observable{

	//Verbleibende Baelle
	private int livesCounter = 3;
	
	/**
	 * notifiziert den Observer, dass sich der livesCounter geaendert hat
	 */
	private void publish() {
		this.setChanged();
	    this.notifyObservers();
	}
	
	public int getLivesCounter() {
		return livesCounter;
	}
	
	public void removeLive() {
		livesCounter --;
		this.publish();
	}

}
