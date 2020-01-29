package com.JayPi4c.gui;

import java.awt.Color;
import java.awt.Dimension;
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

	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;

	// mit diesem Punkt wird festgestellt, in welchem Vektor sich das
	// Koordinaten-System verschiebt
	private Point startPos = null;

	public boolean drawHints;
	public double pos_x_axis, neg_x_axis, pos_y_axis, neg_y_axis;
	public double x_steps, y_steps;

	public CoordinateSystem() {
		this(10, 10, 10, 10);
	}

	public CoordinateSystem(double pos_x_axis, double neg_x_axis, double pos_y_axis, double neg_y_axis) {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.pos_x_axis = pos_x_axis;
		this.neg_x_axis = neg_x_axis;
		this.pos_y_axis = pos_y_axis;
		this.neg_y_axis = neg_y_axis;
		this.x_steps = 1;
		this.y_steps = 1;
		this.drawHints = true;
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
		int w = this.getWidth(), h = this.getHeight();
		BufferedImage buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) buffer.getGraphics();
		g2d.setColor(new Color(51, 51, 51));
		g2d.fillRect(0, 0, w, h);

		// draw axis:
		g2d.setColor(new Color(0, 0, 255));

		// draw y-axis
		double proportion = neg_x_axis / (pos_x_axis + neg_x_axis);
		g2d.drawLine((int) (w * proportion), 0, (int) (w * proportion), h);

		// draw x-axis
		proportion = pos_y_axis / (pos_y_axis + neg_y_axis);
		g2d.drawLine(0, (int) (h * proportion), w, (int) (h * proportion));

		if (drawHints)
			drawHints(g2d);

		// draw the points:
		drawPoints(g2d);

		// draw the function
		drawFunction(g2d);

		// draw the function as text
		if (logic.polynomial != null) {
			g2d.setColor(Color.WHITE);
			g2d.drawString(logic.polynomial.getFormularFormatted(), 10, h - 10);
		}
		g.drawImage(buffer, 0, 0, null);
	}

	private void drawHints(Graphics2D g2d) {
		int w = this.getWidth(), h = this.getHeight();
		double originX = w * (neg_x_axis / (pos_x_axis + neg_x_axis));
		double originY = h * (pos_y_axis / (pos_y_axis + neg_y_axis));
		// x axis
		double proportion = x_steps / (pos_x_axis + neg_x_axis);
		double part = w * proportion;
		// neg part of x axis
		for (int i = 1; i <= (neg_x_axis / x_steps); i++) {
			double x = originX - i * part;
			g2d.drawLine((int) x, (int) (originY - 3), (int) x, (int) (originY + 3));
		}

		// pos part of x axis
		for (int i = 1; i <= (pos_x_axis / x_steps); i++) {
			double x = originX + i * part;
			g2d.drawLine((int) x, (int) (originY - 3), (int) x, (int) (originY + 3));
		}
		// y axis
		proportion = y_steps / (pos_y_axis + neg_y_axis);
		part = h * proportion;
		// neg part of y axis
		for (int i = 1; i <= (neg_y_axis / y_steps); i++) {
			double y = originY + i * part;
			g2d.drawLine((int) (originX - 3), (int) y, (int) (originX + 3), (int) y);
		}

		// pos part of y axis
		for (int i = 1; i <= (pos_y_axis / y_steps); i++) {
			double y = originY - i * part;

			g2d.drawLine((int) (originX - 3), (int) y, (int) (originX + 3), (int) y);
		}
	}

	private void drawPoints(Graphics2D g2d) {
		g2d.setColor(new Color(200, 200, 200));
		for (Point p : logic.points) {

			Point convertedPoint = convertPoint(p);
			g2d.fillOval((int) (convertedPoint.getX() - r), (int) (convertedPoint.getY() - r), r * 2, r * 2);
		}
	}

	private void drawFunction(Graphics2D g2d) {
		if (logic.points.size() > logic.getDegree()) {
			// draw the function:
			g2d.setColor(new Color(0, 255, 0));
			Point prev = convertPoint(new Point(-1 * neg_x_axis, logic.polynomial.getY(-1 * neg_x_axis)));
			for (double i = -1 * neg_x_axis + 0.001; i < pos_x_axis; i += 0.001) {
				Point p = new Point(i, logic.polynomial.getY(i));
				Point convertedP = convertPoint(p);
				g2d.drawLine((int) prev.getX(), (int) prev.getY(), (int) convertedP.getX(), (int) convertedP.getY());
				prev = convertedP;
			}
		}
	}

	private Point convertPoint(Point p) {
		int w = this.getWidth(), h = this.getHeight();
		double originX = w * (neg_x_axis / (pos_x_axis + neg_x_axis));
		double originY = h * (pos_y_axis / (pos_y_axis + neg_y_axis));
		double x = p.getX();
		double x_out;
		if (x < 0)
			x_out = map(x, -1 * neg_x_axis, 0, 0, originX);
		else
			x_out = map(x, 0, pos_x_axis, originX, w);
		double y_out;
		double y = p.getY();
		if (y < 0)
			y_out = map(y, pos_y_axis, 0, 0, originY);
		else
			y_out = map(y, 0, -1 * neg_y_axis, originY, h);
		return new Point(x_out, y_out);
	}

	private Point getPoint(double x, double y) {
		int w = this.getWidth(), h = this.getHeight();
		double originX = w * (neg_x_axis / (pos_x_axis + neg_x_axis));
		double originY = h * (pos_y_axis / (pos_y_axis + neg_y_axis));

		double x_out;
		if (x < originX)

			x_out = map(x, 0, originX, -1 * neg_x_axis, 0);
		else
			x_out = map(x, originX, w, 0, pos_x_axis);
		double y_out;
		if (y < originY)
			y_out = map(y, 0, originY, pos_y_axis, 0);
		else
			y_out = map(y, originY, h, 0, -1 * neg_y_axis);

		return new Point(x_out, y_out);
	}

	public void resizeWindow(Point p) {
		if (p.getX() < -1 * neg_x_axis)
			neg_x_axis = p.getX() + neg_x_axis * 0.01;
		else if (p.getX() > pos_x_axis)
			pos_x_axis = p.getX() + pos_x_axis * 0.01;
		if (p.getY() < -1 * neg_y_axis)
			neg_y_axis = p.getY() + neg_y_axis * 0.01;
		else if (p.getY() > pos_y_axis)
			pos_y_axis = p.getY() + pos_y_axis * 0.01;

	}

	private void moveSystem(double xOffset, double yOffset) {
		neg_x_axis -= xOffset;
		pos_x_axis += xOffset;
		neg_y_axis -= yOffset;
		pos_y_axis += yOffset;
	}

	// TODO: mousePressed started -> mouseDragged update -> mouseReleased nutzen, um
	// CoordSys zu verschieben
	@Override
	public void mouseReleased(MouseEvent e) {

		if (startPos != null) {
			Point endPos = getPoint(e.getX(), e.getY());
			double xOff = startPos.getX() - endPos.getX();
			double yOff = startPos.getY() - endPos.getY();
			startPos = null;
			moveSystem(xOff, yOff);
			repaint();
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point p;
		logic.points.add(p = getPoint(e.getX(), e.getY()));
		logic.update();
		resizeWindow(p);
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		startPos = getPoint(e.getX(), e.getY());
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// Mit dieser Funktion stimmt irgendetwas noch nicht so ganz. ist dieser Teil im
		// Programm implementiert, das verschieben goes crazy
		/*
		 * if (startPos != null) { Point endPos = getPoint(e.getX(), e.getY()); double
		 * xOff = startPos.getX() - endPos.getX(); double yOff = startPos.getY() -
		 * endPos.getY(); startPos = endPos; moveSystem(xOff, yOff); repaint();
		 * 
		 * }
		 */

	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	/**
	 * copied from Processing.org
	 * 
	 * @param value
	 * @param istart
	 * @param istop
	 * @param ostart
	 * @param ostop
	 * @return
	 */
	private static final double map(double value, double istart, double istop, double ostart, double ostop) {
		return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	}

}
