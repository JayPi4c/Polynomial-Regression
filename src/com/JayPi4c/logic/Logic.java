package com.JayPi4c.logic;

import java.util.ArrayList;

import com.JayPi4c.Matrix;
import com.JayPi4c.Polynomial;
import com.JayPi4c.Vector;

public class Logic {

	public ArrayList<Point> points;
	public ArrayList<Point> unignoredPoints;
	public Polynomial polynomial;
	public int degree = 0;
	public int maxDegree = 8;
	public double threshold = 0.3;
	public int iterations = 50;
	public boolean autoAdjusting = false;
	public boolean ignoreOutliers = false;
	public int ignoreCount = 1;

	public Logic() {
		points = new ArrayList<Point>();
		unignoredPoints = new ArrayList<Point>();
	}

	public Polynomial calculateCoefficients(ArrayList<Point> ps) {

		double b_data[] = new double[degree + 1];
		for (int i = 0; i < b_data.length; i++) {
			double sum = 0;
			for (Point p : ps)
				sum += Math.pow(p.getX(), i) * p.getY();
			b_data[i] = sum;
		}
		Vector v = new Vector(b_data);

		Matrix m = new Matrix(degree + 1, degree + 1);
		for (int i = 0; i < degree + 1; i++) {
			double row[] = new double[degree + 1];
			for (int j = 0; j < degree + 1; j++) {
				double sum = 0;
				for (Point p : ps) {
					sum += Math.pow(p.getX(), i + j);
				}
				row[j] = sum;
			}
			m.setRow(i, row);
		}

		Polynomial poly = new Polynomial();
		Vector coeffs = Matrix.getSolution(m, v);
		double[] data = coeffs.getData();
		for (int i = 0; i < degree + 1; i++) {
			poly.add(poly.new Term(data[i], i));
		}
		/*
		 * for (int i = 0; i < degree + 1; i++) { Matrix m_ = m.copy().setColumn(i,
		 * b_data); // Den Determinanten zu berechnen ist sehr zeitaufwendig und sollte
		 * evtl // in der Matrix library überarbeitet werden
		 * polynomial.add(polynomial.new Term(m_.det() / m.det(), i)); }
		 */
		poly.combine();
		poly.reorder();
		poly.fill();
		return poly;
	}

	private double getAverageDistance(ArrayList<Point> ps, Polynomial poly) {
		double sum = 0;
		for (Point p : ps)
			sum += getDistance(p, poly);
		return (sum / ps.size());
	}

	private double getDistance(Point p, Polynomial poly) {
		double a = p.getX();
		double b = p.getY();
		double coeffs1[] = { a * a, -2 * a, 1, b * b };
		int degrees1[] = { 0, 1, 2, 0 };
		// a²-2ax+x²+b²-2bf(x)+(f(x))²
		Polynomial p1 = new Polynomial(coeffs1, degrees1);
		Polynomial copy = poly.copy();
		copy.mult((-2 * b));
		p1.add(copy);
		copy = poly.copy();
		copy.mult(copy);
		p1.add(copy);
		p1.combine();
		p1.reorder();
		p1.fill();
		double root1 = p1.getRoot(a, iterations);
		double dist1 = Math.sqrt((a - root1) * (a - root1) + (b - poly.getY(root1)) * (b - poly.getY(root1)));

		// -a+x-bf'(x)+f(x)*f'(x)
		double coeffs2[] = { -a, 1 };
		int degrees2[] = { 0, 1 };
		Polynomial p2 = new Polynomial(coeffs2, degrees2);
		Polynomial derivative = poly.getDerivation();
		derivative.mult(-b);
		p2.add(derivative);
		copy = poly.copy();
		copy.mult(poly.getDerivation());
		p2.add(copy);
		double root2 = p2.getRoot(a, iterations);

		double dist2 = Math.sqrt((a - root2) * (a - root2) + (b - poly.getY(root2)) * (b - poly.getY(root2)));

		return Math.min(dist1, dist2);
	}

	/**
	 * Berechnet die Koeffizienten für den besten Polynom. Hierbei werden ggf.
	 * Punkte, die den Polynom verfälschen nicht ignoriert. Dies kann den Polynom
	 * deutlich verfälschen.
	 */
	private Polynomial calculateAdjustedCoefficients() {
		Polynomial poly = null;
		for (int i = 0; i <= Math.min(maxDegree, points.size() - 1); i++) {
			degree = i;
			poly = calculateCoefficients(points);
			double dist = getAverageDistance(points, poly);
			if (dist < threshold)
				break;
		}
		return poly;
	}

	@SuppressWarnings("unchecked")
	private Polynomial calculateCoefficientsIgnoreOutliers() {
		Polynomial poly;
		ArrayList<Point> current = (ArrayList<Point>) points.clone();
		int record_index = 0;
		current.remove(record_index);
		poly = calculateCoefficients(current);
		double record = getAverageDistance(current, poly);
		for (int i = 1; i < points.size(); i++) {
			current = (ArrayList<Point>) points.clone();
			current.remove(i);
			poly = calculateCoefficients(current);
			double d = getAverageDistance(current, poly);
			if (d < record) {
				record = d;
				record_index = i;
			}
		}
		current = (ArrayList<Point>) points.clone();
		current.remove(record_index);
		unignoredPoints = current;
		return calculateCoefficients(current);
	}

	private Polynomial calculateAdjustedCoefficientsIgnoreOutliers() {
		Polynomial poly = null;
		for (int i = 0; i <= Math.min(maxDegree, points.size() - 1); i++) {
			degree = i;
			poly = calculateCoefficientsIgnoreOutliers();
			double dist = getAverageDistance(unignoredPoints, poly);
			if (dist < threshold)
				break;
		}
		return poly;
	}

	public void update() {
		if (autoAdjusting && ignoreOutliers && points.size() > ignoreCount)
			polynomial = calculateAdjustedCoefficientsIgnoreOutliers();
		else if (autoAdjusting) {
			polynomial = calculateAdjustedCoefficients();
		} else if (ignoreOutliers) {
			// TODO
			if (points.size() > ignoreCount)
				polynomial = calculateCoefficientsIgnoreOutliers();
			else
				polynomial = calculateCoefficients(points);
		} else {
			polynomial = calculateCoefficients(points);
		}
		// polynomial.print();

	}

	// ------------------HELPER-----------------

	public void addPoint(Point p) {
		points.add(p);
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int d) {
		degree = Math.min(maxDegree, d);
	}

}
