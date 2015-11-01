package reiseFieber;

import kundenkartei.Kunde;
import kundenkartei.Tools;

public class Singlereise extends Bootsreise{	//erbt von Klasse Bootsreise

	Singlereise(int bootsgroese) {
		super(bootsgroese);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void hinzufuegen(Kunde kunde) {
		try {
			if(kunde.istVolljaehrig()){
				super.hinzufuegen(kunde);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println(Tools.istVolljaehrigAusgabe(kunde));
		}
		// TODO Auto-generated method stub
		
	}
	

}
