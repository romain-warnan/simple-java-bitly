package fr.plaisance.bitly;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

public class Bitly {

	private String access_token;
	private String proxyUri, proxyUsername, proxyPassword;

	private Bitly(String access_token) {
		this.access_token = access_token;
	}

	// https://bitly.com/a/oauth_apps
	// 2175eebfef303b70377f6d6d5dfcf9b1b9a9b4b3
	public static BitlyBuilder of(String access_token) {
		return new BitlyBuilder(access_token);
	}

	public String shorten(String longUrl) {
		String encodedUrl = this.encode(longUrl);
		return this.shorten(this.access_token, encodedUrl);
	}

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
		catch (UnsupportedEncodingException e) {}
		return url;
	}

	public static class BitlyBuilder {

		private String access_token;
		private String proxyUri, proxyUsername, proxyPassword;

		private BitlyBuilder(String access_token) {
			this.access_token = access_token;
		}

		public BitlyBuilder proxyUri(String proxyUri) {
			this.proxyUri = proxyUri;
			return this;
		}

		public BitlyBuilder proxyUsername(String proxyUsername) {
			this.proxyUsername = proxyUsername;
			return this;
		}

		public BitlyBuilder proxyPassword(String proxyPassword) {
			this.proxyPassword = proxyPassword;
			return this;
		}

		public Bitly bitly() {
			Bitly bitly = new Bitly(this.access_token);
			bitly.proxyUri = this.proxyUri;
			bitly.proxyUsername = this.proxyUsername;
			bitly.proxyPassword = this.proxyPassword;
			return bitly;
		}
	}
}
