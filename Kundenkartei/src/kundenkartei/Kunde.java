package kundenkartei;

import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Kunde {
	static Dimension d = new Dimension(100,200);


	private long Kundennummer;

	private String name;

	private String vorname;

	private Date geburtsdatum;

	private String telefonnummer;

	private String adresse;

	private String geschlecht;

	public String getGeschlecht() {
		return geschlecht;
	}
	public void setGeschlecht(String geschlecht) {
		this.geschlecht = geschlecht;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public Kunde(){
		setName("Mustermann");
		setVorname("Max");
		//		geburtsdatum=new Date();
		geburtsdatum=null;

	}
	public Kunde(String neuerVorname,
			String neuerName){
		setName(neuerName);
		setVorname(neuerVorname);
		//		geburtsdatum=new Date();
		geburtsdatum=null;


	}
	public int getLaenge(){
		int buchstaben = vorname.length();
		buchstaben=buchstaben+name.length();
		return buchstaben;
	}
	public long getKundennummer() {
		return Kundennummer;
	}


	public String getName() {
		return name;

	}
	public String getVorname() {
		return vorname;
	}
	public Date getGeburtsdatum() {
		return geburtsdatum;
	}
	public String getTelefonnummer() {
		return telefonnummer;
	}


	public boolean istVolljaehrig() throws Exception{
		Date now=new Date();
		long msnow=now.getTime();
		if (geburtsdatum!=null) {
			long msgeburt=geburtsdatum.getTime();
			long msalter=msnow-msgeburt;
			long sekalter=msalter/1000;
			long minalter=sekalter/60;
			long stdalter=minalter/60;
			long tagalter=stdalter/24;
			long jahralter=tagalter/365;
			if (jahralter<18)
				return false;
			else
				return true;
		}
		throw new Exception("Kein Alter!");
	}
	public void setKundennummer(long kundennummer) {
		Kundennummer = kundennummer;
	}



	public void setName(String name){
		this.name=name;
	}
	public void setVorname(String neuerVorname){
		this.vorname=neuerVorname;

	}


	public void setTelefonnummer(String telefonnummer) {
		this.telefonnummer = telefonnummer;
	}


	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}
	/**
	 * 
	 * @param strgeb
	 * @return
	 */
	public static Date datumAusText(String strgeb) {
		SimpleDateFormat sdfToDate = new SimpleDateFormat( "dd.MM.yyyy" );
		Date geb;
		try {
			geb = sdfToDate.parse(strgeb);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			geb=new Date();
		}
		return geb;
	}
}