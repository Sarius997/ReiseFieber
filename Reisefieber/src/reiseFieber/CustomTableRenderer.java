package reiseFieber;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Diese Klasse passt die Funktionen von
 * {@link javax.swing.table.DefaultTableCellRenderer} an, um in der
 * Benutzeroberfläche den Gewinn und Verlust bei einzelnen Reisen und die
 * Buchungen die auf der Warteliste stehen farbig hervorzuheben.
 * 
 */
@SuppressWarnings("serial")
public class CustomTableRenderer extends DefaultTableCellRenderer {
	private int teilnehmerzahl;
	private int colls;
	private int tabIndex;
	private String[] gewinn;

	/**
	 * Erzeugt einen TableRenderer speziell für die Übersicht aller aktiven
	 * Buchungen der die Buchungen auf der Warteliste farblich hervorhebt.
	 * 
	 * @param teilnehmerzahl
	 *            Die maximale Teilnehmerzahl für die aktuell angezeigte Reise
	 * @param tabIndex
	 *            Der Index der Tabelle für die der TableRenderer erzeugt wird
	 */
	public CustomTableRenderer(int teilnehmerzahl, int tabIndex) {
		this.teilnehmerzahl = teilnehmerzahl;
		this.tabIndex = tabIndex;
	}

	/**
	 * Erzeugt einen TableRenderer speziell für die Übersicht über alle
	 * eingetragenen Reisen um den Gewinn / Verlust farblich hervorzuheben.
	 * 
	 * @param colls
	 *            Die Spaltenzahl der Tabelle um festzulegen in welcher Spalte
	 *            der Gewinn angezeigt wird
	 * @param tabIndex
	 *            Der Index der Tabelle für die der TableRenderer erzeugt wird
	 * @param gewinn
	 *            Ein Array mit dem Gewinn der durch die einzelnen Reisen
	 *            erzielt wird
	 */
	public CustomTableRenderer(int colls, int tabIndex, String[] gewinn) {
		this.colls = colls;
		this.tabIndex = tabIndex;
		this.gewinn = gewinn;
	}

	/**
	 * Erzeugt den Standard TableRenderer für alle Tabellen ohne spezielle
	 * farbliche Hervorhebungen.
	 * 
	 * @param tabIndex
	 *            Der Index der Tabelle, für die der TableRenderer erzeugt wird
	 */
	public CustomTableRenderer(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	/**
	 * Ändert je nach Tabelle, für die der TableRenderer erstellt wurde, die
	 * Text- und die Hintergrundfarbe der Zellen und sorgt für
	 * tabellenspezifische Farbhervorhebungen.
	 * 
	 * @param table
	 *            Die Tabelle, deren Zelle gerendert wird
	 * @param value
	 *            Der Inhalt der Zelle an der Stelle [row, column]
	 * @param isSelected
	 *            {@code true} wenn die Zelle ausgewählt ist
	 * @param hasFocus
	 *            {@code true} wenn die Zelle den Focus hat
	 * @param row
	 *            Die Zeile der zu rendernden Zelle
	 * @param coll
	 *            Die Spalte der zu rendernden Zelle
	 * 
	 * @return Den speziellen TableCellRenderer
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int coll) {
		final Component cellComponent = super.getTableCellRendererComponent(
				table, value, isSelected, hasFocus, row, coll);
		if (tabIndex == 1) {
			if (coll == colls - 1) {
				if (Integer.parseInt(gewinn[row]) >= 0) {
					cellComponent.setForeground(Color.GREEN);
					cellComponent.setBackground(Color.WHITE);
					// cellComponent.setFont(cellComponent.getFont().deriveFont(Font.BOLD));
				} else {
					cellComponent.setForeground(Color.RED);
					cellComponent.setBackground(Color.WHITE);
					// cellComponent.setFont(cellComponent.getFont().deriveFont(Font.BOLD));
				}
			} else {
				cellComponent.setForeground(Color.BLACK);
				cellComponent.setBackground(Color.WHITE);
			}
		} else if (tabIndex == 4) {
			if (row >= teilnehmerzahl) {
				cellComponent.setForeground(Color.RED);
				cellComponent.setBackground(Color.WHITE);
			} else {
				cellComponent.setForeground(Color.BLACK);
				cellComponent.setBackground(Color.WHITE);
			}
		} else {
			cellComponent.setForeground(Color.BLACK);
			cellComponent.setBackground(Color.WHITE);
		}

		if (isSelected) {
			// cellComponent.setForeground(table.getSelectionForeground());
			cellComponent.setBackground(table.getSelectionBackground());
			// cellComponent.setBackground(Color.LIGHT_GRAY);
		}
		return cellComponent;
	}
}
