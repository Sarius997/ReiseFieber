package reiseFieber;

import gui.ReiseFieberGUI;

public class ReiseFieber {
	private static ReiseFieberGUI gui;

	public static void main(String args[]) {
		willkommensNachricht();
		ReiseFieber r = new ReiseFieber();
		gui.show();
	}

	public ReiseFieber() {
		gui = new ReiseFieberGUI(this);
	}

	private static void willkommensNachricht() {
		System.out.println("Willkommen bei ReiseFieber!");
	}
}
