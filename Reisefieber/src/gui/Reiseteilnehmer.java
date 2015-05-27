package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.LinkedTransferQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dbv.DatenbankVerbindung;
import dbv.IKundenDaten;
import dbv.IReiseteilnehmer;
import kundenkartei.Kunde;
import kundenkartei.KundenListe;

public class Reiseteilnehmer implements IReiseteilnehmer {

	private JFrame frame;
	private JLabel labKdNr;
	private JTextField tfKdNr;

	private JLabel labKdName;
	private JTextField tfKdName;
	
	private JLabel labKdVorname;
	private JTextField tfKdVorname;
	
	private JLabel labReiseID;
	private JTextField tfReiseID;
	
	private JLabel labReiseZiel;
	private JTextField tfReiseZiel;
	
	private JButton bnHinzufuegen;
	private JButton bnFertig;

	private Kunde kunde;

	public Reiseteilnehmer(Kunde kunde) {
		this.kunde = kunde;
		frame = new JFrame("Kunde zu Reise hinzufügen");
		labKdNr = new JLabel("Kundennummer:");
		tfKdNr = new JTextField();
		labKdName = new JLabel("Name:");
		tfKdName = new JTextField();
		labKdVorname = new JLabel("Vorname:");
		tfKdVorname = new JTextField();
		
		

		bnHinzufuegen = new JButton("Hinzufügen");
		bnFertig = new JButton("Fertig");

		frame.setLayout(new GridLayout(0, 2, 10, 10));
		frame.add(labKdNr);
		frame.add(tfKdNr);
		frame.add(labKdName);
		frame.add(tfKdName);
		frame.add(labKdVorname);
		frame.add(tfKdVorname);
		frame.add(bnHinzufuegen);
		frame.add(bnFertig);

		addActionListeners();
	}

	public void show() {
		//TODO rework
		frame.pack();
		frame.show();
	}

	private void addActionListeners() {
		bnFertig.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Reiseteilnehmer.this.fertig();
			}
		});
		bnHinzufuegen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Reiseteilnehmer.this.hinzufuegen();
			}
		});
	}

	protected void hinzufuegen() { // baut Verbindung zur Datenbank auf und
									// erstellt neuen Eintrag
		// TODO Auto-generated method stub
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			dbv.doInsertInNewJourney(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void fertig() {
		frame.dispose();
	}

	@Override
	public String getKdNr() {
		return tfKdNr.getText();
	}

	@Override
	public String getVorname() {
		return tfKdVorname.getText();
	}

	@Override
	public String getNachname() {
		return tfKdName.getText();
	}
}