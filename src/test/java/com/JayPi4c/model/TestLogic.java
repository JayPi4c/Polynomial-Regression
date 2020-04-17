package com.JayPi4c.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.JayPi4c.Polynomial;

class TestLogic {

	@Test
	void testCalculateCoefficients() {
		Logic.getInstance().setThreshold(0.00);
		Logic.getInstance().setDegree(2);
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(0, 0));
		points.add(new Point(-1, 1));
		points.add(new Point(1, 1));
		points.add(new Point(2, 4));
		Polynomial p = Logic.getInstance().calculateCoefficients(points);
		assertEquals(25, p.getY(5));
		assertEquals(16, p.getY(-4));
	}

	@Test
	void testGetDistance() {
		Polynomial poly = new Polynomial(new double[] { 1, 1 }, new int[] { 2, 0 });
		Point p = new Point(0, 0);
		assertEquals(1, Logic.getInstance().getDistance(p, poly));
		poly = new Polynomial(new double[] { 1, 1 }, new int[] { 3, 0 });
		Point p1 = new Point(0, 2);
		assertEquals(Logic.getInstance().getDistance(p, poly), Logic.getInstance().getDistance(p1, poly));
	}

	@Test
	void testCalculateAdjustedCoefficients() {
		Logic.getInstance().setThreshold(0);
		Logic.getInstance().setDegree(0);
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(0, 0));
		points.add(new Point(-1, 1));
		points.add(new Point(1, 1));
		points.add(new Point(2, 4));

		Logic.getInstance().setPoints(points);
		Logic.getInstance().update();
		Polynomial result = Logic.getInstance().getPolynomial();
		assertEquals(2, result.getDegree());
	}

	@Test
	void testUpdate() {
		Logic.getInstance().setPoints(new ArrayList<Point>());
		Logic.getInstance().update();
		assertNull(Logic.getInstance().getPolynomial());

		// TODO: add more testcases for update-method
	}
}
