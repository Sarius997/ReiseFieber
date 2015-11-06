package dbv;

/**
 * Stellt grundlegende Funktionen bereit, die benötigt werden, um die Daten
 * einer zu stornierenden Buchung abzurufen.
 * 
 * @author Markus Hofmann
 * @version 1.0
 * 
 */
public interface IStorno {
	/**
	 * 
	 * @return Buchungs-ID der Buchung die Storniert werden soll
	 */
	public String getBuchungsID();
}
