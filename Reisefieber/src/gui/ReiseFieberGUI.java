package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import reiseFieber.CustomTableRenderer;
import reiseFieber.JTableNoEditing;
import dbv.DatenbankVerbindung;

/**
 * Diese Klasse verwaltet die Benutzeroberfläche.
 * 
 */
public class ReiseFieberGUI {

	private JFrame frame;
	private JButton bnNeuenKundenErstellen;
	private JButton bnSucheKunde;
	private JButton bnKundenAendern;
	private JButton bnAddToReise;
	private JButton bnNeueReise;
	private JButton bnReiseStornieren;
	private JButton bnReiseTeilnehmerAnzeigen;

	private JButton bnRefresh;

	private JButton beenden;

	private JMenuBar menuBar;

	private JScrollPane scrollPaneKunden;
	private JScrollPane scrollPaneReisen;
	private JScrollPane scrollPaneKundenReise;
	private JScrollPane scrollPaneSearchResult;
	private JScrollPane scrollPaneReiseTeilnehmer;
	private JTable tableKunden;
	private JTable tableReisen;
	private JTable tableKundenReise;
	private JTable tableSearchResult;
	private JTable tableReiseTeilnehmer;
	private JTabbedPane tabPane;
	private JLabel statusLabel;
	private JPanel statusPanel;

	private JPopupMenu popupKunden;
	private JPopupMenu popupReisen;
	private JPopupMenu popupKundenReise;
	private JPopupMenu popupSearch;
	private JPopupMenu popupReiseTeilnehmer;

	private AbstractAction acBearbeiten;
	private AbstractAction acKundeErstellen;
	private AbstractAction acZuReiseHinzufuegen;
	private AbstractAction acReiseErstellen;
	private AbstractAction acKundeSuchen;
	private AbstractAction acReiseStornieren;
	private AbstractAction acReiseTeilnehmerAnzeigen;

	private JMenuItem bearbeiten;
	private JMenuItem kundeErstellen;
	private JMenuItem zuReiseHinzufuegen;
	private JMenuItem reiseErstellen;
	private JMenuItem kundeSuchen;
	private JMenuItem reiseStornieren;
	private JMenuItem reiseTeilnehmerAnzeigen;

	private String[][] dataKunden;
	private String[][] dataReisen;
	private String[][] dataKundenReise;
	private String[][] dataSearchResult;
	private String[][] dataReiseTeilnehmer;

	private String contentSelectedRowID;
	private String addToJourney = "";

	private MouseListener popupListener;

