package reiseFieber;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class JTableNoEditing extends JTable{
	public JTableNoEditing() {
		super();
	}

	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}
}
