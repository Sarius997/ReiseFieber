package twoWayCommunicationWithDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBV {

	// constants for connection
	private static final String JDBC_DRIVER = "org.postgresql.Driver"; // "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	String dbServer;
	String dbPort;
	String dbName;
	String dbTable;
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

	public DBV(String server, String port, String name, String table, String user,
			String password) {
		/*
		 * dbServer = "localhost"; // achtung: Port eigentlich 5432!! // 5433
		 * ist testport // dbTable eigentlich = "kunden" dbPort = "5433"; dbName
		 * = "postgres"; dbTable = "test"; dbUser = "postgres"; dbPassword =
		 * "1234";
		 */
		
		dbServer = server;
		dbPort = port;
		dbName = name;
		dbTable = table;
		dbUser = user;
		dbPassword = password;
	}

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

			// ResultSet r = stmt.executeQuery(query); // nur f�r
			// Select-Befehle!

			resultSet = statement.executeQuery(querybkp);
			resultAusgabeConsole(resultSet);

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTable + ", N:" + dbName + ", U:"
					+ dbUser + ", P:" + dbPassword;
		}
	}

	public void doSearch(String request) throws Exception {

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
			String dbSuche = quoted(request);
			// dbEingabe= getTfKdName() + getTfKdVorname() + getTfWohnort +
			// getTfGeburtsdatum + getTfTelefon + getTfGeschlecht;
			// final String query =
			// "INSERT INTO Kunden (nachname, vorname, wohnort, geburtstag, volljaehrig, telefonnummer, geschlecht) VALUES ('Krenn', 'Helmut');";
			// // qb.toString();
			final String query = "SELECT * FROM public." + dbTable
					+ " WHERE nachname=" + dbSuche + ";";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			// ResultSet r = stmt.executeQuery(query); // nur f�r
			// Select-Befehle!

			resultSet = statement.executeQuery(querybkp);
			// resultSet = executeQuery(querybkp);
			resultAusgabeConsole(resultSet);

			deallocateResources(resultSet, statement);
		} catch (SQLException ex) {
			final String s = "executed Select Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTable + ", N:" + dbName + ", U:"
					+ dbUser + ", P:" + dbPassword;
		}
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

	private ResultSet executeQuery(String querybkp) {
		// TODO Auto-generated method stub
		return null;
	}

	public void doInsert(String[] request) throws Exception {

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
			String dbEingabe = quoted(request[0]) + ", " + quoted(request[1])
					+ ", " + quoted(request[2]) + ", " + quoted(request[3])
					+ ", " + quoted(request[4]) + ", " + quoted(request[5]);
			// dbEingabe= getTfKdName() + getTfKdVorname() + getTfWohnort +
			// getTfGeburtsdatum + getTfTelefon + getTfGeschlecht;
			// final String query =
			// "INSERT INTO Kunden (nachname, vorname, wohnort, geburtstag, volljaehrig, telefonnummer, geschlecht) VALUES ('Krenn', 'Helmut');";
			// // qb.toString();
			final String query = "INSERT INTO "
					+ dbTable
					+ " (nachname, vorname, wohnort, geburtstag, telefonnummer, geschlecht) "
					+ "VALUES (" + dbEingabe + ");";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			stmt.executeUpdate(query);// f�r Insert- und Update-Befehle
			deallocateResources(resSet, stmt);
		} catch (SQLException ex) {
			final String s = "executed Insert Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTable + ", N:" + dbName + ", U:"
					+ dbUser + ", P:" + dbPassword;
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

	public void doChange(String[] request) throws Exception {

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
			final String query = "UPDATE " + dbTable + " SET nachname="
					+ quoted(request[0]) + ", vorname=" + quoted(request[1])
					+ ", wohnort=" + quoted(request[2]) + ", geburtstag="
					+ quoted(request[3]) + ", telefonnummer="
					+ quoted(request[4]) + ", geschlecht=" + quoted(request[5])
					+ " WHERE id=" + quoted(request[6]) + ";";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			stmt.executeUpdate(query);// f�r Insert- und Update-Befehle
			deallocateResources(resSet, stmt);
		} catch (SQLException ex) {
			final String s = "executed Insert Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTable + ", N:" + dbName + ", U:"
					+ dbUser + ", P:" + dbPassword;
		}
	}

	public void doInsertInNewJourney(String[] request) throws Exception {

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
			String dbEingabe = quoted(request[0]) + ", " + quoted(request[1])
					+ ", " + quoted(request[2]);
			// dbEingabe= getTfKdName() + getTfKdVorname() + getTfWohnort +
			// getTfGeburtsdatum + getTfTelefon + getTfGeschlecht;
			// final String query =
			// "INSERT INTO Kunden (nachname, vorname, wohnort, geburtstag, volljaehrig, telefonnummer, geschlecht) VALUES ('Krenn', 'Helmut');";
			// // qb.toString();
			final String query = "INSERT INTO reiseteilnehmer (nachname, vorname, id) "
					+ "VALUES (" + dbEingabe + ");";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			stmt.executeUpdate(query);// f�r Insert- und Update-Befehle
			deallocateResources(resSet, stmt);
		} catch (SQLException ex) {
			final String s = "executed Insert Query\n" + querybkp + "\non S:"
					+ dbServer + ", T:" + dbTable + ", N:" + dbName + ", U:"
					+ dbUser + ", P:" + dbPassword;
		}
	}

	public String[] doSearchByID(String id) throws Exception {

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
			String dbSuche = quoted(id);
			// dbEingabe= getTfKdName() + getTfKdVorname() + getTfWohnort +
			// getTfGeburtsdatum + getTfTelefon + getTfGeschlecht;
			// final String query =
			// "INSERT INTO Kunden (nachname, vorname, wohnort, geburtstag, volljaehrig, telefonnummer, geschlecht) VALUES ('Krenn', 'Helmut');";
			// // qb.toString();
			final String query = "SELECT * FROM public." + dbTable
					+ " WHERE id=" + dbSuche + ";";
			querybkp = query;

			System.out.println("SQL Statement is:\n" + query);

			// ResultSet r = stmt.executeQuery(query); // nur f�r
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
					+ dbServer + ", T:" + dbTable + ", N:" + dbName + ", U:"
					+ dbUser + ", P:" + dbPassword;
		}
		return result;
	}
}