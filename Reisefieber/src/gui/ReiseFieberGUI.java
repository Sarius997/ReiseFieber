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
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import reiseFieber.ReiseFieber;
import dbv.DatenbankVerbindung;

public class ReiseFieberGUI {

	private JFrame frame;
	private JButton neuerKunde;
	private JButton sucheKunde;
	private JButton kundenAendern;
	private JButton addToReise;
	private JButton neueReise;
	private JButton bnReiseStornieren;

	private JButton bnRefresh;

	private JButton beenden;

	private JMenuBar menuBar;

	private JScrollPane scrollPaneKunden;
	private JScrollPane scrollPaneReisen;
	private JScrollPane scrollPaneKundenReise;
	private JTable tableKunden;
	private JTable tableReisen;
	private JTable tableKundenReise;
	private JTabbedPane tabPane;
	private JLabel statusLabel;
	private JPanel statusPanel;

	private JPopupMenu popupKunden;
	private JPopupMenu popupReisen;
	private JPopupMenu popupKundenReise;
	private JPopupMenu popupSearch;

	private AbstractAction acBearbeiten;
	private AbstractAction acKundeErstellen;
	private AbstractAction acZuReiseHinzufügen;
	private AbstractAction acReiseErstellen;
	private AbstractAction acKundeSuchen;
	private AbstractAction acReiseStornieren;

	private JMenuItem bearbeiten;
	private JMenuItem kundeErstellen;
	private JMenuItem zuReiseHinzufügen;
	private JMenuItem reiseErstellen;
	private JMenuItem kundeSuchen;
	private JMenuItem reiseStornieren;

	private String[][] dataKunden;
	private String[][] dataReisen;
	private String[][] dataKundenReise;

	// TODO rework
	private int selectedRow = 0;
	private String contentSelectedRowID;
	private String addToJourney = "";

	private String[][] searchResult;
	private int searchSelectedRow = -1;
	private MouseListener popupListener;

	private ReiseFieber reiseFieber;

	// TODO test and rework
	public ReiseFieberGUI(ReiseFieber reiseFieber) {
		this.reiseFieber = reiseFieber;

		frame = new JFrame("ReiseFieber");
		neuerKunde = new JButton("Kunde erstellen");
		sucheKunde = new JButton("Kunden suchen");
		kundenAendern = new JButton("Kundendaten ändern");
		addToReise = new JButton("Kunden zu Reise hinzufügen");
		neueReise = new JButton("Neue Reise anlegen");
		bnReiseStornieren = new JButton("Reise stornieren");

		bnRefresh = new JButton("Tabellen neu laden");

		beenden = new JButton("Beenden");

		menuBar = new JMenuBar();
		menuBar.add(neuerKunde);
		menuBar.add(sucheKunde);
		menuBar.add(kundenAendern);
		menuBar.add(addToReise);
		menuBar.add(neueReise);
		menuBar.add(bnReiseStornieren);

		menuBar.add(bnRefresh);

		menuBar.add(beenden);
		frame.setJMenuBar(menuBar);
		/*
		 * int selectedScrollPane = tabPane.getSelectedIndex(); if
		 * (selectedScrollPane == 0) { int selectedRow =
		 * tableKunden.getSelectedRow(); int selectedColumn =
		 * tableKunden.getSelectedColumn(); TableColumnModel tcm =
		 * tableKunden.getColumnModel(); String statustext = "" +
		 * tcm.getColumn(selectedColumn).getHeaderValue() +
		 * tableKunden.getModel().getValueAt(selectedRow, selectedColumn); }
		 */

		tabPane = new JTabbedPane();

		tableKunden = new JTable();
		tableReisen = new JTable();
		tableKundenReise = new JTable();
		
		loadTableData();

		scrollPaneKunden = new JScrollPane(tableKunden);
		scrollPaneReisen = new JScrollPane(tableReisen);
		scrollPaneKundenReise = new JScrollPane(tableKundenReise);

		tabPane.addTab("Kunden", scrollPaneKunden);
		tabPane.addTab("Reisen", scrollPaneReisen);
		tabPane.addTab("Anmeldungen", scrollPaneKundenReise);

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

		// TODO does't work
		/*
		 * Component[] components = frame.getComponents(); for (Component
		 * component : components) { component.addFocusListener(new
		 * FocusListener() {
		 * 
		 * @Override public void focusLost(FocusEvent e) { // TODO
		 * Auto-generated method stub }
		 * 
		 * @Override public void focusGained(FocusEvent e) { // TODO
		 * Auto-generated method stub loadTableData(); } }); }
		 */

		addMouseListenersTables();
	}

	public void stop() {
		frame.dispose();
	}

