package com.JayPi4c.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.JayPi4c.logic.Logic;
import com.JayPi4c.logic.Point;

public class CoordinateSystem extends JPanel implements MouseListener, MouseWheelListener, MouseMotionListener {

	private static final long serialVersionUID = 1000943736638858551L;

	private int r = 3;

	private Logic logic;

	private java.awt.Point beginMovingPoint = null;
	private java.awt.Point endMovingPoint = null;

	public CoordinateSystem() {
		this.logic = new Logic();
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		this.addMouseMotionListener(this);
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
		g2d.drawLine((int) getXPosInFrame(0), (int) getYPosInFrame(logic.y_max), (int) getXPosInFrame(0),
				(int) getYPosInFrame(logic.y_min)); // y-axis
		g2d.drawLine((int) getXPosInFrame(logic.x_min), (int) getYPosInFrame(0), (int) getXPosInFrame(logic.x_max),
				(int) getYPosInFrame(0)); // x-axis

		// draw the points:
		g2d.setColor(new Color(200, 200, 200));
		for (Point p : logic.points) {
			double x = getXPosInFrame(p.x) - r, y = getYPosInFrame(p.y) - r;
			g2d.fillOval((int) x, (int) y, r * 2, r * 2);
		}
		if (logic.points.size() > logic.getDegree()) {
			// draw the function:
			g2d.setColor(new Color(0, 255, 0));
			Point prev = new Point(logic.x_min, logic.polynomial.getY(logic.x_min));
			for (double i = logic.x_min + 0.001; i < logic.x_max; i += 0.001) {
				Point p = new Point(i, logic.polynomial.getY(i));
				g2d.drawLine((int) getXPosInFrame(prev.x), (int) getYPosInFrame(prev.y), (int) getXPosInFrame(p.x),
						(int) getYPosInFrame(p.y));
				prev = p;
			}
		}
		if (logic.polynomial != null) {
			g2d.setColor(Color.WHITE);
			g2d.drawString(logic.polynomial.getFormularFormatted(), 10, this.getHeight() - 10);
		}
		g.drawImage(buffer, 0, 0, null);
	}

	public double getXPosInFrame(double x) {
		if (x < 0) {
			return x / -logic.x_min * (this.getWidth() * 0.5) + this.getWidth() * 0.5;
		} else {
			return x / logic.x_max * (this.getWidth() * 0.5) + this.getWidth() * 0.5;
		}
	}

	public double getYPosInFrame(double y) {
		if (y < 0) {
			return y * -1 / -logic.y_min * (this.getHeight() * 0.5) + this.getHeight() * 0.5;
		} else {
			return y * -1 / logic.y_max * (this.getHeight() * 0.5) + this.getHeight() * 0.5;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		endMovingPoint = e.getPoint();
		Point vector = new Point(endMovingPoint.getX() - beginMovingPoint.getX(),
				endMovingPoint.getY() - beginMovingPoint.getY());
		// logic.move(vector);
		// logic.update();
		// repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point point = logic.getScaledPoint(e.getPoint(), getWidth(), getHeight());
		logic.points.add(point);
		logic.update();
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		beginMovingPoint = e.getPoint();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		java.awt.Point p = e.getPoint();
		System.out.print("x:" + p.getX() + " y:" + p.getY());
		Point pt = logic.getScaledPoint(p, getWidth(), getHeight());
		System.out.println("->   x:" + pt.getX() + " y:" + pt.getY());

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// damit sich der Frame flüssig verschiebt, muss in dieser Funktion das
		// Koordsystem immer wieder neu berechnet und gezeichnet werden.
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}
