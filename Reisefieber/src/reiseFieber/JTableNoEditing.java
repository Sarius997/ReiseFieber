package reiseFieber;

import javax.swing.JTable;

/**
 * 
 * @author Markus Hofmann
 * @version 1.0
 */
@SuppressWarnings("serial")
public class JTableNoEditing extends JTable {
	/**
	 * Ruft den Konstruktor der Klasse {@link javax.swing.JTable} ohne Parameter
	 * auf und verhindert um Fehler zu vermeiden das Vertauschen von Spalten in der Benutzeroberfl�che 
	 */
	public JTableNoEditing() {
		super();
		this.getTableHeader().setReorderingAllowed(false);
	}

	/**
	 * �berschreibt die {@link javax.swing.JTable#isCellEditable(int, int)}
	 * Methode und
	 * 
	 * @return false und verhindert dadurch das Bearbeiten von einzelen Zellen
	 *         in der Benutzeroberfl�che
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}	
}
