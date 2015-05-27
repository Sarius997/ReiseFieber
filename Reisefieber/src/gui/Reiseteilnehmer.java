package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.concurrent.LinkedTransferQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dbv.DatenbankVerbindung;
import dbv.IKundenDaten;
import dbv.IKundenReise;
import kundenkartei.Kunde;
import kundenkartei.KundenListe;

public class Reiseteilnehmer implements IKundenReise {

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
	private JButton bnFertig;

	private Kunde kunde;

	public Reiseteilnehmer(Kunde kunde) {
		this.kunde = kunde;
		frame = new JFrame("Kunde zu Reise hinzufügen");
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

		bnHinzufuegen = new JButton("Hinzufügen");
		bnFertig = new JButton("Fertig");

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
		frame.add(bnFertig);

		addActionListeners();
	}

	public void show() {
		frame.pack();
		frame.show();
	}

	private void addActionListeners() {

		tfKdNr.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				sucheKunde();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		tfReiseID.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				sucheReise();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		bnFertig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Reiseteilnehmer.this.fertig();
			}
		});
		bnHinzufuegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Reiseteilnehmer.this.hinzufuegen();
			}
		});
	}

	protected void sucheKunde(){
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			String[] idSearch = dbv.doSearchByKundenID(getKundeID());			
			labShowKdName.setText(idSearch[1]);
			labShowKdVorname.setText(idSearch[2]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	protected void sucheReise(){
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			String[] idSearch = dbv.doSearchByReiseID(getReiseID());			
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