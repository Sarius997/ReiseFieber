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
import dbv.IStorno;

/**
 * Diese Klasse zeigt das Fenster zum stornieren von Buchungen an.
 * 
 * @author Markus Hofmann
 * @version 1.0
 * 
 */
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

	/**
	 * Initialisiert das Fenster zum stornieren von Buchungen. Eine Stornierung
	 * kann nicht wieder aufgehoben werden.
	 * 
	 * @param selectedID
	 *            ID der zu stornierenden Buchung.<br>
	 *            Wenn die ID {@code null} ist, wird das Fenster mit leeren
	 *            Textfeldern angezeigt.<br>
	 *            Wenn eine ID übergeben wurde, werden die Daten der Buchung mit
	 *            dieser ID in die Felder übernommen.
	 */
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

		if (selectedID != null) {
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
		addListeners();
	}

	/**
	 * Registriert alle Listener für dieses Fenster.<br>
	 * Wenn keine Buchungsnummer eingegeben ist, passiert nichts.
	 */
	private void addListeners() {
		tfID.addFocusListener(new FocusListener() {

			/**
			 * Wenn aus dem Feld für die Buchungs-ID herausgeklickt wird, mit
			 * der Tabulatortaste weitergeschaltet wird oder das Feld
			 * anderweitig den Fokus verliert, werden aus der Datenbank die
			 * Daten der Buchung mit der eingetragenen ID herausgesucht und in die Entsprechenden Felder
			 * eingetragen.
			 */
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
				if (!tfID.getText().equals("")) {
					ReiseStornierenDialog.this.storno();
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
			 * Wenn die Eingabetaste gedrückt wird, wird der Fokus von dem
			 * Textfeld auf den "Buchung stornieren"-Button gesetzt und dann ein
			 * Klick auf diesen simuliert.
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bnBestaetigen.requestFocus();
					bnBestaetigen.doClick();
				}
			}
		};

		tfID.addKeyListener(enterListener);

	}

	/**
	 * Zeigt das Fenster an.
	 */
	@SuppressWarnings("deprecation")
	public void show() {
		frame.pack();
		frame.show();
	}

	/**
	 * Sucht über {@link DatenbankVerbindung#getBuchungByID(String)} die Buchung
	 * mit der eingegeben ID und übernimmt die Daten dieser Buchung in die
	 * entsprechenden Felder.
	 * 
	 * @param searchId
	 *            die ID der gesuchten Buchung
	 */
	protected void sucheBuchung(String searchId) {
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			String[] buchungSearch = dbv.getBuchungByID(searchId);
			tfID.setText(searchId);
			tfK_ID.setText(buchungSearch[3]);
			tfKdName.setText(buchungSearch[4]);
			tfKdVorname.setText(buchungSearch[5]);

			tfR_ID.setText(buchungSearch[0]);
			tfReiseName.setText(buchungSearch[1]);
			tfReiseZiel.setText(buchungSearch[2]);
			if (buchungSearch[6] != null && buchungSearch[6].equals("f")) {
				tfStorno.setText("hat nicht storniert");
				bnBestaetigen.setEnabled(true);
				bnBestaetigen.setToolTipText(null);
			} else if (buchungSearch[6] != null && buchungSearch[6].equals("t")) {
				tfStorno.setText("hat bereits storniert");
				bnBestaetigen.setEnabled(false);
				bnBestaetigen
						.setToolTipText("Diese Buchung wurde bereits storniert!");
			} else {
				tfStorno.setText("");
				bnBestaetigen.setEnabled(false);
				bnBestaetigen.setToolTipText("Buchung existiert nicht!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Storniert über {@link DatenbankVerbindung#doStorno(IStorno)} die Buchung
	 * mit der eingegeben ID.
	 */
	protected void storno() {
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			dbv.doStorno(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Schließt das Fenster.
	 */
	private void cancel() {
		frame.dispose();
	}

	/**
	 * @return die eingegebene Buchungs-ID
	 */
	@Override
	public String getBuchungsID() {
		return tfID.getText();
	}
}
