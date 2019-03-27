package com.JayPi4c.gui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.event.HyperlinkEvent;

import com.JayPi4c.Main;
import com.JayPi4c.logic.Point;

public class Frame extends JFrame {

	private static final long serialVersionUID = -208823195276469124L;

	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;

	private CoordinateSystem coordSys;

	public Frame() {
		super(Main.messages.getString("MainFrame"));

		coordSys = new CoordinateSystem();
		setSize(WIDTH, HEIGHT);

		// Menubaritems:
		JMenu settings = new JMenu(Main.messages.getString("settings"));
		settings.setMnemonic(KeyEvent.VK_S);

		JCheckBoxMenuItem autoAdjusting = new JCheckBoxMenuItem(Main.messages.getString("autoAdjusting"));
		// anstelle von einer Ausgabe muss hier dann eine Funktion getriggert werden,
		// die automatisch eine Auto-adjusting vornimmt.
		autoAdjusting.addActionListener(event -> {
			coordSys.getLogic().autoAdjusting = autoAdjusting.isSelected();
			if (coordSys.getLogic().autoAdjusting && coordSys.getLogic().points.size() > 0) {
				coordSys.getLogic().update();
				coordSys.repaint();
			}
		});
		autoAdjusting.setToolTipText(Main.messages.getString("adjustToolTip"));

		JCheckBoxMenuItem ignoreOutliers = new JCheckBoxMenuItem("ignore outliers");
		ignoreOutliers.addActionListener(event -> {
			coordSys.getLogic().ignoreOutliers = ignoreOutliers.isSelected();
			if (coordSys.getLogic().points.size() > 0) {
				coordSys.getLogic().unignoredPoints = new ArrayList<Point>();
				coordSys.getLogic().update();
				coordSys.repaint();
			}
		});
		ignoreOutliers.setToolTipText("Yet to do");

		JMenuItem window = new JMenuItem(Main.messages.getString("changeWindow"));
		window.addActionListener(event -> System.out.println("Einstellen des Window"));

		JMenuItem setValues = new JMenuItem(Main.messages.getString("setValues"));
		setValues.addActionListener(event -> new SettingsFrame(coordSys));

		JMenuItem delPoints = new JMenuItem(Main.messages.getString("delPoints"));
		delPoints.addActionListener(event -> {
			int answer = JOptionPane.showConfirmDialog(null, Main.messages.getString("delMessage"),
					Main.messages.getString("delPointsTitle"), JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION) {
				coordSys.getLogic().points = new ArrayList<Point>();
				coordSys.getLogic().polynomial = null;
				coordSys.repaint();
			}
		});

		JMenuItem controlPoints = new JMenuItem("Control Points");
		controlPoints.addActionListener(event -> new PointSettingsFrame(coordSys));

		settings.add(setValues);
		settings.add(controlPoints);
		settings.add(window);
		settings.addSeparator();
		settings.add(ignoreOutliers);
		settings.add(autoAdjusting);
		settings.addSeparator();
		settings.add(delPoints);

		JMenu options = new JMenu(Main.messages.getString("options"));
		options.setMnemonic(KeyEvent.VK_O);

		JMenu language = new JMenu("Language");
		options.add(language);

		JMenuItem english = new JMenuItem("English");
		english.addActionListener(
				event -> Main.messages = ResourceBundle.getBundle("MessagesBundle", new Locale("en", "US")));
		JMenuItem german = new JMenuItem("German");
		german.addActionListener(
				event -> Main.messages = ResourceBundle.getBundle("MessagesBundle", new Locale("de", "DE")));

		language.add(english);
		language.add(german);

		// help:
		JMenu help = new JMenu("Help");

		JMenuItem credits = new JMenuItem("Credits");
		credits.addActionListener(event -> {
			JEditorPane ep = new JEditorPane("text/html", "This program is inspired by:<br>"
					+ "<a href=\"https://thecodingtrain.com/\">The coding train</a><br>"
					+ "Please visit this website for the mathematics behind this program:<br>"
					+ "<a href=\"https://neutrium.net/mathematics/least-squares-fitting-of-a-polynomial/\">neutrium.net</a><br>"
					+ "Find this project on GitHub:<br>"
					+ "<a href=\"https://github.com/JayPi4c/Polynomial-Regression/\">GitHub.com</a><br>" + "~JayPi4c");

			ep.addHyperlinkListener(e -> {
				if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
					try {
						Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
						if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
							desktop.browse(e.getURL().toURI());
					} catch (URISyntaxException | IOException ex) {
						System.out.println(ex.getMessage());
					}
				}
			});
			ep.setEditable(false);
			ep.setBackground(coordSys.getBackground());

			JOptionPane.showMessageDialog(null, ep, "Credits", JOptionPane.INFORMATION_MESSAGE);
		});

		help.add(credits);

		// Die egentliche MenuBar:
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(settings);
		menuBar.add(options);
		menuBar.add(help);

		setJMenuBar(menuBar);

		setLayout(new BorderLayout());
		add(coordSys, BorderLayout.CENTER);

		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);

	}

}
