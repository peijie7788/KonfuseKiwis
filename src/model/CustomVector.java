package model;

public class CustomVector {
	private double xCoord;
	private double yCoord;
	
	public CustomVector(double xCoord, double yCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}

	public double getxCoord() {
		return xCoord;
	}

	public void setxCoord(double xCoord) {
		this.xCoord = xCoord;
	}
	
	public void increasexCoord(double increase) {
		this.xCoord += increase;
	}

	public double getyCoord() {
		return yCoord;
	}

	public void setyCoord(double yCoord) {
		this.yCoord = yCoord;
	}
	
	/**
	 * berechnet die Laenge des Vektors
	 * @return berechnete Laenge
	 */
	public double getScala() {
		
		double a = Math.pow(this.xCoord,2);
		double b = Math.pow(this.yCoord,2);
		double result = Math.sqrt(a+b);
		return result;
		
	}
	
	/**
	 * skaliert den Vektor 
	 * @param scale der Faktor
	 * @return skalierter Vektor
	 */
	public CustomVector scaleWith(double scale) {
		return new CustomVector(this.xCoord*scale, this.yCoord* scale);
	}
	
	/**
	 * addiert einen anderen Vektor zu diesem Vektor
	 * @param vector zu addierender Vektor
	 * @return addierten Vektor
	 */
	public CustomVector addVector(CustomVector vector) {
		return new CustomVector(this.xCoord+ vector.xCoord, this.yCoord+vector.yCoord);
	}
	
	/**
	 * bebarbeitet den Vektor durch die neuen Werte
	 * @param xCoord neue X Koordinate
	 * @param yCoord neue Y Koordinate
	 */
	public void setVector(double xCoord, double yCoord) {
		setxCoord(xCoord);
		setyCoord(yCoord);
	}
	
	/**
	 * bestimmt, wie der Vektor als String angezeigt wird
	 */
	@Override
	public String toString(){
		return "X: " + this.xCoord + " Y: " + this.yCoord;
	}
}
