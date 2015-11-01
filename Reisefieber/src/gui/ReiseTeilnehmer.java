package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dbv.DatenbankVerbindung;

public class ReiseTeilnehmer {

	private JFrame frame;
	private JLabel labReiseID;
	private JTextField tfReiseID;

	private JButton bnAnzeigen;
	private JButton bnAbbrechen;
	private ReiseFieberGUI gui;

	public ReiseTeilnehmer(String selectedID, ReiseFieberGUI gui) {
		this.gui = gui;
		if (selectedID == null) {
			frame = new JFrame("Reiseteilnehmer anzeigen");
			labReiseID = new JLabel("Reisenummer:");
			tfReiseID = new JTextField();

			bnAnzeigen = new JButton("Anzeigen");
			bnAbbrechen = new JButton("Abbrechen");

			frame.setLayout(new GridLayout(0, 2, 10, 10));
			frame.add(labReiseID);
			frame.add(tfReiseID);

			frame.add(bnAnzeigen);
			frame.add(bnAbbrechen);

			addActionListeners();
		} else {
			tfReiseID = new JTextField();
			tfReiseID.setText(selectedID);
			anzeigen();
		}
	}

	public void show() {
		frame.pack();
		frame.setVisible(true);
	}

	private void addActionListeners() {
		bnAbbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReiseTeilnehmer.this.fertig();
			}
		});
		bnAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReiseTeilnehmer.this.anzeigen();
			}
		});

		KeyListener enterListener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bnAnzeigen.doClick();
				}
			}
		};

		tfReiseID.addKeyListener(enterListener);
	}

	protected void anzeigen() {
		DatenbankVerbindung dbv = new DatenbankVerbindung();
		try {
			String[][] result = dbv.reiseTeilnehmer(getReiseID(), "false");
			gui.showReiseTeilnehmer(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fertig() {
		frame.dispose();
	}

	private String getReiseID() {
		return this.tfReiseID.getText();
	}
}
