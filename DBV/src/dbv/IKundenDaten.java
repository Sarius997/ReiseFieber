package dbv;

/**
 * Stellt grundlegende Funktionen bereit, die benötigt werden, um einen neuen
 * Kunden anzulegen
 * 
 * @author Markus Hofmann
 * @version 1.0
 * 
 */
public interface IKundenDaten {
	
	/**
	 * 
	 * @return Vorname des neuen Kunden
	 */
	public String getVorname();

	/**
	 * 
	 * @return Nachname des neuen Kunden
	 */
	public String getNachname();

	/**
	 * 
	 * @return Wohnort des neuen Kunden
	 */
	public String getWohnort();

	/**
	 * 
	 * @return Geburtstag des neuen Kunden
	 */
	public String getGeburtstag();

	/**
	 * 
	 * @return Telefonnummer des neuen Kunden
	 */
	public String getTelefonnummer();

	/**
	 * 
	 * @return Geschlecht des neuen Kunden
	 */
	public String getGeschlecht();

	/**
	 * 
	 * @return Adresse des neuen Kunden
	 */
	public String getAdresse();

	/**
	 * 
	 * @return Postleitzahl des neuen Kunden
	 */
	public String getPostleitzahl();
}
