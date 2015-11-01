package reiseFieber;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomTableRenderer extends DefaultTableCellRenderer {
	private int teilnehmerzahl;
	private int colls;
	private int tabIndex;
	private String[] gewinn;

	public CustomTableRenderer(int teilnehmerzahl, int tabIndex) {
		this.teilnehmerzahl = teilnehmerzahl;
		this.tabIndex = tabIndex;
	}

	public CustomTableRenderer(int colls, int tabIndex, String[] gewinn) {
		this.colls = colls;
		this.tabIndex = tabIndex;
		this.gewinn = gewinn;
	}

	public CustomTableRenderer(int tabIndex) {
		this.tabIndex = tabIndex;
	}

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
