package fr.plaisance.utils;

public abstract class Strings {

	private static final char LF = '\n';
	private static final char CR = '\r';

	private Strings() {
		// Private constructor
	}

	/**
	 * <p>
	 * Checks if a String is empty ("") or null.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty("")        = true
	 * StringUtils.isEmpty(" ")       = false
	 * StringUtils.isEmpty("bob")     = false
	 * StringUtils.isEmpty("  bob  ") = false
	 * </pre>
	 * <p>
	 *
	 * @param string - the String to check, may be null
	 * @return {@code true} if the String is empty or null
	 */
	public static boolean isEmpty(final String string) {
		return string == null || string.length() == 0;
	}

	/**
	 * <p>
	 * Removes one newline from end of a String if it's there,
	 * otherwise leave it alone. A newline is &quot;{@code \n}&quot;,
	 * &quot;{@code \r}&quot;, or &quot;{@code \r\n}&quot;.
	 * </p>
	 * <p>
	 *
	 * <pre>
	 * StringUtils.chomp(null)          = null
	 * StringUtils.chomp("")            = ""
	 * StringUtils.chomp("abc \r")      = "abc "
	 * StringUtils.chomp("abc\n")       = "abc"
	 * StringUtils.chomp("abc\r\n")     = "abc"
	 * StringUtils.chomp("abc\r\n\r\n") = "abc\r\n"
	 * StringUtils.chomp("abc\n\r")     = "abc\n"
	 * StringUtils.chomp("abc\n\rabc")  = "abc\n\rabc"
	 * StringUtils.chomp("\r")          = ""
	 * StringUtils.chomp("\n")          = ""
	 * StringUtils.chomp("\r\n")        = ""
	 * </pre>
	 *
	 * @param string the String to chomp a newline from, may be null
	 * @return String without newline, {@code null} if null String input
	 */
	public static String chomp(final String string) {
		if (isEmpty(string)) {
			return string;
		}

		if (string.length() == 1) {
			final char ch = string.charAt(0);
			if (ch == CR || ch == LF) {
				return "";
			}
			return string;
		}

		int lastIndex = string.length() - 1;
		final char last = string.charAt(lastIndex);

		if (last == LF) {
			if (string.charAt(lastIndex - 1) == CR) {
				lastIndex--;
			}
		}
		else
			if (last != CR) {
				lastIndex++;
			}
		return string.substring(0, lastIndex);
	}
}
