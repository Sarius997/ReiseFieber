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
import dbv.IKundenÄndern;

public class KundenDatenÄndern implements IKundenÄndern {
	private JFrame frame;
	private JLabel labID;
	private JTextField tfID;

	private JLabel labKdName;
	private JTextField tfKdName;

	private JLabel labKdVorname;
	private JTextField tfKdVorname;

	private JLabel labWohnort;
	private JTextField tfWohnort;

	private JLabel labGeburtsdatum;
	private JTextField tfGeburtsdatum;

	private JLabel labTelefon;
	private JTextField tfTelefon;

	private JLabel labGeschlecht;
	private JTextField tfGeschlecht;

	private JLabel labAdresse;
	private JTextField tfAdresse;

	private JLabel labPostleitzahl;
	private JTextField tfPostleitzahl;

	private JButton bnÄndern;
	private JButton bnFertig;

	private Kunde kunde;

	public KundenDatenÄndern(Kunde kunde) {
		this.kunde = kunde;
		frame = new JFrame("Kundendaten ändern");

		labID = new JLabel("ID des zu ändernden Kunden:");
		tfID = new JTextField();

		labKdName = new JLabel("Nachname:");
		tfKdName = new JTextField();

		labKdVorname = new JLabel("Vorname:");
		tfKdVorname = new JTextField();

		labWohnort = new JLabel("Wohnort:");
		tfWohnort = new JTextField();

		labGeburtsdatum = new JLabel("Geburtsdatum:");
		tfGeburtsdatum = new JTextField();

		labTelefon = new JLabel("Telefonnummer:");
		tfTelefon = new JTextField();

		labGeschlecht = new JLabel("Geschlecht:");
		tfGeschlecht = new JTextField();

		labAdresse = new JLabel("Adresse:");
		tfAdresse = new JTextField();

		labPostleitzahl = new JLabel("Postleitzahl:");
		tfPostleitzahl = new JTextField();

		bnÄndern = new JButton("Ändern");
		bnFertig = new JButton("Fertig");

		frame.setLayout(new GridLayout(0, 2, 10, 10));

		frame.add(labID);
		frame.add(tfID);
		frame.add(labKdName);
		frame.add(tfKdName);
		frame.add(labKdVorname);
		frame.add(tfKdVorname);
		frame.add(labGeschlecht);
		frame.add(tfGeschlecht);
		frame.add(labGeburtsdatum);
		frame.add(tfGeburtsdatum);
		frame.add(labTelefon);
		frame.add(tfTelefon);
		frame.add(labAdresse);
		frame.add(tfAdresse);
		frame.add(labPostleitzahl);
		frame.add(tfPostleitzahl);
		frame.add(labWohnort);
		frame.add(tfWohnort);
		frame.add(bnÄndern);
		frame.add(bnFertig);
		addActionListeners();
	}

	private void addActionListeners() {
		tfID.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if (tfID != null && !tfID.equals("")) {
					suchen();
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});

		bnFertig.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				KundenDatenÄndern.this.fertig();
			}
		});
		bnÄndern.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KundenDatenÄndern.this.ändern();
			}
		});

	}

	public void show() {
		// TODO rework
		frame.pack();
		frame.show();
	}

	protected void suchen() {
		// TODO Auto-generated method stub
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			String[] idSearch = dbv.doSearchByKundenID(getID());

			tfKdName.setText(idSearch[1]);
			tfKdVorname.setText(idSearch[2]);
			tfGeschlecht.setText(idSearch[3]);
			tfGeburtsdatum.setText(idSearch[4]);
			tfTelefon.setText(idSearch[5]);
			tfAdresse.setText(idSearch[6]);
			tfPostleitzahl.setText(idSearch[7]);
			tfWohnort.setText(idSearch[8]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void ändern() {
		// TODO Auto-generated method stub
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			dbv.doChange(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void fertig() {
		frame.dispose();
	}

	@Override
	public String getVorname() {
		if (tfKdVorname != null) {
			return tfKdVorname.getText();
		}
		return "";
	}

	@Override
	public String getWohnort() {
		if (tfWohnort != null) {
			return tfWohnort.getText();
		}
		return "";
	}

	@Override
	public String getGeburtstag() {
		if (tfGeburtsdatum != null) {
			return tfGeburtsdatum.getText();
		}
		return "";
	}

	@Override
	public String getTelefonnummer() {
		if (tfTelefon != null) {
			return tfTelefon.getText();
		}
		return "";
	}

	@Override
	public String getGeschlecht() {
		if (tfGeschlecht != null) {
			return tfGeschlecht.getText();
		}
		return "";
	}

	@Override
	public String getNachname() {
		if (tfKdName != null) {
			return tfKdName.getText();
		}
		return "";
	}

	@Override
	public String getID() {
		if (tfID != null) {
			return tfID.getText();
		}
		return "";
	}

	@Override
	public String getAdresse() {
		return tfAdresse.getText();
	}

	@Override
	public String getPostleitzahl() {
		return tfPostleitzahl.getText();
	}
}
