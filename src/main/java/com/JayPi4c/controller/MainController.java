package com.JayPi4c.controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;

import com.JayPi4c.model.IModel;
import com.JayPi4c.model.RegressionModel;
import com.JayPi4c.utils.ILanguageChangeListener;
import com.JayPi4c.utils.Messages;
import com.JayPi4c.view.MainView;

public class MainController implements ILanguageChangeListener {

	IModel model;
	MainView mainView;

	public MainController() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				model = new RegressionModel();
				mainView = new MainView(model);
				addControllers();
			}
		});
		Messages.registerListener(this);
	}

	private void addControllers() {
		mainView.setCoordinateSystemController(
				new CoordinateSystemController(model, mainView.getCoordinateSystemView()));
		mainView.setSettingsController(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!SettingsController.isActive())
					new SettingsController(model, mainView);
			}
		});

		mainView.setPointsController(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!PointsController.isActive())
					new PointsController(model, mainView);
			}
		});

		mainView.setCreditsController(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JEditorPane ep = new JEditorPane("text/html", Messages.getString("Main.credits.text"));

				ep.addHyperlinkListener(l -> {
					if (l.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
						try {
							Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
							if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
								desktop.browse(l.getURL().toURI());
						} catch (URISyntaxException | IOException ex) {
							System.out.println(ex.getMessage());
						}
					}
				});
				ep.setEditable(false);
				ep.setBackground(mainView.getBackground());

				JOptionPane.showMessageDialog(mainView, ep, Messages.getString("Main.credits"),
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

	}

	@Override
	public void onLanguageChanged() {
		mainView.getSettingsButton().setText(Messages.getString("Main.settings"));
		mainView.getOptionsButton().setText(Messages.getString("Main.options"));
		mainView.getPointsButton().setText(Messages.getString("Main.points"));
		mainView.getHelpButton().setText(Messages.getString("Main.help"));
		mainView.setTitle(Messages.getString("Main.title"));
	}

}
