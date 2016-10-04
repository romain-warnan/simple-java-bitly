package fr.plaisance.bitly;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

/**
 * <p>
 * Main class of the lib. Once you have an instance of this class, you can call the {@link #shorten(String)} and {@link #expand(String)} methods.
 * </p>
 * <p>
 * Simple example:
 * </p>
 * 
 * <pre>
 * <code>
 * String shortUrl = Bit.ly(access_token)
 *	.bitly()
 *	.shorten("https://github.com/romain-warnan/simple-java-bitly");
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
 * @author Romain Warnan - romain.warnan@gmail.com
 */
public class Bitly {

	private String access_token;
	private String proxyUri, proxyUsername, proxyPassword;

	Bitly(String access_token) {
		this.access_token = access_token;
	}

	/**
	 * Static method providing a builder.
	 * 
	 * @deprecated Since 1.1, use {@link Bit#ly(String)} instead.
	 * @param access_token The secret token that you can have <a href="https://bitly.com/a/oauth_apps">here</a>, if you have a Bitly account.
	 * @return A builder that provide a Bitly instance when you call the {@link BitlyBuilder#bitly()} method.
	 */
	@Deprecated
	public static BitlyBuilder of(String access_token) {
		return new BitlyBuilder(access_token);
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
		return ClientBuilder
			.newClient(clientConfig())
			.target("https://api-ssl.bitly.com")
			.path("v3")
			.path("shorten")
			.queryParam("access_token", access_token)
			.queryParam("longUrl", longUrl)
			.queryParam("format", "txt")
			.request(MediaType.TEXT_PLAIN)
			.get(String.class);
	}

	private String expand(String access_token, String shortUrl) {
		return ClientBuilder
			.newClient(clientConfig())
			.target("https://api-ssl.bitly.com")
			.path("v3")
			.path("expand")
			.queryParam("access_token", access_token)
			.queryParam("shortUrl", shortUrl)
			.queryParam("format", "txt")
			.request(MediaType.TEXT_PLAIN)
			.get(String.class);
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

	/**
	 * Add a proxy to the Bitly instance.
	 * 
	 * @param proxyUri Proxy URI like that: <code>http://proxy.host.com:port</code>
	 * @return The object itself.
	 */
	public Bitly proxyUri(String proxyUri) {
		this.proxyUri = proxyUri;
		return this;
	}

	/**
	 * Specify a username for the proxy.
	 * 
	 * @param proxyUsername Set a username for the proxy.
	 * @return The object itself.
	 */
	public Bitly proxyUsername(String proxyUsername) {
		this.proxyUsername = proxyUsername;
		return this;
	}

	/**
	 * Specify a password for the proxy.
	 * 
	 * @param proxyPassword Set a password for the proxy.
	 * @return The object itself.
	 */
	public Bitly proxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
		return this;
	}

	/**
	 * Builder for Bitly objects.
	 * The {@link BitlyBuilder#bitly()} method returns an instance of {@link Bitly}.
	 * 
	 * @author Romain Warnan
	 */
	@Deprecated
	public static class BitlyBuilder {

		private String access_token;
		private String proxyUri, proxyUsername, proxyPassword;

		private BitlyBuilder(String access_token) {
			this.access_token = access_token;
		}

		/**
		 * Add a proxy to the Bitly instance.
		 * 
		 * @param proxyUri Proxy URI like that: <code>http://proxy.host.com:port</code>
		 * @return The builder itself.
		 */
		public BitlyBuilder proxyUri(String proxyUri) {
			this.proxyUri = proxyUri;
			return this;
		}

		/**
		 * Specify a username for the proxy.
		 * 
		 * @param proxyUsername Set a username for the proxy.
		 * @return The builder itself.
		 */
		public BitlyBuilder proxyUsername(String proxyUsername) {
			this.proxyUsername = proxyUsername;
			return this;
		}

		/**
		 * Specify a password for the proxy.
		 * 
		 * @param proxyPassword Set a password for the proxy.
		 * @return The builder itself.
		 */
		public BitlyBuilder proxyPassword(String proxyPassword) {
			this.proxyPassword = proxyPassword;
			return this;
		}

		/**
		 * This method returns a new Bitly instance.
		 * 
		 * @return A new Bitly instance.
		 */
		@Deprecated
		public Bitly bitly() {
			Bitly bitly = new Bitly(this.access_token);
			bitly.proxyUri = this.proxyUri;
			bitly.proxyUsername = this.proxyUsername;
			bitly.proxyPassword = this.proxyPassword;
			return bitly;
		}
	}
}
