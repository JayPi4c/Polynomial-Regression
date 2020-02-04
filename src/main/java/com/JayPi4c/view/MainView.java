package com.JayPi4c.view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import com.JayPi4c.controller.CoordinateSystemController;
import com.JayPi4c.model.IModel;
import com.JayPi4c.utils.Messages;

public class MainView extends JFrame {
	private static final long serialVersionUID = 1L;

	// private IModel model;

	private CoordinateSystemView coordSystemView;

	private JMenuBar menuBar;
	private JMenuItem settings;
	private JMenu options;
	private JMenuItem points;

	private JMenu help;
	private JMenuItem credits;

	public MainView(IModel model) {
		super(Messages.getString("Main.title"));
		// this.model = model;

		settings = new JMenuItem(Messages.getString("Main.settings"));
		points = new JMenuItem(Messages.getString("Main.points"));

		options = new JMenu(Messages.getString("Main.options"));

		options.add(settings);
		options.add(points);

		credits = new JMenuItem(Messages.getString("Main.credits"));

		help = new JMenu(Messages.getString("Main.help"));
		help.add(credits);
		// add menus to menubar
		menuBar = new JMenuBar();

		menuBar.add(options);
		menuBar.add(help);
		this.setJMenuBar(menuBar);
		coordSystemView = new CoordinateSystemView(model);
		this.setLayout(new BorderLayout());
		this.add(coordSystemView, BorderLayout.CENTER);
		pack();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void setCoordinateSystemController(CoordinateSystemController coordController) {
		this.coordSystemView.setController(coordController);
	}

	public void setSettingsController(ActionListener controller) {
		settings.addActionListener(controller);
	}

	public void setPointsController(ActionListener controller) {
		points.addActionListener(controller);
	}

	public void setCreditsController(ActionListener controller) {
		credits.addActionListener(controller);
	}

	public CoordinateSystemView getCoordinateSystemView() {
		return coordSystemView;
	}

	public JMenu getHelpButton() {
		return help;
	}

	JMenuItem getCreditsButton() {
		return credits;
	}

	public JMenu getOptionsButton() {
		return options;
	}

	public JMenuItem getSettingsButton() {
		return settings;
	}

	public JMenuItem getPointsButton() {
		return points;
	}
}