	/**
	 * Initialisiert die Benutzeroberfläche.
	 */
	public ReiseFieberGUI() {

		frame = new JFrame("ReiseFieber            WSeminararbeit von Markus Hofmann Q12, Maristengymnasium F\u00fcrstenzell, 10. November 2015");
		bnNeuenKundenErstellen = new JButton("Kunde erstellen");
		bnSucheKunde = new JButton("Kunden suchen");
		bnKundenAendern = new JButton("Kundendaten \u00e4ndern");
		bnAddToReise = new JButton("Kunden zu Reise hinzuf\u00fcgen");
		bnNeueReise = new JButton("Neue Reise anlegen");
		bnReiseStornieren = new JButton("Buchung stornieren");
		bnReiseTeilnehmerAnzeigen = new JButton("Teilnehmerliste anzeigen");

		bnRefresh = new JButton("Tabellen neu laden");

		beenden = new JButton("Beenden");

		menuBar = new JMenuBar();
		menuBar.add(bnNeuenKundenErstellen);
		menuBar.add(bnSucheKunde);
		menuBar.add(bnKundenAendern);
		menuBar.add(bnAddToReise);
		menuBar.add(bnNeueReise);
		menuBar.add(bnReiseStornieren);
		menuBar.add(bnReiseTeilnehmerAnzeigen);

		menuBar.add(bnRefresh);

		menuBar.add(beenden);
		frame.setJMenuBar(menuBar);

		tabPane = new JTabbedPane();

		tableKunden = new JTableNoEditing();
		tableReisen = new JTableNoEditing();
		tableKundenReise = new JTableNoEditing();
		tableSearchResult = new JTableNoEditing();
		tableReiseTeilnehmer = new JTableNoEditing();

		loadTableData();

		scrollPaneKunden = new JScrollPane(tableKunden);
		scrollPaneReisen = new JScrollPane(tableReisen);
		scrollPaneKundenReise = new JScrollPane(tableKundenReise);
		scrollPaneSearchResult = new JScrollPane(tableSearchResult);
		scrollPaneReiseTeilnehmer = new JScrollPane(tableReiseTeilnehmer);

		tabPane.addTab("Kunden", scrollPaneKunden);
		tabPane.addTab("Reisen", scrollPaneReisen);
		tabPane.addTab("Buchungen", scrollPaneKundenReise);
		tabPane.addTab("Suchergebnis", scrollPaneSearchResult);
		tabPane.addTab("Reiseteilnehmer", scrollPaneReiseTeilnehmer);

		frame.setLayout(new BorderLayout());

		frame.add(tabPane, BorderLayout.CENTER);

		statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		frame.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 25));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		statusLabel = new JLabel("");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addActionListeners();
		addMouseListeners();

		addMouseListenersTables();

	}

	/**
	 * Schließt die Benutzeroberfläche.
	 */
	public void stop() {
		frame.dispose();
	}

	/**
	 * Zeigt die übergebenen Suchergebnisse (Kundendaten) sortiert nach
	 * Kunden-ID in der Benutzeroberfläche unter dem Tab "Suchergebnis" an und
	 * wechselt vom bisher aktiven Tab zu diesem.
	 * 
	 * @param data
	 *            Das Suchergebnis, wenn nach Kunden gesucht wurde
	 */
	public void showSearchResultData(String[][] data) {
		dataSearchResult = data;
		String[] columnSearchResult = { "ID", "Nachname", "Vorname",
				"Geschlecht", "Geburtstag", "Telefonnummer",
				"Adresse", "Postleitzahl", "Wohnort" };

		Arrays.sort(dataSearchResult, new Comparator<String[]>() {
			public int compare(final String[] entry1, final String[] entry2) {
				final int tmp1 = Integer.parseInt(entry1[0]);
				final int tmp2 = Integer.parseInt(entry2[0]);
				return tmp1 - tmp2;
			}
		});

		DefaultTableModel modelSearchResult = new DefaultTableModel(
				dataSearchResult, columnSearchResult);
		tableSearchResult.setModel(modelSearchResult);
		tableSearchResult.setDefaultRenderer(Object.class,
				new CustomTableRenderer(3));

		tabPane.setSelectedIndex(3);
	}

	/**
	 * Zeigt die übergebenen Suchergebnisse (Buchungsdaten) sortiert nach der
	 * Buchungs-ID in der Benutzeroberfläche unter dem Tab "Reiseteilnehmer" an
	 * und wechselt vom bisher aktiven Tab zu diesem.
	 * 
	 * @param data
	 *            Das Suchergebnis, wenn nach Teilnehmern einer Reise gesucht
	 *            wurde
	 */
	public void showReiseTeilnehmer(String[][] data) {
		dataReiseTeilnehmer = data;
		String teilnehmerzahlString;
		int teilnehmerzahl = 0;
		try {
			teilnehmerzahlString = new DatenbankVerbindung()
					.getMaximaleTeilnehmerZahl(dataReiseTeilnehmer[0][1]);
			teilnehmerzahl = Integer.parseInt(teilnehmerzahlString);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] columnReiseTeilnehmer = { "Buchungsnummer", "Reisenummer",
				"Reisename", "Reiseziel", "Kundennummer", "Nachname", "Vorname" };
		Arrays.sort(dataReiseTeilnehmer, new Comparator<String[]>() {
			public int compare(final String[] entry1, final String[] entry2) {
				final int tmp1 = Integer.parseInt(entry1[0]);
				final int tmp2 = Integer.parseInt(entry2[0]);
				return tmp1 - tmp2;
			}
		});

		DefaultTableModel modelReiseTeilnehmer = new DefaultTableModel(
				dataReiseTeilnehmer, columnReiseTeilnehmer);
		tableReiseTeilnehmer.setModel(modelReiseTeilnehmer);
		tableReiseTeilnehmer.setDefaultRenderer(Object.class,
				new CustomTableRenderer(teilnehmerzahl, 4));
		tabPane.setSelectedIndex(4);
	}

	/**
	 * Die einzelnen Tabs und die Tabellen werden geladen. Außerdem werden die
	 * gespeicherten Kundendaten, Reisen und Buchungen sortiert nach Kunden-ID /
	 * Reise-ID / Buchungs-ID aus der Datenbank geladen und in den Tabs
	 * "Kunden", "Reisen" und "Buchungen" angezeigt.
	 */
	private void loadTableData() {

		String[] columnKunden = { "ID", "Nachname", "Vorname", "Geschlecht",
				"Geburtstag", "Telefonnummer", "Adresse",
				"Postleitzahl", "Wohnort" };
		String[] columnReisen = { "ID", "Name", "Ziel", "max. Teilnehmerzahl",
				"Beginn", "Ende", "Preis pro Person", "Kosten" };
		String[] columnKundenReise = { "Buchungsnummer", "Reisenummer",
				"Reisename", "Reiseziel", "Kundennummer", "Nachname",
				"Vorname", "Storno" };
		String[] columnSearchResult = { "ID", "Nachname", "Vorname",
				"Geschlecht", "Geburtstag", "Telefonnummer",
				"Adresse", "Postleitzahl", "Wohnort" };
		String[] columnReiseTeilnehmer = { "Buchungsnummer", "Reisenummer",
				"Reisename", "Reiseziel", "Kundennummer", "Nachname", "Vorname" };

		try {

			dataKunden = new DatenbankVerbindung().kundenUebersicht();

			Arrays.sort(dataKunden, new Comparator<String[]>() {
				public int compare(final String[] entry1, final String[] entry2) {
					final int tmp1 = Integer.parseInt(entry1[0]);
					final int tmp2 = Integer.parseInt(entry2[0]);
					return tmp1 - tmp2;
				}
			});

			DefaultTableModel modelKunden = new DefaultTableModel(dataKunden,
					columnKunden);
			tableKunden.setModel(modelKunden);
			tableKunden.setDefaultRenderer(Object.class,
					new CustomTableRenderer(0));

			dataReisen = new DatenbankVerbindung().reiseUebersicht();

			Arrays.sort(dataReisen, new Comparator<String[]>() {
				public int compare(final String[] entry1, final String[] entry2) {
					final int tmp1 = Integer.parseInt(entry1[0]);
					final int tmp2 = Integer.parseInt(entry2[0]);
					return tmp1 - tmp2;
				}
			});
			String[] gewinn = new String[dataReisen.length];
			for (int i = 0; i < dataReisen.length; i++) {
				String preisProPersonStr = dataReisen[i][dataReisen[i].length - 2];
				String kostenStr = dataReisen[i][dataReisen[i].length - 1];
				int preisProPerson = Integer.parseInt(preisProPersonStr);
				int kosten = Integer.parseInt(kostenStr);
				String teilnehmerzahlStr = new DatenbankVerbindung()
						.getAktuelleTeilnehmerZahl(dataReisen[i][0]);
				int teilnehmerzahl = Integer.parseInt(teilnehmerzahlStr);
				int result = (preisProPerson * teilnehmerzahl) - kosten;

				gewinn[i] = "" + result;
			}

			DefaultTableModel modelReisen = new DefaultTableModel(dataReisen,
					columnReisen);
			modelReisen.addColumn("Gewinn/Verlust", gewinn);
			tableReisen.setModel(modelReisen);
			tableReisen.setDefaultRenderer(Object.class,
					new CustomTableRenderer(9, 1, gewinn));

			dataKundenReise = new DatenbankVerbindung().buchungsUebersicht();

			for (int j = 0; j < dataKundenReise.length; j++) {
				String strings = dataKundenReise[j][7];
				if (strings.equals("f")) {
					dataKundenReise[j][7] = "hat nicht storniert";
				} else if (strings.equals("t")) {
					dataKundenReise[j][7] = "hat storniert";
				}
			}

			Arrays.sort(dataKundenReise, new Comparator<String[]>() {
				public int compare(final String[] entry1, final String[] entry2) {
					final int tmp1 = Integer.parseInt(entry1[0]);
					final int tmp2 = Integer.parseInt(entry2[0]);
					return tmp1 - tmp2;
				}
			});

			DefaultTableModel modelKundenReise = new DefaultTableModel(
					dataKundenReise, columnKundenReise);
			tableKundenReise.setModel(modelKundenReise);
			tableKundenReise.setDefaultRenderer(Object.class,
					new CustomTableRenderer(2));

			// these two blocks are only here to show the searchresult table and
			// the fellow travelers table by default
			DefaultTableModel modelSearchResult = new DefaultTableModel(
					dataSearchResult, columnSearchResult);
			tableSearchResult.setModel(modelSearchResult);

			DefaultTableModel modelReiseTeilnehmer = new DefaultTableModel(
					dataReiseTeilnehmer, columnReiseTeilnehmer);
			tableReiseTeilnehmer.setModel(modelReiseTeilnehmer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Registriert für alle Tabellen MouseListener und erstellt für jede Tabelle
	 * ein Menü, das bei einem Rechtsklick angezeigt wird. Über diese Menüs sind
	 * einige Funktionen leichter zu erreichen und bei Funktionen, die Kunden-,
	 * Reise- oder Buchungsdaten benötigen, werden die Daten des ausgewählten
	 * Kunden, der ausgewählen Reise oder der ausgewählten Buchung sofort
	 * übernommen.<br>
	 * Ausgegraute Optionen sind von den Tabellen aus nur verfügbar, wenn ein
	 * Kunde, eine Reise oder eine Buchung ausgewählt ist, mit welcher die
	 * Funktion aufgerufen werden soll.
	 */
	@SuppressWarnings("serial")
	private void addMouseListenersTables() {
		acBearbeiten = new AbstractAction("Kunde bearbeiten") {
			public void actionPerformed(ActionEvent e) {
				int selectedTable = tabPane.getSelectedIndex();
				if (selectedTable == 0) {
					contentSelectedRowID = dataKunden[tableKunden
							.getSelectedRow()][0];
				} else if (selectedTable == 1) {
					contentSelectedRowID = null;
				} else if (selectedTable == 2) {
					contentSelectedRowID = null;
				} else if (selectedTable == 3) {
					contentSelectedRowID = dataSearchResult[tableSearchResult
							.getSelectedRow()][0];
				} else if (selectedTable == 4) {
					contentSelectedRowID = null;
				}
				aendern();
			}
		};
		acKundeErstellen = new AbstractAction("Neuen Kunden anlegen") {
			public void actionPerformed(ActionEvent e) {
				contentSelectedRowID = null;
				kundeAnlegen();
			}
		};
		acZuReiseHinzufuegen = new AbstractAction(
				"Kunde zu Reise hinzuf\u00fcgen") {
			public void actionPerformed(ActionEvent e) {
				int selectedTable = tabPane.getSelectedIndex();
				if (selectedTable == 0) {
					contentSelectedRowID = dataKunden[tableKunden
							.getSelectedRow()][0];
					addToJourney = "Kunde";
				} else if (selectedTable == 1) {
					// selectedRow = tableReisen.getSelectedRow();
					contentSelectedRowID = dataReisen[tableReisen
							.getSelectedRow()][0];
					addToJourney = "Reise";
				} else if (selectedTable == 2) {
					contentSelectedRowID = null;
					addToJourney = "";
				} else if (selectedTable == 3) {
					contentSelectedRowID = dataSearchResult[tableSearchResult
							.getSelectedRow()][0];
					addToJourney = "Kunde";
				} else if (selectedTable == 4) {
					contentSelectedRowID = dataReiseTeilnehmer[0][1];
					addToJourney = "Reise";
				}
				buchen();
			}
		};

		acReiseErstellen = new AbstractAction("Neue Reise anlegen") {
			public void actionPerformed(ActionEvent e) {
				contentSelectedRowID = null;
				reiseAnlegen();
			}
		};
		acKundeSuchen = new AbstractAction("Kunde suchen") {
			public void actionPerformed(ActionEvent e) {
				contentSelectedRowID = null;
				suche();
			}
		};
		acReiseStornieren = new AbstractAction("Buchung stornieren") {
			public void actionPerformed(ActionEvent e) {
				int selectedTable = tabPane.getSelectedIndex();
				if (selectedTable == 0) {
					contentSelectedRowID = null;
				} else if (selectedTable == 1) {
					contentSelectedRowID = null;
				} else if (selectedTable == 2) {
					contentSelectedRowID = dataKundenReise[tableKundenReise
							.getSelectedRow()][0];
				} else if (selectedTable == 3) {
					contentSelectedRowID = null;
				} else if (selectedTable == 4) {
					contentSelectedRowID = dataReiseTeilnehmer[tableReiseTeilnehmer
							.getSelectedRow()][0];
				}
				reiseStornieren();
			}
		};
		acReiseTeilnehmerAnzeigen = new AbstractAction(
				"Reiseteilnehmer anzeigen") {
			public void actionPerformed(ActionEvent arg0) {
				int selectedTable = tabPane.getSelectedIndex();
				if (selectedTable == 0) {
					contentSelectedRowID = null;
				} else if (selectedTable == 1) {
					contentSelectedRowID = dataReisen[tableReisen
							.getSelectedRow()][0];
				} else if (selectedTable == 2) {
					contentSelectedRowID = dataKundenReise[tableKundenReise
							.getSelectedRow()][1];
				} else if (selectedTable == 3) {
					contentSelectedRowID = null;
				} else if (selectedTable == 4) {
					contentSelectedRowID = null;
				}
				reiseTeilnehmerAnzeigen();
			}
		};

		bearbeiten = new JMenuItem(acBearbeiten);
		kundeErstellen = new JMenuItem(acKundeErstellen);
		zuReiseHinzufuegen = new JMenuItem(acZuReiseHinzufuegen);
		reiseErstellen = new JMenuItem(acReiseErstellen);
		kundeSuchen = new JMenuItem(acKundeSuchen);

		reiseStornieren = new JMenuItem(acReiseStornieren);

		reiseTeilnehmerAnzeigen = new JMenuItem(acReiseTeilnehmerAnzeigen);

		// Add listener to components that can bring up popup menus.
		popupListener = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int index = tabPane.getSelectedIndex();
				System.out.println(index);
				if (index == 0) {
					popupKunden = new JPopupMenu();
					popupKunden.add(kundeSuchen);
					popupKunden.add(kundeErstellen);
					popupKunden.add(bearbeiten);
					popupKunden.add(zuReiseHinzufuegen);

					if (tableKunden.getSelectedRow() == -1) {
						bearbeiten.setEnabled(false);
						zuReiseHinzufuegen.setEnabled(false);
					} else {
						bearbeiten.setEnabled(true);
						zuReiseHinzufuegen.setEnabled(true);
					}
				} else if (index == 1) {
					popupReisen = new JPopupMenu();
					popupReisen.add(reiseErstellen);
					popupReisen.add(zuReiseHinzufuegen);
					popupReisen.add(reiseTeilnehmerAnzeigen);

					if (tableReisen.getSelectedRow() == -1) {
						zuReiseHinzufuegen.setEnabled(false);
						reiseTeilnehmerAnzeigen.setEnabled(false);
					} else {
						zuReiseHinzufuegen.setEnabled(true);
						reiseTeilnehmerAnzeigen.setEnabled(true);
					}
				} else if (index == 2) {
					popupKundenReise = new JPopupMenu();
					popupKundenReise.add(reiseStornieren);

					if (tableKundenReise.getSelectedRow() == -1) {
						reiseStornieren.setEnabled(false);
					} else {
						reiseStornieren.setEnabled(true);
					}
				} else if (index == 3) {
					popupSearch = new JPopupMenu();
					popupSearch.add(kundeSuchen);
					popupSearch.add(kundeErstellen);
					popupSearch.add(bearbeiten);
					popupSearch.add(zuReiseHinzufuegen);

					if (tableSearchResult.getSelectedRow() == -1) {
						bearbeiten.setEnabled(false);
						zuReiseHinzufuegen.setEnabled(false);
					} else {
						bearbeiten.setEnabled(true);
						zuReiseHinzufuegen.setEnabled(true);
					}
				} else if (index == 4) {
					popupReiseTeilnehmer = new JPopupMenu();
					popupReiseTeilnehmer.add(reiseStornieren);

					if (tableReiseTeilnehmer.getSelectedRow() == -1) {
						reiseStornieren.setEnabled(false);
					} else {
						reiseStornieren.setEnabled(true);
					}
				}
				maybeShowPopup(e);
			}

			public void mouseReleased(MouseEvent e) {
				maybeShowPopup(e);
			}

			private void maybeShowPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					int index = tabPane.getSelectedIndex();
					if (index == 0) {
						popupKunden.show(e.getComponent(), e.getX(), e.getY());
					} else if (index == 1) {
						popupReisen.show(e.getComponent(), e.getX(), e.getY());
					} else if (index == 2) {
						popupKundenReise.show(e.getComponent(), e.getX(),
								e.getY());
					} else if (index == 3) {
						popupSearch.show(e.getComponent(), e.getX(), e.getY());
					} else if (index == 4) {
						popupReiseTeilnehmer.show(e.getComponent(), e.getX(),
								e.getY());
					}
				}
			}
		};
		tableKunden.addMouseListener(popupListener);

		tableReisen.addMouseListener(popupListener);

		tableKundenReise.addMouseListener(popupListener);

		tableSearchResult.addMouseListener(popupListener);
		tableReiseTeilnehmer.addMouseListener(popupListener);
	}

	/**
	 * Registriert einen MouseListener für jede Tabelle, welcher in der unteren
	 * Zeile der Benutzeroberfläche immer den den Namen der aktuell ausgewählten
	 * Spalte und den Inhalt der ausgewählten Zelle anzeigt, um das Lesen langer
	 * Einträge zu erleichtern.
	 */
	private void addMouseListeners() {
		tableKunden.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getButton() == MouseEvent.BUTTON1) {
					int col = tableKunden.getSelectedColumn();
					int row = tableKunden.getSelectedRow();
					TableColumnModel tcm = tableKunden.getColumnModel();
					String columnHeader = ((String) tcm.getColumn(col)
							.getHeaderValue());
					String selectedText = (String) tableKunden.getModel()
							.getValueAt(row, col);
					if (selectedText == null) {
						selectedText = "";
					}
					statusLabel.setText(columnHeader + ": " + selectedText);
				}
			}
		});
		tableReisen.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getButton() == MouseEvent.BUTTON1) {
					int col = tableReisen.getSelectedColumn();
					int row = tableReisen.getSelectedRow();
					TableColumnModel tcm = tableReisen.getColumnModel();
					String columnHeader = ((String) tcm.getColumn(col)
							.getHeaderValue());
					String selectedText = (String) tableReisen.getModel()
							.getValueAt(row, col);
					if (selectedText == null) {
						selectedText = "";
					}
					statusLabel.setText(columnHeader + ": " + selectedText);
				}
			}
		});
		tableKundenReise.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getButton() == MouseEvent.BUTTON1) {
					int col = tableKundenReise.getSelectedColumn();
					int row = tableKundenReise.getSelectedRow();
					TableColumnModel tcm = tableKundenReise.getColumnModel();
					String columnHeader = ((String) tcm.getColumn(col)
							.getHeaderValue());
					String selectedText = (String) tableKundenReise.getModel()
							.getValueAt(row, col);
					if (selectedText == null) {
						selectedText = "";
					}
					statusLabel.setText(columnHeader + ": " + selectedText);
				}
			}
		});
		tableSearchResult.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getButton() == MouseEvent.BUTTON1) {
					int col = tableSearchResult.getSelectedColumn();
					int row = tableSearchResult.getSelectedRow();
					TableColumnModel tcm = tableSearchResult.getColumnModel();
					String columnHeader = ((String) tcm.getColumn(col)
							.getHeaderValue());
					String selectedText = (String) tableSearchResult.getModel()
							.getValueAt(row, col);
					if (selectedText == null) {
						selectedText = "";
					}
					statusLabel.setText(columnHeader + ": " + selectedText);
				}
			}
		});
		tableReiseTeilnehmer.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getButton() == MouseEvent.BUTTON1) {
					int col = tableReiseTeilnehmer.getSelectedColumn();
					int row = tableReiseTeilnehmer.getSelectedRow();
					TableColumnModel tcm = tableReiseTeilnehmer
							.getColumnModel();
					String columnHeader = ((String) tcm.getColumn(col)
							.getHeaderValue());
					String selectedText = (String) tableReiseTeilnehmer
							.getModel().getValueAt(row, col);
					if (selectedText == null) {
						selectedText = "";
					}
					statusLabel.setText(columnHeader + ": " + selectedText);
				}
			}
		});
	}

	/**
	 * Zeigt die Benutzeroberfläche an.
	 */
	public void show() {
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Registriert die ActionListener für alle Buttons in der
	 * Benutzeroberfläche.
	 */
	private void addActionListeners() {
		bnNeuenKundenErstellen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				kundeAnlegen();
			}
		});
		bnSucheKunde.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				suche();
			}
		});
		bnKundenAendern.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				aendern();
			}
		});
		bnAddToReise.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				buchen();
			}
		});
		bnNeueReise.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				reiseAnlegen();
			}
		});
		bnReiseStornieren.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				reiseStornieren();
			}
		});
		bnReiseTeilnehmerAnzeigen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				reiseTeilnehmerAnzeigen();
			}
		});
		bnRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadTableData();
			}
		});
		beenden.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				System.exit(0);
			}
		});
	}

	/**
	 * Öffnet das Fenster zur Suche von Kunden.
	 */
	private void suche() {
		SucheKundeDialog suchDialog = new SucheKundeDialog(this);
		suchDialog.show();
	}

	/**
	 * Öffnet das Fenster zum Ändern von Kundendaten.
	 */
	private void aendern() {
		KundenDatenAendern aendernDialog = new KundenDatenAendern(
				contentSelectedRowID);
		aendernDialog.show();
	}

	/**
	 * Öffnet das Fenster zum Speichern eines neuen Kunden.
	 */
	private void kundeAnlegen() {
		KundenEingabeFeld eingabeFeld = new KundenEingabeFeld();
		eingabeFeld.show();
	}

	/**
	 * Öffnet das Fenster zum Buchen einer Reise.
	 */
	private void buchen() {
		ReiseBuchen teilnehmenDialog = new ReiseBuchen(contentSelectedRowID,
				addToJourney);
		teilnehmenDialog.show();
	}

	/**
	 * Öffnet das Fenster zum Erstellen einer neuen Reise.
	 */
	private void reiseAnlegen() {
		ReiseAnlegenDialog neueReiseDialog = new ReiseAnlegenDialog();
		neueReiseDialog.show();
	}

	/**
	 * Öffnet das Fenster zum Stornieren einer Buchung.
	 */
	private void reiseStornieren() {
		ReiseStornierenDialog stornierenDialog = new ReiseStornierenDialog(
				contentSelectedRowID);
		stornierenDialog.show();
	}

	/**
	 * Öffnet das Fenster zum Suchen von Reiseteilnehmern einer bestimmten
	 * Reise.
	 */
	private void reiseTeilnehmerAnzeigen() {
		ReiseTeilnehmer reiseTeilnehmerDialog = new ReiseTeilnehmer(
				contentSelectedRowID, this);
		if (contentSelectedRowID == null) {
			reiseTeilnehmerDialog.show();
		}
		contentSelectedRowID = null;
	}
}
