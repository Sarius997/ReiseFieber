package kundenkartei;


public class Tools {

	public static String istVolljaehrigAusgabe(Kunde kunde) {
		String nachricht;
		try {
			if (kunde.istVolljaehrig()){			
				nachricht="ist Vollj\u00e4hrig.";
			}
			else {
				nachricht="ist nicht Vollj\u00e4hrig.";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//			e.printStackTrace();
			nachricht="hat kein Alter angegeben.";
		}
		return nachricht;
	}

}
