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

public class SettingsRegressionElementView<T extends Number> extends JPanel {

	private static final long serialVersionUID = 1L;

	private TitledBorder titledBorder;
	private JButton reset;
	private JTextField inputField;
	private T initialValue;

	private Class<T> type;

	public SettingsRegressionElementView(String title, T initialValue, Class<T> type) {
		this.type = type;
		this.initialValue = initialValue;
		setBorder(titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY),
				title));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		inputField = new JTextField(initialValue.toString());
		add(inputField);
		add(reset = new JButton(Messages.getString("Settings.regression.reset")));
	}

	// Ist das zulässig? Darf eine View Generics haben?
	public T getInput() throws NumberFormatException {
		if (type == Double.class)
			return type.cast(Double.parseDouble(inputField.getText()));
		return type.cast(Integer.parseInt(inputField.getText()));

	}

	public void setInput(T d) {
		inputField.setText(d.toString());
	}

	public T getResetValue() {
		return initialValue;
	}

	public TitledBorder getTitledBorder() {
		return titledBorder;
	}

	public JButton getResetButton() {
		return reset;
	}

	public void setResetController(ActionListener controller) {
		reset.addActionListener(controller);
	}

}
