package com.JayPi4c.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.JayPi4c.model.IModel;
import com.JayPi4c.model.Point;
import com.JayPi4c.utils.ILanguageChangeListener;
import com.JayPi4c.utils.Messages;
import com.JayPi4c.view.MainView;
import com.JayPi4c.view.PointsImportView;
import com.JayPi4c.view.PointsView;

public class PointsImportController implements ILanguageChangeListener {

	MainView mainView;
	PointsView parentView;
	PointsImportView view;
	IModel model;

	String path = "";

	public PointsImportController(IModel model, PointsView view, MainView mainView) {
		this.model = model;
		this.parentView = view;
		this.mainView = mainView;
		this.view = new PointsImportView();

		addControllers();

		parentView.setContentViewPort(this.view);

		Messages.registerListener(this);
	}

	private void addControllers() {
		view.addPathChooseListener(e -> {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
			chooser.setFileFilter(filter);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (chooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
				path = chooser.getSelectedFile().getAbsolutePath();
				view.setPath(path);
			}
		});

		view.addImportButtonListener(e -> {
			if (path.equals("")) {
				JOptionPane.showMessageDialog(view, Messages.getString("Points.import.error"));
				return;
			}
			try {
				BufferedReader br = Files.newBufferedReader(Paths.get(path));
				String line = null;
				// skip the header line
				// if(header)
				br.readLine();
				while ((line = br.readLine()) != null) {
					String[] parts = line.split(",");
					double x = Double.parseDouble(parts[0]);
					double y = Double.parseDouble(parts[1]);
					model.addPoint(new Point(x, y));
				}
				model.update();
				mainView.getCoordinateSystemView().repaint();

			} catch (IOException exception) {
				exception.printStackTrace();
			} catch (NumberFormatException nfe) {
				System.out.println("could not format");
			}

		});
	}

	@Override
	public void onLanguageChanged() {
		view.getCustomButton().setText(Messages.getString("Points.import.delimiter.custom"));
		view.getTabButton().setText(Messages.getString("Points.import.delimiter.tab"));
		view.getDelimiterBorder().setTitle(Messages.getString("Points.import.delimiter"));

		view.getPathBorder().setTitle(Messages.getString("Points.import.path"));
		view.getPathButton().setText(Messages.getString("Points.import.path.button"));
		if (path.equals(""))
			view.getPathLabel().setText(Messages.getString("Points.import.path.label"));

		view.getImportButton().setText(Messages.getString("Points.import"));
		view.repaint();
	}

}
