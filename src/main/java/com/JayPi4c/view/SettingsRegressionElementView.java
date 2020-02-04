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
import javax.swing.border.TitledBorder;

public class SettingsPanel extends JPanel implements ILocaleChangeListener {

	private static final long serialVersionUID = -6865806340350516582L;
	private JTextField input;
	private JButton apply;
	private JButton reset;

	private ActionListener al;
	private double current;

	private String titleKey;
	private String toolTipKey;
	private TitledBorder titledBorder;

	public SettingsPanel(String titleKey, String toolTipKey, int min, int max, double current) {
		this.titleKey = titleKey;
		this.toolTipKey = toolTipKey;
		this.setBorder(titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY),
				Messages.getString(titleKey)));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setToolTipText(Messages.getString(toolTipKey));
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
		apply = new JButton(Messages.getString("SettingsPanel.apply"));

		reset = new JButton(Messages.getString("SettingsPanel.reset"));
		reset.addActionListener(event -> {
			slider.setValue((min + max) / 2);
			input.setText(slider.getValue() + "");
		});
		controlPanel.add(apply);
		controlPanel.add(reset);

		this.add(inputPanel);
		this.add(controlPanel);
		this.setVisible(true);
		Messages.registerListener(this);

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

	@Override
	public void onLocaleChange() {
		titledBorder.setTitle(Messages.getString(titleKey));
		setToolTipText(Messages.getString(toolTipKey));
		repaint();
		apply.setText(Messages.getString("SettingsPanel.apply"));
		reset.setText(Messages.getString("SettingsPanel.reset"));
	}

}