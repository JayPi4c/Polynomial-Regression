package com.JayPi4c.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import com.JayPi4c.Main;

public class SettingsPanel extends JPanel {

	private static final long serialVersionUID = -6865806340350516582L;
	private JTextField input;
	private JButton apply;

	private ActionListener al;
	private double current;

	public SettingsPanel(String title, String toolTip, int min, int max, double current) {
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), title));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setToolTipText(toolTip);
		this.current = current;
		// control the values in this panel:
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		JSlider slider = new JSlider(min, max, (int) limit(current, min, max));
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

	public double limit(double value, double min, double max) {
		return Math.max(min, Math.min(value, max));
	}

	protected void apply() {
		al.actionPerformed(null);
	}

	protected void addListener(ActionListener al) {
		this.al = al;
		apply.addActionListener(al);
	}

	protected double getValue() {
		try {
			return Double.parseDouble(input.getText());
		} catch (NumberFormatException e) {
			return current;
		}
	}

}