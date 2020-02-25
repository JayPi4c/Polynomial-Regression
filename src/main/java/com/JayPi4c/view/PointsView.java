package com.JayPi4c.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.JayPi4c.utils.Messages;

public class PointsView extends JFrame {

	private static final long serialVersionUID = -4435519779958527967L;

	JScrollPane choosePane;
	JPanel choosePanel;
	JScrollPane contentScrollPane;

	JButton listPoints;
	JButton importPoints;
	JButton deletePoints;

	final private int CHOOSE_PANEL_WIDTH = 150;
	final private int CONTENT_PANEL_WIDTH = 300;
	final private int VIEW_HEIGHT = 400;

	public PointsView(MainView view) {
		super(Messages.getString("Points.title"));

		choosePanel = new JPanel();
		choosePanel.setLayout(new BoxLayout(choosePanel, BoxLayout.Y_AXIS));

		// choosePanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		listPoints = new JButton(Messages.getString("Points.list"));
		importPoints = new JButton(Messages.getString("Points.import"));
		deletePoints = new JButton(Messages.getString("Points.delete"));

		// TODO: Synchronize all buttons to the width of the biggest button. Part of
		// Controller or View (Probably Controller, with extra function getting passed
		// all buttons and syncing the width)
		// Math.max(CHOOSE_PANEL_WIDTH, (int) listPoints.getPreferredSize().getWidth())
		listPoints.setMaximumSize(new Dimension(CHOOSE_PANEL_WIDTH, (int) listPoints.getPreferredSize().getHeight()));

		importPoints
				.setMaximumSize(new Dimension(CHOOSE_PANEL_WIDTH, (int) importPoints.getPreferredSize().getHeight()));
		// Math.max(CHOOSE_PANEL_WIDTH, (int)
		// deletePoints.getPreferredSize().getWidth())
		deletePoints
				.setMaximumSize(new Dimension(CHOOSE_PANEL_WIDTH, (int) deletePoints.getPreferredSize().getHeight()));

		choosePanel.add(listPoints);
		choosePanel.add(importPoints);
		choosePanel.add(deletePoints);

		choosePane = new JScrollPane();
		choosePane.setPreferredSize(new Dimension(CHOOSE_PANEL_WIDTH, VIEW_HEIGHT));
		choosePane.setViewportView(choosePanel);

		contentScrollPane = new JScrollPane();
		contentScrollPane.setPreferredSize(new Dimension(CONTENT_PANEL_WIDTH, VIEW_HEIGHT));
		// contentScrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		setLayout(new BorderLayout());
		add(choosePane, BorderLayout.WEST);
		add(contentScrollPane, BorderLayout.EAST);
		pack();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(view);
	}

	public void setContentViewPort(JPanel view) {
		contentScrollPane.setViewportView(view);
	}

	public void setListPointsController(ActionListener controller) {
		listPoints.addActionListener(controller);
	}

	public void setImportPointsController(ActionListener controller) {
		importPoints.addActionListener(controller);
	}

	public void setDeletePointsController(ActionListener controller) {
		deletePoints.addActionListener(controller);
	}

	public JButton getListPointsButton() {
		return listPoints;
	}

	public JButton getImportPointsButton() {
		return importPoints;
	}

	public JButton getDeletePointsButton() {
		return deletePoints;
	}
}
