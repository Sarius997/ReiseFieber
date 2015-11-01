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

	public ReiseAnlegenDialog() {
		frame = new JFrame("Reise anlegen");
		labName = new JLabel("Reisename:");
		tfName = new JTextField();

		labZiel = new JLabel("Reiseziel:");
		tfZiel = new JTextField();
		labTeilnehmerzahl = new JLabel("Teilnehmerzahl:");
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

		// fillText();

		addActionListeners();
	}

	// private String getKundennummer() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	public void show() {
		// TODO rework
		frame.pack();
		frame.setVisible(true);
	}

	// private void fillText()
	// {
	// if (kunde != null)
	// {
	// String strKdNr=String.valueOf(kunde.getKundennummer());
	// tfKdNr.setText(strKdNr);
	// }
	// }
	private void addActionListeners() {
		bnAbbrechen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ReiseAnlegenDialog.this.fertig();
			}
		});
		bnHinzufuegen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ReiseAnlegenDialog.this.hinzufuegen();
				frame.dispose();
			}
		});

		KeyListener enterListener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

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

	protected void hinzufuegen() {
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			dbv.doCreateJourney(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fertig() {
		frame.dispose();
	}

	@Override
	public String getReiseZiel() {
		return tfZiel.getText();
	}

	@Override
	public String getTeilnehmerZahl() {
		return tfTeilnehmerzahl.getText();
	}

	@Override
	public String getBeginn() {
		return tfBeginn.getText();
	}

	@Override
	public String getEnde() {
		return tfEnde.getText();
	}

	@Override
	public String getPreisProPerson() {
		return tfPreisProPerson.getText();
	}

	@Override
	public String getKosten() {
		return tfKosten.getText();
	}

	@Override
	public String getReiseName() {
		return tfName.getText();
	}
}