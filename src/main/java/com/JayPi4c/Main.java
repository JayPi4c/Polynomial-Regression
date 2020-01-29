package com.JayPi4c;

import javax.swing.SwingUtilities;

import com.JayPi4c.gui.Frame;

public class Main {

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Frame();
			}
		});
	}

}
