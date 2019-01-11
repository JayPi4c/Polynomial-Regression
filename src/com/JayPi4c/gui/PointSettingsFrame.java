package com.JayPi4c.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

public class PointSettingsFrame extends JFrame {

	private static final long serialVersionUID = -1864802896071899983L;

	private CoordinateSystem coordSys;

	public PointSettingsFrame(CoordinateSystem sys) {
		super("Point Settings");
		coordSys = sys;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(200, 300));
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		for (int i = 0; i < coordSys.getLogic().points.size(); i++)
			contentPanel.add(new PointSettingsPanel(coordSys.getLogic().points, i));
		scrollPane.setViewportView(contentPanel);
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		JButton addButton = new JButton("Add");
		addButton.addActionListener(event -> {
			System.out.println("Frage mit OptionPane nach den Koordinaten des neuen Punktes");
		});
		addButton.setVisible(true);
		controlPanel.add(addButton);

		JButton done = new JButton("Done");
		done.addActionListener(event -> {
			setVisible(false);
			dispose();
			coordSys.getLogic().update();
			coordSys.repaint();
		});
		controlPanel.add(done);

		this.add(controlPanel, BorderLayout.SOUTH);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);

	}

	class PointSettingsPanel extends JPanel {

		private static final long serialVersionUID = -6900549933290746043L;

		public PointSettingsPanel(ArrayList<Point> points, int index) {
			this.setSize(new Dimension(75, this.getHeight()));
			this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY),
					"Punkt #" + (index + 1)));
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			Point p = points.get(index);
			JLabel label = new JLabel("( " + p.getX() + " | " + p.getY() + ")");
			this.add(label);
			JButton button = new JButton("delete");
			button.addActionListener(event -> {
				points.remove(index);
				button.setEnabled(false);
				this.setEnabled(false);
			});
			this.add(button);
			this.setVisible(true);
		}
	}
}
