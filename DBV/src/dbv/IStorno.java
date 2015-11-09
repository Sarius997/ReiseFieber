package dbv;

/**
 * Stellt grundlegende Funktionen bereit, die benötigt werden, um die Daten
 * einer zu stornierenden Buchung abzurufen.
 * 
 */
public interface IStorno {
	/**
	 * 
	 * @return ID der Buchung, die storniert werden soll
	 */
	public String getBuchungsID();
}
