package com.JayPi4c.gui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.HyperlinkEvent;

import com.JayPi4c.logic.Point;

public class Frame extends JFrame implements ILocaleChangeListener {

	private static final long serialVersionUID = -208823195276469124L;

	private CoordinateSystem coordSys;

	// GUI elements:
	private JMenu settings;
	private JCheckBoxMenuItem autoAdjusting;
	private JCheckBoxMenuItem ignoreOutliers;
	private JCheckBoxMenuItem showHints;
	private JMenuItem window;
	private JMenuItem setValues;
	private JMenuItem delPoints;
	private JMenuItem controlPoints;
	private JMenu options;
	private JMenu language;
	private JMenuItem english;
	private JMenuItem german;
	private JMenu help;
	private JMenuItem credits;
	private JMenuBar menuBar;

	public Frame() {
		super(Messages.getString("Frame.title"));

		coordSys = new CoordinateSystem();

		// Menubaritems:
		settings = new JMenu(Messages.getString("Frame.settings"));
		settings.setMnemonic(KeyEvent.VK_S);

		autoAdjusting = new JCheckBoxMenuItem(Messages.getString("Frame.autoAdjust"));
		// anstelle von einer Ausgabe muss hier dann eine Funktion getriggert werden,
		// die automatisch eine Auto-adjusting vornimmt.
		autoAdjusting.addActionListener(event -> {
			coordSys.getLogic().autoAdjusting = autoAdjusting.isSelected();
			if (coordSys.getLogic().autoAdjusting && coordSys.getLogic().points.size() > 0) {
				coordSys.getLogic().update();
				coordSys.repaint();
			}
		});
		autoAdjusting.setToolTipText(Messages.getString("Frame.autoAdjust.toolTip"));

		ignoreOutliers = new JCheckBoxMenuItem(Messages.getString("Frame.ignoreOutliers"));
		ignoreOutliers.addActionListener(event -> {
			coordSys.getLogic().ignoreOutliers = ignoreOutliers.isSelected();
			if (coordSys.getLogic().points.size() > 0) {
				coordSys.getLogic().unignoredPoints = new ArrayList<Point>();
				coordSys.getLogic().update();
				coordSys.repaint();
			}
		});
		ignoreOutliers.setToolTipText(Messages.getString("Frame.ignoreOutliers.toolTip"));

		showHints = new JCheckBoxMenuItem(Messages.getString("Frame.showHints"));
		showHints.setSelected(coordSys.drawHints);
		showHints.addActionListener(event -> {
			coordSys.drawHints = showHints.isSelected();
			coordSys.repaint();
		});
		showHints.setToolTipText(Messages.getString("Frame.showHints.toolTip"));

		window = new JMenuItem(Messages.getString("Frame.changeWindow"));
		window.addActionListener(event -> SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new WindowSettingsFrame(coordSys);
			}
		}));

		setValues = new JMenuItem(Messages.getString("Frame.setValues"));
		setValues.addActionListener(event -> SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SettingsFrame(coordSys);
			}
		}));

		delPoints = new JMenuItem(Messages.getString("Frame.delPoints"));
		delPoints.addActionListener(event -> {
			int answer = JOptionPane.showConfirmDialog(null, Messages.getString("Frame.delMessage"),
					Messages.getString("Frame.delPointsTitle"), JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION) {
				coordSys.getLogic().points = new ArrayList<Point>();
				coordSys.getLogic().polynomial = null;
				coordSys.repaint();
			}
		});

		controlPoints = new JMenuItem(Messages.getString("Frame.controlPoints"));
		controlPoints.addActionListener(event -> new PointSettingsFrame(coordSys));

		settings.add(setValues);
		settings.add(controlPoints);
		settings.add(window);
		settings.addSeparator();
		settings.add(showHints);
		settings.add(ignoreOutliers);
		settings.add(autoAdjusting);
		settings.addSeparator();
		settings.add(delPoints);

		options = new JMenu(Messages.getString("Frame.options"));
		options.setMnemonic(KeyEvent.VK_O);

		language = new JMenu(Messages.getString("Frame.language"));
		options.add(language);

		english = new JMenuItem(Messages.getString("Frame.english"));
		english.addActionListener(event -> Messages.changeBundle("com.JayPi4c.messages_en"));
		german = new JMenuItem(Messages.getString("Frame.german"));
		german.addActionListener(event -> Messages.changeBundle("com.JayPi4c.messages_de"));

		language.add(english);
		language.add(german);

		// help:
		help = new JMenu(Messages.getString("Frame.help"));

		credits = new JMenuItem(Messages.getString("Frame.credits"));
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

			JOptionPane.showMessageDialog(null, ep, Messages.getString("Frame.credits"),
					JOptionPane.INFORMATION_MESSAGE);
		});

		help.add(credits);

		// Die egentliche MenuBar:
		menuBar = new JMenuBar();
		menuBar.add(settings);
		menuBar.add(options);
		menuBar.add(help);

		setJMenuBar(menuBar);

		setLayout(new BorderLayout());
		add(coordSys, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
		Messages.registerListener(this);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);

	}

	@Override
	public void onLocaleChange() {
		setTitle(Messages.getString("Frame.title"));
		settings.setText(Messages.getString("Frame.settings"));
		autoAdjusting.setText(Messages.getString("Frame.autoAdjust"));
		autoAdjusting.setToolTipText(Messages.getString("Frame.autoAdjust.toolTip"));
		ignoreOutliers.setText(Messages.getString("Frame.ignoreOutliers"));
		ignoreOutliers.setToolTipText(Messages.getString("Frame.ignoreOutliers.toolTip"));
		showHints.setText(Messages.getString("Frame.showHints"));
		showHints.setToolTipText(Messages.getString("Frame.showHints.toolTip"));
		window.setText(Messages.getString("Frame.changeWindow"));
		setValues.setText(Messages.getString("Frame.setValues"));
		delPoints.setText(Messages.getString("Frame.delPoints"));
		controlPoints.setText(Messages.getString("Frame.controlPoints"));
		options.setText(Messages.getString("Frame.options"));
		language.setText(Messages.getString("Frame.language"));
		english.setText(Messages.getString("Frame.english"));
		german.setText(Messages.getString("Frame.german"));
		help.setText(Messages.getString("Frame.help"));
		credits.setText(Messages.getString("Frame.credits"));
	}

}
