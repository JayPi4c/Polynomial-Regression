package com.JayPi4c.model;

import java.util.ArrayList;

import com.JayPi4c.Polynomial;

public class RegressionModel implements IModel {

	private double pos_x_axis = 10, neg_x_axis = 10, pos_y_axis = 10, neg_y_axis = 10;
	private double xSteps = 1, ySteps = 1;
	private boolean drawHints = true;

	@Override
	public ArrayList<Point> getPoints() {
		return Logic.getInstance().getPoints();
	}

	@Override
	public void addPoint(Point p) {
		Logic.getInstance().addPoint(p);
	}

	@Override
	public void removePoint(Point p) {
		Logic.getInstance().removePoint(p);
	}

	@Override
	public int getDegree() {
		return Logic.getInstance().getDegree();
	}

	@Override
	public int getMaxDegree() {
		return Logic.getInstance().getMaxDegree();
	}

	@Override
	public Polynomial getPolynomial() {
		return Logic.getInstance().getPolynomial();
	}

	@Override
	public void setPolynomial(Polynomial p) {
		Logic.getInstance().setPolynomial(p);
	}

	@Override
	public String getPolynomailFormular() {
		return null;
	}

	@Override
	public double getPos_x_axis() {
		return pos_x_axis;
	}

	@Override
	public void setPos_x_axis(double pos_x_axis) {
		this.pos_x_axis = pos_x_axis;
	}

	@Override
	public double getNeg_x_axis() {
		return neg_x_axis;
	}

	@Override
	public void setNeg_x_axis(double neg_x_axis) {
		this.neg_x_axis = neg_x_axis;
	}

	@Override
	public double getPos_y_axis() {
		return pos_y_axis;
	}

	@Override
	public void setPos_y_axis(double pos_y_axis) {
		this.pos_y_axis = pos_y_axis;
	}

	@Override
	public double getNeg_y_axis() {
		return neg_y_axis;
	}

	@Override
	public void setNeg_y_axis(double neg_y_axis) {
		this.neg_y_axis = neg_y_axis;
	}

	@Override
	public void removeAllPoints() {
		Logic.getInstance().setPoints(new ArrayList<Point>());
	}

	@Override
	public boolean drawHints() {
		return drawHints;
	}

	@Override
	public void setDrawHints(boolean hints) {
		this.drawHints = hints;
	}

	@Override
	public void setYSteps(double steps) {
		ySteps = steps;
	}

	@Override
	public double getYSteps() {
		return ySteps;
	}

	@Override
	public void setXSteps(double steps) {
		xSteps = steps;
	}

	@Override
	public double getXSteps() {
		return xSteps;
	}

	@Override
	public void update() {
		Logic.getInstance().update();
	}

	@Override
	public void setDegree(int deg) {
		Logic.getInstance().setDegree(deg);
	}

	@Override
	public void setMaxDegree(int deg) {
		Logic.getInstance().setMaxDegree(deg);
	}

	@Override
	public double getThreshold() {
		return Logic.getInstance().getThreshold();
	}

	@Override
	public void setThreshold(double thres) {
		Logic.getInstance().setThreshold(thres);
	}

	@Override
	public int getIterations() {
		return Logic.getInstance().getIterations();
	}

	@Override
	public void setIterations(int iter) {
		Logic.getInstance().setIterations(iter);
	}

	@Override
	public int getIgnoreCount() {
		return Logic.getInstance().getIgnoreCount();
	}

	@Override
	public void setIgnoreCount(int count) {
		Logic.getInstance().setIgnoreCount(count);
	}

	@Override
	public boolean getAutoAdjusting() {
		return Logic.getInstance().getAutoAdjusting();
	}

	@Override
	public void setAutoAdjusting(boolean flag) {
		Logic.getInstance().setAutoAdjusting(flag);
	}

	@Override
	public boolean getIgnoreOutliers() {
		return Logic.getInstance().getIgnoreOutliers();
	}

	@Override
	public void setIgnoreOutliers(boolean flag) {
		Logic.getInstance().setIgnoreOutliers(flag);
	}

}
