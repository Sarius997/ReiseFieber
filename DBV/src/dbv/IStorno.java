package dbv;

/**
 * Stellt grundlegende Funktionen bereit, die benötigt werden, um die Daten
 * einer zu stornierenden Buchung abzurufen.
 * 
 */
public interface IStorno {
	/**
	 * 
	 * @return Buchungs-ID der Buchung die Storniert werden soll
	 */
	public String getBuchungsID();
}
