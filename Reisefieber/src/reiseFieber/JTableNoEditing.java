package reiseFieber;

import javax.swing.JTable;

/**
 * Diese Klasse passt die Funktionen von {@link javax.swing.JTable} an, um
 * Bedienungsfehler zu vermeiden.
 */
@SuppressWarnings("serial")
public class JTableNoEditing extends JTable {
	/**
	 * Ruft den Konstruktor der Klasse {@link javax.swing.JTable} ohne Parameter
	 * auf und verhindert, um Fehler zu vermeiden, das Vertauschen von Spalten in
	 * der Benutzeroberfläche.
	 */
	public JTableNoEditing() {
		super();
		this.getTableHeader().setReorderingAllowed(false);
	}

	/**
	 * Überschreibt die {@link javax.swing.JTable#isCellEditable(int, int)}
	 * Methode.
	 * 
	 * @return <code>false</code> und verhindert dadurch das Bearbeiten von einzelen Zellen
	 *         in der Benutzeroberfläche
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
