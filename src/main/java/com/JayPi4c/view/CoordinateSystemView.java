package com.JayPi4c.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.JayPi4c.controller.CoordinateSystemController;
import com.JayPi4c.model.IModel;

public class CoordinateSystemView extends JPanel {

	private static final long serialVersionUID = 1L;

	IModel model;
	CoordinateSystemController controller;

	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;

	public CoordinateSystemView(IModel m) {
		model = m;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	public void setController(CoordinateSystemController controller) {
		this.controller = controller;
		this.addMouseListener(controller);
	}

	@Override
	public void paint(Graphics g) {
		BufferedImage buffer = controller.getImage();
		g.drawImage(buffer, 0, 0, null);
	}

}
