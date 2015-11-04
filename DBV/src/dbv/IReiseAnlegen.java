package dbv;

/**
 * Stellt grundlegende Funktionen bereit, die ben�tigt werden, um die Daten
 * einer Reise abzurufen die erstellt werden soll
 * 
 * @author Markus Hofmann
 * @version 1.0
 * 
 */
public interface IReiseAnlegen {
	/**
	 * 
	 * @return Name der Reise
	 */
	public String getReiseName();

	/**
	 * 
	 * @return Reiseziel
	 */
	public String getReiseZiel();

	/**
	 * 
	 * @return maximale Teilnehmerzahl
	 */
	public String getTeilnehmerZahl();

	/**
	 * 
	 * @return Beginn der Reise
	 */
	public String getBeginn();

	/**
	 * 
	 * @return Ende der Reise
	 */
	public String getEnde();

	/**
	 * 
	 * @return Preis der pro Person f�r die Reise verlangt wird
	 */
	public String getPreisProPerson();

	/**
	 * 
	 * @return Kosten die f�r die Reise entstehen
	 */
	public String getKosten();
}
