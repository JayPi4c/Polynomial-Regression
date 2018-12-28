package com.JayPi4c;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Main {

	public static void main(String args[]) {
		JFrame frame = new JFrame("Polynomial Regression");
		frame.setSize(640, 480);
		Panel p = new Panel();
		frame.setLayout(new BorderLayout());
		frame.add(p, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	static class Panel extends JPanel implements MouseListener, KeyListener {

		private static final long serialVersionUID = 1000943736638858551L;

		int k = 0;
		double coefficients[];

		ArrayList<Point> points;
		int r = 3;

		public Panel() {
			points = new ArrayList<Point>();
			this.setFocusable(true);
			this.addMouseListener(this);
			this.addKeyListener(this);
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
			for (Point p : points)
				g2d.fillOval(p.x - r + (int) (this.getWidth() * 0.5), p.y * -1 - r + (int) (this.getHeight() * 0.5),
						r * 2, r * 2);
			if (points.size() > k + 1) {
				// draw the function:
				g2d.setColor(new Color(0, 255, 0));
				Point prev = new Point(-this.getWidth(), (int) getY(-this.getWidth()));
				for (int i = -this.getWidth() + 1; i < this.getWidth() + 1; i++) {
					Point p = new Point(i, (int) getY(i));
					g2d.drawLine(prev.x + (int) (this.getWidth() * 0.5), prev.y * -1 + (int) (this.getHeight() * 0.5),
							p.x + (int) (this.getWidth() * 0.5), p.y * -1 + (int) (this.getHeight() * 0.5));
					prev = p;
				}
			}

			g.drawImage(buffer, 0, 0, null);
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			Point point = e.getPoint();
			point.setLocation(point.x - this.getWidth() * 0.5, (point.y - this.getHeight() * 0.5) * -1);
			points.add(point);
			// System.out.println("Ein Neuer Punkt bei (" + p.x + "|" + p.y + ")");
			if (points.size() > 1) {
				// do the Matrix math:
				calculateCoefficients();

				// calculate the average distance of the points to the function
				/*
				 * double avg = 0; for (Point p : points) avg += getDistance(p); avg /=
				 * points.size();
				 * System.out.println("Der durchschnittlche Abstand zur Funktion beträgt: " +
				 * avg);
				 */

			}
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		public double getY(double x) {
			double sum = 0;
			for (int i = coefficients.length - 1; i >= 0; i--)
				sum += coefficients[i] * Math.pow(x, i);
			return sum;
		}

		public void calculateCoefficients() {
			double b_data[] = new double[k + 1];
			for (int i = 0; i < b_data.length; i++) {
				double sum = 0;
				for (Point p : points)
					sum += Math.pow(p.x, i) * p.y;
				b_data[i] = sum;
			}
			Matrix b = new Matrix(b_data.length, 1);
			b.setColumn(0, b_data);
			// b.print();

			Matrix m = new Matrix(k + 1, k + 1);
			for (int i = 0; i < k + 1; i++) {
				double row[] = new double[k + 1];
				for (int j = 0; j < k + 1; j++) {
					double sum = 0;
					for (Point p : points) {
						sum += Math.pow(p.x, i + j);
					}
					row[j] = sum;
				}
				m.setRow(i, row);
			}

			// System.out.println("Das ist M:");
			// m.print();

			coefficients = new double[k + 1];
			for (int i = 0; i < coefficients.length; i++) {
				Matrix m_ = m.copy().setColumn(i, b_data);
				// System.out.println("Das ist M" + i + ":");
				// m_.print();
				coefficients[i] = m_.det() / m.det();
			}

			// print the generated polynom:
			System.out.println(getPolynom(coefficients));
		}

		public double getYDerivative(double x) {
			double coeffs[] = getDerivative();
			double sum = 0;
			for (int i = coeffs.length - 1; i >= 0; i--)
				sum += coeffs[i] * Math.pow(x, i);
			return sum;
		}

		public String getPolynom(double coeffs[]) {
			String s = "";
			for (int i = coeffs.length - 1; i >= 0; i--)
				s += coeffs[i] + (i > 0 ? "x" : "") + (i > 1 ? "^" + i : "")
						+ (i > 0 ? (coeffs[i - 1] >= 0 ? "+" : "") : "");
			return s;
		}

		public String getPolynomFormatted(double coeffs[]) {
			DecimalFormat df = new DecimalFormat("#.######");
			df.setRoundingMode(RoundingMode.HALF_UP);
			String s = "";
			for (int i = coeffs.length - 1; i >= 0; i--)
				s += df.format(coeffs[i]) + (i > 0 ? "x" : "") + (i > 1 ? "^" + i : "")
						+ (i > 0 ? (coeffs[i - 1] >= 0 ? "+" : "") : "");
			return s;
		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				int answer = JOptionPane.showConfirmDialog(null, "Sollen wirklich alle Punkte gelöscht werden?",
						"Punkte löschen?", JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					points = new ArrayList<Point>();
					repaint();
				}
			} else if (e.getKeyChar() == 'i') {
				JOptionPane.showMessageDialog(null, "Der Polynom lautet:\n" + getPolynomFormatted(coefficients),
						"Polynomial function", JOptionPane.INFORMATION_MESSAGE);
			} else if (e.getKeyChar() == 'd') {
				JOptionPane.showMessageDialog(null, "Die Ableitung lautet:\n" + getPolynomFormatted(getDerivative()),
						"Ableitung", JOptionPane.INFORMATION_MESSAGE);
			} else if (e.getKeyChar() == 'a') {
				k += 1;
				calculateCoefficients();
				repaint();
			} else if (e.getKeyChar() == 's') {
				k = Math.max(0, k - 1);
				calculateCoefficients();
				repaint();
			}
		}

		public double[] getDerivative() {
			double output[] = new double[coefficients.length - 1];
			for (int i = coefficients.length - 1; i >= 1; i--)
				output[i - 1] = coefficients[i] * i;
			return output;
		}

		/*
		 * public double getX(Point p) { double x = -this.getWidth() * 0.5; double a =
		 * p.x; double b = p.y; for (; x < this.getWidth() * 0.5; x += 0.0001) { double
		 * y = getY(x); double y_ = getYDerivative(x); double factor1 = 0.5 *
		 * Math.pow((a * a - 2 * a * x + x * x + b * b - 2 * b * y + y * y), -0.5);
		 * double factor2 = (-2 * a + 2 * x - 2 * b * y_ + 2 * y * y_); double product =
		 * factor1 * factor2; // System.out.println(product); if (Math.abs(product) <
		 * 0.2) { System.out.println(product + ": close enaugh"); return x; } }
		 * System.out.println("not found"); return this.getWidth(); }
		 * 
		 * public double getDistance(Point p) { double a = p.x; double b = p.y; double x
		 * = getX(p); if (x == this.getWidth()) return 5; double y = getY(x); double
		 * dist = Math.sqrt((a - x) * (a - x) + (b - y) * (b - y)); return dist; }
		 */
	}

}
