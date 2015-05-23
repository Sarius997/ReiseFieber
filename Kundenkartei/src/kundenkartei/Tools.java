package kundenkartei;


public class Tools {

	public static String istVolljaehrigAusgabe(Kunde kunde) {
		String nachricht;
		try {
			if (kunde.istVolljaehrig()){			
				nachricht="ist Vollj�hrig.";
			}
			else {
				nachricht="ist nicht Vollj�hrig.";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//			e.printStackTrace();
			nachricht="hat kein Alter angegeben.";
		}
		return nachricht;
	}

}
