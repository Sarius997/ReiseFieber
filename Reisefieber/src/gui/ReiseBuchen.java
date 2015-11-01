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

		addActionListeners();
	}

	public void show() {
		frame.pack();
		frame.setVisible(true);
	}

	private void addActionListeners() {

		tfKdNr.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				sucheKunde(getKundeID());
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});

		tfReiseID.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				sucheReise(getReiseID());
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

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

	protected void hinzufuegen() {
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			dbv.doInsertInNewJourney(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fertig() {
		frame.dispose();
	}

	@Override
	public String getKundeID() {
		// TODO Auto-generated method stub
		return tfKdNr.getText();
	}

	@Override
	public String getReiseID() {
		// TODO Auto-generated method stub
		return tfReiseID.getText();
	}
}