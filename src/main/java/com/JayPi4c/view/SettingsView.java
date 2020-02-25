package com.JayPi4c.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.JayPi4c.utils.Messages;

public class SettingsView extends JFrame {

	private static final long serialVersionUID = -3204918135300995614L;
	JScrollPane settingsChoosePane;
	JPanel settingsChoosePanel;
	JScrollPane contentScrollPane;

	JButton regression;
	JButton language;
	JButton window;

	public SettingsView() {
		super(Messages.getString("Settings.title"));
		settingsChoosePanel = new JPanel();
		settingsChoosePanel.setLayout(new BoxLayout(settingsChoosePanel, BoxLayout.Y_AXIS));

		regression = new JButton(Messages.getString("Settings.regression"));
		language = new JButton(Messages.getString("Settings.language"));
		window = new JButton(Messages.getString("Settings.window"));
		regression.setMaximumSize(new Dimension(150, (int) regression.getPreferredSize().getHeight()));
		language.setMaximumSize(new Dimension(150, (int) language.getPreferredSize().getHeight()));
		window.setMaximumSize(new Dimension(150, (int) window.getPreferredSize().getHeight()));

		settingsChoosePanel.add(regression);
		settingsChoosePanel.add(language);
		settingsChoosePanel.add(window);

		/*
		 * for (int i = 0; i < 15; i++) { JButton b = new JButton("i:" + i);
		 * b.setMaximumSize(new Dimension(150, (int) b.getPreferredSize().getHeight()));
		 * settingsChoosePanel.add(b); }
		 */

		settingsChoosePane = new JScrollPane();
		settingsChoosePane.setPreferredSize(new Dimension(150, 400));
		settingsChoosePane.setViewportView(settingsChoosePanel);

		contentScrollPane = new JScrollPane();
		contentScrollPane.setPreferredSize(new Dimension(300, 400));
		setLayout(new BorderLayout());
		add(settingsChoosePane, BorderLayout.WEST);
		add(contentScrollPane, BorderLayout.EAST);
		pack();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	public void setContentViewPort(JPanel view) {
		contentScrollPane.setViewportView(view);
	}

	public JScrollPane getContentScrollPane() {
		return contentScrollPane;
	}

	public void setRegressionController(ActionListener controller) {
		regression.addActionListener(controller);
	}

	public void setLanguageController(ActionListener controller) {
		language.addActionListener(controller);
	}

	public void setWindowController(ActionListener controller) {
		window.addActionListener(controller);
	}

	public JButton getRegression() {
		return regression;
	}

	public JButton getLanguage() {
		return language;
	}

	public JButton getWindow() {
		return window;
	}

}
