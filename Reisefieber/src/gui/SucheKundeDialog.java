package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import kundenkartei.KundenListe;
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
	KundenListe kunden;

	public SucheKundeDialog(KundenListe kunden) {
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
		this.kunden = kunden;

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
		frame.show();
	}

	private void addActionListeners() {
		bnSuchen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SucheKundeDialog.this.suchenKunde();
			}
		});
		bnAbbrechen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SucheKundeDialog.this.abbrechen();
			}
		});
	}

	protected void suchenKunde() { // baut Verbindung zur Datenbank auf und
									// f�hrt die Suche aus
		// TODO Auto-generated method stub
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			dbv.doSearch(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * private void sucheKunde(){ System.out.println("Suchen wurde gedr�ckt.");
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
