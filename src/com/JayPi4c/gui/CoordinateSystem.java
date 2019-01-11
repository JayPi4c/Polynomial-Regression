package com.JayPi4c.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.JayPi4c.logic.Logic;

public class CoordinateSystem extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1000943736638858551L;

	private int r = 3;

	private Logic logic;

	public CoordinateSystem() {
		this.logic = new Logic();
		this.setFocusable(true);
		this.addMouseListener(this);
	}

	public Logic getLogic() {
		return this.logic;
	}

	@Override
	public void paint(Graphics g) {
		BufferedImage buffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) buffer.getGraphics();
		g2d.setColor(new Color(51, 51, 51));
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

		// draw axis:
		g2d.setColor(new Color(0, 0, 255));
		g2d.drawLine(0, (int) (this.getHeight() * 0.5), this.getWidth(), (int) (this.getHeight() * 0.5));
		g2d.drawLine((int) (this.getWidth() * 0.5), 0, (int) (this.getWidth() * 0.5), this.getHeight());

		// draw the points:
		g2d.setColor(new Color(200, 200, 200));
		for (Point p : logic.points)
			g2d.fillOval(p.x - r + (int) (this.getWidth() * 0.5), p.y * -1 - r + (int) (this.getHeight() * 0.5), r * 2,
					r * 2);
		if (logic.points.size() > logic.getDegree()) {
			// draw the function:
			g2d.setColor(new Color(0, 255, 0));
			Point prev = new Point(-this.getWidth(), (int) logic.polynomial.getY(-this.getWidth()));
			for (int i = -this.getWidth() + 1; i < this.getWidth() + 1; i++) {
				Point p = new Point(i, (int) logic.polynomial.getY(i));
				g2d.drawLine(prev.x + (int) (this.getWidth() * 0.5), prev.y * -1 + (int) (this.getHeight() * 0.5),
						p.x + (int) (this.getWidth() * 0.5), p.y * -1 + (int) (this.getHeight() * 0.5));
				prev = p;
			}
		}
		if (logic.polynomial != null) {
			g2d.setColor(Color.WHITE);
			g2d.drawString(logic.polynomial.getFormularFormatted(), 10, this.getHeight() - 10);
		}
		g.drawImage(buffer, 0, 0, null);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Point point = e.getPoint();
		point.setLocation(point.x - this.getWidth() * 0.5, (point.y - this.getHeight() * 0.5) * -1);
		logic.points.add(point);
		logic.update();
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

}
