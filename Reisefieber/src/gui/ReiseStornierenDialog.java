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
	private JLabel tfK_ID;

	private JLabel labKdName;
	private JLabel tfKdName;

	private JLabel labKdVorname;
	private JLabel tfKdVorname;

	private JLabel labR_ID;
	private JLabel tfR_ID;

	private JLabel labReiseName;
	private JLabel tfReiseName;

	private JLabel labReiseZiel;
	private JLabel tfReiseZiel;
	
	private JLabel labStorno;
	private JLabel tfStorno;

	private JButton bnBestaetigen;
	private JButton bnAbbrechen;

	public ReiseStornierenDialog(String selectedID) {
		labID = new JLabel("Buchungsnummer:");
		tfID = new JTextField();

		labK_ID = new JLabel("Kundennummer:");
		tfK_ID = new JLabel();

		labKdName = new JLabel("Nachname:");
		tfKdName = new JLabel();

		labKdVorname = new JLabel("Vorname:");
		tfKdVorname = new JLabel();

		labR_ID = new JLabel("Reisenummer:");
		tfR_ID = new JLabel();

		labReiseName = new JLabel("Reisename:");
		tfReiseName = new JLabel();

		labReiseZiel = new JLabel("Reiseziel:");
		tfReiseZiel = new JLabel();
		
		labStorno = new JLabel("Storniert:");
		tfStorno = new JLabel();		

		bnBestaetigen = new JButton("Buchung stornieren");
		bnAbbrechen = new JButton("Abbrechen");		
		
		
		if(selectedID!= null){
			sucheBuchung(selectedID);
		}		
		

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
				sucheBuchung(getBuchungsID());
			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});
		bnAbbrechen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ReiseStornierenDialog.this.cancel();
			}
		});
		bnBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReiseStornierenDialog.this.storno();
			}
		});

	}

	@SuppressWarnings("deprecation")
	public void show() {
		frame.pack();
		frame.show();
	}

	protected void sucheBuchung(String searchId) {
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			String[] buchungSearch = dbv.getBuchungByID(searchId);
			tfID.setText(searchId);
			tfK_ID.setText(buchungSearch[0]);
			tfKdName.setText(buchungSearch[1]);
			tfKdVorname.setText(buchungSearch[2]);

			tfR_ID.setText(buchungSearch[3]);
			tfReiseName.setText(buchungSearch[4]);
			tfReiseZiel.setText(buchungSearch[5]);
			if(buchungSearch[6] != null && buchungSearch[6].equals("f")){
				tfStorno.setText("hat nicht storniert");
				bnBestaetigen.setEnabled(true);
				bnBestaetigen.setToolTipText(null);
			} else if(buchungSearch[6] != null && buchungSearch[6].equals("t")){
				tfStorno.setText("hat bereits storniert");
				bnBestaetigen.setEnabled(false);
				bnBestaetigen.setToolTipText("Diese Buchung wurde bereits storniert!");
			} else {
				tfStorno.setText("");
				bnBestaetigen.setEnabled(false);
				bnBestaetigen.setToolTipText("Buchung existiert nicht!");
			}
		} catch (Exception e) {
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
