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

		Polynomial polynomial;

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
			if (points.size() > k) {
				// draw the function:
				g2d.setColor(new Color(0, 255, 0));
				Point prev = new Point(-this.getWidth(), (int) this.polynomial.getY(-this.getWidth()));
				for (int i = -this.getWidth() + 1; i < this.getWidth() + 1; i++) {
					Point p = new Point(i, (int) this.polynomial.getY(i));
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
			if (points.size() > 0) {
				// do the Matrix math:
				calculateCoefficients();

				// calculate the average distance of the points to the function
				System.out.println("Der durchschnittliche Abstand betraegt: " + getAverageDistance());
			}
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

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

			polynomial = new Polynomial();
			for (int i = 0; i < k + 1; i++) {
				Matrix m_ = m.copy().setColumn(i, b_data);
				polynomial.add(polynomial.new Term(m_.det() / m.det(), i));
			}
			polynomial.combine();
			polynomial.reorder();
			polynomial.fill();

			// print the generated polynom:
			polynomial.print();
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
				JOptionPane.showMessageDialog(null, "Der Polynom lautet:\n"/* + getPolynomFormatted(coefficients) */,
						"Polynomial function", JOptionPane.INFORMATION_MESSAGE);
			} else if (e.getKeyChar() == 'd') {
				JOptionPane.showMessageDialog(null,
						"Die Ableitung lautet:\n" /* + getPolynomFormatted(getDerivative()) */, "Ableitung",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (e.getKeyChar() == 'a') {
				k += 1;
				calculateCoefficients();
				repaint();
			} else if (e.getKeyChar() == 's') {
				k = Math.max(0, k - 1);
				calculateCoefficients();
				repaint();
			}
			System.out.println("Der durchschnittliche Abstand betraegt: " + getAverageDistance());

		}

		public double getDistance(Point p) {
			double a = p.getX();
			double b = p.getY();
			double coeffs1[] = { a * a, -2 * a, 1, b * b };
			int degrees1[] = { 0, 1, 2, 0 };
			// a²-2ax+x²+b²-2bf(x)+(f(x))²
			Polynomial p1 = new Polynomial(coeffs1, degrees1);
			Polynomial copy = polynomial.copy();
			copy.mult((-2 * b));
			p1.add(copy);
			copy = polynomial.copy();
			copy.mult(copy);
			p1.add(copy);
			p1.combine();
			p1.reorder();
			p1.fill();
			double root1 = p1.getRoot(a, 50);
			double dist1 = Math.sqrt(
					(a - root1) * (a - root1) + (b - this.polynomial.getY(root1)) * (b - this.polynomial.getY(root1)));

			// -a+x-bf'(x)+f(x)*f'(x)
			double coeffs2[] = { -a, 1 };
			int degrees2[] = { 0, 1 };
			Polynomial p2 = new Polynomial(coeffs2, degrees2);
			Polynomial derivative = this.polynomial.getDerivation();
			derivative.mult(-b);
			p2.add(derivative);
			copy = polynomial.copy();
			copy.mult(this.polynomial.getDerivation());
			p2.add(copy);
			double root2 = p2.getRoot(a, 50);

			double dist2 = Math.sqrt(
					(a - root2) * (a - root2) + (b - this.polynomial.getY(root2)) * (b - this.polynomial.getY(root2)));

			return Math.min(dist1, dist2);
		}

		public double getAverageDistance() {
			double sum = 0;
			for (Point p : points)
				sum += getDistance(p);
			return (sum / points.size());
		}

	}

}
