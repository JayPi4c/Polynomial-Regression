package com.JayPi4c.model;

import java.util.ArrayList;

import com.JayPi4c.Polynomial;

public interface IModel {
	public ArrayList<Point> getPoints();

	public void addPoint(Point p);

	public void removePoint(Point p);

	public void removeAllPoints();

	public int getDegree();

	public void setDegree(int deg);

	public int getMaxDegree();

	public void setMaxDegree(int deg);

	public Polynomial getPolynomial();

	public void setPolynomial(Polynomial p);

	public double getThreshold();

	public void setThreshold(double thres);

	public int getIterations();

	public void setIterations(int iter);

	public int getIgnoreCount();

	public void setIgnoreCount(int count);

	public String getPolynomailFormular();

	public double getPos_x_axis();

	public void setPos_x_axis(double pos_x_axis);

	public double getNeg_x_axis();

	public void setNeg_x_axis(double neg_x_axis);

	public double getPos_y_axis();

	public void setPos_y_axis(double pos_y_axis);

	public double getNeg_y_axis();

	public void setNeg_y_axis(double neg_y_axis);

	public void setYSteps(double steps);

	public double getYSteps();

	public void setXSteps(double steps);

	public double getXSteps();

	public boolean drawHints();

	public void setDrawHints(boolean hints);

	public void update();

	public boolean getAutoAdjusting();

	public void setAutoAdjusting(boolean flag);

	public boolean getIgnoreOutliers();

	public void setIgnoreOutliers(boolean flag);
}
