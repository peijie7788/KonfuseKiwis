package util;

/**
 * Eigenerstellte Klasse zur Berarbeitung von Vektoren
 * @author peijie
 *
 */
public class CustomMath {
	
	/**
	 * gibt die Laenge des Vektors zurueck
	 * @param x
	 * @param y
	 * @return Skala
	 */
	public static double getScala(double x, double y) {
		double a = Math.pow(x,2);
		double b = Math.pow(y,2);
		double result = Math.sqrt(a+b);
		return result;
		
	}
}
