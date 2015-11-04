package dbv;

/**
 * Stellt grundlegende Funktionen bereit, die benötigt werden, um die Daten
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
	 * @return Preis der pro Person für die Reise verlangt wird
	 */
	public String getPreisProPerson();

	/**
	 * 
	 * @return Kosten die für die Reise entstehen
	 */
	public String getKosten();
}
