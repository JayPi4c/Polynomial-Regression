package com.JayPi4c.view;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.JayPi4c.utils.Messages;

public class SettingsWindowElementView extends JPanel {

	private static final long serialVersionUID = 1L;

	private TitledBorder titledBorder;
	private JButton reset;
	private JTextField inputField;
	private double initialValue;

	public SettingsWindowElementView(String title, double initVal) {
		initialValue = initVal;
		setBorder(titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY),
				title));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		inputField = new JTextField(initVal + "");
		add(inputField);
		add(reset = new JButton(Messages.getString("Settings.window.reset")));
	}

	public JButton getResetButton() {
		return reset;
	}

	public double getInput() throws NumberFormatException {
		return Double.parseDouble(inputField.getText());
	}

	public void setInput(double d) {
		inputField.setText(d + "");
	}

	public double getResetValue() {
		return initialValue;
	}

	public TitledBorder getTitledBorer() {
		return titledBorder;
	}

	public void setResetController(ActionListener controller) {
		reset.addActionListener(controller);
	}

}
