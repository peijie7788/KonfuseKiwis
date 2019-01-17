package controller;

import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;
import model.BreakOut;

/**
 * Timer sorgt dafuer, dass in jedem Frame der Bat bewegt wird durch die Maus
 * @author peijie
 *
 */
public class BatTimerMouse extends AnimationTimer {

	private MouseEvent event;
	private BreakOut breakOut;

	/**
	 * Konstruktor von BatTimerMouse
	 * @param breakOut
	 */
	public BatTimerMouse(BreakOut breakOut) {
		this.breakOut = breakOut;
	}
	
	/**
	 * start(MouseEvent event) startet den BatTimerMouse und setzt this.event auf das �bergebene
	 * event  
	 * @param event
	 *            ist das uebergebene MouseEvent.
	 */
	public void start(MouseEvent event) {
		this.event = event;
		start();
	}

	/**
	 * Die Methode handle(long now) wird im abstand von long now aufgerufen und
	 * Ueberprueft ob der Mauszeiger sich relativ zur scene rechts oder links vom
	 * Mittelpunkt des Schlaeges Bat befindet und bewegt den Schlaeger bis die
	 * x-Position vom Mittelpunktes des Schlaeger gleich der des Mauszeigers ist
	 */
	@Override
	public void handle(long now) {

		double xPositionMouse = event.getX();
		
		if (xPositionMouse >= (breakOut.getBat().getxCoord() + 0.5 * breakOut.getBat().getwidthBat()) - 5
				&& xPositionMouse <= (breakOut.getBat().getxCoord() + 0.5 * breakOut.getBat().getwidthBat()) + 5) {
			breakOut.getBat().setspeedvektor();
			breakOut.getBat().setMovementDirection(0);
			breakOut.getBat().getSpeedVector().setVector(0, 0);
		}
		if (xPositionMouse < (breakOut.getBat().getxCoord() + 0.5 * breakOut.getBat().getwidthBat()) - 5 && xPositionMouse >= 0
				&& xPositionMouse <= breakOut.getColumnCounter()*60) {
			breakOut.getBat().moveLeft();
			breakOut.getBat().getSpeedVector().setVector(-breakOut.getBat().getdefaultspeedvektor(), 0);

		}
		if (xPositionMouse > (breakOut.getBat().getxCoord() + 0.5 * breakOut.getBat().getwidthBat()) + 5 && xPositionMouse <= breakOut.getColumnCounter()*60
				&& xPositionMouse >= 0) {
			breakOut.getBat().moveRight();
			breakOut.getBat().getSpeedVector().setVector(breakOut.getBat().getdefaultspeedvektor(), 0);
		}
	}

}