	// TODO option zum schließen der tabs!!!
	public void addResultTable(String[][] data) {
		if (tabPane.getComponentCount() > 3) {
			tabPane.remove(3);
		}

		int numberOfComponents = tabPane.getComponentCount();
		String[] columnResult = { "ID", "Nachname", "Vorname", "Geschlecht",
				"Geburtstag", "Volljährig", "Telefonnummer", "Adresse",
				"Postleitzahl", "Wohnort" };

		Arrays.sort(data, new Comparator<String[]>() {
			public int compare(final String[] entry1, final String[] entry2) {
				final int tmp1 = Integer.parseInt(entry1[0]);
				final int tmp2 = Integer.parseInt(entry2[0]);
				return tmp1 - tmp2;
			}
		});

		searchResult = data;

		final JTable result = new JTable(data, columnResult);
		JScrollPane resultPane = new JScrollPane(result);

		result.addMouseListener(popupListener);

		result.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getButton() == MouseEvent.BUTTON1) {
					searchSelectedRow = result.getSelectedRow();
					int col = result.getSelectedColumn();
					int row = result.getSelectedRow();
					TableColumnModel tcm = result.getColumnModel();
					String columnHeader = ((String) tcm.getColumn(col)
							.getHeaderValue());
					String selectedText = (String) result.getModel()
							.getValueAt(row, col);
					if (selectedText == null) {
						selectedText = "";
					}
					statusLabel.setText(columnHeader + ": " + selectedText);
				}
			}
		});
		tabPane.add("Suchergebnis", resultPane);
		tabPane.setSelectedIndex(numberOfComponents);
	}
	

	private void loadTableData() {

		String[] columnKunden = { "ID", "Nachname", "Vorname", "Geschlecht",
				"Geburtstag", "Volljährig", "Telefonnummer", "Adresse",
				"Postleitzahl", "Wohnort" };
		String[] columnReisen = { "ID", "Name", "Ziel", "Teilnehmerzahl",
				"Beginn", "Ende", "Preis pro Person", "Kosten" };
		String[] columnKundenReise = { "Buchungsnummer", "Reisenummer",
				"Reisename", "Reiseziel", "Kundennummer", "Nachname",
				"Vorname", "Storno" };

		try {
			
			dataKunden = new DatenbankVerbindung().kundenUebersicht();

			Arrays.sort(dataKunden, new Comparator<String[]>() {
				public int compare(final String[] entry1, final String[] entry2) {
					final int tmp1 = Integer.parseInt(entry1[0]);
					final int tmp2 = Integer.parseInt(entry2[0]);
					return tmp1 - tmp2;
				}
			});

			DefaultTableModel modelKunden = new DefaultTableModel(
					dataKunden, columnKunden);
			tableKunden.setModel(modelKunden);
			

			dataReisen = new DatenbankVerbindung().reiseUebersicht();

			Arrays.sort(dataReisen, new Comparator<String[]>() {
				public int compare(final String[] entry1, final String[] entry2) {
					final int tmp1 = Integer.parseInt(entry1[0]);
					final int tmp2 = Integer.parseInt(entry2[0]);
					return tmp1 - tmp2;
				}
			});

			DefaultTableModel modelReisen = new DefaultTableModel(dataReisen,
					columnReisen);
			tableReisen.setModel(modelReisen);

			dataKundenReise = new DatenbankVerbindung()
					.reiseTeilnehmerUebersicht();

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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addMouseListenersTables() {
		acBearbeiten = new AbstractAction("Kunde bearbeiten") {
			public void actionPerformed(ActionEvent e) {
				int selectedTable = tabPane.getSelectedIndex();
				if (selectedTable == 0) {
					contentSelectedRowID = dataKunden[tableKunden.getSelectedRow()][0];
				} else if (selectedTable == 1) {
					contentSelectedRowID = null;
				} else if (selectedTable == 2) {
					contentSelectedRowID = null;
				} else if (selectedTable == 3) {
					contentSelectedRowID = searchResult[searchSelectedRow][0];
				}
				testÄndern();
			}
		};
		acKundeErstellen = new AbstractAction("Neuen Kunden anlegen") {
			public void actionPerformed(ActionEvent e) {
				contentSelectedRowID = null;
				testKundeAnlegen();
			}
		};
		acZuReiseHinzufügen = new AbstractAction("Kunde zu Reise hinzufügen") {
			public void actionPerformed(ActionEvent e) {
				int selectedTable = tabPane.getSelectedIndex();
				if (selectedTable == 0) {
					contentSelectedRowID = dataKunden[tableKunden.getSelectedRow()][0];
					addToJourney = "Kunde";
				} else if (selectedTable == 1) {
					selectedRow = tableReisen.getSelectedRow();
					contentSelectedRowID = dataReisen[tableReisen.getSelectedRow()][0];
					addToJourney = "Reise";
				} else if (selectedTable == 2) {
					contentSelectedRowID = null;
					addToJourney = "";
				} else if (selectedTable == 3) {
					contentSelectedRowID = searchResult[searchSelectedRow][0];
					addToJourney = "Kunde";
				}
				testReise();
			}
		};

		acReiseErstellen = new AbstractAction("Neue Reise anlegen") {
			public void actionPerformed(ActionEvent e) {
				contentSelectedRowID = null;
				testReiseAnlegen();
			}
		};
		acKundeSuchen = new AbstractAction("Kunde suchen") {
			public void actionPerformed(ActionEvent e) {
				contentSelectedRowID = null;
				testSuche();
			}
		};
		acReiseStornieren = new AbstractAction("Reise stornieren") {
			public void actionPerformed(ActionEvent e) {
				// TODO take data from current selected customer/ journey
				int selectedTable = tabPane.getSelectedIndex();
				if (selectedTable == 0) {
					contentSelectedRowID = null;
				} else if (selectedTable == 1) {
					contentSelectedRowID = null;
				} else if (selectedTable == 2) {
					contentSelectedRowID = dataKundenReise[tableKundenReise.getSelectedRow()][0];
				} else if (selectedTable == 3) {
					contentSelectedRowID = null;
				}
				testReiseStornieren();
			}
		};

		bearbeiten = new JMenuItem(acBearbeiten);
		kundeErstellen = new JMenuItem(acKundeErstellen);
		zuReiseHinzufügen = new JMenuItem(acZuReiseHinzufügen);
		reiseErstellen = new JMenuItem(acReiseErstellen);
		kundeSuchen = new JMenuItem(acKundeSuchen);

		reiseStornieren = new JMenuItem(acReiseStornieren);

		// Add listener to components that can bring up popup menus.
		popupListener = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int index = tabPane.getSelectedIndex();
				System.out.println(index);
				if (index == 0) {
					if (tableKunden.getSelectedRow() == -1) {
						popupKunden = new JPopupMenu();
						popupKunden
								.add("Kein Kunde zur Interaktion ausgewählt");
						popupKunden.addSeparator();
						popupKunden.add(kundeSuchen);
						popupKunden.add(kundeErstellen);
					} else {
						popupKunden = new JPopupMenu();
						popupKunden.add(kundeSuchen);
						popupKunden.add(bearbeiten);
						popupKunden.add(kundeErstellen);
						popupKunden.add(zuReiseHinzufügen);
					}
				} else if (index == 1) {
					if (tableReisen.getSelectedRow() == -1) {
						popupReisen = new JPopupMenu();
						popupReisen
								.add("Keine Reise zur Interaktion ausgewählt");
						popupReisen.addSeparator();
						popupReisen.add(reiseErstellen);
					} else {
						popupReisen = new JPopupMenu();
						popupReisen.add(reiseErstellen);
						popupReisen.add(zuReiseHinzufügen);
					}
				} else if (index == 2) {
					if (tableKundenReise.getSelectedRow() == -1) {
						popupKundenReise = new JPopupMenu();
						popupKundenReise
								.add("Keine Buchung zur Interaktion ausgewählt");
					} else {
						popupKundenReise = new JPopupMenu();
						popupKundenReise.add(reiseStornieren);
						popupKundenReise.add(zuReiseHinzufügen);
					}
				} else if (index == 3) {
					if (searchSelectedRow == -1) {
						popupSearch = new JPopupMenu();
						popupSearch
								.add("Kein Kunde zur Interaktion ausgewählt");
						popupSearch.addSeparator();
						popupSearch.add(kundeSuchen);
						popupSearch.add(kundeErstellen);
					} else {
						popupSearch = new JPopupMenu();
						popupSearch.add(kundeSuchen);
						popupSearch.add(bearbeiten);
						popupSearch.add(kundeErstellen);
						popupSearch.add(zuReiseHinzufügen);
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
					} else if (index == 3){
						popupSearch.show(e.getComponent(),  e.getX(), e.getY());
					}
				}
			}
		};
		tableKunden.addMouseListener(popupListener);
		menuBar.addMouseListener(popupListener);

		tableReisen.addMouseListener(popupListener);
		menuBar.addMouseListener(popupListener);

		tableKundenReise.addMouseListener(popupListener);
		menuBar.addMouseListener(popupListener);
	}

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
	}

	public void show() {
		frame.pack();
		frame.show();
	}

	private void addActionListeners() {
		neuerKunde.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				testKundeAnlegen();
			}
		});
		sucheKunde.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				testSuche();
			}
		});
		kundenAendern.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				testÄndern();
			}
		});
		addToReise.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				testReise();
			}
		});
		neueReise.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				testReiseAnlegen();
			}
		});
		bnReiseStornieren.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				testReiseStornieren();
			}
		});
		bnRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				// TODO only temporary --> rework
				// reiseFieber.reloadGui();

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

	private void testSuche() {
		SucheKundeDialog suchDialog = new SucheKundeDialog(this);
		suchDialog.show();
	}

	private void testÄndern() {
		KundenDatenÄndern ändernDialog = new KundenDatenÄndern(
				contentSelectedRowID);
		ändernDialog.show();
	}

	private void testKundeAnlegen() {
		KundenEingabeFeld eingabeFeld = new KundenEingabeFeld();
		eingabeFeld.show();
	}

	private void testReise() {
		Reiseteilnehmer teilnehmenDialog = new Reiseteilnehmer(
				contentSelectedRowID, addToJourney);
		teilnehmenDialog.show();
	}

	private void testReiseAnlegen() {
		ReiseAnlegenDialog neueReiseDialog = new ReiseAnlegenDialog();
		neueReiseDialog.show();
	}

	private void testReiseStornieren() {
		ReiseStornierenDialog stornierenDialog = new ReiseStornierenDialog(
				contentSelectedRowID);
		stornierenDialog.show();
	}
}
