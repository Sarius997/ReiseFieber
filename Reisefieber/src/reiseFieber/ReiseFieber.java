package reiseFieber;

import gui.ReiseFieberGUI;
import kundenkartei.KundenListe;

public class ReiseFieber {
	KundenListe kundenListe;
	private static ReiseFieberGUI gui = new ReiseFieberGUI();

	public static void main(String args[]) {
		willkommensNachricht();
		ReiseFieber r = new ReiseFieber();
		gui.show();
	}

	public ReiseFieber() {
		kundenListe = new KundenListe();
	}

	private static void willkommensNachricht() {
		System.out.println("Willkommen bei ReiseFieber!");
	}
}
