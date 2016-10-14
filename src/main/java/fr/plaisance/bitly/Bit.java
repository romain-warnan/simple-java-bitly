package fr.plaisance.bitly;

import fr.plaisance.bitly.Bitly.BitlyBuilder;

/**
 * <p>
 * Entry point of the lib..
 * </p>
 * <p>
 * Simple example:
 * </p>
 * 
 * <pre>
 * <code>
 * String shortUrl = Bit.ly(access_token).shorten("https://github.com/romain-warnan/simple-java-bitly");
 * </code>
 * </pre>
 * 
 * Example with proxy:
 * 
 * <pre>
 * <code>
 * String shortUrl = Bit.ly(access_token)
 * 	.proxyUri("http://proxy.host.com:port")
 * 	.proxyUsername("username")
 * 	.proxyPassword("password")
 * 	.bitly()
 *	.shorten(longUrl);
 * </code>
 * </pre>
 * 
 * @since 1.1
 * @author Romain Warnan romain.warnan@gmail.com
 */
@SuppressWarnings("deprecation")
public class Bit {

	/**
	 * Static method providing a {@link Bitly} instance.
	 * 
	 * @since 1.1, instead of {@link Bitly#of(String)}.
	 * @param access_token The secret token that you can have <a href="https://bitly.com/a/oauth_apps">here</a>, if you have a Bitly account.
	 * @return A builder that provide a Bitly instance when you call the {@link BitlyBuilder#bitly()} method.
	 */
	public static Bitly ly(String access_token) {
		return new Bitly(access_token);
	}
}
