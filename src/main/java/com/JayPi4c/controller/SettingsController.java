package com.JayPi4c.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.SwingUtilities;

import com.JayPi4c.model.IModel;
import com.JayPi4c.utils.ILanguageChangeListener;
import com.JayPi4c.utils.Messages;
import com.JayPi4c.view.MainView;
import com.JayPi4c.view.SettingsView;

public class SettingsController implements ILanguageChangeListener {

	SettingsView view;
	MainView mainView;
	IModel model;

	ILanguageChangeListener currentContentController = null;

	private static boolean isActice = false;

	SettingsController(IModel model, MainView mainView) {
		isActice = true;
		this.model = model;
		this.mainView = mainView;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				view = new SettingsView();
				addControllers();
				new SettingsRegressionController(SettingsController.this.model, view, mainView);
			}
		});
		Messages.registerListener(this);
	}

	private void addControllers() {
		view.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}

			@Override
			public void windowClosing(WindowEvent e) {

			}

			@Override
			public void windowClosed(WindowEvent e) {
				isActice = false;
			}

			@Override
			public void windowActivated(WindowEvent e) {

			}
		});
		view.setRegressionController(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeLanguageListener();
				new SettingsRegressionController(model, view, mainView);
			}
		});

		view.setLanguageController(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeLanguageListener();
				currentContentController = new SettingsLanguageController(model, view);
			}
		});

		view.setWindowController(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeLanguageListener();
				new SettingsWindowController(model, view, mainView);
			}
		});
	}

	private void removeLanguageListener() {
		if (currentContentController != null)
			Messages.removeListener(currentContentController);

	}

	@Override
	public void onLanguageChanged() {
		view.setTitle(Messages.getString("Settings.title"));
		view.getWindow().setText(Messages.getString("Settings.window"));
		view.getRegression().setText(Messages.getString("Settings.regression"));
		view.getLanguage().setText(Messages.getString("Settings.language"));
	}

	public static boolean isActive() {
		return isActice;
	}

}
