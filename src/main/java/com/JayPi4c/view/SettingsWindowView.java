package com.JayPi4c.view;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.JayPi4c.utils.Messages;

public class SettingsWindowView extends JPanel {

	private static final long serialVersionUID = 1L;

	JButton apply;
	JCheckBox showHints;

	public SettingsWindowView() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public void addApplyButton(ActionListener controller) {
		apply = new JButton(Messages.getString("Settings.window.apply"));
		apply.addActionListener(controller);
		add(apply);
	}

	public void addShowHintsCheckBox(boolean isChecked) {
		showHints = new JCheckBox(Messages.getString("Settings.window.showHints"));
		showHints.setSelected(isChecked);
		add(showHints);
	}

	public JCheckBox getShowHintsCheckBox() {
		return showHints;
	}
}
