package kundenkartei;

import java.util.LinkedList;
import java.util.List;

public class KundenListe {
	private List<Kunde> liste;
	public KundenListe() {
		liste=new LinkedList<Kunde>();
	}
	public void hinzufuegen(Kunde kunde) {
		liste.add(kunde);
	}
	public void alleAusgeben() {
		int anzahl=liste.size();
		for(int i=0; i<anzahl; i=i+1){
			Kunde k=liste.get(i);
			ausgeben(k);
		}

	}
	public long getAnzahlKunden(){
		return liste.size();
	}
	public Kunde getKundeByIndex(int index){

		int size = liste.size();
		if(index < size)
			return liste.get(index);
		return null;
	}

	public KundenListe sucheKunde(String suchName){
		KundenListe suchErgebnis=new KundenListe();
		int anzahl=liste.size();
		for(int i=0; i<anzahl; i=i+1){
			Kunde k=liste.get(i);
			if (k.getName().equals (suchName)){
				suchErgebnis.hinzufuegen(k);
			}
		}
		return suchErgebnis;
	}


	static void ausgeben(Kunde kunde) {
		System.out.println("Der Kunde Nummer " + kunde.getKundennummer() + " heißt: " + kunde.getVorname() + " " + kunde.getName());
		System.out.println("Der gesamte Kundenname hat: " + kunde.getLaenge() + " Buchstaben.");
		String nachricht = Tools.istVolljaehrigAusgabe(kunde);
		System.out.print("Der Kunde ");
		System.out.println(nachricht);
		System.out.println("Die Telefonnummer des Kunden ist: " + kunde.getTelefonnummer());
	}
}
