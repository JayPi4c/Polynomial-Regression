package com.JayPi4c.view;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.JayPi4c.utils.Messages;

public class SettingsRegressionView extends JPanel {

	private static final long serialVersionUID = 1L;

	JButton apply;
	JCheckBox autoAdjust;
	JCheckBox ignoreOutliers;

	public SettingsRegressionView() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public void addAutoAdjust(boolean isChecked) {
		autoAdjust = new JCheckBox(Messages.getString("Settings.regression.autoAdjust"));
		autoAdjust.setSelected(isChecked);
		add(autoAdjust);
	}

	public void addIgnoreOutliers(boolean isChecked) {
		ignoreOutliers = new JCheckBox(Messages.getString("Settings.regression.ignoreOutliers"));
		ignoreOutliers.setSelected(isChecked);
		add(ignoreOutliers);
	}

	public void addApplyButton(ActionListener controller) {
		apply = new JButton(Messages.getString("Settings.regression.apply"));
		apply.addActionListener(controller);
		add(apply);
	}

	public JCheckBox getAutoAdjustingCheckBox() {
		return autoAdjust;
	}

	public JCheckBox getIgnoreOutliersCheckBox() {
		return ignoreOutliers;
	}

}
