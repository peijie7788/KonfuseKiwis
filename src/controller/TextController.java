package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.BreakOut;

/**
 * Listener von MouseEvent. ist fuer die Auswahl eines Textfeldes zustaendig
 * 
 * @author peijie
 *
 * @param <T>
 */
public class TextController<T extends Event> implements EventHandler<MouseEvent> {

	private BreakOut breakOut;
	private TextField textfield;

	public TextController(BreakOut breakOut, TextField textfield) {
		this.breakOut = breakOut;
		this.textfield = textfield;
	}

	/**
	 * Wenn das Textfeld mit der Maus geklickt wird, ist das Textfeld bearbeitbar.
	 */
	@Override
	public void handle(MouseEvent event) {
		if (event.getEventType() == event.MOUSE_CLICKED && this.textfield == breakOut.getView().getInputTextfield()) {
			breakOut.getView().getInputTextfield().setEditable(true);
			breakOut.getView().getInputTextfield().clear();
		}
		if (event.getEventType() == event.MOUSE_CLICKED && this.textfield == breakOut.getView().getIpTextfield()) {
			breakOut.getView().getIpTextfield().setEditable(true);
			breakOut.getView().getIpTextfield().clear();
		}
		if (event.getEventType() == event.MOUSE_CLICKED && this.textfield == breakOut.getView().getPortTextfield()) {
			breakOut.getView().getPortTextfield().setEditable(true);
			breakOut.getView().getPortTextfield().clear();
		}
	}

}
