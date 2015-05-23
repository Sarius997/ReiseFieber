package reiseFieber;

import kundenkartei.Kunde;
import kundenkartei.KundenListe;
import kundenkartei.Tools;

public class Bootsreise extends KundenListe{	//erbt von Klasse KundenListe
	int bootsgroese;
	Bootsreise(int bootsgroese){
		this.bootsgroese=bootsgroese;


	}
	@Override
	public void hinzufuegen(Kunde kunde) {
		long n=getAnzahlKunden();
		if (bootsgroese>n){
		super.hinzufuegen(kunde);
		}
	}

}
