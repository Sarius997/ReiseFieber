package dbv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Diese Klasse baut eine Verbindung zu einer PostgreSQL Datenbank mit festen
 * Daten auf und stellt alle Funktionen bereit, die benötigt werden, um die
 * Daten aus der Datenbank auszulesen oder Daten in der Datenbank zu speichern.
 * 
 */
public class DatenbankVerbindung {

	// constants for connection
	private static final String JDBC_DRIVER = "org.postgresql.Driver";

	String dbServer;
	String dbPort;
	String dbName;
	String dbTableKunden;
	String dbTableKundenreise;
	String dbTableReise;
	String dbUser;
	String dbPassword;
	Statement statement;
	ResultSet resultSet;
	ArrayList<String[]> result = new ArrayList<String[]>();

	/**
	 * Initialisiert die Daten für die Datenbankverbindung.
	 */
	public DatenbankVerbindung() {
		dbServer = "localhost";
		dbPort = "5432";
		dbName = "postgres";
		dbTableKunden = "kunden";
		dbTableKundenreise = "kundenreise";
		dbTableReise = "reise";
		dbUser = "postgres";
		dbPassword = "1234";
	}

	/**
	 * Sucht alle Daten von Kunden, deren Nachname mit der als {@link IKundenSuchen#getKdName()}
	 * übergebenen Zeichenkette anfangen, aus der Datenbank heraus.
	 * 
	 * @param kundenDaten
	 *            Für die Suche wird der Nachname (oder ein Teil des Nachnamen)
	 *            des gesuchten Kunden verwendet, der durch
	 *            {@link IKundenSuchen#getKdName()} übergeben wird
	 * @return {@code String[][]} mit den Suchergebnissen.<br>
	 *         Die Spalten sind sortiert nach:<br>
	 *         ID, Nachname, Vorname, Geschlecht, Geburtstag, Telefonnummer,
	 *         Adresse, Postleitzahl, Wohnort
	 * @throws Exception
	 */
	public String[][] doSearch(IKundenSuchen kundenDaten) throws Exception {
		String[][] result = null;
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;

			String dbSuche = quoted(kundenDaten.getKdName() + "%");

			final String query = "SELECT id, nachname, vorname, geschlecht, geburtstag, telefonnummer, "
					+ "adresse, postleitzahl, wohnort FROM public." + dbTableKunden
					+ " WHERE lower(nachname) similar to " + dbSuche.toLowerCase() + ";";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			resultSet = statement.executeQuery(querybkp);

			ArrayList<String[]> resultArrays = new ArrayList<String[]>(5);
			while (resultSet.next()) {
				String[] tmp = new String[10];
				for (int k = 1; k <= resultSet.getMetaData().getColumnCount(); k++) {
					tmp[k - 1] = resultSet.getString(k);
				}
				resultArrays.add(tmp);
			}

			result = new String[resultArrays.size()][8];

			int i = 0;
			for (Iterator<String[]> iterator = resultArrays.iterator(); iterator.hasNext(); i++) {
				String[] strings = iterator.next();
				result[i] = strings;
			}

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:" + dbServer + ", T:" + dbTableKunden
					+ ", N:" + dbName + ", U:" + dbUser + ", P:" + dbPassword;
			System.err.println(s);
		}
		return result;
	}

	/**
	 * Speichert die Daten eines Kunden, die als {@link IKundenDaten} übergeben
	 * werden, in der Datenbank.
	 * 
	 * @param kundenDaten
	 *            Die Daten des zu speichernden Kunden
	 * @throws Exception
	 */
	public void doInsert(IKundenDaten kundenDaten) throws Exception {

		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return;

			final Statement stmt = conn.createStatement();
			ResultSet resSet = null;

			String dbEingabe = quoted(kundenDaten.getNachname()) + ", " + quoted(kundenDaten.getVorname()) + ", "
					+ quoted(kundenDaten.getWohnort()) + ", " + quoted(kundenDaten.getGeburtstag()) + ", "
					+ quoted(kundenDaten.getTelefonnummer()) + ", " + quoted(kundenDaten.getGeschlecht()) + ", "
					+ quoted(kundenDaten.getAdresse()) + ", " + quoted(kundenDaten.getPostleitzahl());

			final String query = "INSERT INTO " + dbTableKunden
					+ " (nachname, vorname, wohnort, geburtstag, telefonnummer, geschlecht, adresse, postleitzahl) "
					+ "VALUES (" + dbEingabe + ");";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			stmt.executeUpdate(query);
			deallocateResources(resSet, stmt);
		} catch (SQLException ex) {
			final String s = "executed Insert Query\n" + querybkp + "\non S:" + dbServer + ", T:" + dbTableKunden
					+ ", N:" + dbName + ", U:" + dbUser + ", P:" + dbPassword;
			System.err.println(s);
		}
	}

	/**
	 * Setzt die übergebene Zeichenkette in Anführungszeichen, damit sie in den
	 * SQL-Abfragen auch als Text behandelt werden.
	 * 
	 * @param s
	 *            Text, der in Anführungszeichen gesetzt werden soll
	 * @return Der übergebene Text in Anführungszeichen
	 */
	public static String quoted(String s) {
		return "'" + s + "'";

	}

	/**
	 * Stellt die Verbindung zur Datenbank her.
	 * 
	 * @return Zugang zu einer Datenbankverbindung oder {@code null} wenn ein
	 *         Fehler aufgetreten ist
	 * @throws AfpsException
	 */
	private Connection connect() throws Exception {
		String url = "(none)";
		try {
			Class.forName(JDBC_DRIVER);
			final String pgUrl = "jdbc:postgresql://" + dbServer + ":" + dbPort + "/" + dbName;
			url = pgUrl;
			final Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
			return conn;
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			throw new Exception("ClassNotFound: " + ex.getMessage() + "\nURL: " + url);
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("SQLException: " + ex.getMessage() + "\nURL:" + url);
		}
	}

	/**
	 * Gibt die Ressourcen wieder frei, um die Verbindung zur Datenbank sicher
	 * zu beenden und Fehler bei erneuten Abfragen zu verhindern.
	 * 
	 * @param resSet
	 *            Freizugebendes ResultSet
	 * 
	 * @param stmt
	 *            Freizugebendes Statement
	 */
	private void deallocateResources(final ResultSet resSet, final Statement stmt) {
		try {
			if (resSet != null)
				resSet.close();
			if (stmt != null)
				stmt.close();
		} catch (SQLException ex) {
			System.err.println("Aieh. Some exception occured when tidying resources...");
		}
	}

	/**
	 * Ändert in der Datenbank die Daten des Kunden mit der ID
	 * {@link IKundenAendern#getID()} zu den Daten die als
	 * {@link IKundenAendern} übergeben werden.
	 * 
	 * @param kundenDaten
	 *            Kundendaten, die geändert werden sollen
	 * @throws Exception
	 */
	public void doChange(IKundenAendern kundenDaten) throws Exception {

		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return;

			final Statement stmt = conn.createStatement();
			ResultSet resSet = null;

			final String query = "UPDATE " + dbTableKunden + " SET nachname=" + quoted(kundenDaten.getNachname())
					+ ", vorname=" + quoted(kundenDaten.getVorname()) + ", wohnort=" + quoted(kundenDaten.getWohnort())
					+ ", geburtstag=" + quoted(kundenDaten.getGeburtstag()) + ", telefonnummer="
					+ quoted(kundenDaten.getTelefonnummer()) + ", geschlecht=" + quoted(kundenDaten.getGeschlecht())
					+ ", adresse=" + quoted(kundenDaten.getAdresse()) + ", postleitzahl="
					+ quoted(kundenDaten.getPostleitzahl()) + " WHERE id=" + quoted(kundenDaten.getID()) + ";";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			stmt.executeUpdate(query);
			deallocateResources(resSet, stmt);
		} catch (SQLException ex) {
			final String s = "executed Update Query\n" + querybkp + "\non S:" + dbServer + ", T:" + dbTableKunden
					+ ", N:" + dbName + ", U:" + dbUser + ", P:" + dbPassword;
			System.err.println(s);
		}
	}

	/**
	 * Legt in der Datenbank eine Buchung für die Reise mit der ID
	 * {@link IKundenReise#getReiseID()} und den Kunden mit der ID
	 * {@link IKundenReise#getKundeID()} an.
	 * 
	 * @param kundenReise
	 *            Kunden-ID und Reise-ID für die vorzunehmende Buchung
	 * @throws Exception
	 */
	public void doInsertInJourney(IKundenReise kundenReise) throws Exception {

		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return;

			final Statement stmt = conn.createStatement();
			ResultSet resSet = null;

			String dbEingabe = quoted(kundenReise.getKundeID()) + ", " + quoted(kundenReise.getReiseID()) + ", false";

			final String query = "INSERT INTO " + dbTableKundenreise + " (k_id, r_id, storno) " + "VALUES (" + dbEingabe
					+ ");";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			stmt.executeUpdate(query);
			deallocateResources(resSet, stmt);
		} catch (SQLException ex) {
			final String s = "executed Insert Query\n" + querybkp + "\non S:" + dbServer + ", T:" + dbTableKundenreise
					+ ", N:" + dbName + ", U:" + dbUser + ", P:" + dbPassword;
			System.err.println(s);
		}
	}

	/**
	 * Sucht die Daten des Kunden mit der übergebenen ID aus der Datenbank
	 * heraus.
	 * 
	 * @param kundenID
	 *            Die ID des gesuchten Kunden
	 * @return Die Daten des Kunden mit der übergebenen ID.<br>
	 *         Die Spalten sind sortiert nach:<br>
	 *         ID, Nachname, Vorname, Geschlecht, Geburtstag, Telefonnummer,
	 *         Adresse, Postleitzahl, Wohnort
	 * @throws Exception
	 */
	public String[] doSearchByKundenID(String kundenID) throws Exception {

		String[] result = new String[9];
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;

			String dbSuche = quoted(kundenID);

			final String query = "SELECT id, nachname, vorname, " + "geschlecht, geburtstag, telefonnummer, "
					+ "adresse, postleitzahl, wohnort FROM " + dbTableKunden + " WHERE id=" + dbSuche + ";";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			resultSet = statement.executeQuery(querybkp);

			while (resultSet.next()) {
				for (int k = 1; k <= resultSet.getMetaData().getColumnCount(); k++) {
					result[k - 1] = resultSet.getString(k);
				}
			}

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:" + dbServer + ", T:" + dbTableKunden
					+ ", N:" + dbName + ", U:" + dbUser + ", P:" + dbPassword;
			System.err.println(s);
		}
		return result;
	}

	/**
	 * Erstellt einen neuen Datenbankeintrag für die Reise, mit den durch
	 * {@link IReiseAnlegen} übergebenen Daten.
	 * 
	 * @param reiseDaten
	 *            Die Daten der Reise, die erstellt wird
	 * @throws Exception
	 */
	public void doCreateJourney(IReiseAnlegen reiseDaten) throws Exception {
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return;

			final Statement stmt = conn.createStatement();
			ResultSet resSet = null;

			String dbEingabe = quoted(reiseDaten.getReiseName()) + ", " + quoted(reiseDaten.getReiseZiel()) + ", "
					+ quoted(reiseDaten.getTeilnehmerZahl()) + ", " + quoted(reiseDaten.getBeginn()) + ", "
					+ quoted(reiseDaten.getEnde()) + ", " + quoted(reiseDaten.getPreisProPerson()) + ", "
					+ quoted(reiseDaten.getKosten());

			final String query = "INSERT INTO " + dbTableReise
					+ " (name, ziel, teilnehmerzahl, beginn, ende, preisproperson, kosten) " + "VALUES (" + dbEingabe
					+ ");";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			stmt.executeUpdate(query);
			deallocateResources(resSet, stmt);
		} catch (SQLException ex) {
			final String s = "executed Insert Query\n" + querybkp + "\non S:" + dbServer + ", T:" + dbTableReise
					+ ", N:" + dbName + ", U:" + dbUser + ", P:" + dbPassword;
			System.err.println(s);
		}
	}

	/**
	 * Sucht die Daten der Reise mit der übergebenen ID aus der Datenbank
	 * heraus.
	 * 
	 * @param reiseID
	 *            ID der zu suchenden Reise
	 * @return Die Daten der gesuchten Reise
	 * @throws Exception
	 */
	public String[] doSearchByReiseID(String reiseID) throws Exception {
		String[] result = new String[8];
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;

			String dbSuche = quoted(reiseID);

			final String query = "SELECT * FROM " + dbTableReise + " WHERE id=" + dbSuche + ";";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			resultSet = statement.executeQuery(querybkp);

			while (resultSet.next()) {
				for (int k = 1; k <= resultSet.getMetaData().getColumnCount(); k++) {
					result[k - 1] = resultSet.getString(k);
				}
			}

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:" + dbServer + ", T:" + dbTableReise
					+ ", N:" + dbName + ", U:" + dbUser + ", P:" + dbPassword;
			System.err.println(s);
		}
		return result;
	}

	/**
	 * Sucht die Daten aller gespeicherten Kunden aus der Datenbank heraus.
	 * 
	 * @return Die Daten aller gespeicherter Kunden.<br>
	 *         Die Spalten sind sortiert nach:<br>
	 *         ID, Nachname, Vorname, Geschlecht, Geburtstag, Telefonnummer,
	 *         Adresse, Postleitzahl, Wohnort
	 * @throws Exception
	 */
	public String[][] kundenUebersicht() throws Exception {
		String[][] result = null;
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;

			final String query = "SELECT id, nachname, vorname, " + "geschlecht, geburtstag, telefonnummer, "
					+ "adresse, postleitzahl, wohnort FROM " + dbTableKunden;
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			resultSet = statement.executeQuery(querybkp);

			ArrayList<String[]> resultArrays = new ArrayList<String[]>(5);
			while (resultSet.next()) {
				String[] tmp = new String[10];
				for (int k = 1; k <= resultSet.getMetaData().getColumnCount(); k++) {
					tmp[k - 1] = resultSet.getString(k);
				}
				resultArrays.add(tmp);
			}

			result = new String[resultArrays.size()][8];
			int i = 0;
			for (Iterator<String[]> iterator = resultArrays.iterator(); iterator.hasNext(); i++) {
				String[] strings = iterator.next();
				result[i] = strings;
			}

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:" + dbServer + ", T:" + dbTableKunden
					+ ", N:" + dbName + ", U:" + dbUser + ", P:" + dbPassword;
			System.err.println(s);
		}
		return result;
	}

	/**
	 * Sucht die Daten aller gespeicherten Reisen aus der Datenbank heraus.
	 * 
	 * @return Die Daten aller gespeicherten Reisen
	 * @throws Exception
	 */
	public String[][] reiseUebersicht() throws Exception {
		String[][] result = null;
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;

			final String query = "SELECT * FROM " + dbTableReise;
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			resultSet = statement.executeQuery(querybkp);

			ArrayList<String[]> resultArrays = new ArrayList<String[]>(5);
			while (resultSet.next()) {
				String[] tmp = new String[8];
				for (int k = 1; k <= resultSet.getMetaData().getColumnCount(); k++) {
					tmp[k - 1] = resultSet.getString(k);
				}
				resultArrays.add(tmp);
			}

			result = new String[resultArrays.size()][8];
			int i = 0;
			for (Iterator<String[]> iterator = resultArrays.iterator(); iterator.hasNext(); i++) {
				String[] strings = iterator.next();
				result[i] = strings;
			}

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:" + dbServer + ", T:" + dbTableReise
					+ ", N:" + dbName + ", U:" + dbUser + ", P:" + dbPassword;
			System.err.println(s);
		}
		return result;
	}

	/**
	 * Sucht die Daten aller gespeicherten Buchungen aus der Datenbank heraus.
	 * 
	 * @return Die Daten aller gespeicherter Buchungen
	 * @throws Exception
	 */
	public String[][] buchungsUebersicht() throws Exception {
		String[][] result = null;
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;

			final String query = "SELECT " + dbTableKundenreise + ".id, " + dbTableReise + ".id, " + dbTableReise
					+ ".name, " + dbTableReise + ".ziel, " + dbTableKunden + ".id, " + dbTableKunden + ".nachname, "
					+ dbTableKunden + ".vorname, " + dbTableKundenreise + ".storno " + "FROM " + dbTableKundenreise
					+ " JOIN " + dbTableKunden + " ON " + dbTableKunden + ".id=" + dbTableKundenreise + ".k_id "
					+ "JOIN " + dbTableReise + " ON " + dbTableReise + ".id=" + dbTableKundenreise + ".r_id";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			resultSet = statement.executeQuery(querybkp);

			ArrayList<String[]> resultArrays = new ArrayList<String[]>(5);
			while (resultSet.next()) {
				String[] tmp = new String[8];
				for (int k = 1; k <= resultSet.getMetaData().getColumnCount(); k++) {
					tmp[k - 1] = resultSet.getString(k);
				}
				resultArrays.add(tmp);
			}

			result = new String[resultArrays.size()][8];
			int i = 0;
			for (Iterator<String[]> iterator = resultArrays.iterator(); iterator.hasNext(); i++) {
				String[] strings = iterator.next();
				result[i] = strings;
			}

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:" + dbServer + ", T:" + dbTableKundenreise
					+ ", N:" + dbName + ", U:" + dbUser + ", P:" + dbPassword;
			System.err.println(s);
		}
		return result;
	}

	/**
	 * Storniert die Buchung mit der ID {@link IStorno#getBuchungsID()} und
	 * speichert die Buchung als storniert.
	 * 
	 * @param buchung
	 *            Die Daten der zu stornierenden Buchung
	 * @throws Exception
	 */
	public void doStorno(IStorno buchung) throws Exception {
		String querybkp = "(empty)";
		try {
			Connection conn = connect();

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;
			final String query = "UPDATE " + dbTableKundenreise + " " + "SET storno=true WHERE id="
					+ buchung.getBuchungsID();
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			resultSet = statement.executeQuery(querybkp);

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed UPDATE Query\n" + querybkp + "\non S:" + dbServer + ", T:" + dbTableKundenreise
					+ ", N:" + dbName + ", U:" + dbUser + ", P:" + dbPassword;
			System.err.println(s);
		}
	}

	/**
	 * Sucht die Buchung mit der übergebenen ID aus der Datenbank heraus.
	 * 
	 * @param buchungsID
	 *            ID der gesuchten Buchung
	 * @return Die Daten der gesuchten Buchung.<br>
	 *         Die Spalten sind sortiert nach:<br>
	 *         Reise-ID, Reisename, Reiseziel, Kunden-ID, Nachname des Kunden,
	 *         Vorname des Kunden, Storno der Buchung
	 * @throws Exception
	 */
	public String[] getBuchungByID(String buchungsID) throws Exception {
		String[] result = new String[7];
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;

			final String query = "SELECT " + dbTableReise + ".id, " + dbTableReise + ".name, " + dbTableReise
					+ ".ziel, " + dbTableKunden + ".id, " + dbTableKunden + ".nachname, " + dbTableKunden + ".vorname, "
					+ dbTableKundenreise + ".storno " + "FROM " + dbTableKundenreise + " JOIN " + dbTableKunden + " ON "
					+ dbTableKunden + ".id=" + dbTableKundenreise + ".k_id " + "JOIN " + dbTableReise + " ON "
					+ dbTableReise + ".id=" + dbTableKundenreise + ".r_id WHERE " + dbTableKundenreise + ".id="
					+ buchungsID;
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			resultSet = statement.executeQuery(querybkp);

			while (resultSet.next()) {
				for (int k = 1; k <= resultSet.getMetaData().getColumnCount(); k++) {
					result[k - 1] = resultSet.getString(k);
				}
			}

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:" + dbServer + ", T:" + dbTableKundenreise
					+ ", N:" + dbName + ", U:" + dbUser + ", P:" + dbPassword;
			System.err.println(s);
		}
		return result;
	}

	/**
	 * Sucht die Daten aller Buchungen der Reise mit der übergebenen ID aus der
	 * Datenbank heraus, bei denen Storno den als {@code storno} übergebenen Wert hat.
	 * 
	 * @param reiseID
	 *            ID der gesuchten Reise
	 * @param storno
	 *            {@code f} wenn nicht stornierte Buchungen gesucht werden, {@code t} wenn
	 *            stornierte Buchungen gesucht werden
	 * @return Die Daten aller Buchungen der gegebenen Reise, die storniert /
	 *         nicht storniert wurden
	 * @throws Exception
	 */
	public String[][] reiseTeilnehmer(String reiseID, String storno) throws Exception {
		String[][] result = null;
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;
			final String query = "SELECT " + dbTableKundenreise + ".id, " + dbTableReise + ".id, " + dbTableReise
					+ ".name, " + dbTableReise + ".ziel, " + dbTableKunden + ".id, " + dbTableKunden + ".nachname, "
					+ dbTableKunden + ".vorname " + "FROM " + dbTableKundenreise + " JOIN " + dbTableKunden + " ON "
					+ dbTableKunden + ".id=" + dbTableKundenreise + ".k_id " + "JOIN " + dbTableReise + " ON "
					+ dbTableReise + ".id=" + dbTableKundenreise + ".r_id WHERE " + dbTableReise + ".id=" + reiseID
					+ " AND " + dbTableKundenreise + ".storno=" + storno;
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			resultSet = statement.executeQuery(querybkp);

			ArrayList<String[]> resultArrays = new ArrayList<String[]>(5);
			while (resultSet.next()) {
				String[] tmp = new String[8];
				for (int k = 1; k <= resultSet.getMetaData().getColumnCount(); k++) {
					tmp[k - 1] = resultSet.getString(k);
				}
				resultArrays.add(tmp);
			}

			result = new String[resultArrays.size()][8];
			int i = 0;
			for (Iterator<String[]> iterator = resultArrays.iterator(); iterator.hasNext(); i++) {
				String[] strings = iterator.next();
				result[i] = strings;
			}

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:" + dbServer + ", T:" + dbTableKundenreise
					+ ", N:" + dbName + ", U:" + dbUser + ", P:" + dbPassword;
			System.err.println(s);
		}
		return result;
	}

	/**
	 * Sucht die maximale Teilnehmerzahl der Reise mit der übergebenen ID aus
	 * der Datenbank heraus.
	 * 
	 * @param reiseID
	 *            Die ID der Reise für die die maximale Teilnehmerzahl gesucht
	 *            wird
	 * @return Die maximale Teilnehmerzahl der gesuchten Reise
	 * @throws Exception
	 */
	public String getMaximaleTeilnehmerZahl(String reiseID) throws Exception {
		String result = null;
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;
			final String query = "SELECT " + dbTableReise + ".teilnehmerzahl " + "FROM " + dbTableReise + " WHERE "
					+ dbTableReise + ".id=" + reiseID;
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			resultSet = statement.executeQuery(querybkp);

			while (resultSet.next()) {
				for (int k = 1; k <= resultSet.getMetaData().getColumnCount(); k++) {
					result = resultSet.getString(k);
				}
			}

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:" + dbServer + ", T:" + dbTableReise
					+ ", N:" + dbName + ", U:" + dbUser + ", P:" + dbPassword;
			System.err.println(s);
		}
		return result;
	}

	/**
	 * Sucht die aktuelle Zahl der nicht stornierten Buchungen für die Reise mit
	 * der übergebenen ID aus der Datenbank heraus.
	 * 
	 * @param reiseID
	 *            Die ID der Buchung
	 * @return Die Zahl der nicht stornierten Buchungen für die Reise mit der
	 *         übergebenen ID
	 * @throws Exception
	 */
	public String getAktuelleTeilnehmerZahl(String reiseID) throws Exception {
		String result = null;
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;
			final String query = "SELECT COUNT(*) FROM " + dbTableKundenreise + " WHERE r_id=" + reiseID
					+ " AND storno=false";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			resultSet = statement.executeQuery(querybkp);

			while (resultSet.next()) {
				for (int k = 1; k <= resultSet.getMetaData().getColumnCount(); k++) {
					result = resultSet.getString(k);
				}
			}

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:" + dbServer + ", T:" + dbTableKundenreise
					+ ", N:" + dbName + ", U:" + dbUser + ", P:" + dbPassword;
			System.err.println(s);
		}
		return result;
	}
}
