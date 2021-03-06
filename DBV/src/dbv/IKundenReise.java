package dbv;

/**
 * Stellt grundlegende Funktionen bereit, die benötigt werden, um eine Buchung
 * vorzunehmen.
 * 
 */
public interface IKundenReise {
	/**
	 * 
	 * @return ID des Kunden, für den die Reise gebucht wird
	 */
	public String getKundeID();

	/**
	 * 
	 * @return ID der Reise, die gebucht wird
	 */
	public String getReiseID();
}
