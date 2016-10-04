package fr.plaisance.bitly;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import fr.plaisance.bitly.Bitly.BitlyBuilder;
import fr.plaisance.utils.Strings;

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
public class Bit {

	private String access_token;
	private String proxyUri, proxyUsername, proxyPassword;

	private Bit(String access_token) {
		this.access_token = access_token;
	}

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

	/**
	 * <p>
	 * Shorten the given URL. The shortening is made by Bitly.
	 * </p>
	 * <p>
	 * The <code>longUrl</code> does not have to be encoded: the method will url-encode it before shortening it.
	 * </p>
	 * 
	 * @param longUrl The long URL that needs to be shorten by Bitly.
	 * @return The shorten URL.
	 */
	public String shorten(String longUrl) {
		String encodedUrl = this.encode(longUrl);
		return this.shorten(this.access_token, encodedUrl);
	}

	/**
	 * <p>
	 * Expand the given URL. The expanding is made by Bitly.
	 * </p>
	 * 
	 * @param shortUrl The shorten URL that needs to be expanded by Bitly.
	 * @return The expanded URL.
	 */
	public String expand(String shortUrl) {
		String encodedUrl = this.encode(shortUrl);
		return this.expand(this.access_token, encodedUrl);
	}

	private String shorten(String access_token, String longUrl) {
		String url = ClientBuilder
			.newClient(clientConfig())
			.target("https://api-ssl.bitly.com")
			.path("v3")
			.path("shorten")
			.queryParam("access_token", access_token)
			.queryParam("longUrl", longUrl)
			.queryParam("format", "txt")
			.request(MediaType.TEXT_PLAIN)
			.get(String.class);
		return Strings.chomp(url);
	}

	private String expand(String access_token, String shortUrl) {
		String url = ClientBuilder
			.newClient(clientConfig())
			.target("https://api-ssl.bitly.com")
			.path("v3")
			.path("expand")
			.queryParam("access_token", access_token)
			.queryParam("shortUrl", shortUrl)
			.queryParam("format", "txt")
			.request(MediaType.TEXT_PLAIN)
			.get(String.class);
		return Strings.chomp(url);

	}

	private ClientConfig clientConfig() {
		ClientConfig clientConfig = new ClientConfig();
		if (proxyUri != null) {
			clientConfig.connectorProvider(new ApacheConnectorProvider());
			clientConfig.property(ClientProperties.PROXY_URI, proxyUri);
		}
		if (proxyUsername != null) {
			clientConfig.property(ClientProperties.PROXY_USERNAME, proxyUsername);
			clientConfig.property(ClientProperties.PROXY_PASSWORD, proxyPassword);
		}
		return clientConfig;
	}

	private String encode(String url) {
		try {
			return URLEncoder.encode(url, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			// Nothing, UTF-8 is ok.
		}
		return url;
	}
}
