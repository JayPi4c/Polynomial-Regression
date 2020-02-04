package com.JayPi4c.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import com.JayPi4c.model.IModel;
import com.JayPi4c.model.Point;
import com.JayPi4c.utils.ILanguageChangeListener;
import com.JayPi4c.utils.IPointAddedListener;
import com.JayPi4c.utils.Messages;
import com.JayPi4c.view.MainView;
import com.JayPi4c.view.PointsListElementView;
import com.JayPi4c.view.PointsListView;
import com.JayPi4c.view.PointsView;

public class PointsListController implements ILanguageChangeListener, IPointAddedListener {

	MainView mainView;
	PointsView parentView;
	PointsListView view;
	IModel model;

	public PointsListController(IModel model, PointsView view, MainView mainView) {
		this.model = model;
		this.parentView = view;
		this.mainView = mainView;

		PointsListController.this.view = new PointsListView();
		loadComponents(0);
		parentView.setContentViewPort(PointsListController.this.view);

		Messages.registerListener(this);
		CoordinateSystemController.registerPointAddedListener(this);

	}

	private void addListeners() {
		for (int i = 0; i < view.getComponentCount(); i++) {
			final PointsListElementView plev = (PointsListElementView) view.getComponent(i);
			plev.setDeleteController(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Point p = model.getPoints().get(plev.getIndex());
					model.removePoint(p);
					plev.getDeleteButton().setEnabled(false);
					slideOut(plev);
					model.update();
					mainView.getCoordinateSystemView().repaint();
				}
			});
		}
	}

	private void slideOut(final PointsListElementView plev) {
		new Timer(10, event -> {
			int x = plev.getX();
			if (x <= view.getWidth()) {
				plev.setLocation(x + 10, plev.getY());
			} else {
				((Timer) event.getSource()).stop();
				refreshComponents();
			}

		}).start();

	}

	private void refreshComponents() {
		view.removeAll();
		loadComponents(0);

		view.revalidate();
		view.repaint();

	}

	private void loadComponents(int start) {
		ArrayList<Point> pts = model.getPoints();
		for (int i = start; i < model.getPoints().size(); i++) {
			Point p = pts.get(i);
			PointsListElementView v = new PointsListElementView(i, p.getX(), p.getY(), false);
			view.add(v);
		}
		addListeners();
	}

	@Override
	public void onLanguageChanged() {
		for (Component elt : view.getComponents()) {
			PointsListElementView eltView = (PointsListElementView) elt;
			eltView.getTiltedBorder().setTitle(Messages.getString("Points.list.title") + eltView.getTitleNumber());
			eltView.getDeleteButton().setText(Messages.getString("Points.list.delete"));
			eltView.repaint();
		}
	}

	@Override
	public void onPointAdded() {
		refreshComponents();
	}

}
