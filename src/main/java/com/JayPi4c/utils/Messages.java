package com.JayPi4c.gui;

import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static String BUNDLE_NAME = "com.JayPi4c.messages_de"; //$NON-NLS-1$

	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private static ArrayList<ILocaleChangeListener> listeners = new ArrayList<ILocaleChangeListener>();

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static void registerListener(ILocaleChangeListener l) {
		listeners.add(l);
	}

	public static void removeListener(ILocaleChangeListener l) {
		listeners.remove(l);
	}

	public static void changeBundle(String bundleName) {
		BUNDLE_NAME = bundleName;
		RESOURCE_BUNDLE = ResourceBundle.getBundle(bundleName);
		for (ILocaleChangeListener l : listeners)
			l.onLocaleChange();
	};

}
