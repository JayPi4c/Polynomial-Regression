package com.JayPi4c.model;

public class Point {

	private double x, y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}
}