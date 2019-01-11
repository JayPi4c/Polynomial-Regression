package com.JayPi4c;

import java.util.Locale;
import java.util.ResourceBundle;

import com.JayPi4c.gui.Frame;

public class Main {

	public static ResourceBundle messages;

	public static void main(String args[]) {
		Locale currentLocale = Locale.getDefault();
		messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
		new Frame();
	}

}
