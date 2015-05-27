package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import kundenkartei.Kunde;
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
	private JButton bnFertig;

	private Kunde kunde;

	public ReiseAnlegenDialog(Kunde kunde) {
		this.kunde = kunde;
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
		labKosten = new JLabel("Kosten f�r die Reise:");
		tfKosten = new JTextField();

		bnHinzufuegen = new JButton("Hinzuf�gen");
		bnFertig = new JButton("Fertig");

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
		frame.add(bnFertig);

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
		frame.show();
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
		bnFertig.addActionListener(new ActionListener() {

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
			}
		});
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