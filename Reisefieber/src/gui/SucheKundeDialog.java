package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dbv.DatenbankVerbindung;
import dbv.IKundenSuchen;

public class SucheKundeDialog implements IKundenSuchen { // implementiert das
															// Interface
															// IKundenSuchen
	private JFrame frame; // Es kann nur mit dem Nachnamen gesucht werden
	private JLabel labEingebeaufforderung;
	// JLabel labKdNr;
	// JTextField tfKdNr;
	private JLabel labKdName;
	private JTextField tfKdName;
	private JButton bnSuchen;
	private JButton bnAbbrechen;

	private ReiseFieberGUI gui;

	public SucheKundeDialog(ReiseFieberGUI gui) {
		this.gui = gui;

		frame = new JFrame();
		frame.setTitle("Suche");
		labEingebeaufforderung = new JLabel(
				"Bitte geben Sie den Kundennamen ein.");
		// labKdNr=new JLabel("Kundennummer:");
		// tfKdNr=new JTextField();
		labKdName = new JLabel("Kundenname:");
		tfKdName = new JTextField();
		bnSuchen = new JButton("Suchen");
		bnAbbrechen = new JButton("Abbrechen");

		frame.setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(bnSuchen);
		buttonPanel.add(bnAbbrechen);

		JPanel suchPanel = new JPanel(new GridLayout(0, 2, 10, 10));

		// suchPanel.add(labKdNr);
		// suchPanel.add(tfKdNr);
		suchPanel.add(labKdName);
		suchPanel.add(tfKdName);

		frame.add(labEingebeaufforderung, BorderLayout.NORTH);
		frame.add(suchPanel, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		addActionListeners();
	}

	public void show() {
		// TODO rework
		frame.pack();
		frame.setVisible(true);
	}

	private void addActionListeners() {
		bnSuchen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SucheKundeDialog.this.suchenKunde();
				frame.dispose();
			}
		});
		bnAbbrechen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SucheKundeDialog.this.abbrechen();
			}
		});
		KeyListener enterListener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bnSuchen.doClick();
				}
			}
		};

		tfKdName.addKeyListener(enterListener);
	}

	protected void suchenKunde() { // baut Verbindung zur Datenbank auf und
									// führt die Suche aus
		// TODO Auto-generated method stub
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			String[][] searchResult = dbv.doSearch(this);
			gui.showSearchResultData(searchResult);
			;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * private void sucheKunde(){ System.out.println("Suchen wurde gedrückt.");
	 * String eingabe=tfKdName.getText();
	 * System.out.println("Kundenname ist "+eingabe);
	 * System.out.println("Es gibt "+ kunden.getAnzahlKunden()+" Kunden");
	 * 
	 * }
	 */
	private void abbrechen() {
		frame.dispose();
	}

	@Override
	public String getKdName() {
		if (tfKdName != null) {
			return tfKdName.getText();
		}
		return "";
	}
}
