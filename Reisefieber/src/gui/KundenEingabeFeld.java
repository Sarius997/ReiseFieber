package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dbv.DatenbankVerbindung;
import dbv.IKundenDaten;

/**
 * Diese Klasse verwaltet das Fenster zum speichern der Daten eines neuen Kunden.
 * 
 * @author Markus Hofmann
 * @version 1.0
 * 
 */
public class KundenEingabeFeld implements IKundenDaten {
	private JFrame frame;

	private JLabel labKdName;
	private JTextField tfKdName;
	private JLabel labKdVorname;
	private JTextField tfKdVorname;
	private JLabel labGeschlecht;
	private JTextField tfGeschlecht;
	private JLabel labGeburtsdatum;
	private JTextField tfGeburtsdatum;
	private JLabel labTelefon;
	private JTextField tfTelefon;
	private JLabel labWohnort;
	private JTextField tfWohnort;

	private JLabel labAdresse;
	private JTextField tfAdresse;
	private JLabel labPostleitzahl;
	private JTextField tfPostleitzahl;

	private JButton bnHinzufuegen;
	private JButton bnAbbrechen;

	/**
	 * Initialisiert das Fenster zum speichern der Daten von neuen Kunden.
	 */
	public KundenEingabeFeld() {
		frame = new JFrame("Kunde hinzuf\u00fcgen");

		labKdName = new JLabel("Name:");
		tfKdName = new JTextField();
		labKdVorname = new JLabel("Vorname:");
		tfKdVorname = new JTextField();
		labGeburtsdatum = new JLabel("Geburtsdatum:");
		tfGeburtsdatum = new JTextField();
		labGeschlecht = new JLabel("Geschlecht:");
		tfGeschlecht = new JTextField();
		labTelefon = new JLabel("Telefonnummer:");
		tfTelefon = new JTextField();
		labWohnort = new JLabel("Wohnort:");
		tfWohnort = new JTextField();

		labAdresse = new JLabel("Adresse:");
		tfAdresse = new JTextField();
		labPostleitzahl = new JLabel("Postleitzahl:");
		tfPostleitzahl = new JTextField();

		bnHinzufuegen = new JButton("Hinzuf\u00fcgen");
		bnAbbrechen = new JButton("Abbrechen");

		frame.setLayout(new GridLayout(0, 2, 10, 10));

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
	 * Registriert die Listener für dieses Fenster.<br>
	 * Wenn die Eingabetaste gedrückt oder auf den "Hinzufügen"-Button geklickt
	 * wird, wird überprüft ob alle Felder ausgefüllt sind.<br>
	 * Wenn ja, werden die Daten in der Datenbank gespeichert.<br>
	 * Wenn noch Felder leer sind, passiert nichts.
	 */
	private void addListeners() {
		bnAbbrechen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				KundenEingabeFeld.this.fertig();
			}
		});
		bnHinzufuegen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!tfKdName.getText().equals("")
						&& !tfKdVorname.getText().equals("")
						&& !tfGeschlecht.getText().equals("")
						&& !tfGeburtsdatum.getText().equals("")
						&& !tfTelefon.getText().equals("")
						&& !tfAdresse.getText().equals("")
						&& !tfPostleitzahl.getText().equals("")
						&& !tfWohnort.getText().equals("")) {
					KundenEingabeFeld.this.hinzufuegen();
					frame.dispose();
				}
			}
		});

		KeyListener enterListener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			/**
			 * Simuliert beim drücken der Eingabetaste einen Klick auf den
			 * "Hinzufügen"-Button.
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bnHinzufuegen.doClick();
				}
			}
		};

		tfKdName.addKeyListener(enterListener);
		tfKdVorname.addKeyListener(enterListener);
		tfGeschlecht.addKeyListener(enterListener);
		tfGeburtsdatum.addKeyListener(enterListener);
		tfTelefon.addKeyListener(enterListener);
		tfAdresse.addKeyListener(enterListener);
		tfPostleitzahl.addKeyListener(enterListener);
		tfWohnort.addKeyListener(enterListener);
	}

	/**
	 * Speichert die Daten des neuen Kunden über
	 * {@link DatenbankVerbindung#doInsert(IKundenDaten)} in der Datenbank.
	 */
	protected void hinzufuegen() {
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			dbv.doInsert(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Schließt das Fenster.
	 */
	private void fertig() {
		frame.dispose();
	}

	/**
	 * @return den eingegebenen Vornamen
	 */
	@Override
	public String getVorname() {

		if (tfKdVorname != null) {
			return tfKdVorname.getText();
		}
		return "";
	}

	/**
	 * @return den eingegebenen Nachnamen
	 */
	@Override
	public String getNachname() {
		if (tfKdName != null) {
			return tfKdName.getText();
		}
		return "";
	}

	/**
	 * @return den eingegebenen Wohnort
	 */
	@Override
	public String getWohnort() {
		if (tfWohnort != null) {
			return tfWohnort.getText();
		}
		return "";
	}

	/**
	 * @return den eingegebenen Geburtstag
	 */
	@Override
	public String getGeburtstag() {
		if (tfGeburtsdatum != null) {
			return tfGeburtsdatum.getText();
		}
		return "";
	}

	/**
	 * @return die eingegebene Telefonnummer
	 */
	@Override
	public String getTelefonnummer() {
		if (tfTelefon != null) {
			return tfTelefon.getText();
		}
		return "";
	}

	/**
	 * @return das eingegebene Geschlecht
	 */
	@Override
	public String getGeschlecht() {
		if (tfGeschlecht != null) {
			return tfGeschlecht.getText();
		}
		return "";
	}

	/**
	 * @return die eingegebene Adresse
	 */
	@Override
	public String getAdresse() {
		return tfAdresse.getText();
	}

	/**
	 * @return die eingegebene Postleitzahl
	 */
	@Override
	public String getPostleitzahl() {
		return tfPostleitzahl.getText();
	}
}