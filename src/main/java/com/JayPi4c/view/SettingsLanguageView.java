package com.JayPi4c.view;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.JayPi4c.utils.Messages;

public class SettingsLanguageView extends JPanel {

	private static final long serialVersionUID = 5777610760861338152L;

	JButton english;
	JButton german;

	public SettingsLanguageView() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		english = new JButton(Messages.getString("Settings.language.english"));
		german = new JButton(Messages.getString("Settings.language.german"));
		add(english);
		add(german);
	}

	public void addGermanListener(ActionListener controller) {
		german.addActionListener(controller);
	}

	public void addEnglishListener(ActionListener controller) {
		english.addActionListener(controller);
	}

	public JButton getEnglishButton() {
		return english;
	}

	public JButton getGermanButton() {
		return german;
	}

}
