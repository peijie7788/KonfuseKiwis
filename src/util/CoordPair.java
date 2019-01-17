package util;


/**
 * Paar von X und Y Koordinaten
 * @author peijie
 *
 */

public class CoordPair {
	private double xCoord;
	private double yCoord;
	public CoordPair(double xCoord, double yCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}
	public double getxCoord() {
		return xCoord;
	}
	public void setxCoord(double xCoord) {
		this.xCoord = xCoord;
	}
	public double getyCoord() {
		return yCoord;
	}
	public void setyCoord(double yCoord) {
		this.yCoord = yCoord;
	}
}
