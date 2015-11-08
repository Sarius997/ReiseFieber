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
import dbv.IReiseAnlegen;

/**
 * Diese Klasse verwaltet das Fenster zum erstellen und speichern neuer Reisen.
 * 
 */
public class ReiseAnlegenDialog implements IReiseAnlegen {
	private JFrame frame;

	private JLabel labName;
	private JTextField tfName;

	private JLabel labZiel;
	private JTextField tfZiel;
	private JLabel labTeilnehmerzahl;
	private JTextField tfTeilnehmerzahl;
	private JLabel labBeginn;
	private JTextField tfBeginn;
	private JLabel labEnde;
	private JTextField tfEnde;
	private JLabel labPreisProPerson;
	private JTextField tfPreisProPerson;
	private JLabel labKosten;
	private JTextField tfKosten;

	private JButton bnHinzufuegen;
	private JButton bnAbbrechen;

	/**
	 * Initialisiert das Fenster zum erstellen neuer Reisen.
	 */
	public ReiseAnlegenDialog() {
		frame = new JFrame("Reise anlegen");
		labName = new JLabel("Reisename:");
		tfName = new JTextField();

		labZiel = new JLabel("Reiseziel:");
		tfZiel = new JTextField();
		labTeilnehmerzahl = new JLabel("max. Teilnehmerzahl:");
		tfTeilnehmerzahl = new JTextField();
		labBeginn = new JLabel("Beginn:");
		tfBeginn = new JTextField();
		labEnde = new JLabel("Ende:");
		tfEnde = new JTextField();
		labPreisProPerson = new JLabel("Preis pro Person:");
		tfPreisProPerson = new JTextField();
		labKosten = new JLabel("Kosten f\u00fcr die Reise:");
		tfKosten = new JTextField();

		bnHinzufuegen = new JButton("Hinzuf\u00fcgen");
		bnAbbrechen = new JButton("Abbrechen");

		frame.setLayout(new GridLayout(0, 2, 10, 10));
		frame.add(labName);
		frame.add(tfName);

		frame.add(labZiel);
		frame.add(tfZiel);
		frame.add(labTeilnehmerzahl);
		frame.add(tfTeilnehmerzahl);
		frame.add(labBeginn);
		frame.add(tfBeginn);
		frame.add(labEnde);
		frame.add(tfEnde);
		frame.add(labPreisProPerson);
		frame.add(tfPreisProPerson);
		frame.add(labKosten);
		frame.add(tfKosten);
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
	 * Wenn ja, wird eine neue Reise mit den eingegeben Daten erstellt.<br>
	 * Wenn noch Felder leer sind, passiert nichts.
	 */
	private void addListeners() {
		bnAbbrechen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ReiseAnlegenDialog.this.fertig();
			}
		});
		bnHinzufuegen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!tfZiel.getText().equals("")
						&& !tfTeilnehmerzahl.getText().equals("")
						&& !tfBeginn.getText().equals("")
						&& !tfEnde.getText().equals("")
						&& !tfPreisProPerson.getText().equals("")
						&& !tfKosten.getText().equals("")) {
					ReiseAnlegenDialog.this.hinzufuegen();
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
			 * Simuliert einen Klick auf den "Hinzufügen"-Button wenn die
			 * Eingabetaste gedrückt wird.
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bnHinzufuegen.doClick();
				}
			}
		};

		tfZiel.addKeyListener(enterListener);
		tfTeilnehmerzahl.addKeyListener(enterListener);
		tfBeginn.addKeyListener(enterListener);
		tfEnde.addKeyListener(enterListener);
		tfPreisProPerson.addKeyListener(enterListener);
		tfKosten.addKeyListener(enterListener);
	}

	/**
	 * Speichert über {@link DatenbankVerbindung#doCreateJourney(IReiseAnlegen)}
	 * eine neue Reise mit den eingegebenen Daten und speichert diese in der
	 * Datenbank.
	 */
	protected void hinzufuegen() {
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			dbv.doCreateJourney(this);
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
	 * @return das eingegebene Reiseziel
	 */
	@Override
	public String getReiseZiel() {
		return tfZiel.getText();
	}

	/**
	 * @return die eingegebene maximale Teilnehmerzahl
	 */
	@Override
	public String getTeilnehmerZahl() {
		return tfTeilnehmerzahl.getText();
	}

	/**
	 * @return den eingegebenen Beginn der Reise
	 */
	@Override
	public String getBeginn() {
		return tfBeginn.getText();
	}

	/**
	 * @return das eingegebene Ende der Reise
	 */
	@Override
	public String getEnde() {
		return tfEnde.getText();
	}

	/**
	 * @return den eingegebenen Preis der pro Person verlangt wird
	 */
	@Override
	public String getPreisProPerson() {
		return tfPreisProPerson.getText();
	}

	/**
	 * @return die eingegebenen Kosten für die Reise
	 */
	@Override
	public String getKosten() {
		return tfKosten.getText();
	}

	/**
	 * @return den eingegebenen Reisenamen
	 */
	@Override
	public String getReiseName() {
		return tfName.getText();
	}
}