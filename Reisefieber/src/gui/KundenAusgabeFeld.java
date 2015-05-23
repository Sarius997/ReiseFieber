package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dbv.IKundenSuchen;
import kundenkartei.Kunde;
import kundenkartei.KundenListe;
import kundenkartei.Tools;

public class KundenAusgabeFeld implements IKundenSuchen {
	private JFrame frame;
	private JLabel labKdNr;
	private JTextField tfKdNr;
	private JLabel labKdName;
	private JTextField tfKdName;
	private JLabel labKdVorname;
	private JTextField tfKdVorname;
	private JLabel labAdresse;
	private JTextField tfAdresse;
	private JLabel labTelefon;
	private JTextField tfTelefon;
	private JButton bnNeueSuche;
	private JButton bnFertig;
	private JLabel labGeschlecht;
	private JTextField tfGeschlecht;
	private JLabel labVolljaehrig;
	private JTextField tfVolljaehrig;
	private Kunde kunde;

	public KundenAusgabeFeld(Kunde kunde) {	
		this.kunde=kunde;
		frame=new JFrame();
		frame.setTitle("Kunden");
		labKdNr=new JLabel("Kundennummer:");
		tfKdNr=new JTextField();
		tfKdNr.setEnabled(false);
		labKdName=new JLabel("Name:");
		tfKdName=new JTextField();
		tfKdName.setEnabled(false);
		labKdVorname=new JLabel("Vorname:");
		tfKdVorname=new JTextField();
		tfKdVorname.setEnabled(false);
		labGeschlecht=new JLabel("Geschlecht:");
		tfGeschlecht=new JTextField();
		tfGeschlecht.setEnabled(false);
		labVolljaehrig=new JLabel("Der Kunde");
		tfVolljaehrig=new JTextField();
		tfVolljaehrig.setEnabled(false);
		labAdresse=new JLabel("Adresse:");
		tfAdresse=new JTextField();
		tfAdresse.setEnabled(false);
		labTelefon=new JLabel("Telefon:");
		tfTelefon=new JTextField();
		tfTelefon.setEnabled(false);

		bnNeueSuche=new JButton("neue Suche");
		bnFertig=new JButton("Fertig");


		frame.setLayout(new GridLayout(0, 2, 10, 10));		// festlegen des Layouts
		frame.add(labKdNr);
		frame.add(tfKdNr);
		frame.add(labKdName);
		frame.add(tfKdName);
		frame.add(labKdVorname);
		frame.add(tfKdVorname);
		frame.add(labGeschlecht);
		frame.add(tfGeschlecht);
		frame.add(labVolljaehrig);
		frame.add(tfVolljaehrig);
		frame.add(labAdresse);
		frame.add(tfAdresse);
		frame.add(labTelefon);
		frame.add(tfTelefon);
		frame.add(bnNeueSuche);
		frame.add(bnFertig);

		fillText();

		addActionListeners();
	}
	private String getKundennummer() {
		// TODO Auto-generated method stub
		return null;
	}
	public void show(){
		//TODO rework
		frame.pack();
		frame.show();
	}

	private void fillText()
	{
		if (kunde != null)
		{
			String strKdNr=String.valueOf(kunde.getKundennummer());
			tfKdNr.setText(strKdNr);
			tfKdName.setText(kunde.getName());
			tfKdVorname.setText(kunde.getVorname());
			tfAdresse.setText(kunde.getAdresse());
			tfTelefon.setText(kunde.getTelefonnummer());
			tfGeschlecht.setText(kunde.getGeschlecht());
			tfVolljaehrig.setText(Tools.istVolljaehrigAusgabe(kunde));

		}
	}
	private void addActionListeners(){
		bnFertig.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				KundenAusgabeFeld.this.fertig();
			}
		});
	}
	private void fertig(){
		frame.dispose();
	}
	@Override
	public String getKdName() {
		if (tfKdName != null){
			return tfKdName.getText();
		}return "";
	}
}
