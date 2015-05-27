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

import kundenkartei.Kunde;
import dbv.DatenbankVerbindung;
import dbv.IStorno;

public class ReiseStornierenDialog implements IStorno {

	private JFrame frame;
	private JLabel labID;
	private JTextField tfID;

	private JLabel labK_ID;
	private JTextField tfK_ID;

	private JLabel labKdName;
	private JLabel tfKdName;

	private JLabel labKdVorname;
	private JLabel tfKdVorname;

	private JLabel labR_ID;
	private JTextField tfR_ID;

	private JLabel labReiseName;
	private JLabel tfReiseName;

	private JLabel labReiseZiel;
	private JLabel tfReiseZiel;
	
	private JLabel labStorno;
	private JLabel tfStorno;

	private JButton bnBestaetigen;
	private JButton bnAbbrechen;

	public ReiseStornierenDialog() {
		labID = new JLabel("Buchungsnummer:");
		tfID = new JTextField();

		labK_ID = new JLabel("Kundennummer:");
		tfK_ID = new JTextField();

		labKdName = new JLabel("Nachname:");
		tfKdName = new JLabel();

		labKdVorname = new JLabel("Vorname:");
		tfKdVorname = new JLabel();

		labR_ID = new JLabel("Reisenummer:");
		tfR_ID = new JTextField();

		labReiseName = new JLabel("Reisename:");
		tfReiseName = new JLabel();

		labReiseZiel = new JLabel("Reiseziel:");
		tfReiseZiel = new JLabel();
		
		labStorno = new JLabel("Storniert:");
		tfStorno = new JLabel();		

		bnBestaetigen = new JButton("Buchung stornieren");
		bnAbbrechen = new JButton("Abbrechen");

		frame = new JFrame("Buchung stornieren");
		frame.setLayout(new GridLayout(0, 2, 10, 10));

		frame.add(labID);
		frame.add(tfID);
		frame.add(labK_ID);
		frame.add(tfK_ID);
		frame.add(labKdName);
		frame.add(tfKdName);
		frame.add(labKdVorname);
		frame.add(tfKdVorname);

		frame.add(labR_ID);
		frame.add(tfR_ID);
		frame.add(labReiseName);
		frame.add(tfReiseName);
		frame.add(labReiseZiel);
		frame.add(tfReiseZiel);
		
		frame.add(labStorno);
		frame.add(tfStorno);

		frame.add(bnBestaetigen);
		frame.add(bnAbbrechen);
		addActionListeners();
	}

	private void addActionListeners() {
		tfID.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				sucheBuchung();
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		bnAbbrechen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ReiseStornierenDialog.this.cancel();
			}
		});
		bnBestaetigen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ReiseStornierenDialog.this.storno();
			}
		});

	}

	public void show() {
		// TODO rework
		frame.pack();
		frame.show();
	}

	protected void sucheBuchung() {
		// TODO Auto-generated method stub
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			String[] buchungSearch = dbv.getBuchungByID(getBuchungsID());
			tfK_ID.setText(buchungSearch[0]);
			tfKdName.setText(buchungSearch[1]);
			tfKdVorname.setText(buchungSearch[2]);

			tfR_ID.setText(buchungSearch[3]);
			tfReiseName.setText(buchungSearch[4]);
			tfReiseZiel.setText(buchungSearch[5]);
			tfStorno.setText(buchungSearch[6]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void storno() {
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			dbv.doStorno(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cancel() {
		frame.dispose();
	}

	@Override
	public String getBuchungsID() {
		return tfID.getText();
	}
}
