package com.JayPi4c.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.JayPi4c.Main;
import com.JayPi4c.logic.Logic;

public class SettingsFrame extends JFrame {

	private static final long serialVersionUID = -5987437176470122610L;

	public SettingsFrame() {
		super(Main.messages.getString("settings"));

		SettingsPanel degreePanel = new SettingsPanel(Main.messages.getString("degree"),
				Main.messages.getString("degreeTooltip"), 0, Logic.maxDegree, Logic.degree);
		degreePanel.addListener(event -> Logic.degree = degreePanel.getValue());
		SettingsPanel thresholdPanel = new SettingsPanel(Main.messages.getString("threshold"),
				Main.messages.getString("thresholdTooltip"), 1, 100, Logic.threshold);
		thresholdPanel.addListener(event -> Logic.threshold = thresholdPanel.getValue());
		SettingsPanel maxDegreePanel = new SettingsPanel(Main.messages.getString("maxDegree"),
				Main.messages.getString("maxDegreeTooltip"), 1, 10, Logic.maxDegree);
		maxDegreePanel.addListener(event -> Logic.maxDegree = maxDegreePanel.getValue());
		SettingsPanel iterationsPanel = new SettingsPanel("Iterations", "je höher um so genauer, aber langsamer.", 1,
				100, Logic.iterations);
		iterationsPanel.addListener(event -> Logic.iterations = iterationsPanel.getValue());
		SettingsPanel ignoreCountPanel = new SettingsPanel("Ignore Count",
				"Die Anzahl der Punkte die maximal ignoriert werden dürfen.", 0, 10, Logic.ignoreCount);
		ignoreCountPanel.addListener(event -> Logic.ignoreCount = ignoreCountPanel.getValue());

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
			Logic.update();
			Main.coordSys.repaint();
		});
		controlPanel.add(done);
		this.add(controlPanel, BorderLayout.SOUTH);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.pack();
	}

	class SettingsPanel extends JPanel {

		private static final long serialVersionUID = -6865806340350516582L;
		JTextField input;
		JButton apply;

		ActionListener al;
		int current;

		public SettingsPanel(String title, String toolTip, int min, int max, int current) {
			this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), title));
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			this.setToolTipText(toolTip);
			this.current = current;
			// control the values in this panel:
			JPanel inputPanel = new JPanel();
			inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
			JSlider slider = new JSlider(min, max, limit(current, min, max));
			input = new JTextField(current + "");
			slider.addChangeListener(event -> input.setText(slider.getValue() + ""));
			input.setMaximumSize(new Dimension(200, (int) input.getPreferredSize().getHeight()));
			inputPanel.add(input);
			inputPanel.add(slider);

			JPanel controlPanel = new JPanel();
			controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
			apply = new JButton("apply");

			JButton reset = new JButton(Main.messages.getString("reset"));
			reset.addActionListener(event -> {
				slider.setValue((min + max) / 2);
				input.setText(slider.getValue() + "");
			});
			controlPanel.add(apply);
			controlPanel.add(reset);

			this.add(inputPanel);
			this.add(controlPanel);
			this.setVisible(true);

		}

		public int limit(int value, int min, int max) {
			return Math.max(min, Math.min(value, max));
		}

		void apply() {
			al.actionPerformed(null);
		}

		void addListener(ActionListener al) {
			this.al = al;
			apply.addActionListener(al);
		}

		int getValue() {
			try {
				return Integer.parseInt(input.getText());
			} catch (NumberFormatException e) {
				return current;
			}
		}

	}

}
