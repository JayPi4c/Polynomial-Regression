package com.JayPi4c.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.JayPi4c.model.IModel;
import com.JayPi4c.model.Point;
import com.JayPi4c.utils.IPointAddedListener;
import com.JayPi4c.view.CoordinateSystemView;

public class CoordinateSystemController implements MouseListener {
	CoordinateSystemView view;

	private static final int R = 3;
	IModel model;

	private static ArrayList<IPointAddedListener> pointAddedListeners = new ArrayList<IPointAddedListener>();

	public CoordinateSystemController(IModel m, CoordinateSystemView v) {
		model = m;
		view = v;
	}

	public BufferedImage getImage() {
		int w = view.getWidth(), h = view.getHeight();
		BufferedImage buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) buffer.getGraphics();
		g2d.setColor(new Color(51, 51, 51));
		g2d.fillRect(0, 0, w, h);

		// draw axis:
		g2d.setColor(new Color(0, 0, 255));

		// draw y-axis
		double proportion = model.getNeg_x_axis() / (model.getPos_x_axis() + model.getNeg_x_axis());
		g2d.drawLine((int) (w * proportion), 0, (int) (w * proportion), h);

		// draw x-axis
		proportion = model.getPos_y_axis() / (model.getPos_y_axis() + model.getNeg_y_axis());
		g2d.drawLine(0, (int) (h * proportion), w, (int) (h * proportion));

		if (model.drawHints())
			drawHints(g2d);

		// draw the points:
		drawPoints(g2d);

		// draw the function
		drawFunction(g2d);

		// draw the function as text
		if (model.getPolynomial() != null) {
			g2d.setColor(Color.WHITE);
			g2d.drawString(model.getPolynomial().getFormularFormatted(), 10, h - 10);
		}
		return buffer;
	}

	private void drawHints(Graphics2D g2d) {
		int w = view.getWidth(), h = view.getHeight();
		double originX = w * (model.getNeg_x_axis() / (model.getPos_x_axis() + model.getNeg_x_axis()));
		double originY = h * (model.getPos_y_axis() / (model.getPos_y_axis() + model.getNeg_y_axis()));
		// x axis
		double proportion = model.getXSteps() / (model.getPos_x_axis() + model.getNeg_x_axis());
		double part = w * proportion;
		// neg part of x axis
		for (int i = 1; i <= (model.getNeg_x_axis() / model.getXSteps()); i++) {
			double x = originX - i * part;
			g2d.drawLine((int) x, (int) (originY - 3), (int) x, (int) (originY + 3));
		}

		// pos part of x axis
		for (int i = 1; i <= (model.getPos_x_axis() / model.getXSteps()); i++) {
			double x = originX + i * part;
			g2d.drawLine((int) x, (int) (originY - 3), (int) x, (int) (originY + 3));
		}
		// y axis
		proportion = model.getYSteps() / (model.getPos_y_axis() + model.getNeg_y_axis());
		part = h * proportion;
		// neg part of y axis
		for (int i = 1; i <= (model.getNeg_y_axis() / model.getYSteps()); i++) {
			double y = originY + i * part;
			g2d.drawLine((int) (originX - 3), (int) y, (int) (originX + 3), (int) y);
		}

		// pos part of y axis
		for (int i = 1; i <= (model.getPos_y_axis() / model.getYSteps()); i++) {
			double y = originY - i * part;

			g2d.drawLine((int) (originX - 3), (int) y, (int) (originX + 3), (int) y);
		}
	}

	private void drawPoints(Graphics2D g2d) {
		g2d.setColor(new Color(200, 200, 200));
		for (Point p : model.getPoints()) {

			Point convertedPoint = convertPoint(p);
			g2d.fillOval((int) (convertedPoint.getX() - R), (int) (convertedPoint.getY() - R), R * 2, R * 2);
		}
	}

	private void drawFunction(Graphics2D g2d) {
		if (model.getPoints().size() > model.getDegree()) {
			// draw the function:
			g2d.setColor(new Color(0, 255, 0));
			Point prev = convertPoint(
					new Point(-1 * model.getNeg_x_axis(), model.getPolynomial().getY(-1 * model.getNeg_x_axis())));
			for (double i = -1 * model.getNeg_x_axis() + 0.001; i < model.getPos_x_axis(); i += 0.001) {
				Point p = new Point(i, model.getPolynomial().getY(i));
				Point convertedP = convertPoint(p);
				g2d.drawLine((int) prev.getX(), (int) prev.getY(), (int) convertedP.getX(), (int) convertedP.getY());
				prev = convertedP;
			}
		}
	}

	private Point convertPoint(Point p) {
		int w = view.getWidth(), h = view.getHeight();
		double originX = w * (model.getNeg_x_axis() / (model.getPos_x_axis() + model.getNeg_x_axis()));
		double originY = h * (model.getPos_y_axis() / (model.getPos_y_axis() + model.getNeg_y_axis()));
		double x = p.getX();
		double x_out;
		if (x < 0)
			x_out = map(x, -1 * model.getNeg_x_axis(), 0, 0, originX);
		else
			x_out = map(x, 0, model.getPos_x_axis(), originX, w);
		double y_out;
		double y = p.getY();
		if (y < 0)
			y_out = map(y, model.getPos_y_axis(), 0, 0, originY);
		else
			y_out = map(y, 0, -1 * model.getNeg_y_axis(), originY, h);
		return new Point(x_out, y_out);
	}

	private Point getPoint(double x, double y) {
		int w = view.getWidth(), h = view.getHeight();
		double originX = w * (model.getNeg_x_axis() / (model.getPos_x_axis() + model.getNeg_x_axis()));
		double originY = h * (model.getPos_y_axis() / (model.getPos_y_axis() + model.getNeg_y_axis()));

		double x_out;
		if (x < originX)

			x_out = map(x, 0, originX, -1 * model.getNeg_x_axis(), 0);
		else
			x_out = map(x, originX, w, 0, model.getPos_x_axis());
		double y_out;
		if (y < originY)
			y_out = map(y, 0, originY, model.getPos_y_axis(), 0);
		else
			y_out = map(y, originY, h, 0, -1 * model.getNeg_y_axis());

		return new Point(x_out, y_out);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Point p;
		// model.addPoint(p = getPoint(e.getX(), e.getY()));

		model.addPoint(getPoint(e.getX(), e.getY()));
		model.update();
		view.repaint();

		for (IPointAddedListener listener : pointAddedListeners)
			listener.onPointAdded();
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public static void registerPointAddedListener(IPointAddedListener listener) {
		pointAddedListeners.add(listener);
	}

	public static void removePointAddedListener(IPointAddedListener listener) {
		pointAddedListeners.remove(listener);
	}

	private static final double map(double value, double istart, double istop, double ostart, double ostop) {
		return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	}
}
