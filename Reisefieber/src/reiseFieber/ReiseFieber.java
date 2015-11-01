package reiseFieber;

import gui.ReiseFieberGUI;
import kundenkartei.KundenListe;

public class ReiseFieber {
	KundenListe kundenListe;
	private static ReiseFieberGUI gui;

	@SuppressWarnings("unused")
	public static void main(String args[]) {
		willkommensNachricht();
		ReiseFieber r = new ReiseFieber();
		gui.show();
	}

	public ReiseFieber() {
		gui = new ReiseFieberGUI(this);
		
		// TODO rework/remove
		kundenListe = new KundenListe();
	}

	// TODO only temporary --> rework
	public void reloadGui(){
		gui.stop();
		gui = new ReiseFieberGUI(this);
		gui.show();
	}

	private static void willkommensNachricht() {
		System.out.println("Willkommen bei ReiseFieber!");
	}
}
