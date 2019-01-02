package com.JayPi4c.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import com.JayPi4c.logic.Logic;

public class SettingsFrame extends JFrame {

	private static final long serialVersionUID = -5987437176470122610L;

	public SettingsFrame() {
		super("Settings");
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(new SettingsPanel("Degree", 0, Logic.maxDegree));
		contentPanel.add(new SettingsPanel("Threshold", 1, 50));
		contentPanel.add(new SettingsPanel("Max Degree", 1, 10));

		this.setLayout(new BorderLayout());
		this.add(contentPanel, BorderLayout.CENTER);
		JButton apply = new JButton("apply");
		apply.addActionListener(event -> {
			SettingsPanel sp = (SettingsPanel) contentPanel.getComponent(0);
			Logic.degree = (int) sp.getValue();
			sp = (SettingsPanel) contentPanel.getComponent(1);
			Logic.threshold = sp.getValue();
			sp = (SettingsPanel) contentPanel.getComponent(2);
			Logic.maxDegree = (int) sp.getValue();
			setVisible(false);
			dispose();
		});
		this.add(apply, BorderLayout.SOUTH);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.pack();
	}

	class SettingsPanel extends JPanel {

		private static final long serialVersionUID = -6865806340350516582L;
		JTextField input;

		public SettingsPanel(String title, int min, int max) {
			this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), title));
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			JPanel inputPanel = new JPanel();
			inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
			JSlider slider = new JSlider(min, max, (min + max) / 2);
			input = new JTextField(slider.getValue() + "");
			slider.addChangeListener(event -> input.setText(slider.getValue() + ""));
			input.setMaximumSize(new Dimension(200, (int) input.getPreferredSize().getHeight()));
			inputPanel.add(input);
			inputPanel.add(slider);

			this.add(inputPanel);

			this.setVisible(true);
			JButton reset = new JButton("reset");
			reset.addActionListener(event -> {
				slider.setValue((min + max) / 2);
				input.setText(slider.getValue() + "");
			});
			this.add(reset);
		}

		double getValue() {
			return Double.parseDouble(input.getText());
		}

	}

}
