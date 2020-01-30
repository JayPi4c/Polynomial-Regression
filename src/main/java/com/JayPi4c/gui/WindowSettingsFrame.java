package com.JayPi4c.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class WindowSettingsFrame extends JFrame implements ILocaleChangeListener {

	private static final long serialVersionUID = -5987437176470122610L;

	CoordinateSystem coordSys;

	private SettingsPanel neg_x_axisPanel;
	private SettingsPanel pos_x_axisPanel;
	private SettingsPanel neg_y_axisPanel;
	private SettingsPanel pos_y_axisPanel;
	private SettingsPanel xHintSpacerPanel;
	private SettingsPanel yHintSpacerPanel;

	private JButton apply;
	private JButton done;

	public WindowSettingsFrame(CoordinateSystem sys) {
		super(Messages.getString("WindowSettingsFrame.title"));
		coordSys = sys;

		neg_x_axisPanel = new SettingsPanel("WindowSettingsFrame.negXAxis", "WindowSettingsFrame.negXAxis.toolTip",
				-100, 0, coordSys.neg_x_axis);
		neg_x_axisPanel.addListener(event -> coordSys.neg_x_axis = Math.abs(neg_x_axisPanel.getValue()));
		pos_x_axisPanel = new SettingsPanel("WindowSettingsFrame.posXAxis", "WindowSettingsFrame.posXAxis.toolTip", 0,
				100, coordSys.pos_x_axis);
		pos_x_axisPanel.addListener(event -> coordSys.pos_x_axis = pos_x_axisPanel.getValue());
		neg_y_axisPanel = new SettingsPanel("WindowSettingsFrame.negYAxis", "WindowSettingsFrame.negYAxis.toolTip",
				-100, 0, coordSys.neg_y_axis);
		neg_y_axisPanel.addListener(event -> coordSys.neg_y_axis = Math.abs(neg_y_axisPanel.getValue()));
		pos_y_axisPanel = new SettingsPanel("WindowSettingsFrame.posYAxis", "WindowSettingsFrame.posYAxis.toolTip", 0,
				100, coordSys.pos_y_axis);
		pos_y_axisPanel.addListener(event -> coordSys.pos_y_axis = pos_y_axisPanel.getValue());
		xHintSpacerPanel = new SettingsPanel("WindowSettingsFrame.xHintSpacer",
				"WindowSettingsFrame.xHintSpacer.toolTip", 0, 50, coordSys.x_steps);
		xHintSpacerPanel.addListener(event -> coordSys.x_steps = xHintSpacerPanel.getValue());
		yHintSpacerPanel = new SettingsPanel("WindowSettingsFrame.yHintSpacer",
				"WindowSettingsFrame.yHintSpacer.toolTip", 0, 50, coordSys.y_steps);
		yHintSpacerPanel.addListener(event -> coordSys.y_steps = yHintSpacerPanel.getValue());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

		contentPanel.add(neg_x_axisPanel);
		contentPanel.add(pos_x_axisPanel);
		contentPanel.add(neg_y_axisPanel);
		contentPanel.add(pos_y_axisPanel);
		contentPanel.add(xHintSpacerPanel);
		contentPanel.add(yHintSpacerPanel);

		this.setLayout(new BorderLayout());
		this.add(contentPanel, BorderLayout.CENTER);
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		apply = new JButton(Messages.getString("WindowSettingsFrame.applyAll"));
		apply.addActionListener(event -> {
			for (Component c : contentPanel.getComponents()) {
				SettingsPanel sp = (SettingsPanel) (c);
				sp.apply();
			}

		});
		controlPanel.add(apply);
		done = new JButton(Messages.getString("WindowSettingsFrame.done"));
		done.addActionListener(event -> {
			setVisible(false);
			dispose();
			Messages.removeListener(WindowSettingsFrame.this);
			if (coordSys.getLogic().points.size() > 0)
				coordSys.getLogic().update();
			coordSys.repaint();
		});
		controlPanel.add(done);
		this.add(controlPanel, BorderLayout.SOUTH);
		this.setResizable(false);

		this.setLocationRelativeTo(null);
		this.setVisible(true);
		Messages.registerListener(this);

		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.pack();
	}

	@Override
	public void onLocaleChange() {
		setTitle(Messages.getString("WindowSettingsFrame.title"));
		done.setText(Messages.getString("WindowSettingsFrame.done"));
		apply.setText(Messages.getString("WindowSettingsFrame.apply"));
	}
}
