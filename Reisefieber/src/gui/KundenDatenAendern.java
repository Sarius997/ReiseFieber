package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dbv.DatenbankVerbindung;
import dbv.IKundenAendern;

/**
 * Diese Klasse verwaltet das Fenster zum ändern von gespeicherten Kundendaten.
 * 
 * @author Markus Hofmann
 * @version 1.0
 * 
 */
public class KundenDatenAendern implements IKundenAendern {
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

	private JButton bnAendern;
	private JButton bnAbbrechen;

	/**
	 * Initialisiert das Fenster zum ändern von Kundendaten.
	 * 
	 * @param selectedID
	 *            ID des zu ändernden Kunden.<br>
	 *            Wenn die ID {@code null} ist, wird das Fenster mit leeren
	 *            Textfeldern angezeigt.<br>
	 *            Wenn eine ID übergeben wurde, werden die gespeicherten Daten
	 *            des Kunden mit der übergebenen ID in die Felder übernommen.
	 */
	public KundenDatenAendern(String selectedID) {
		frame = new JFrame("Kundendaten \u00e4ndern");

		labID = new JLabel("ID des zu \u00e4ndernden Kunden:");
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

		bnAendern = new JButton("\u00c4ndern");
		bnAbbrechen = new JButton("Abbrechen");

		frame.setLayout(new GridLayout(0, 2, 10, 10));

		if (selectedID != null) {
			suchen(selectedID);
		}

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
		frame.add(bnAendern);
		frame.add(bnAbbrechen);
		addListeners();
	}

	/**
	 * Registriert die Listener für dieses Fenster.<br>
	 * Wenn die Eingabetaste gedrückt oder auf den "Ändern"-Button geklickt
	 * wird, wird überprüft ob alle Felder ausgefüllt sind.<br>
	 * Wenn ja, werden die Daten des Kunden durch die neuen Daten ersetzt.<br>
	 * Wenn noch Felder leer sind, passiert nichts.
	 */
	private void addListeners() {
		tfID.addFocusListener(new FocusListener() {

			/**
			 * Wenn das Textfeld mit der ID des zu ändernden Kunden den Fokus
			 * verliert, werden die Daten des Kunden mit der eingegebenen ID aus
			 * der Datenbank herausgesucht und in die entsprechenden Felder
			 * eingetragen.
			 */
			@Override
			public void focusLost(FocusEvent e) {
				if (tfID != null && !tfID.equals("")) {
					suchen(getID());
				}
			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});

		bnAbbrechen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				KundenDatenAendern.this.fertig();
			}
		});
		bnAendern.addActionListener(new ActionListener() {
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
					KundenDatenAendern.this.aendern();
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
			 * Simuliert einen Klick auf den "Ändern"-Button wenn die
			 * Eingabetaste gedrückt wird.
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bnAendern.doClick();
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
	 * Zeigt das Fenster an.
	 */
	public void show() {
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Sucht über {@link DatenbankVerbindung#doSearchByKundenID(String)} die
	 * Daten des Kunden mit der eingegebenen ID aus der Datenbank heraus und
	 * übernimmt sie in die entsprechenden Felder.
	 * 
	 * @param searchId
	 *            die ID des gesuchten Kunden
	 */
	protected void suchen(String searchId) {
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			String[] idSearch = dbv.doSearchByKundenID(searchId);
			tfID.setText(searchId);
			tfKdName.setText(idSearch[1]);
			tfKdVorname.setText(idSearch[2]);
			tfGeschlecht.setText(idSearch[3]);
			tfGeburtsdatum.setText(idSearch[4]);
			tfTelefon.setText(idSearch[5]);
			tfAdresse.setText(idSearch[6]);
			tfPostleitzahl.setText(idSearch[7]);
			tfWohnort.setText(idSearch[8]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ändert über {@link DatenbankVerbindung#doChange(IKundenAendern)} die in
	 * der Datenbank gespeicherten Daten des Kunden mit der eingegebenen ID.
	 */
	protected void aendern() {
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			dbv.doChange(this);
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
	 * @return die eingegebene ID
	 */
	@Override
	public String getID() {
		if (tfID != null) {
			return tfID.getText();
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
