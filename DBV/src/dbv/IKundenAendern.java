package dbv;

/**
 * Stellt grundlegende Funktionen bereit, die benötigt werden, um die Daten
 * eines Kunden zu ändern.
 * 
 * @author Markus Hofmann
 * @version 1.0
 * 
 */
public interface IKundenAendern {
	/**
	 * 
	 * @return Nachname des zu ändernden Kunden
	 */
	public String getNachname();

	/**
	 * 
	 * @return Vorname des zu ändernden Kunden
	 */
	public String getVorname();

	/**
	 * 
	 * @return Wohnort des zu ändernden Kunden
	 */
	public String getWohnort();

	/**
	 * 
	 * @return Geburtstag des zu ändernden Kunden
	 */
	public String getGeburtstag();

	/**
	 * 
	 * @return Telefonnummer des zu ändernden Kunden
	 */
	public String getTelefonnummer();

	/**
	 * 
	 * @return Geschlecht des zu ändernden Kunden
	 */
	public String getGeschlecht();

	/**
	 * 
	 * @return ID des zu ändernden Kunden
	 */
	public String getID();

	/**
	 * 
	 * @return Adresse des zu ändernden Kunden
	 */
	public String getAdresse();

	/**
	 * 
	 * @return Postleitzahl des zu ändernden Kunden
	 */
	public String getPostleitzahl();
}
