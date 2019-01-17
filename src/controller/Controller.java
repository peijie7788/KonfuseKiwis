package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import model.BreakOut;

/**
 * Listener zur Steuerung des Schlaegers durch die Tastatur
 * 
 * @author peijie
 *
 * @param <T>
 */
public class Controller<T extends Event> implements EventHandler<KeyEvent> {

	private BreakOut breakOut;

	/**
	 * Konstruktor des Controllers
	 * 
	 * @param breakOut
	 */
	public Controller(BreakOut breakOut) {
		this.breakOut = breakOut;
	}

	/**
	 * Die Methode handle bekommt ein KeyEvent event übergeben und überprüft ob des
	 * event KEY_PRESSED oder KEY_RELEASED ist. Bei KEY_PRESSED also wenn ein konpf
	 * gedrückt wird, wird der BatTimer gestartet. Bei KEY_RELEASED also wenn ein
	 * knopf losgelassen wird, dann wird der BatTimer gestoppt. und der speedvektor
	 * des Bat wird auf den standartwert zurückgesetzt
	 */
	public void handle(KeyEvent event) {
		if (event.getEventType() == KeyEvent.KEY_PRESSED) {
			breakOut.getTimerBat().start(event);

		}
		if (event.getEventType() == KeyEvent.KEY_RELEASED) {
			breakOut.getBat().setspeedvektor();
			breakOut.getBat().setMovementDirection(0);
			breakOut.getTimerBat().stop();
			breakOut.getBat().getSpeedVector().setVector(0, 0);
		}
	}

}
