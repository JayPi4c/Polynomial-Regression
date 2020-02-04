package com.JayPi4c.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.JayPi4c.model.IModel;
import com.JayPi4c.utils.Messages;
import com.JayPi4c.view.MainView;
import com.JayPi4c.view.SettingsRegressionElementView;
import com.JayPi4c.view.SettingsRegressionView;
import com.JayPi4c.view.SettingsView;

public class SettingsRegressionController {

	IModel model;
	SettingsRegressionView view;

	// Gui elements
	SettingsRegressionElementView<Integer> srevDegree;
	SettingsRegressionElementView<Integer> srevMaxDegree;
	SettingsRegressionElementView<Double> srevThreshold;
	SettingsRegressionElementView<Integer> srevIterations;
	SettingsRegressionElementView<Integer> srevIgnoreCount;

	public SettingsRegressionController(IModel model, final SettingsView settingsView, final MainView mainView) {
		this.model = model;

		view = new SettingsRegressionView();
		loadComponents();

		view.addAutoAdjust(model.getAutoAdjusting());
		view.addIgnoreOutliers(model.getIgnoreOutliers());

		view.addApplyButton(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					model.setDegree(srevDegree.getInput());
				} catch (NumberFormatException exc) {
					showErrorDialog(Messages.getString("Settings.regression.degree.errorMsg"), view, srevDegree);
				}
				try {
					model.setMaxDegree(srevMaxDegree.getInput());
				} catch (NumberFormatException exc) {
					showErrorDialog(Messages.getString("Settings.regression.maxDegree.errorMsg"), view, srevMaxDegree);
				}
				try {
					model.setThreshold(srevThreshold.getInput());
				} catch (NumberFormatException exc) {
					showErrorDialog(Messages.getString("Settings.regression.threshold.errorMsg"), view, srevThreshold);
				}
				try {
					model.setIterations(srevIterations.getInput());
				} catch (NumberFormatException exc) {
					showErrorDialog(Messages.getString("Settings.regression.iterations.errorMsg"), view,
							srevIterations);
				}
				try {
					model.setIgnoreCount(srevIgnoreCount.getInput());
				} catch (NumberFormatException exc) {
					showErrorDialog(Messages.getString("Settings.regression.ignoreCount.errorMsg"), view,
							srevIgnoreCount);
				}

				model.setAutoAdjusting(view.getAutoAdjustingCheckBox().isSelected());
				model.setIgnoreOutliers(view.getIgnoreOutliersCheckBox().isSelected());
				model.update();

				mainView.getCoordinateSystemView().repaint();
			}
		});
		settingsView.setContentViewPort(view);
	}

	private <T extends Number> void showErrorDialog(String msg, Component parent,
			SettingsRegressionElementView<T> element) {
		JOptionPane.showMessageDialog(parent, msg);
		element.setInput(element.getResetValue());
	}

	private <T extends Number> void addController(SettingsRegressionElementView<T> elt) {
		elt.setResetController(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				elt.setInput(elt.getInput());
			}
		});
	}

	private void loadComponents() {
		srevDegree = new SettingsRegressionElementView<Integer>(Messages.getString("Settings.regression.degree"),
				model.getDegree(), Integer.class);
		addController(srevDegree);
		view.add(srevDegree);

		srevMaxDegree = new SettingsRegressionElementView<Integer>(Messages.getString("Settings.regression.maxDegree"),
				model.getMaxDegree(), Integer.class);
		addController(srevMaxDegree);

		view.add(srevMaxDegree);

		srevThreshold = new SettingsRegressionElementView<Double>(Messages.getString("Settings.regression.threshold"),
				model.getThreshold(), Double.class);
		addController(srevThreshold);

		view.add(srevThreshold);

		srevIterations = new SettingsRegressionElementView<Integer>(
				Messages.getString("Settings.regression.iterations"), model.getIterations(), Integer.class);
		addController(srevIterations);

		view.add(srevIterations);

		srevIgnoreCount = new SettingsRegressionElementView<Integer>(
				Messages.getString("Settings.regression.ignoreCount"), model.getIgnoreCount(), Integer.class);
		addController(srevIgnoreCount);

		view.add(srevIgnoreCount);
	}

}
