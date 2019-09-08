package com.JayPi4c.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import com.JayPi4c.logic.Point;

public class PointSettingsFrame extends JFrame {

	private static final long serialVersionUID = -1864802896071899983L;

	private CoordinateSystem coordSys;

	private JPanel contentPanel;
	private JScrollPane scrollPane;
	public static final int WIDTH = 220;
	public static final int HEIGHT = 300;
	public static final int INPUT_COLUMNS = 5;

	public PointSettingsFrame(CoordinateSystem sys) {
		super("Point Settings");
		coordSys = sys;
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		loadComponents(0);

		scrollPane.setViewportView(contentPanel);
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		JButton addButton = new JButton("Add");
		addButton.addActionListener(event -> {

			JTextField xField = new JTextField(INPUT_COLUMNS);
			JTextField yField = new JTextField(INPUT_COLUMNS);

			JPanel myPanel = new JPanel();
			myPanel.add(new JLabel("x:"));
			myPanel.add(xField);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("y:"));
			myPanel.add(yField);
			int result = JOptionPane.showConfirmDialog(null, myPanel, "Please enter X and Y Coords",
					JOptionPane.OK_CANCEL_OPTION);
			double x, y;
			if (result == JOptionPane.OK_OPTION) {
				try {
					x = Double.parseDouble(xField.getText());
					y = Double.parseDouble(yField.getText());
					coordSys.getLogic().addPoint(new Point(x, y));
					contentPanel.add(new PointSettingsPanel(coordSys.getLogic().points,
							coordSys.getLogic().points.size() - 1, this));
					contentPanel.revalidate();
					contentPanel.repaint();

				} catch (NumberFormatException exc) {
					JOptionPane.showMessageDialog(null, "Keine Zahlen eingegeben", "No Number Error",
							JOptionPane.OK_OPTION);
				}
			}
		});
		addButton.setVisible(true);
		controlPanel.add(addButton);

		JButton done = new JButton("Done");
		done.addActionListener(event -> {
			setVisible(false);
			dispose();
			if (coordSys.getLogic().points.size() > 0) {
				coordSys.getLogic().update();
			}
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

	private void loadComponents(int start) {
		for (int i = start; i < coordSys.getLogic().points.size(); i++)
			contentPanel.add(new PointSettingsPanel(coordSys.getLogic().points, i, this));
	}

	public void deleteComponent(int index) {
		for (int i = this.contentPanel.getComponentCount() - 1; i >= index; i--)
			this.contentPanel.remove(i);
		loadComponents(index);
		this.contentPanel.revalidate();
		this.contentPanel.repaint();
	}

	class PointSettingsPanel extends JPanel {

		private static final long serialVersionUID = -6900549933290746043L;

		private PointSettingsFrame parent;
		private int index;
		public static final int TIMER_DELAY = 10;

		public PointSettingsPanel(ArrayList<Point> points, int index, PointSettingsFrame parent) {
			this.parent = parent;
			this.index = index;
			this.setSize(new Dimension(75, this.getHeight()));
			if (coordSys.getLogic().unignoredPoints.size() != 0)
				this.setBackground(
						coordSys.getLogic().unignoredPoints.contains(points.get(index)) ? this.getBackground()
								: Color.RED);
			this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY),
					"Punkt #" + (index + 1)));
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			Point p = points.get(index);

			DecimalFormat df = new DecimalFormat("#.####");
			df.setRoundingMode(RoundingMode.HALF_UP);
			JLabel label = new JLabel("( " + df.format(p.getX()) + " | " + df.format(p.getY()) + ")");
			this.add(label);
			JButton button = new JButton("delete");
			button.addActionListener(event -> {
				points.remove(index);
				button.setEnabled(false);
				this.setEnabled(false);
				this.slideOut();
			});
			this.add(button);
			this.setVisible(true);
		}

		public void slideOut() {
			new Timer(TIMER_DELAY, event -> {
				int x = this.getX();
				if (x <= parent.getX())
					this.setLocation(x + 10, this.getY());
				else {
					((Timer) event.getSource()).stop();
					parent.deleteComponent(index);
				}
				parent.repaint();
			}).start();
		}

	}
}
