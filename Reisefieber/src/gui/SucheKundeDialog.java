package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dbv.DatenbankVerbindung;
import dbv.IKundenSuchen;

/**
 * Diese Klasse zeigt das Suchfenster an.
 * 
 * @author Markus Hofmann
 * @version 1.0
 * 
 */
public class SucheKundeDialog implements IKundenSuchen { // implementiert das
															// Interface
															// IKundenSuchen
	private JFrame frame; // Es kann nur mit dem Nachnamen gesucht werden
	private JLabel labEingebeaufforderung;
	private JLabel labKdName;
	private JTextField tfKdName;
	private JButton bnSuchen;
	private JButton bnAbbrechen;

	private ReiseFieberGUI gui;

	/**
	 * Initialisiert das Suchfenster.
	 * 
	 * @param gui
	 *            Instanz der Benutzeroberfläche. Wird genutzt um die
	 *            Suchergebnisse anzuzeigen.
	 */
	public SucheKundeDialog(ReiseFieberGUI gui) {
		this.gui = gui;

		frame = new JFrame();
		frame.setTitle("Suche");
		labEingebeaufforderung = new JLabel(
				"Bitte geben Sie den Kundennamen ein.");
		labKdName = new JLabel("Kundenname:");
		tfKdName = new JTextField();
		bnSuchen = new JButton("Suchen");
		bnAbbrechen = new JButton("Abbrechen");

		frame.setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(bnSuchen);
		buttonPanel.add(bnAbbrechen);

		JPanel suchPanel = new JPanel(new GridLayout(0, 2, 10, 10));

		suchPanel.add(labKdName);
		suchPanel.add(tfKdName);

		frame.add(labEingebeaufforderung, BorderLayout.NORTH);
		frame.add(suchPanel, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		addListeners();
	}

	/**
	 * Zeigt das Suchfenster an.
	 */
	public void show() {
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Registriert alle Listener für das Suchfenster.<br>
	 * Wenn die Eingabetaste gedrückt oder auf den "Suchen"-Button geklickt
	 * wird, wird überprüft ob das Suchfeld leer ist.<br>
	 * Wenn es nicht leer ist wird die Suche nach Kunden mit dem eingegebenen
	 * Namen ausgeführt.<br>
	 * Wenn es leer ist, passiert nichts.
	 * 
	 */
	private void addListeners() {

		bnSuchen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!tfKdName.getText().equals("")) {
					SucheKundeDialog.this.suchenKunde();
					frame.dispose();
				}
			}
		});
		bnAbbrechen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SucheKundeDialog.this.abbrechen();
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
			 * "Suchen"-Button
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bnSuchen.doClick();
				}
			}
		};

		tfKdName.addKeyListener(enterListener);
	}

	/**
	 * Führt über {@link DatenbankVerbindung#doSearch(IKundenSuchen)} die Suche
	 * in der Datenbank aus und zeigt die Suchergebnisse in der
	 * Benutzeroberfläche an.
	 */
	protected void suchenKunde() {
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			String[][] searchResult = dbv.doSearch(this);
			gui.showSearchResultData(searchResult);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Schließt das Fenster.
	 */
	private void abbrechen() {
		frame.dispose();
	}

	/**
	 * @return den eingegebenen Namen
	 */
	@Override
	public String getKdName() {
		if (tfKdName != null) {
			return tfKdName.getText();
		}
		return "";
	}
}
