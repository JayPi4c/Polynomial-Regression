package com.JayPi4c.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class SettingsFrame extends JFrame implements ILocaleChangeListener {

	private static final long serialVersionUID = -5987437176470122610L;

	CoordinateSystem coordSys;

	private SettingsPanel degreePanel;
	private SettingsPanel thresholdPanel;
	private SettingsPanel maxDegreePanel;
	private SettingsPanel iterationsPanel;
	private SettingsPanel ignoreCountPanel;

	private JButton done;
	private JButton apply;

	public SettingsFrame(CoordinateSystem sys) {
		super(Messages.getString("SettingsFrame.title"));
		coordSys = sys;

		degreePanel = new SettingsPanel("SettingsFrame.degree", "SettingsFrame.degreeTooltip", 0,
				coordSys.getLogic().maxDegree, coordSys.getLogic().degree);
		degreePanel.addListener(event -> coordSys.getLogic().degree = (int) degreePanel.getValue());
		thresholdPanel = new SettingsPanel("SettingsFrame.threshold", "SettingsFrame.thresholdTooltip", 1, 100,
				coordSys.getLogic().threshold);
		thresholdPanel.addListener(event -> coordSys.getLogic().threshold = thresholdPanel.getValue());
		maxDegreePanel = new SettingsPanel("SettingsFrame.maxDegree", "SettingsFrame.maxDegreeTooltip", 1, 10,
				coordSys.getLogic().maxDegree);
		maxDegreePanel.addListener(event -> coordSys.getLogic().maxDegree = (int) maxDegreePanel.getValue());
		iterationsPanel = new SettingsPanel("SettingsFrame.iterations", "SettingsFrame.iterations.toolTip", 1, 100,
				coordSys.getLogic().iterations);
		iterationsPanel.addListener(event -> coordSys.getLogic().iterations = (int) iterationsPanel.getValue());
		ignoreCountPanel = new SettingsPanel("SettingsFrame.ignoreCount", "SettingsFrame.ignoreCount.toolTip", 0, 10,
				coordSys.getLogic().ignoreCount);
		ignoreCountPanel.addListener(event -> coordSys.getLogic().ignoreCount = (int) ignoreCountPanel.getValue());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

		contentPanel.add(degreePanel);
		contentPanel.add(maxDegreePanel);
		contentPanel.add(thresholdPanel);
		contentPanel.add(iterationsPanel);
		contentPanel.add(ignoreCountPanel);

		this.setLayout(new BorderLayout());
		this.add(contentPanel, BorderLayout.CENTER);
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		apply = new JButton(Messages.getString("SettingsFrame.applyAll"));
		apply.addActionListener(event -> {
			for (Component c : contentPanel.getComponents()) {
				SettingsPanel sp = (SettingsPanel) (c);
				sp.apply();
			}

		});
		controlPanel.add(apply);
		done = new JButton(Messages.getString("SettingsFrame.done"));
		done.addActionListener(event -> {
			setVisible(false);
			dispose();
			Messages.removeListener(SettingsFrame.this);
			if (coordSys.getLogic().points.size() > 0)
				coordSys.getLogic().update();
			coordSys.repaint();
		});
		controlPanel.add(done);
		this.add(controlPanel, BorderLayout.SOUTH);
		this.setResizable(false);

		Messages.registerListener(this);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.pack();
	}

	@Override
	public void onLocaleChange() {
		setTitle(Messages.getString("SettingsFrame.title"));
		done.setText(Messages.getString("SettingsFrame.done"));
		apply.setText(Messages.getString("SettingsFrame.applyAll"));
	}

}
