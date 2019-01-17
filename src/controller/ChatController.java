package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.BreakOut;

/**
 * Eventhandler vom Betritt und Verlassen der Maus in das Chatfenster
 * 
 * @author peijie
 *
 * @param <T>
 */
public class ChatController<T extends Event> implements EventHandler<MouseEvent> {

	private BreakOut breakOut;

	public ChatController(BreakOut breakOut) {
		this.breakOut = breakOut;
	}

	/**
	 * wird aufgerufen, wenn die Maus das Chatfenster betritt oder verlaesst. setzt
	 * alle Komponenten je nach dem, als zugreifbar / nicht zugreifbar
	 */
	@Override
	public void handle(MouseEvent event) {
		if (event.getEventType() == event.MOUSE_ENTERED) {
			breakOut.getView().getInputTextfield().setDisable(false);
			breakOut.getView().getIpTextfield().setDisable(false);
			breakOut.getView().getPortTextfield().setDisable(false);
			breakOut.getView().getTextArea().setDisable(false);
			breakOut.getView().getSendButton().setDisable(false);
			breakOut.getView().getConnectButton().setDisable(false);
		}
		if (event.getEventType() == event.MOUSE_EXITED) {
			breakOut.getView().getInputTextfield().setDisable(true);
			breakOut.getView().getIpTextfield().setDisable(true);
			breakOut.getView().getPortTextfield().setDisable(true);
			breakOut.getView().getTextArea().setDisable(true);
			breakOut.getView().getSendButton().setDisable(true);
			breakOut.getView().getConnectButton().setDisable(true);
		}
	}

}