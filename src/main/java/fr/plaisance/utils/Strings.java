package fr.plaisance.utils;

public abstract class Strings {

	private Strings() {
		// Private constructor
	}

	public static String deleteLastChar(String string) {
		if (string == null || string.length() <= 1) {
			return "";
		}
		return string.substring(0, string.length() - 1);
	}
}
