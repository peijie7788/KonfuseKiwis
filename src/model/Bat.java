package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bat extends Rectangle{
	
	private double xCoord;
	private double yCoord;
	private boolean sticky;
	

	private final double DEFAULT_WIDTH = 120;
	private double speedvektor = 3.0;
	private double widthBat = 120;
	private double heigthBat = 30;
	private double radiusRoundedEdges = 15;
	private int movementDirection = 0;
	
	private double defaultSpeed = 5.0;
	
	
	/**
	 * der Vektor, der die aktuelle Geschwindigkeit entspricht
	 */
	private CustomVector speedVector;

	private BreakOut breakOut;

	public Bat(BreakOut breakOut) {
		
		/**
		 * Hier wird sowohl die Breite als auch die Höhe des Schlägers bestimmt
		 */
		
		super(120,30);
		speedVector = new CustomVector(0,0);
		
		this.breakOut = breakOut;
		setFill(Color.AQUAMARINE);
	    setTranslateX(breakOut.getColumnCounter()*breakOut.getBrickWidth()/2 - widthBat/2);
	    setTranslateY((breakOut.getRowCounter()+breakOut.getLowerSpace())*breakOut.getBrickHeight());
	    
	    xCoord = this.getTranslateX();
	    yCoord = this.getTranslateY();
	    
	    setArcHeight(radiusRoundedEdges*2);
	    setArcWidth(radiusRoundedEdges*2);
	    setStroke(Color.BLACK);
	}

	/**
	 * bewegt den Schlaeger nach rechts
	 */
	public void moveRight() {
		if(this.xCoord + widthBat + getspeedvektor() < breakOut.getColumnCounter()*breakOut.getBrickWidth() ) {
			setTranslateX(this.xCoord + speedvektor);
			setMovementDirection(1);
			xCoord = this.xCoord + speedvektor;
		}
		
	}
	
	/**
	 * bewegt den Schlaeger nach links
	 */
	public void moveLeft() {
		if(this.xCoord > 0 + getspeedvektor()){
			setTranslateX(this.xCoord - speedvektor);
			setMovementDirection(-1);
			xCoord = this.xCoord - speedvektor;
		}
	}

	public double getxCoord() {
		return xCoord;
	}

	public double getyCoord() {
		return yCoord;
	}
	
	public void setspeedvektor() {
		speedvektor = defaultSpeed;
	}
	
	public double getspeedvektor() {
		return speedvektor;
	}
	
	public void setspeedvektor(double increase) {
		speedvektor += increase;
	}
	
	public double getdefaultspeedvektor() {
		return this.defaultSpeed;
	}
	
	public double getwidthBat() {
		return widthBat;
	}
	
	public double getHeigthBat() {
		return heigthBat;
	}

	public void setHeigthBat(double heigthBat) {
		this.heigthBat = heigthBat;
	}
	
	public double getRadiusRoundedEdges() {
		return radiusRoundedEdges;
	}
	
	public boolean isSticky() {
		return sticky;
	}

	public void setSticky(boolean sticky) {
		this.sticky = sticky;
	}
	public int getMovementDirection() {
		return movementDirection;
	}

	public void setMovementDirection(int movementDirection) {
		this.movementDirection = movementDirection;
	}
	
	public double getWidthBat() {
		return widthBat;
	}

	public void setWidthBat(double widthBat) {
		this.widthBat = widthBat;
	}
	
	public CustomVector getSpeedVector() {
		return speedVector;
	}

	public void setxCoord(double xCoord) {
		this.xCoord = xCoord;
	}

	public void setyCoord(double yCoord) {
		this.yCoord = yCoord;
	}

	public double getDEFAULT_WIDTH() {
		return DEFAULT_WIDTH;
	}
	
	
	
	
}
