package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import model.BreakOut;

/**
 * Listener von MessageSendButton, 
 * 
 * @author peijie
 *
 * @param <T>
 */
public class ButtonMessageSendListener<T extends Event> implements EventHandler<T> {
	private BreakOut breakOut;

	public ButtonMessageSendListener(BreakOut breakOut) {
		this.breakOut = breakOut;
	}

	/**
	 * Die Nachricht vom InputBox wird geholt und an dem Server gesendet
	 */
	@Override
	public void handle(T event) {
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
