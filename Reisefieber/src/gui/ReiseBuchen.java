package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dbv.DatenbankVerbindung;
import dbv.IKundenReise;

/**
 * Diese Klasse verwaltet das Fenster zum buchen von Reisen.
 * 
 * @author Markus Hofmann
 * @version 1.0
 * 
 */
public class ReiseBuchen implements IKundenReise {

	private JFrame frame;
	private JLabel labKdNr;
	private JTextField tfKdNr;

	private JLabel labKdName;
	private JLabel labShowKdName;

	private JLabel labKdVorname;
	private JLabel labShowKdVorname;

	private JLabel labReiseID;
	private JTextField tfReiseID;

	private JLabel labReiseName;
	private JLabel labShowReiseName;

	private JLabel labReiseZiel;
	private JLabel labShowReiseZiel;

	private JButton bnHinzufuegen;
	private JButton bnAbbrechen;

	/**
	 * Initialisiert das Fenster.<br>
	 * Wenn ein Parameter {@code null} ist oder beide Parameter {@code null}
	 * sind, sind die Textfelder in dem angezeigten Fenster leer.<br>
	 * Wenn kein Parameter {@code null} ist, werden die Daten der Reise / des
	 * Kunden mit der übergebenen ID automatisch in die Textfelder übernommen.
	 * 
	 * @param selectedID
	 *            ID der ausgewählten Reise oder des ausgewählten Kunden
	 * @param selectedTable
	 *            {@code Kunde} wenn ein Kunde ausgewählt ist, {@code Reise}
	 *            wenn eine Reise ausgewählt ist.
	 * 
	 */
	public ReiseBuchen(String selectedID, String selectedTable) {
		frame = new JFrame("Kunde zu Reise hinzuf\u00fcgen");
		labKdNr = new JLabel("Kundennummer:");
		tfKdNr = new JTextField();
		labKdName = new JLabel("Name:");
		labShowKdName = new JLabel();
		labKdVorname = new JLabel("Vorname:");
		labShowKdVorname = new JLabel();

		labReiseID = new JLabel("Reisenummer:");
		tfReiseID = new JTextField();

		labReiseName = new JLabel("Reisename:");
		labShowReiseName = new JLabel();

		labReiseZiel = new JLabel("Reiseziel:");
		labShowReiseZiel = new JLabel();

		bnHinzufuegen = new JButton("Hinzuf\u00fcgen");
		bnAbbrechen = new JButton("Abbrechen");

		if (selectedID != null && selectedTable != null) {
			if (selectedTable.equals("Kunde")) {
				sucheKunde(selectedID);
			} else if (selectedTable.equals("Reise")) {
				sucheReise(selectedID);
			}
		}

		frame.setLayout(new GridLayout(0, 2, 10, 10));
		frame.add(labKdNr);
		frame.add(tfKdNr);
		frame.add(labKdName);
		frame.add(labShowKdName);
		frame.add(labKdVorname);
		frame.add(labShowKdVorname);

		frame.add(labReiseID);
		frame.add(tfReiseID);
		frame.add(labReiseName);
		frame.add(labShowReiseName);
		frame.add(labReiseZiel);
		frame.add(labShowReiseZiel);

		frame.add(bnHinzufuegen);
		frame.add(bnAbbrechen);

		addListeners();
	}

	/**
	 * Zeigt das Fenster an.
	 */
	public void show() {
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Registriert die Listener für dieses Fenster
	 */
	private void addListeners() {

		tfKdNr.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				sucheKunde(getKundeID());
			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});

		tfReiseID.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				sucheReise(getReiseID());
			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});
		bnAbbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReiseBuchen.this.fertig();
			}
		});
		bnHinzufuegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReiseBuchen.this.hinzufuegen();
				frame.dispose();
			}
		});
	}

	/**
	 * Sucht über {@link DatenbankVerbindung#doSearchByKundenID(String)} aus der
	 * Datenbank Nachname und Vorname des Kunden mit der übergebenen ID heraus
	 * und übernimmt sie in die entsprechenden Textfelder des Fensters.
	 * 
	 * @param searchId
	 *            die ID des gesuchten Kunden
	 */
	protected void sucheKunde(String searchId) {
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			String[] idSearch = dbv.doSearchByKundenID(searchId);
			tfKdNr.setText(searchId);
			labShowKdName.setText(idSearch[1]);
			labShowKdVorname.setText(idSearch[2]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sucht über {@link DatenbankVerbindung#doSearchByReiseID(String)} aus der
	 * Datenbank Name und Ziel der Reise mit der übergebenen ID heraus und
	 * übernimmt sie in die entsprechenden Textfelder des Fensters.
	 * 
	 * @param searchId
	 *            die ID der gesuchten Reise
	 */
	protected void sucheReise(String searchId) {
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			String[] idSearch = dbv.doSearchByReiseID(searchId);
			tfReiseID.setText(searchId);
			labShowReiseName.setText(idSearch[1]);
			labShowReiseZiel.setText(idSearch[2]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Speichert über
	 * {@link DatenbankVerbindung#doInsertInJourney(IKundenReise)} eine neue
	 * Buchung mit der Kunden-ID und Reise-ID in der Datenbank.
	 */
	protected void hinzufuegen() {
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			dbv.doInsertInJourney(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Schließt das Fenster
	 */
	private void fertig() {
		frame.dispose();
	}

	/**
	 * @return die eingegebene Kunden-ID
	 */
	@Override
	public String getKundeID() {
		return tfKdNr.getText();
	}

	/**
	 * @return die eingegebene Reise-ID
	 */
	@Override
	public String getReiseID() {
		return tfReiseID.getText();
	}
}