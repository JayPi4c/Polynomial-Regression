package com.JayPi4c;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Point;
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

import com.JayPi4c.gui.CoordinateSystem;
import com.JayPi4c.gui.SettingsFrame;
import com.JayPi4c.logic.Logic;

public class Main {

	public static ResourceBundle messages;

	public static CoordinateSystem coordSys;

	public static void main(String args[]) {
		Locale currentLocale = new Locale("en", "US");
		messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);

		JFrame frame = new JFrame(messages.getString("MainFrame"));
		frame.setSize(640, 480);

		coordSys = new CoordinateSystem();

		// Menubaritems:
		JMenu settings = new JMenu(messages.getString("settings"));
		settings.setMnemonic(KeyEvent.VK_S);

		JCheckBoxMenuItem autoAdjusting = new JCheckBoxMenuItem(messages.getString("autoAdjusting"));
		// anstelle von einer Ausgabe muss hier dann eine Funktion getriggert werden,
		// die automatisch eine Auto-adjusting vornimmt.
		autoAdjusting.addActionListener(event -> {
			Logic.autoAdjusting = autoAdjusting.isSelected();
			if (Logic.autoAdjusting && Logic.points.size() > 0) {
				Logic.update();
				coordSys.repaint();
			}
		});
		autoAdjusting.setToolTipText(messages.getString("adjustToolTip"));

		JCheckBoxMenuItem ignoreOutliers = new JCheckBoxMenuItem("ignore outliers");
		ignoreOutliers.addActionListener(event -> {
			Logic.ignoreOutliers = ignoreOutliers.isSelected();
			Logic.update();
			coordSys.repaint();
		});
		ignoreOutliers.setToolTipText("Yet to do");

		JMenuItem window = new JMenuItem(messages.getString("changeWindow"));
		window.addActionListener(event -> System.out.println("Einstellen des Window"));

		JMenuItem setValues = new JMenuItem(messages.getString("setValues"));
		setValues.addActionListener(event -> new SettingsFrame());

		JMenuItem delPoints = new JMenuItem(messages.getString("delPoints"));
		delPoints.addActionListener(event -> {
			int answer = JOptionPane.showConfirmDialog(null, messages.getString("delMessage"),
					messages.getString("delPointsTitle"), JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION) {
				Logic.points = new ArrayList<Point>();
				Logic.polynomial = null;
				coordSys.repaint();
			}
		});

		settings.add(setValues);
		settings.add(window);
		settings.addSeparator();
		settings.add(ignoreOutliers);
		settings.add(autoAdjusting);
		settings.addSeparator();
		settings.add(delPoints);

		JMenu options = new JMenu(messages.getString("options"));
		options.setMnemonic(KeyEvent.VK_O);

		JMenu language = new JMenu("Language");
		options.add(language);

		JMenuItem english = new JMenuItem("English");
		english.addActionListener(
				event -> messages = ResourceBundle.getBundle("MessagesBundle", new Locale("en", "US")));
		JMenuItem german = new JMenuItem("German");
		german.addActionListener(
				event -> messages = ResourceBundle.getBundle("MessagesBundle", new Locale("de", "DE")));

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
					+ "<a href=\"github.com/JayPi4c/Polynomial_Regression/\">GitHub.com</a><br>" + "~JayPi4c");

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

		frame.setJMenuBar(menuBar);

		frame.setLayout(new BorderLayout());
		frame.add(coordSys, BorderLayout.CENTER);

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}

}
