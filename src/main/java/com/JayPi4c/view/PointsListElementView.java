package com.JayPi4c.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.JayPi4c.utils.Messages;

public class PointsListElementView extends JPanel {

	private static final long serialVersionUID = -5166998911372075057L;

	private int index;

	private TitledBorder tiltedBorder;
	private JButton delete;

	private String titleNumber;

	public PointsListElementView(int index, double x, double y, boolean isIgnored) {

		this.index = index;
		setBackground(isIgnored ? Color.RED : this.getBackground());

		setBorder(tiltedBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY),
				Messages.getString("Points.list.title") + (titleNumber = (" #" + (index + 1)))));
		// setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setLayout(new BorderLayout());
		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.HALF_UP);
		JLabel label = new JLabel("( " + df.format(x) + " | " + df.format(y) + " )");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		add(label, BorderLayout.CENTER);
		delete = new JButton(Messages.getString("Points.list.delete"));
		add(delete, BorderLayout.EAST);
		setVisible(true);
	}

	public TitledBorder getTiltedBorder() {
		return tiltedBorder;
	}

	public void setDeleteController(ActionListener controller) {
		delete.addActionListener(controller);
	}

	public JButton getDeleteButton() {
		return delete;
	}

	public int getIndex() {
		return index;
	}

	public String getTitleNumber() {
		return titleNumber;
	}

}
