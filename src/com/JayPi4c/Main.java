package com.JayPi4c;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import com.JayPi4c.gui.CoordinateSystem;
import com.JayPi4c.gui.SettingsFrame;
import com.JayPi4c.logic.Logic;

public class Main {

	public static void main(String args[]) {
		JFrame frame = new JFrame("Polynomial Regression");
		frame.setSize(640, 480);

		CoordinateSystem coordSys = new CoordinateSystem();

		// Menubaritems:
		JMenu settings = new JMenu("Settings");
		settings.setMnemonic(KeyEvent.VK_S);

		JCheckBoxMenuItem autoAdjusting = new JCheckBoxMenuItem("Auto-Adjusting");
		// anstelle von einer Ausgabe muss hier dann eine Funktion getriggert werden,
		// die automatisch eine Auto-adjusting vornimmt.
		autoAdjusting.addActionListener(event -> {
			Logic.autoAdjusting = autoAdjusting.isSelected();
			if (Logic.autoAdjusting && Logic.points.size() > 0) {
				Logic.update();
				coordSys.repaint();
			}
		});
		autoAdjusting.setToolTipText("Ermittle automatisch den besten Grad der Funktion.");

		JMenuItem window = new JMenuItem("Change window");
		window.addActionListener(event -> System.out.println("Einstellen des Window"));

		/*
		 * JMenuItem setDegree = new JMenuItem("Set Degree");
		 * setDegree.addActionListener(event -> { Logic.degree = getInput("");
		 * Logic.update(); coordSys.repaint(); });
		 */
		JMenuItem setValues = new JMenuItem("Set Values");
		setValues.addActionListener(event -> new SettingsFrame());

		JMenuItem delPoints = new JMenuItem("Delete Points");
		delPoints.addActionListener(event -> {
			int answer = JOptionPane.showConfirmDialog(null, "Sollen wirklich alle Punkte gelöscht werden?",
					"Punkte löschen?", JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION) {
				Logic.points = new ArrayList<Point>();
				coordSys.repaint();
			}
		});

		settings.add(setValues);
		settings.add(window);
		settings.add(autoAdjusting);
		settings.addSeparator();
		settings.add(delPoints);

		JMenu options = new JMenu("Options");
		options.setMnemonic(KeyEvent.VK_O);

		// Die egentliche MenuBar:
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(settings);
		menuBar.add(options);

		frame.setJMenuBar(menuBar);

		frame.setLayout(new BorderLayout());
		frame.add(coordSys, BorderLayout.CENTER);

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	static int getInput(String message) {
		try {
			return Math.max(0,
					Integer.parseInt(
							JOptionPane.showInputDialog(null, "Welchen Grad soll die Funktion haben\n" + message,
									"Grad festlegen", JOptionPane.QUESTION_MESSAGE)));
		} catch (NumberFormatException e) {
			return getInput("Der Wert muss eine Ganzzahl sein.");
		}
	}

}
