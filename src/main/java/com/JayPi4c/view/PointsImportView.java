package com.JayPi4c.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.JayPi4c.utils.Messages;

public class PointsImportView extends JPanel {

	private static final long serialVersionUID = 1L;

	JRadioButton tab, comma, custom;
	JTextField customDelimiter;
	ButtonGroup delimiterButtonGroup;
	JPanel delimiterPanel;
	TitledBorder delimiterBorder;

	JPanel pathPanel;
	JLabel pathLabel;
	JButton pathChooseButton;
	JFileChooser pathChooser;
	TitledBorder pathBorder;

	JButton importButton;

	public PointsImportView() {
		JPanel settingsPanel = new JPanel();

		RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
		rl.setFill(true);

		// settingsPanel.setLayout(rl);
		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

		// Delimitersettings
		tab = new JRadioButton(Messages.getString("Points.import.delimiter.tab"));
		comma = new JRadioButton(",");
		custom = new JRadioButton(Messages.getString("Points.import.delimiter.custom"));
		delimiterButtonGroup = new ButtonGroup();
		delimiterButtonGroup.add(comma);
		delimiterButtonGroup.add(tab);
		delimiterButtonGroup.add(custom);

		comma.setSelected(true);
		customDelimiter = new JTextField();
		customDelimiter.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				custom.setSelected(true);
			}
		});

		delimiterPanel = new JPanel();
		delimiterPanel.setLayout(new BoxLayout(delimiterPanel, BoxLayout.X_AXIS));
		delimiterPanel.setBorder(delimiterBorder = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.DARK_GRAY), Messages.getString("Points.import.delimiter")));
		delimiterPanel.add(comma);
		delimiterPanel.add(tab);
		delimiterPanel.add(custom);
		delimiterPanel.add(customDelimiter);

		settingsPanel.add(delimiterPanel);

		// file path

		pathLabel = new JLabel(Messages.getString("Points.import.path.label"));
		// TODO: restrict label width to not push button out of view
		pathChooseButton = new JButton(Messages.getString("Points.import.path.button"));
		pathPanel = new JPanel();
		pathPanel.setLayout(new BorderLayout());
		pathPanel.setBorder(pathBorder = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.DARK_GRAY), Messages.getString("Points.import.path")));
		pathPanel.add(pathLabel, BorderLayout.WEST);
		pathPanel.add(pathChooseButton, BorderLayout.EAST);
		settingsPanel.add(pathPanel);

		setLayout(new BorderLayout());

		JPanel importPanel = new JPanel();
		importPanel.setLayout(new FlowLayout());
		importButton = new JButton(Messages.getString("Points.import"));
		importPanel.add(importButton);
		add(settingsPanel, BorderLayout.NORTH);
		add(importPanel, BorderLayout.SOUTH);
	}

	public void addPathChooseListener(ActionListener al) {
		pathChooseButton.addActionListener(al);
	}

	public void addImportButtonListener(ActionListener al) {
		importButton.addActionListener(al);
	}

	public void setPath(String path) {
		pathLabel.setText(path);
	}

	public JRadioButton getTabButton() {
		return tab;
	}

	public JRadioButton getCustomButton() {
		return custom;
	}

	public TitledBorder getDelimiterBorder() {
		return delimiterBorder;
	}

	public JLabel getPathLabel() {
		return pathLabel;
	}

	public JButton getPathButton() {
		return pathChooseButton;
	}

	public TitledBorder getPathBorder() {
		return pathBorder;
	}

	public JButton getImportButton() {
		return importButton;
	}
}
