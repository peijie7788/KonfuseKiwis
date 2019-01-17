package view;

import java.util.Observable;
import java.util.Observer;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import model.CounterPoints;

/**
 * View für das Label das die Punkte im Spiel anzeigt
 * @author Niklas
 *
 */
public class CountViewPoints extends Label implements Observer{

	private CounterPoints counterPoints;
	
	/**
	 * Konstruktor von CountViewPoints
	 */
	public CountViewPoints() {
		this.setFont(Font.font(35));
		this.setPadding(new Insets(5, 0, 0, 0));
		this.setText(String.valueOf(0));
	}
	
	/**
	 * Die update Methode von der Klasse CountViewPoints wird notifiziert,
	 * wenn sich etwas an der Anzahl der Leben ändert und passt das entsprechende 
	 * Label in der View an.
	 */
	@Override
	public void update(Observable observ, Object arg) {	
		counterPoints = (CounterPoints) observ;
		this.setText(String.valueOf(counterPoints.getPointsCounter()));
	}
	
}
