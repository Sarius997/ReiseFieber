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

/**
 * Diese Klasse verwaltet das Fenster, um die Teilnehmer einer bestimmten Reise
 * über die Reisenummer zu suchen.
 * 
 */
public class ReiseTeilnehmer {

	private JFrame frame;
	private JLabel labReiseID;
	private JTextField tfReiseID;

	private JButton bnAnzeigen;
	private JButton bnAbbrechen;
	private ReiseFieberGUI gui;

	/**
	 * Initialisiert das Fenster zum Suchen von Buchungen für eine bestimmte
	 * Reise.
	 * 
	 * @param selectedID
	 *            <code>null</code> wenn keine Reise ausgewählt ist, dann wird auch
	 *            das Fenster angezeigt, in das dann die ID der Reise eingegeben
	 *            werden muss.<br>
	 *            Ansonsten wird die ID der ausgewählten Reise übergeben und es
	 *            werden sofort die Buchungen für diese Reise angezeigt.
	 * @param gui
	 *            Instanz der Benutzeroberfläche. Wird genutzt, um die
	 *            Suchergebnisse anzuzeigen.
	 */
	public ReiseTeilnehmer(String selectedID, ReiseFieberGUI gui) {
		this.gui = gui;
		if (selectedID == null) {
			frame = new JFrame("Reiseteilnehmer anzeigen");
			labReiseID = new JLabel("Reisenummer:");
			tfReiseID = new JTextField();

			bnAnzeigen = new JButton("Anzeigen");
			bnAbbrechen = new JButton("Abbrechen");

			frame.setLayout(new GridLayout(0, 2, 10, 10));
			frame.add(labReiseID);
			frame.add(tfReiseID);

			frame.add(bnAnzeigen);
			frame.add(bnAbbrechen);

			addListeners();
		} else {
			tfReiseID = new JTextField();
			tfReiseID.setText(selectedID);
			anzeigen();
		}
	}

	/**
	 * Zeigt das Fenster an.
	 */
	public void show() {
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Registriert alle Listener für das Fenster.<br>
	 * Wenn die Eingabetaste gedrückt oder auf den "Anzeigen"-Button geklickt
	 * wird, wird überprüft, ob das Suchfeld leer ist.<br>
	 * Wenn es nicht leer ist, wird die Suche nach Buchungen für die Reise mit
	 * der eingegebenen ID ausgeführt.<br>
	 * Wenn es leer ist, kann nicht gesucht werden.
	 */
	private void addListeners() {
		bnAbbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReiseTeilnehmer.this.fertig();
			}
		});
		bnAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!tfReiseID.getText().equals("")) {
					ReiseTeilnehmer.this.anzeigen();
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
			 * Simuliert beim Drücken der Eingabetaste einen Klick auf den
			 * "Anzeigen"-Button.
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bnAnzeigen.doClick();
				}
			}
		};

		tfReiseID.addKeyListener(enterListener);
	}

	/**
	 * Führt über {@link DatenbankVerbindung#reiseTeilnehmer(String, String)} in
	 * der Datenbank die Suche nach Buchungen für die Reise mit der eingegebenen
	 * ID, die nicht storniert wurden, aus und zeigt die Suchergebnisse in der
	 * Benutzeroberfläche an.
	 */
	protected void anzeigen() {
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			String[][] result = dbv.reiseTeilnehmer(getReiseID(), "false");
			gui.showReiseTeilnehmer(result);
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
	 * 
	 * @return Die eingegebene Reise-ID
	 */
	private String getReiseID() {
		return this.tfReiseID.getText();
	}
}
