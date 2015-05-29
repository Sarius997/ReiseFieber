package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ReiseFieberGUI {

	private JFrame frame;
	private JButton eingabe;
	private JButton suche;
	private JButton aendern;
	private JButton reise;
	private JButton beenden;

	public ReiseFieberGUI() {
		frame = new JFrame("ReiseFieber");
		eingabe = new JButton("Kunde erstellen");
		suche = new JButton("Kunden suchen");
		aendern = new JButton("Kundendaten ändern");
		reise = new JButton("Kunden zu Reise hinzufügen");
		beenden = new JButton("EXIT");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1, 5));
		frame.add(eingabe);
		frame.add(suche);
		frame.add(aendern);
		frame.add(reise);
		frame.add(beenden);
		addActionListeners();

	}

	public void show() {
		//TODO rework
		frame.pack();
		frame.show();
	}

	private void addActionListeners() {
		eingabe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				testEingabe();
			}
		});
		suche.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				testSuche();
			}
		});
		aendern.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				testÄndern();
			}
		});
		reise.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				testReise();
			}
		});
		beenden.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				frame.dispose();
			}
		});
	}

	private void testSuche() { // Hier werden die Fenster geöffnet
		SucheKundeDialog suchDialog = new SucheKundeDialog(null); // "
		suchDialog.show(); // "
	} // "
		// "

	private void testÄndern() { // "
		KundenDatenÄndern ändernDialog = new KundenDatenÄndern(null); // "
		ändernDialog.show(); // "
	} // "
		// "

	private void testEingabe() { // "
		KundenEingabeFeld ef = new KundenEingabeFeld(null); // "
		ef.show(); // "
	} // "

	private void testReise() {
		Reiseteilnehmer teilnehmenDialog = new Reiseteilnehmer(null);
		teilnehmenDialog.show();
	}
}
