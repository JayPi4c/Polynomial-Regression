package com.JayPi4c.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import com.JayPi4c.model.IModel;
import com.JayPi4c.utils.ILanguageChangeListener;
import com.JayPi4c.utils.Messages;
import com.JayPi4c.view.SettingsLanguageView;
import com.JayPi4c.view.SettingsView;

public class SettingsLanguageController implements ILanguageChangeListener {

	IModel model;
	SettingsLanguageView view;
	SettingsView parentView;

	public SettingsLanguageController(IModel model, SettingsView view) {
		this.model = model;
		this.parentView = view;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				SettingsLanguageController.this.view = new SettingsLanguageView();
				parentView.setContentViewPort(SettingsLanguageController.this.view);
				addListeners();
			}
		});
		Messages.registerListener(this);
	}

	private void addListeners() {
		view.addEnglishListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Messages.changeBundle("com.JayPi4c.lang.messages_en");
			}
		});
		view.addGermanListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Messages.changeBundle("com.JayPi4c.lang.messages_de");
			}
		});
	}

	public void onLanguageChanged() {
		view.getGermanButton().setText(Messages.getString("Settings.language.german"));
		view.getEnglishButton().setText(Messages.getString("Settings.language.english"));
	}

}
