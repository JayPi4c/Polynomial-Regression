package com.JayPi4c.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.JayPi4c.model.IModel;
import com.JayPi4c.utils.Messages;
import com.JayPi4c.view.MainView;
import com.JayPi4c.view.SettingsView;
import com.JayPi4c.view.SettingsWindowElementView;
import com.JayPi4c.view.SettingsWindowView;

public class SettingsWindowController {
	IModel model;
	SettingsWindowView view;

	SettingsWindowElementView swevPosX;
	SettingsWindowElementView swevPosY;
	SettingsWindowElementView swevNegX;
	SettingsWindowElementView swevNegY;
	SettingsWindowElementView swevSpacerX;
	SettingsWindowElementView swevSpacerY;

	public SettingsWindowController(IModel model, SettingsView settingsView, MainView mainView) {
		this.model = model;

		view = new SettingsWindowView();
		loadComponents();
		settingsView.setContentViewPort(view);
		view.addShowHintsCheckBox(model.drawHints());
		view.addApplyButton(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					model.setPos_x_axis(swevPosX.getInput());
				} catch (NumberFormatException exc) {
					showErrorDialog(Messages.getString("Settings.window.posX.errorMsg"), view, swevPosX);
				}
				try {
					model.setPos_y_axis(swevPosY.getInput());
				} catch (NumberFormatException exc) {
					showErrorDialog(Messages.getString("Settings.window.posY.errorMsg"), view, swevPosY);
				}
				try {
					model.setNeg_x_axis(swevNegX.getInput());
				} catch (NumberFormatException exc) {
					showErrorDialog(Messages.getString("Settings.window.negX.errorMsg"), view, swevNegX);
				}
				try {
					model.setNeg_y_axis(swevNegY.getInput());
				} catch (NumberFormatException exc) {
					showErrorDialog(Messages.getString("Settings.window.negY.errorMsg"), view, swevNegY);
				}
				try {
					model.setXSteps(swevSpacerX.getInput());
				} catch (NumberFormatException exc) {
					showErrorDialog(Messages.getString("Settings.window.spacerX.errorMsg"), view, swevSpacerX);
				}
				try {
					model.setYSteps(swevSpacerY.getInput());
				} catch (NumberFormatException exc) {
					showErrorDialog(Messages.getString("Settings.window.spacerY.errorMsg"), view, swevSpacerY);
				}
				model.setDrawHints(view.getShowHintsCheckBox().isSelected());

				model.update();

				mainView.getCoordinateSystemView().repaint();
			}
		});
	}

	private void showErrorDialog(String msg, Component parent, SettingsWindowElementView element) {
		JOptionPane.showMessageDialog(parent, msg);
		element.setInput(element.getResetValue());
	}

	private void addController(SettingsWindowElementView elt) {
		elt.setResetController(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				elt.setInput(elt.getResetValue());
			}
		});
	}

	private void loadComponents() {
		swevPosX = new SettingsWindowElementView(Messages.getString("Settings.window.posX"), model.getPos_x_axis());
		addController(swevPosX);
		view.add(swevPosX);

		swevNegX = new SettingsWindowElementView(Messages.getString("Settings.window.negX"), model.getNeg_x_axis());
		addController(swevNegX);
		view.add(swevNegX);

		swevPosY = new SettingsWindowElementView(Messages.getString("Settings.window.posY"), model.getPos_y_axis());
		addController(swevPosY);
		view.add(swevPosY);

		swevNegY = new SettingsWindowElementView(Messages.getString("Settings.window.negY"), model.getNeg_y_axis());
		addController(swevNegY);
		view.add(swevNegY);

		swevSpacerX = new SettingsWindowElementView(Messages.getString("Settings.window.spacerX"), model.getXSteps());
		addController(swevSpacerX);
		view.add(swevSpacerX);

		swevSpacerY = new SettingsWindowElementView(Messages.getString("Settings.window.spacerY"), model.getYSteps());
		addController(swevSpacerY);
		view.add(swevSpacerY);
	}

}
