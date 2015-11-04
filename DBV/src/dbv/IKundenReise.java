package dbv;

/**
 * Stellt grundlegende Funktionen bereit, die ben�tigt werden, um eine Buchung vorzunehmen
 * 
 * @author Markus Hofmann
 * @version 1.0
 * 
 */
public interface IKundenReise {
	/**
	 * 
	 * @return Kunden-ID des Kunden f�r den die Reise gebucht wird
	 */
	public String getKundeID();
	
	/**
	 * 
	 * @return Reise-ID der Reise die gebucht wird
	 */
	public String getReiseID();
}
