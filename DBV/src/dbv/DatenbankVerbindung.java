package dbv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class DatenbankVerbindung {

	// constants for connection
	private static final String JDBC_DRIVER = "org.postgresql.Driver"; // "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	String dbServer;
	String dbPort;
	String dbName;
	String dbTableKunden;
	String dbUser;
	String dbPassword;
	Statement statement;
	ResultSet resultSet;
	ArrayList<String[]> result = new ArrayList<String[]>();

	/**
	 * fetch connection data from hard coded data. TODO: replace by config file
	 * or remove standalone runability
	 * 
	 * @return success
	 */

	public DatenbankVerbindung() {
		dbServer = "localhost";
		dbPort = "5432";
		dbName = "postgres";
		dbTableKunden = "kunden";
		dbUser = "postgres";
		dbPassword = "1234";
	}

	@SuppressWarnings("unused")
	public void doCount() throws Exception {

		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;

			/*
			 * final java.util.List<String> captionLi = new
			 * LinkedList<String>(); final java.util.List<String> valueLi = new
			 * LinkedList<String>(); final String captions = ""; // ... final
			 * String values = ""; // ...
			 * 
			 * StringBuilder qb = new StringBuilder();
			 * qb.append("INSERT INTO "); qb.append(dbTable);
			 * qb.append(captions); qb.append("\nVALUES "); qb.append(values);
			 * qb.append(";");
			 */
			// dbEingabe= getTfKdName() + getTfKdVorname() + getTfWohnort +
			// getTfGeburtsdatum + getTfTelefon + getTfGeschlecht;
			// final String query =
			// "INSERT INTO Kunden (nachname, vorname, wohnort, geburtstag, volljaehrig, telefonnummer, geschlecht) VALUES ('Krenn', 'Helmut');";
			// // qb.toString();
			final String query = "SELECT COUNT(DISTINCT id) AS NumberOfCustomers FROM reiseteilnehmer;";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			// ResultSet r = stmt.executeQuery(query); // nur für
			// Select-Befehle!

			resultSet = statement.executeQuery(querybkp);
			resultAusgabeConsole(resultSet);

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTableKunden + ", N:" + dbName
					+ ", U:" + dbUser + ", P:" + dbPassword;
		}
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public String[][] doSearch(IKundenSuchen kundenDaten) throws Exception {
		String[][] result = null;
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;

			/*
			 * final java.util.List<String> captionLi = new
			 * LinkedList<String>(); final java.util.List<String> valueLi = new
			 * LinkedList<String>(); final String captions = ""; // ... final
			 * String values = ""; // ...
			 * 
			 * StringBuilder qb = new StringBuilder();
			 * qb.append("INSERT INTO "); qb.append(dbTable);
			 * qb.append(captions); qb.append("\nVALUES "); qb.append(values);
			 * qb.append(";");
			 */
			String dbSuche = quoted(kundenDaten.getKdName() + "%");
			// dbEingabe= getTfKdName() + getTfKdVorname() + getTfWohnort +
			// getTfGeburtsdatum + getTfTelefon + getTfGeschlecht;
			// final String query =
			// "INSERT INTO Kunden (nachname, vorname, wohnort, geburtstag, volljaehrig, telefonnummer, geschlecht) VALUES ('Krenn', 'Helmut');";
			// // qb.toString();
			final String query = "SELECT id, nachname, vorname, geschlecht, geburtstag, volljaehrig, telefonnummer, "
					+ "adresse, postleitzahl, wohnort FROM public."
					+ dbTableKunden + " WHERE lower(nachname) similar to " + dbSuche.toLowerCase() + ";";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			// ResultSet r = stmt.executeQuery(query); // nur für
			// Select-Befehle!

			resultSet = statement.executeQuery(querybkp);
			// resultSet = executeQuery(querybkp);
			// resultAusgabeConsole(resultSet);

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
			for (Iterator iterator = resultArrays.iterator(); iterator
					.hasNext(); i++) {
				String[] strings = (String[]) iterator.next();
				result[i] = strings;
			}

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTableKunden + ", N:" + dbName
					+ ", U:" + dbUser + ", P:" + dbPassword;
		}
		return result;
	}

	private void resultAusgabeConsole(ResultSet resultSet) throws SQLException {
		int spalten = resultSet.getMetaData().getColumnCount();
		System.out.println("Anzahl Spalten: " + spalten);

		while (resultSet.next()) {
			String[] str = new String[10];
			for (int k = 1; k <= spalten; k++) {
				str[k - 1] = resultSet.getString(k);
				System.out.print(str[k - 1] + ", ");
			}
			result.add(str);
			System.out.println();
			System.out
					.println("--------------------------------------------------------------------------------------------------------------------------------------");
		}
	}

	@SuppressWarnings("unused")
	private ResultSet executeQuery(String querybkp) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unused")
	public void doInsert(IKundenDaten kundenDaten) throws Exception {

		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return;

			final Statement stmt = conn.createStatement();
			ResultSet resSet = null;

			/*
			 * final java.util.List<String> captionLi = new
			 * LinkedList<String>(); final java.util.List<String> valueLi = new
			 * LinkedList<String>(); final String captions = ""; // ... final
			 * String values = ""; // ...
			 * 
			 * StringBuilder qb = new StringBuilder();
			 * qb.append("INSERT INTO "); qb.append(dbTable);
			 * qb.append(captions); qb.append("\nVALUES "); qb.append(values);
			 * qb.append(";");
			 */
			String dbEingabe = quoted(kundenDaten.getNachname()) + ", "
					+ quoted(kundenDaten.getVorname()) + ", "
					+ quoted(kundenDaten.getWohnort()) + ", "
					+ quoted(kundenDaten.getGeburtstag()) + ", "
					+ quoted(kundenDaten.getTelefonnummer()) + ", "
					+ quoted(kundenDaten.getGeschlecht()) + ", "
					+ quoted(kundenDaten.getAdresse()) + ", "
					+ quoted(kundenDaten.getPostleitzahl());
			// dbEingabe= getTfKdName() + getTfKdVorname() + getTfWohnort +
			// getTfGeburtsdatum + getTfTelefon + getTfGeschlecht;
			// final String query =
			// "INSERT INTO Kunden (nachname, vorname, wohnort, geburtstag, volljaehrig, telefonnummer, geschlecht) VALUES ('Krenn', 'Helmut');";
			// // qb.toString();
			final String query = "INSERT INTO "
					+ dbTableKunden
					+ " (nachname, vorname, wohnort, geburtstag, telefonnummer, geschlecht, adresse, postleitzahl) "
					+ "VALUES (" + dbEingabe + ");";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			stmt.executeUpdate(query);// für Insert- und Update-Befehle
			deallocateResources(resSet, stmt);
		} catch (SQLException ex) {
			final String s = "executed Insert Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTableKunden + ", N:" + dbName
					+ ", U:" + dbUser + ", P:" + dbPassword;
		}
	}

	public static String quoted(String s) {
		return "'" + s + "'";

	}

	/**
	 * establish connection to database
	 * 
	 * @return handle to a database connection or null on failure
	 * @throws AfpsException
	 */
	@SuppressWarnings("unused")
	private Connection connect() throws Exception {
		String url = "(none)";
		try {
			Class.forName(JDBC_DRIVER);
			final String pgUrl = "jdbc:postgresql://" + dbServer + ":" + dbPort
					+ "/" + dbName;
			final String msqlUrl = "jdbc:sqlserver://" + dbServer
					+ ";databaseName=" + dbName;
			// final String url = "jdbc:sqlserver://" + dbServer +
			// ";databaseName=" + dbName;
			url = pgUrl;
			final Connection conn = DriverManager.getConnection(url, dbUser,
					dbPassword);
			return conn;
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			throw new Exception("ClassNotFound: " + ex.getMessage() + "\nURL: "
					+ url);
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("SQLException: " + ex.getMessage() + "\nURL:"
					+ url);
		}
		// return null;
	}

	/**
	 * deallocate resources
	 * 
	 * @param resSet
	 *            : this ResultSet's data are deallocated
	 * @param stmt
	 *            : this Statement's data are deallocated
	 */

	private void deallocateResources(final ResultSet resSet,
			final Statement stmt) {
		try {
			if (resSet != null)
				resSet.close();
			if (stmt != null)
				stmt.close();
		} catch (SQLException ex) {
			System.out
					.println("Aieh. Some exception occured when tidying resources...");
		}
	}

	@SuppressWarnings("unused")
	public void doChange(IKundenAendern kundenDaten) throws Exception {

		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return;

			final Statement stmt = conn.createStatement();
			ResultSet resSet = null;

			/*
			 * final java.util.List<String> captionLi = new
			 * LinkedList<String>(); final java.util.List<String> valueLi = new
			 * LinkedList<String>(); final String captions = ""; // ... final
			 * String values = ""; // ...
			 * 
			 * StringBuilder qb = new StringBuilder();
			 * qb.append("INSERT INTO "); qb.append(dbTable);
			 * qb.append(captions); qb.append("\nVALUES "); qb.append(values);
			 * qb.append(";");
			 */
			// dbEingabe= getTfKdName() + getTfKdVorname() + getTfWohnort +
			// getTfGeburtsdatum + getTfTelefon + getTfGeschlecht;
			// final String query =
			// "INSERT INTO Kunden (nachname, vorname, wohnort, geburtstag, volljaehrig, telefonnummer, geschlecht) VALUES ('Krenn', 'Helmut');";
			// // qb.toString();
			final String query = "UPDATE " + dbTableKunden + " SET nachname="
					+ quoted(kundenDaten.getNachname()) + ", vorname="
					+ quoted(kundenDaten.getVorname()) + ", wohnort="
					+ quoted(kundenDaten.getWohnort()) + ", geburtstag="
					+ quoted(kundenDaten.getGeburtstag()) + ", telefonnummer="
					+ quoted(kundenDaten.getTelefonnummer()) + ", geschlecht="
					+ quoted(kundenDaten.getGeschlecht()) + ", adresse="
					+ quoted(kundenDaten.getAdresse()) + ", postleitzahl="
					+ quoted(kundenDaten.getPostleitzahl()) + " WHERE id="
					+ quoted(kundenDaten.getID()) + ";";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			stmt.executeUpdate(query);// für Insert- und Update-Befehle
			deallocateResources(resSet, stmt);
		} catch (SQLException ex) {
			final String s = "executed Insert Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTableKunden + ", N:" + dbName
					+ ", U:" + dbUser + ", P:" + dbPassword;
		}
	}

	@SuppressWarnings("unused")
	public void doInsertInNewJourney(IKundenReise kundenReise) throws Exception {

		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return;

			final Statement stmt = conn.createStatement();
			ResultSet resSet = null;

			/*
			 * final java.util.List<String> captionLi = new
			 * LinkedList<String>(); final java.util.List<String> valueLi = new
			 * LinkedList<String>(); final String captions = ""; // ... final
			 * String values = ""; // ...
			 * 
			 * StringBuilder qb = new StringBuilder();
			 * qb.append("INSERT INTO "); qb.append(dbTable);
			 * qb.append(captions); qb.append("\nVALUES "); qb.append(values);
			 * qb.append(";");
			 */
			String dbEingabe = quoted(kundenReise.getKundeID()) + ", "
					+ quoted(kundenReise.getReiseID()) + ", false";
			// dbEingabe= getTfKdName() + getTfKdVorname() + getTfWohnort +
			// getTfGeburtsdatum + getTfTelefon + getTfGeschlecht;
			// final String query =
			// "INSERT INTO Kunden (nachname, vorname, wohnort, geburtstag, volljaehrig, telefonnummer, geschlecht) VALUES ('Krenn', 'Helmut');";
			// // qb.toString();
			final String query = "INSERT INTO kundenreise (k_id, r_id, storno) "
					+ "VALUES (" + dbEingabe + ");";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			stmt.executeUpdate(query);// für Insert- und Update-Befehle
			deallocateResources(resSet, stmt);
		} catch (SQLException ex) {
			final String s = "executed Insert Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTableKunden + ", N:" + dbName
					+ ", U:" + dbUser + ", P:" + dbPassword;
		}
	}

	@SuppressWarnings("unused")
	public String[] doSearchByKundenID(String kundenID) throws Exception {

		String[] result = new String[9];
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;

			/*
			 * final java.util.List<String> captionLi = new
			 * LinkedList<String>(); final java.util.List<String> valueLi = new
			 * LinkedList<String>(); final String captions = ""; // ... final
			 * String values = ""; // ...
			 * 
			 * StringBuilder qb = new StringBuilder();
			 * qb.append("INSERT INTO "); qb.append(dbTable);
			 * qb.append(captions); qb.append("\nVALUES "); qb.append(values);
			 * qb.append(";");
			 */
			String dbSuche = quoted(kundenID);
			// dbEingabe= getTfKdName() + getTfKdVorname() + getTfWohnort +
			// getTfGeburtsdatum + getTfTelefon + getTfGeschlecht;
			// final String query =
			// "INSERT INTO Kunden (nachname, vorname, wohnort, geburtstag, volljaehrig, telefonnummer, geschlecht) VALUES ('Krenn', 'Helmut');";
			// // qb.toString();
			final String query = "SELECT id, nachname, vorname, "
					+ "geschlecht, geburtstag, telefonnummer, "
					+ "adresse, postleitzahl, wohnort FROM " + dbTableKunden
					+ " WHERE id=" + dbSuche + ";";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			// ResultSet r = stmt.executeQuery(query); // nur für
			// Select-Befehle!

			resultSet = statement.executeQuery(querybkp);
			// resultSet = executeQuery(querybkp);

			while (resultSet.next()) {
				for (int k = 1; k <= resultSet.getMetaData().getColumnCount(); k++) {
					result[k - 1] = resultSet.getString(k);
				}
			}

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTableKunden + ", N:" + dbName
					+ ", U:" + dbUser + ", P:" + dbPassword;
		}
		return result;
	}

	@SuppressWarnings("unused")
	public void doCreateJourney(IReiseAnlegen reiseDaten) throws Exception {
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return;

			final Statement stmt = conn.createStatement();
			ResultSet resSet = null;

			/*
			 * final java.util.List<String> captionLi = new
			 * LinkedList<String>(); final java.util.List<String> valueLi = new
			 * LinkedList<String>(); final String captions = ""; // ... final
			 * String values = ""; // ...
			 * 
			 * StringBuilder qb = new StringBuilder();
			 * qb.append("INSERT INTO "); qb.append(dbTable);
			 * qb.append(captions); qb.append("\nVALUES "); qb.append(values);
			 * qb.append(";");
			 */
			String dbEingabe = quoted(reiseDaten.getReiseName()) + ", "
					+ quoted(reiseDaten.getReiseZiel()) + ", "
					+ quoted(reiseDaten.getTeilnehmerZahl()) + ", "
					+ quoted(reiseDaten.getBeginn()) + ", "
					+ quoted(reiseDaten.getEnde()) + ", "
					+ quoted(reiseDaten.getPreisProPerson()) + ", "
					+ quoted(reiseDaten.getKosten());
			// dbEingabe= getTfKdName() + getTfKdVorname() + getTfWohnort +
			// getTfGeburtsdatum + getTfTelefon + getTfGeschlecht;
			// final String query =
			// "INSERT INTO Kunden (nachname, vorname, wohnort, geburtstag, volljaehrig, telefonnummer, geschlecht) VALUES ('Krenn', 'Helmut');";
			// // qb.toString();
			final String query = "INSERT INTO reise"
					+ " (name, ziel, teilnehmerzahl, beginn, ende, preisproperson, kosten) "
					+ "VALUES (" + dbEingabe + ");";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			stmt.executeUpdate(query);// für Insert- und Update-Befehle
			deallocateResources(resSet, stmt);
		} catch (SQLException ex) {
			final String s = "executed Insert Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTableKunden + ", N:" + dbName
					+ ", U:" + dbUser + ", P:" + dbPassword;
		}
	}

	@SuppressWarnings("unused")
	public String[] doSearchByReiseID(String reiseID) throws Exception {
		String[] result = new String[8];
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;

			/*
			 * final java.util.List<String> captionLi = new
			 * LinkedList<String>(); final java.util.List<String> valueLi = new
			 * LinkedList<String>(); final String captions = ""; // ... final
			 * String values = ""; // ...
			 * 
			 * StringBuilder qb = new StringBuilder();
			 * qb.append("INSERT INTO "); qb.append(dbTable);
			 * qb.append(captions); qb.append("\nVALUES "); qb.append(values);
			 * qb.append(";");
			 */
			String dbSuche = quoted(reiseID);
			// dbEingabe= getTfKdName() + getTfKdVorname() + getTfWohnort +
			// getTfGeburtsdatum + getTfTelefon + getTfGeschlecht;
			// final String query =
			// "INSERT INTO Kunden (nachname, vorname, wohnort, geburtstag, volljaehrig, telefonnummer, geschlecht) VALUES ('Krenn', 'Helmut');";
			// // qb.toString();
			final String query = "SELECT * FROM public.reise" + " WHERE id="
					+ dbSuche + ";";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			// ResultSet r = stmt.executeQuery(query); // nur für
			// Select-Befehle!

			resultSet = statement.executeQuery(querybkp);
			// resultSet = executeQuery(querybkp);

			while (resultSet.next()) {
				for (int k = 1; k <= resultSet.getMetaData().getColumnCount(); k++) {
					result[k - 1] = resultSet.getString(k);
				}
			}

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTableKunden + ", N:" + dbName
					+ ", U:" + dbUser + ", P:" + dbPassword;
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public String[][] kundenUebersicht() throws Exception {
		String[][] result = null;
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;

			/*
			 * final java.util.List<String> captionLi = new
			 * LinkedList<String>(); final java.util.List<String> valueLi = new
			 * LinkedList<String>(); final String captions = ""; // ... final
			 * String values = ""; // ...
			 * 
			 * StringBuilder qb = new StringBuilder();
			 * qb.append("INSERT INTO "); qb.append(dbTable);
			 * qb.append(captions); qb.append("\nVALUES "); qb.append(values);
			 * qb.append(";");
			 */
			// dbEingabe= getTfKdName() + getTfKdVorname() + getTfWohnort +
			// getTfGeburtsdatum + getTfTelefon + getTfGeschlecht;
			// final String query =
			// "INSERT INTO Kunden (nachname, vorname, wohnort, geburtstag, volljaehrig, telefonnummer, geschlecht) VALUES ('Krenn', 'Helmut');";
			// // qb.toString();
			final String query = "SELECT id, nachname, vorname, "
					+ "geschlecht, geburtstag, volljaehrig, telefonnummer, "
					+ "adresse, postleitzahl, wohnort FROM public.kunden";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			// ResultSet r = stmt.executeQuery(query); // nur für
			// Select-Befehle!

			resultSet = statement.executeQuery(querybkp);
			// resultSet = executeQuery(querybkp);

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
			for (Iterator iterator = resultArrays.iterator(); iterator
					.hasNext(); i++) {
				String[] strings = (String[]) iterator.next();
				result[i] = strings;
			}

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTableKunden + ", N:" + dbName
					+ ", U:" + dbUser + ", P:" + dbPassword;
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public String[][] reiseUebersicht() throws Exception {
		String[][] result = null;
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;

			/*
			 * final java.util.List<String> captionLi = new
			 * LinkedList<String>(); final java.util.List<String> valueLi = new
			 * LinkedList<String>(); final String captions = ""; // ... final
			 * String values = ""; // ...
			 * 
			 * StringBuilder qb = new StringBuilder();
			 * qb.append("INSERT INTO "); qb.append(dbTable);
			 * qb.append(captions); qb.append("\nVALUES "); qb.append(values);
			 * qb.append(";");
			 */
			// dbEingabe= getTfKdName() + getTfKdVorname() + getTfWohnort +
			// getTfGeburtsdatum + getTfTelefon + getTfGeschlecht;
			// final String query =
			// "INSERT INTO Kunden (nachname, vorname, wohnort, geburtstag, volljaehrig, telefonnummer, geschlecht) VALUES ('Krenn', 'Helmut');";
			// // qb.toString();
			final String query = "SELECT * FROM public.reise";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			// ResultSet r = stmt.executeQuery(query); // nur für
			// Select-Befehle!

			resultSet = statement.executeQuery(querybkp);
			// resultSet = executeQuery(querybkp);

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
			for (Iterator iterator = resultArrays.iterator(); iterator
					.hasNext(); i++) {
				String[] strings = (String[]) iterator.next();
				result[i] = strings;
			}

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTableKunden + ", N:" + dbName
					+ ", U:" + dbUser + ", P:" + dbPassword;
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public String[][] buchungsUebersicht() throws Exception {
		String[][] result = null;
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;

			/*
			 * final java.util.List<String> captionLi = new
			 * LinkedList<String>(); final java.util.List<String> valueLi = new
			 * LinkedList<String>(); final String captions = ""; // ... final
			 * String values = ""; // ...
			 * 
			 * StringBuilder qb = new StringBuilder();
			 * qb.append("INSERT INTO "); qb.append(dbTable);
			 * qb.append(captions); qb.append("\nVALUES "); qb.append(values);
			 * qb.append(";");
			 */
			// dbEingabe= getTfKdName() + getTfKdVorname() + getTfWohnort +
			// getTfGeburtsdatum + getTfTelefon + getTfGeschlecht;
			// final String query =
			// "INSERT INTO Kunden (nachname, vorname, wohnort, geburtstag, volljaehrig, telefonnummer, geschlecht) VALUES ('Krenn', 'Helmut');";
			// // qb.toString();
			final String query = "SELECT kundenreise.id, reise.id, reise.name, reise.ziel, kunden.id, kunden.nachname, kunden.vorname, kundenreise.storno "
					+ "FROM kundenreise JOIN kunden ON kunden.id=kundenreise.k_id "
					+ "JOIN reise ON reise.id=kundenreise.r_id";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			// ResultSet r = stmt.executeQuery(query); // nur für
			// Select-Befehle!

			resultSet = statement.executeQuery(querybkp);
			// resultSet = executeQuery(querybkp);

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
			for (Iterator iterator = resultArrays.iterator(); iterator
					.hasNext(); i++) {
				String[] strings = (String[]) iterator.next();
				result[i] = strings;
			}

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTableKunden + ", N:" + dbName
					+ ", U:" + dbUser + ", P:" + dbPassword;
		}
		return result;
	}

	@SuppressWarnings("unused")
	public void doStorno(IStorno buchung) throws Exception {
		String querybkp = "(empty)";
		try {
			Connection conn = connect();

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;
			final String query = "UPDATE kundenreise "
					+ "SET storno=true WHERE id=" + buchung.getBuchungsID();
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			// ResultSet r = stmt.executeQuery(query); // nur für
			// Select-Befehle!

			resultSet = statement.executeQuery(querybkp);
			// resultSet = executeQuery(querybkp);

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTableKunden + ", N:" + dbName
					+ ", U:" + dbUser + ", P:" + dbPassword;
		}
	}

	@SuppressWarnings("unused")
	public String[] getBuchungByID(String buchungsID) throws Exception {
		String[] result = new String[7];
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;

			/*
			 * final java.util.List<String> captionLi = new
			 * LinkedList<String>(); final java.util.List<String> valueLi = new
			 * LinkedList<String>(); final String captions = ""; // ... final
			 * String values = ""; // ...
			 * 
			 * StringBuilder qb = new StringBuilder();
			 * qb.append("INSERT INTO "); qb.append(dbTable);
			 * qb.append(captions); qb.append("\nVALUES "); qb.append(values);
			 * qb.append(";");
			 */
			String dbSuche = quoted(buchungsID);
			// dbEingabe= getTfKdName() + getTfKdVorname() + getTfWohnort +
			// getTfGeburtsdatum + getTfTelefon + getTfGeschlecht;
			// final String query =
			// "INSERT INTO Kunden (nachname, vorname, wohnort, geburtstag, volljaehrig, telefonnummer, geschlecht) VALUES ('Krenn', 'Helmut');";
			// // qb.toString();
			final String query = "SELECT reise.id, reise.name, reise.ziel, kunden.id, kunden.nachname, kunden.vorname, kundenreise.storno "
					+ "FROM kundenreise JOIN kunden ON kunden.id=kundenreise.k_id "
					+ "JOIN reise ON reise.id=kundenreise.r_id WHERE kundenreise.id="
					+ buchungsID;
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			// ResultSet r = stmt.executeQuery(query); // nur für
			// Select-Befehle!

			resultSet = statement.executeQuery(querybkp);
			// resultSet = executeQuery(querybkp);

			while (resultSet.next()) {
				for (int k = 1; k <= resultSet.getMetaData().getColumnCount(); k++) {
					result[k - 1] = resultSet.getString(k);
				}
			}

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTableKunden + ", N:" + dbName
					+ ", U:" + dbUser + ", P:" + dbPassword;
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public String[][] reiseTeilnehmer(String reiseID, String storno)
			throws Exception {
		String[][] result = null;
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;
			final String query = "SELECT kundenreise.id, reise.id, reise.name, reise.ziel, kunden.id, kunden.nachname, kunden.vorname "
					+ "FROM kundenreise JOIN kunden ON kunden.id=kundenreise.k_id "
					+ "JOIN reise ON reise.id=kundenreise.r_id WHERE reise.id="
					+ reiseID + " AND kundenreise.storno=" + storno;
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
			for (Iterator iterator = resultArrays.iterator(); iterator
					.hasNext(); i++) {
				String[] strings = (String[]) iterator.next();
				result[i] = strings;
			}

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTableKunden + ", N:" + dbName
					+ ", U:" + dbUser + ", P:" + dbPassword;
		}
		return result;
	}

	@SuppressWarnings("unused")
	public String getMaximaleTeilnehmerZahl(String reiseID) throws Exception{
		String result = null;
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;
			final String query = "SELECT reise.teilnehmerzahl "
					+ "FROM reise WHERE reise.id=" + reiseID;
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
			final String s = "executed Select Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTableKunden + ", N:" + dbName
					+ ", U:" + dbUser + ", P:" + dbPassword;
		}
		return result;
	}
	
	@SuppressWarnings("unused")
	public String getAktuelleTeilnehmerZahl(String reiseID) throws Exception{
		String result = null;
		String querybkp = "(empty)";
		try {
			Connection conn = connect();
			if (conn == null)
				return null;

			final Statement statement = conn.createStatement();
			ResultSet resultSet = null;
			final String query = "SELECT COUNT(*) FROM kundenreise WHERE r_id=" + reiseID + " AND storno=false";
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
			final String s = "executed Select Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTableKunden + ", N:" + dbName
					+ ", U:" + dbUser + ", P:" + dbPassword;
		}
		return result;
	}
}
