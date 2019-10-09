package com.JayPi4c.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.JayPi4c.Main;

public class WindowSettingsFrame extends JFrame {

	private static final long serialVersionUID = -5987437176470122610L;

	CoordinateSystem coordSys;

	public WindowSettingsFrame(CoordinateSystem sys) {
		super("alpha" + Main.messages.getString("settings"));
		coordSys = sys;

		SettingsPanel neg_x_axisPanel = new SettingsPanel(Main.messages.getString("negXAxis"),
				Main.messages.getString("negXAxisTooltip"), -100, 0, coordSys.neg_x_axis);
		neg_x_axisPanel.addListener(event -> coordSys.neg_x_axis = Math.abs(neg_x_axisPanel.getValue()));
		SettingsPanel pos_x_axisPanel = new SettingsPanel(Main.messages.getString("posXAxis"),
				Main.messages.getString("posXAxisTooltip"), 0, 100, coordSys.pos_x_axis);
		pos_x_axisPanel.addListener(event -> coordSys.pos_x_axis = pos_x_axisPanel.getValue());
		SettingsPanel neg_y_axisPanel = new SettingsPanel(Main.messages.getString("negYAxis"),
				Main.messages.getString("negYAxisTooltip"), -100, 0, coordSys.neg_y_axis);
		neg_y_axisPanel.addListener(event -> coordSys.neg_y_axis = Math.abs(neg_y_axisPanel.getValue()));
		SettingsPanel pos_y_axisPanel = new SettingsPanel("pos-y-axis", "posYAxisTooltip", 0, 100, coordSys.pos_y_axis);
		pos_y_axisPanel.addListener(event -> coordSys.pos_y_axis = pos_y_axisPanel.getValue());
		SettingsPanel xHintSpacerPanel = new SettingsPanel("xHintSpacer", "Der Platz zwischen den xHints", 0, 50,
				coordSys.x_steps);
		xHintSpacerPanel.addListener(event -> coordSys.x_steps = xHintSpacerPanel.getValue());
		SettingsPanel yHintSpacerPanel = new SettingsPanel("yHintSpacer", "Der Platz zwischen den yHints", 0, 50,
				coordSys.y_steps);
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
		JButton apply = new JButton(Main.messages.getString("applyAll"));
		apply.addActionListener(event -> {
			for (Component c : contentPanel.getComponents()) {
				SettingsPanel sp = (SettingsPanel) (c);
				sp.apply();
			}

		});
		controlPanel.add(apply);
		JButton done = new JButton("done");
		done.addActionListener(event -> {
			setVisible(false);
			dispose();
			if (coordSys.getLogic().points.size() > 0)
				coordSys.getLogic().update();
			coordSys.repaint();
		});
		controlPanel.add(done);
		this.add(controlPanel, BorderLayout.SOUTH);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.pack();
	}
}
