package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.BreakOut;

/**
 * Listener zur Steuerung des Schlaegers durch die Maus
 * 
 * @author peijie
 *
 * @param <T>
 */
public class MouseController<T extends Event> implements EventHandler<MouseEvent> {

	BreakOut breakOut;

	/**
	 * Konstruktor von MouseController
	 * 
	 * @param breakOut
	 */
	public MouseController(BreakOut breakOut) {
		this.breakOut = breakOut;
	}

	/**
	 * handle(MouseEvent event) ruft die start Methode des BatTimerMouse auf wenn
	 * die Maus in der scene bewegt wird.
	 * 
	 * @param event
	 *            ist das uebergebene MouseEvent in der scene
	 */
	@Override
	public void handle(MouseEvent event) {

		if (event.getEventType() == event.MOUSE_CLICKED) {
			breakOut.getBall().setSticked(false);
			breakOut.getView().getInputTextfield().setEditable(false);

			if (!breakOut.isStarted()) {
				breakOut.setStarted(true);
				System.out.print("Launching");
				if (breakOut.getClient().isConnected()) {
					breakOut.getClient().getWriter().println("!@#$START%^&*");
					breakOut.getClient().getWriter().flush();
				}
			}
		}

		if (event.getEventType() == event.MOUSE_MOVED) {
			breakOut.getTimerBatMouse().start(event);
		} else {
			breakOut.getTimerBatMouse().stop();
		}
	}
}
