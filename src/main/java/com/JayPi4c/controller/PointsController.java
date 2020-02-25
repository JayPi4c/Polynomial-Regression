package com.JayPi4c.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.JayPi4c.model.IModel;
import com.JayPi4c.utils.ILanguageChangeListener;
import com.JayPi4c.utils.IPointAddedListener;
import com.JayPi4c.utils.Messages;
import com.JayPi4c.view.MainView;
import com.JayPi4c.view.PointsView;

public class PointsController implements ILanguageChangeListener {

	IModel model;
	MainView mainView;
	PointsView view;

	ILanguageChangeListener currentContentController = null;

	private static boolean isActive = false;

	public PointsController(IModel model, final MainView mainView) {
		isActive = true;
		this.model = model;
		this.mainView = mainView;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				view = new PointsView(mainView);
				addControllers();
				currentContentController = new PointsListController(PointsController.this.model, view, mainView);
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
				isActive = false;
			}

			@Override
			public void windowActivated(WindowEvent e) {

			}
		});
		view.setDeletePointsController(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deletePoints();
			}
		});

		view.setListPointsController(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeListeners();
				currentContentController = new PointsListController(model, view, mainView);
			}
		});

	}

	private void removeListeners() {
		if (currentContentController != null) {
			Messages.removeListener(currentContentController);
			if (currentContentController instanceof IPointAddedListener) {
				CoordinateSystemController.removePointAddedListener((IPointAddedListener) currentContentController);
			}
		}
	}

	private void deletePoints() {
		int answer = JOptionPane.showConfirmDialog(view, Messages.getString("Points.delete.message"),
				Messages.getString("Points.delete.messageTitle"), JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION) {
			model.removeAllPoints();
			model.setPolynomial(null);
			if (currentContentController instanceof IPointAddedListener) {
				IPointAddedListener l = (IPointAddedListener) currentContentController;
				l.onPointAdded();
			}
			mainView.getCoordinateSystemView().repaint();
		}
	}

	@Override
	public void onLanguageChanged() {
		view.getDeletePointsButton().setText(Messages.getString("Points.delete"));
		view.getImportPointsButton().setText(Messages.getString("Points.import"));
		view.getListPointsButton().setText(Messages.getString("Points.list"));
		view.setTitle(Messages.getString("Points.title"));
	}

	public static boolean isActive() {
		return isActive;
	}

}
