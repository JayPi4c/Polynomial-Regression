package com.JayPi4c.logic;

import java.awt.Point;
import java.util.ArrayList;

import com.JayPi4c.Matrix;
import com.JayPi4c.Polynomial;

public class Logic {

	public static ArrayList<Point> points = new ArrayList<Point>();
	public static Polynomial polynomial;
	public static int degree;
	public static boolean autoAdjusting = false;
	public static int maxDegree = 8;
	public static double threshold = 20;

	public static void calculateCoefficients() {
		double b_data[] = new double[degree + 1];
		for (int i = 0; i < b_data.length; i++) {
			double sum = 0;
			for (Point p : points)
				sum += Math.pow(p.x, i) * p.y;
			b_data[i] = sum;
		}
		Matrix b = new Matrix(b_data.length, 1);
		b.setColumn(0, b_data);

		Matrix m = new Matrix(degree + 1, degree + 1);
		for (int i = 0; i < degree + 1; i++) {
			double row[] = new double[degree + 1];
			for (int j = 0; j < degree + 1; j++) {
				double sum = 0;
				for (Point p : points) {
					sum += Math.pow(p.x, i + j);
				}
				row[j] = sum;
			}
			m.setRow(i, row);
		}

		polynomial = new Polynomial();
		for (int i = 0; i < degree + 1; i++) {
			Matrix m_ = m.copy().setColumn(i, b_data);
			polynomial.add(polynomial.new Term(m_.det() / m.det(), i));
		}
		polynomial.combine();
		polynomial.reorder();
		polynomial.fill();

	}

	public static double getAverageDistance() {
		double sum = 0;
		for (Point p : points)
			sum += getDistance(p);
		return (sum / points.size());
	}

	public static double getDistance(Point p) {
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
		double dist1 = Math
				.sqrt((a - root1) * (a - root1) + (b - polynomial.getY(root1)) * (b - polynomial.getY(root1)));

		// -a+x-bf'(x)+f(x)*f'(x)
		double coeffs2[] = { -a, 1 };
		int degrees2[] = { 0, 1 };
		Polynomial p2 = new Polynomial(coeffs2, degrees2);
		Polynomial derivative = polynomial.getDerivation();
		derivative.mult(-b);
		p2.add(derivative);
		copy = polynomial.copy();
		copy.mult(polynomial.getDerivation());
		p2.add(copy);
		double root2 = p2.getRoot(a, 50);

		double dist2 = Math
				.sqrt((a - root2) * (a - root2) + (b - polynomial.getY(root2)) * (b - polynomial.getY(root2)));

		return Math.min(dist1, dist2);
	}

	public static void update() {
		if (autoAdjusting) {
			for (int i = 0; i <= Math.min(maxDegree, points.size() - 1); i++) {
				degree = i;
				calculateCoefficients();
				double dist = getAverageDistance();
				if (dist < threshold)
					break;
			}
		} else {
			calculateCoefficients();
		}
		polynomial.print();

	}

	// ------------------HELPER-----------------

	public static void addPoint(Point p) {
		points.add(p);
	}

	public static int getDegree() {
		return degree;
	}

	public static void setDegree(int d) {
		degree = Math.min(maxDegree, d);
	}

}
