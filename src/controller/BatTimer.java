package controller;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import model.BreakOut;

/**
 * Timer sorgt dafuer, dass in jedem Frame der Bat bewegt wird durch die Tastatur
 * @author peijie
 *
 */
public class BatTimer extends AnimationTimer {

	private KeyEvent event;
	private BreakOut breakOut;

	/**
	 * Konstruktor des BatTimer
	 * 
	 * @param breakOut
	 */
	public BatTimer(BreakOut breakOut) {
		this.breakOut = breakOut;
	}

	/**
	 * start(KeyEvent event): startet den BatTimer und setzt das lokale KeyEvent auf
	 * das erhaltene event KeyEvent
	 * 
	 * @param event
	 *            ist das erhaltene Event
	 */
	public void start(KeyEvent event) {
		this.event = event;
		start();
	}

	/**
	 * Die Methode handle wird wiederholt aufgerufen wenn der BatTimer läuft. Dabei
	 * wird der speedvektor(die Geschwindigkeit) von Bat erhöht. Außerdem wird
	 * überprüft welchen Code das lokale Event besitzt und jenachdem, ob es LEFT
	 * oder RIGHT ist wird der Bat nach links oder nach rechts bewegt, indem die
	 * moveLeft() oder moveRigth() Methode von Bat aufgerufen wird.
	 */
	@Override
	public void handle(long now) {
		switch (event.getCode()) {
		case LEFT: {
			breakOut.getBat().moveLeft();
			breakOut.getBat().getSpeedVector().setVector(-breakOut.getBat().getdefaultspeedvektor(), 0);
			break;
		}
		case RIGHT: {
			breakOut.getBat().moveRight();
			breakOut.getBat().getSpeedVector().setVector(breakOut.getBat().getdefaultspeedvektor(), 0);
			break;
		}
		case SPACE: {
			breakOut.getBall().setSticked(false);
			if (!breakOut.isStarted()) {
				breakOut.setStarted(true);
				System.out.print("Launching");
				if (breakOut.getClient().isConnected()) {
					breakOut.getClient().getWriter().println("!@#$START%^&*");
					breakOut.getClient().getWriter().flush();
				}
			}

			break;
		}
		default:
			break;
		}
	}

}
