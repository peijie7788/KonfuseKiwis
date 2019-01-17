package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.BreakOut;

/**
 * Listener von MessageSendButton, bzw. wenn die Taste ENTER gedrueckt wird
 * 
 * @author peijie
 *
 * @param <T>
 */
public class EnterMessageSendListener<T extends Event> implements EventHandler<KeyEvent> {
	private BreakOut breakOut;

	public EnterMessageSendListener(BreakOut breakOut) {
		this.breakOut = breakOut;
	}

	/**
	 * Die Nachricht vom InputBox wird geholt und an dem Server gesendet
	 */
	@Override
	public void handle(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			String messageFromClient;
			messageFromClient = breakOut.getView().getInputTextfield().getText();
			try {
				breakOut.getClient().getWriter().println(messageFromClient);
				breakOut.getClient().getWriter().flush();
				breakOut.getView().getInputTextfield().clear();
			} catch (Exception e) {
				System.out.println("No reachable Server");
			}
		}
	}

}
