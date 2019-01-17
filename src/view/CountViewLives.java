package view;

import java.util.Observable;
import java.util.Observer;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import model.CounterLives;

/**
 * View für das Label das im Spiel die Leben anziegt.
 * @author Niklas
 *
 */
public class CountViewLives extends Label implements Observer {

	private CounterLives counterLives;

	/**
	 * Konstruktor für CountViewLives
	 */
	public CountViewLives() {
		this.setFont(Font.font(35));
		this.setPadding(new Insets(5, 0, 0, 0));
	    this.setText(String.valueOf(3));
	}
	
	/**
	 * Die update Methode von der Klasse CountViewLives wird notifiziert,
	 * wenn sich etwas an der Anzahl der Leben ändert und passt das entsprechende 
	 * Label in der View an.
	 */
	@Override
	public void update(Observable observ, Object arg) {
		counterLives = (CounterLives) observ;
		this.setText(String.valueOf(counterLives.getLivesCounter()));
	}
	
}
