package reiseFieber;

import gui.ReiseFieberGUI;

import java.util.Date;

import kundenkartei.Kunde;
import kundenkartei.KundenListe;

public class ReiseFieber {
	KundenListe kundenListe;

	public static void main(String args[]) {
		willkommensNachricht();
		ReiseFieber r = new ReiseFieber();
		ReiseFieberGUI gui = new ReiseFieberGUI();
		gui.show();
	}

	public ReiseFieber() {
		kundenListe = new KundenListe();
	}

	private static void willkommensNachricht() {
		System.out.println("Willkommen bei ReiseFieber!");
	}
}
