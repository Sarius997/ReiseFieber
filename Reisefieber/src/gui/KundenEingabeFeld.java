package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.LinkedTransferQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dbv.DatenbankVerbindung;
import dbv.IKundenDaten;
import kundenkartei.Kunde;
import kundenkartei.KundenListe;

public class KundenEingabeFeld implements IKundenDaten  {		//implementiert das Interface IKundenDaten
	private JFrame frame;
//	JLabel labKdNr;
//	JTextField tfKdNr;


	private JLabel labKdName;
	private JTextField tfKdName;
	private JLabel labKdVorname;
	private JTextField tfKdVorname;
	private JLabel labGeburtsdatum;
	private JTextField tfGeburtsdatum;
	private JLabel labTelefon;
	private JTextField tfTelefon;
	private JLabel labGeschlecht;
	private JTextField tfGeschlecht;
	private JLabel labWohnort;
	private JTextField tfWohnort;
	private JButton bnHinzufuegen;
	private JButton bnFertig;


//	public String getTfKdNr() {
//		return tfKdNr.getText();
//	}
//	public void setTfKdNr(JTextField tfKdNr) {
//		this.tfKdNr = tfKdNr;
//	}
	public JTextField getTfKdName() {
		return tfKdName;
	}
	public void setTfKdName(JTextField tfKdName) {
		this.tfKdName = tfKdName;
	}
	public JTextField getTfKdVorname() {
		return tfKdVorname;
	}
	public void setTfKdVorname(JTextField tfKdVorname) {
		this.tfKdVorname = tfKdVorname;
	}
	public JTextField getTfGeburtsdatum() {
		return tfGeburtsdatum;
	}
	public void setTfGeburtsdatum(JTextField tfGeburtsdatum) {
		this.tfGeburtsdatum = tfGeburtsdatum;
	}
	public JTextField getTfTelefon() {
		return tfTelefon;
	}
	public void setTfTelefon(JTextField tfTelefon) {
		this.tfTelefon = tfTelefon;
	}
	public JTextField getTfGeschlecht() {
		return tfGeschlecht;
	}
	public void setTfGeschlecht(JTextField tfGeschlecht) {
		this.tfGeschlecht = tfGeschlecht;
	}
	public JTextField getTfWohnort() {
		return tfWohnort;
	}
	public void setTfWohnort(JTextField tfWohnort) {
		this.tfWohnort = tfWohnort;
	}

	private Kunde kunde;

	public KundenEingabeFeld(Kunde kunde){
		this.kunde=kunde;
		frame=new JFrame("Kunde hinzufügen");
	//	labKdNr=new JLabel("Kundennummer:");
	//	tfKdNr=new JTextField();
		labKdName=new JLabel("Name:");
		tfKdName=new JTextField();
		labKdVorname=new JLabel("Vorname:");
		tfKdVorname=new JTextField();
		labGeburtsdatum=new JLabel("Geburtsdatum:");
		tfGeburtsdatum=new JTextField();
		labGeschlecht=new JLabel("Geschlecht:");
		tfGeschlecht=new JTextField();
		labTelefon=new JLabel("Telefonnummer:");
		tfTelefon=new JTextField();
		labWohnort=new JLabel("Wohnort:");
		tfWohnort=new JTextField();

		bnHinzufuegen=new JButton("Hinzufügen");
		bnFertig=new JButton("Fertig");

		frame.setLayout(new GridLayout(0, 2, 10, 10));
	//	frame.add(labKdNr);
	//	frame.add(tfKdNr);
		frame.add(labKdName);
		frame.add(tfKdName);
		frame.add(labKdVorname);
		frame.add(tfKdVorname);
		frame.add(labGeburtsdatum);
		frame.add(tfGeburtsdatum);
		frame.add(labTelefon);
		frame.add(tfTelefon);
		frame.add(labGeschlecht);
		frame.add(tfGeschlecht);
		frame.add(labWohnort);
		frame.add(tfWohnort);
		frame.add(bnHinzufuegen);
		frame.add(bnFertig);

//		fillText();

		addActionListeners();
	}

//	private String getKundennummer() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	public void show(){
		//TODO rework
		frame.pack();
		frame.show();
	}

//	private void fillText()
//	{
//		if (kunde != null)
//		{
//			String strKdNr=String.valueOf(kunde.getKundennummer());
//			tfKdNr.setText(strKdNr);
//		}
//	}
	private void addActionListeners(){
		bnFertig.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				KundenEingabeFeld.this.fertig();
			}
		});
		bnHinzufuegen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KundenEingabeFeld.this.hinzufuegen();
			}
		});
	}
	protected void hinzufuegen() {									//baut Verbindung zur Datenbank auf und erstellt neuen Eintrag
		// TODO Auto-generated method stub
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			dbv.doInsert(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void fertig(){
		frame.dispose();
	}
	@Override
	public String getVorname() {

		if (tfKdVorname != null){
			return tfKdVorname.getText();
		}return "";
	}
	@Override
	public String getNachname() {
		if(tfKdName != null){
			return tfKdName.getText();
		}return "";
	}
	@Override
	public String getWohnort() {
		if (tfWohnort != null){
			return tfWohnort.getText();
		}return "";
	}
	@Override
	public String getGeburtstag() {
		if (tfGeburtsdatum != null){
			return tfGeburtsdatum.getText();
		}return "";
	}
	@Override
	public String getTelefonnummer() {
		if (tfTelefon != null ){
			return tfTelefon.getText();
		}return "";
	}
	@Override
	public String getGeschlecht() {
		if (tfGeschlecht != null){
			return tfGeschlecht.getText();
		}return "";
	}
}