package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dbv.DatenbankVerbindung;
import dbv.IKunden�ndern;
import kundenkartei.Kunde;

public class KundenDaten�ndern implements IKunden�ndern {
	private JFrame frame;
	private JLabel labID;
	private JTextField tfID;
	
	private JLabel labKdName;
	private JTextField tfKdName;
	
	private JLabel labKdVorname;
	private JTextField tfKdVorname;
	
	private JLabel labWohnort;
	private JTextField tfWohnort;
	
	private JLabel labGeburtsdatum;
	private JTextField tfGeburtsdatum;	

	private JLabel labVolljaehrig;
	private JTextField tfVolljaehrig;
	
	private JLabel labTelefon;
	private JTextField tfTelefon;
	
	private JLabel labGeschlecht;
	private JTextField tfGeschlecht;
	
	private JButton bn�ndern;
	private JButton bnFertig;

	private Kunde kunde;

	public KundenDaten�ndern(Kunde kunde) {
		this.kunde = kunde;
		frame = new JFrame("Kundendaten �ndern");
		
		labID = new JLabel("ID des zu �ndernden Kunden:");
		tfID = new JTextField();
		
		labKdName = new JLabel("Nachname:");
		tfKdName = new JTextField();
		
		labKdVorname = new JLabel("Vorname:");
		tfKdVorname = new JTextField();

		labWohnort = new JLabel("Wohnort:");
		tfWohnort = new JTextField();
		
		labGeburtsdatum = new JLabel("Geburtsdatum:");
		tfGeburtsdatum = new JTextField();
		
		labVolljaehrig = new JLabel("Vollj�hrig:");
		tfVolljaehrig = new JTextField();
		
		labTelefon = new JLabel("Telefonnummer:");
		tfTelefon = new JTextField();
		
		labGeschlecht = new JLabel("Geschlecht:");
		tfGeschlecht = new JTextField();

		bn�ndern = new JButton("�ndern");
		bnFertig = new JButton("Fertig");

		frame.setLayout(new GridLayout(0, 2, 10, 10));

		frame.add(labID);
		frame.add(tfID);
		frame.add(labKdName);
		frame.add(tfKdName);
		frame.add(labKdVorname);
		frame.add(tfKdVorname);
		frame.add(labWohnort);
		frame.add(tfWohnort);
		frame.add(labGeburtsdatum);
		frame.add(tfGeburtsdatum);
		frame.add(labVolljaehrig);
		frame.add(tfVolljaehrig);
		frame.add(labTelefon);
		frame.add(tfTelefon);
		frame.add(labGeschlecht);
		frame.add(tfGeschlecht);
		frame.add(bn�ndern);
		frame.add(bnFertig);
		addActionListeners();
	}

	private void addActionListeners() {
		tfID.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				suchen();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		bnFertig.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				KundenDaten�ndern.this.fertig();
			}
		});
		bn�ndern.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KundenDaten�ndern.this.�ndern();
			}
		});

	}

	public void show() {
		//TODO rework
		frame.pack();
		frame.show();
	}
	
	protected void suchen(){
		// TODO Auto-generated method stub
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			String[] idSearch = dbv.doSearchByID(getID());
			
			tfKdName.setText(idSearch[1]);
			tfKdVorname.setText(idSearch[2]);
			tfWohnort.setText(idSearch[3]);	
			tfGeburtsdatum.setText(idSearch[4]);
			tfVolljaehrig.setText(idSearch[5]);
			tfTelefon.setText(idSearch[6]);	
			tfGeschlecht.setText(idSearch[7]);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void �ndern() {
		// TODO Auto-generated method stub
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			dbv.doChange(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void fertig() {
		frame.dispose();
	}

	@Override
	public String getVorname() {
		if (tfKdVorname != null) {
			return tfKdVorname.getText();
		}
		return "";
	}

	@Override
	public String getWohnort() {
		if (tfWohnort != null) {
			return tfWohnort.getText();
		}
		return "";
	}

	@Override
	public String getGeburtstag() {
		if (tfGeburtsdatum != null) {
			return tfGeburtsdatum.getText();
		}
		return "";
	}

	@Override
	public String getTelefonnummer() {
		if (tfTelefon != null) {
			return tfTelefon.getText();
		}
		return "";
	}

	@Override
	public String getGeschlecht() {
		if (tfGeschlecht != null) {
			return tfGeschlecht.getText();
		}
		return "";
	}

	@Override
	public String getNachname() {
		if (tfKdName != null) {
			return tfKdName.getText();
		}
		return "";
	}

	@Override
	public String getID() {
		if (tfID != null) {
			return tfID.getText();
		}
		return "";
	}
}
