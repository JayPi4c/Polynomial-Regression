package com.JayPi4c.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.JayPi4c.Main;

public class SettingsFrame extends JFrame {

	private static final long serialVersionUID = -5987437176470122610L;

	CoordinateSystem coordSys;

	public SettingsFrame(CoordinateSystem sys) {
		super(Main.messages.getString("settings"));
		coordSys = sys;

		SettingsPanel degreePanel = new SettingsPanel(Main.messages.getString("degree"),
				Main.messages.getString("degreeTooltip"), 0, coordSys.getLogic().maxDegree, coordSys.getLogic().degree);
		degreePanel.addListener(event -> coordSys.getLogic().degree = (int) degreePanel.getValue());
		SettingsPanel thresholdPanel = new SettingsPanel(Main.messages.getString("threshold"),
				Main.messages.getString("thresholdTooltip"), 1, 100, coordSys.getLogic().threshold);
		thresholdPanel.addListener(event -> coordSys.getLogic().threshold = thresholdPanel.getValue());
		SettingsPanel maxDegreePanel = new SettingsPanel(Main.messages.getString("maxDegree"),
				Main.messages.getString("maxDegreeTooltip"), 1, 10, coordSys.getLogic().maxDegree);
		maxDegreePanel.addListener(event -> coordSys.getLogic().maxDegree = (int) maxDegreePanel.getValue());
		SettingsPanel iterationsPanel = new SettingsPanel("Iterations", "je höher um so genauer, aber langsamer.", 1,
				100, coordSys.getLogic().iterations);
		iterationsPanel.addListener(event -> coordSys.getLogic().iterations = (int) iterationsPanel.getValue());
		SettingsPanel ignoreCountPanel = new SettingsPanel("Ignore Count",
				"Die Anzahl der Punkte die maximal ignoriert werden dürfen.", 0, 10, coordSys.getLogic().ignoreCount);
		ignoreCountPanel.addListener(event -> coordSys.getLogic().ignoreCount = (int) ignoreCountPanel.getValue());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

		contentPanel.add(degreePanel);
		contentPanel.add(maxDegreePanel);
		contentPanel.add(thresholdPanel);
		contentPanel.add(iterationsPanel);
		contentPanel.add(ignoreCountPanel);

		this.setLayout(new BorderLayout());
		this.add(contentPanel, BorderLayout.CENTER);
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		JButton apply = new JButton(Main.messages.getString("applyAll"));
		apply.addActionListener(event -> {
			for (Component c : contentPanel.getComponents()) {
				SettingsPanel sp = (SettingsPanel) (c);
				sp.apply();
			}

		});
		controlPanel.add(apply);
		JButton done = new JButton("done");
		done.addActionListener(event -> {
			setVisible(false);
			dispose();
			if (coordSys.getLogic().points.size() > 0)
				coordSys.getLogic().update();
			coordSys.repaint();
		});
		controlPanel.add(done);
		this.add(controlPanel, BorderLayout.SOUTH);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.pack();
	}

}
