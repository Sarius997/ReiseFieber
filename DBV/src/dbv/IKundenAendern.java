package dbv;

/**
 * Stellt grundlegende Funktionen bereit, die ben�tigt werden, um die Daten
 * eines Kunden zu �ndern
 * 
 * @author Markus Hofmann
 * @version 1.0
 * 
 */
public interface IKundenAendern {
	/**
	 * 
	 * @return Nachname des zu �ndernden Kunden
	 */
	public String getNachname();

	/**
	 * 
	 * @return Vorname des zu �ndernden Kunden
	 */
	public String getVorname();

	/**
	 * 
	 * @return Wohnort des zu �ndernden Kunden
	 */
	public String getWohnort();

	/**
	 * 
	 * @return Geburtstag des zu �ndernden Kunden
	 */
	public String getGeburtstag();

	/**
	 * 
	 * @return Telefonnummer des zu �ndernden Kunden
	 */
	public String getTelefonnummer();

	/**
	 * 
	 * @return Geschlecht des zu �ndernden Kunden
	 */
	public String getGeschlecht();

	/**
	 * 
	 * @return ID des zu �ndernden Kunden
	 */
	public String getID();

	/**
	 * 
	 * @return Adresse des zu �ndernden Kunden
	 */
	public String getAdresse();

	/**
	 * 
	 * @return Postleitzahl des zu �ndernden Kunden
	 */
	public String getPostleitzahl();
}
