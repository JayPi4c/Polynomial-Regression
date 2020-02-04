package com.JayPi4c.utils;

import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static String BUNDLE_NAME = "com.JayPi4c.lang.messages_de";

	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private static ArrayList<ILanguageChangeListener> listeners = new ArrayList<ILanguageChangeListener>();

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static void registerListener(ILanguageChangeListener l) {
		listeners.add(l);
	}

	public static void removeListener(ILanguageChangeListener l) {
		listeners.remove(l);
	}

	public static void changeBundle(String bundleName) {
		BUNDLE_NAME = bundleName;
		RESOURCE_BUNDLE = ResourceBundle.getBundle(bundleName);
		for (ILanguageChangeListener l : listeners)
			l.onLanguageChanged();
	};

}
