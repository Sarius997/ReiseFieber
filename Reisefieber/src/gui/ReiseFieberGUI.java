package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextField;

public class ReiseFieberGUI {

	private JMenuBar menuBar;

	private JFrame frame;
	private JButton eingabe;
	private JButton suche;
	private JButton aendern;
	private JButton reise;
	private JButton beenden;

	public ReiseFieberGUI() {
		BorderLayout border = new BorderLayout();
		

		frame = new JFrame("ReiseFieber");
		eingabe = new JButton("Kunde erstellen");
		suche = new JButton("Kunden suchen");
		aendern = new JButton("Kundendaten ändern");
		reise = new JButton("Kunden zu Reise hinzufügen");
		beenden = new JButton("EXIT");

		menuBar = new JMenuBar();
		menuBar.add(eingabe);
		menuBar.add(suche);
		menuBar.add(aendern);
		menuBar.add(reise);
		menuBar.add(beenden);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(border);
		
		frame.add(new JTextField());

		frame.setJMenuBar(menuBar);

		addActionListeners();

	}

	public void show() {
		// TODO rework
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
