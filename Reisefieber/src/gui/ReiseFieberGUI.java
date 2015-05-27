package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ReiseFieberGUI {

	private JFrame frame;
	private JButton neuerKunde;
	private JButton sucheKunde;
	private JButton kundenAendern;
	private JButton addToReise;
	private JButton neueReise;
	private JButton beenden;

	public ReiseFieberGUI() {
		frame = new JFrame("ReiseFieber");
		neuerKunde = new JButton("Kunde erstellen");
		sucheKunde = new JButton("Kunden suchen");
		kundenAendern = new JButton("Kundendaten ändern");
		addToReise = new JButton("Kunden zu Reise hinzufügen");
		neueReise = new JButton("Neue Reise anlegen");
		beenden = new JButton("EXIT");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1, 5));
		frame.add(neuerKunde);
		frame.add(sucheKunde);
		frame.add(kundenAendern);
		frame.add(addToReise);
		frame.add(neueReise);
		frame.add(beenden);
		addActionListeners();

	}

	public void show() {
		frame.pack();
		frame.show();
	}

	private void addActionListeners() {
		neuerKunde.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				testEingabe();
			}
		});
		sucheKunde.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				testSuche();
			}
		});
		kundenAendern.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				testÄndern();
			}
		});
		addToReise.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				testReise();
			}
		});
		neueReise.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				testReiseAnlegen();
			}
		});
		beenden.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				frame.dispose();
				System.exit(0);
			}
		});
	}

	private void testSuche() { // Hier werden die Fenster geöffnet
		SucheKundeDialog suchDialog = new SucheKundeDialog(null); 
		suchDialog.show(); 
	} 
		

	private void testÄndern() { 
		KundenDatenÄndern ändernDialog = new KundenDatenÄndern(null); 
		ändernDialog.show(); 
	}
		

	private void testEingabe() {
		KundenEingabeFeld ef = new KundenEingabeFeld(null); 
		ef.show(); 
	} 

	private void testReise() {
		Reiseteilnehmer teilnehmenDialog = new Reiseteilnehmer(null);
		teilnehmenDialog.show();
	}
	
	private void testReiseAnlegen(){
		ReiseAnlegenDialog neueReiseDialog = new ReiseAnlegenDialog(null);
		neueReiseDialog.show();
	}
}
