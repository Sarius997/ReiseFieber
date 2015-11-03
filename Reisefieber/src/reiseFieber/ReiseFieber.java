package reiseFieber;

import gui.ReiseFieberGUI;

/**
 * 
 * @author Markus Hofmann
 * @version 1.0
 */
public class ReiseFieber {
	private static ReiseFieberGUI gui;

	/**
	 * 
	 * @param args
	 *            Kommandozeilenparameter, werden im Programm nicht benutzt.<br>Ruft
	 *            den Konstruktor auf und startet die Benutzeroberfläche
	 */
	public static void main(String args[]) {
		willkommensNachricht();
		ReiseFieber r = new ReiseFieber();
		gui.show();
	}

	/**
	 * Konstruktor der Hauptklasse Reisefieber.<br>Erstellt eine Instanz der
	 * Benutzeroberfläche {@link gui.ReiseFieberGUI}
	 */
	public ReiseFieber() {
		gui = new ReiseFieberGUI(this);
	}

	/**
	 * Gibt die Willkommensnachricht auf der Konsole aus, falls diese genutzt wird
	 */
	private static void willkommensNachricht() {
		System.out.println("Willkommen bei ReiseFieber!");
	}
}
