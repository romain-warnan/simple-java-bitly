package fr.plaisance.bitly;

public class BitlyTest {

	public static void main(String[] args) {
		String url = Bitly
			.of("2175eebfef303b70377f6d6d5dfcf9b1b9a9b4b3")
			.bitly()
			// .shorten("https://github.com/romain-warnan/simple-java-bitly");
			.expand("http://bit.ly/2cNk0Gp");
		System.out.println(url);
		Bitly
			.of("access_token")
			.proxyUri("http://proxy.host.com:port")
			.bitly()
			.shorten("longUrl");
		Bitly
			.of("access_token")
			.proxyUri("http://proxy.host.com:port")
			.proxyUsername("username")
			.proxyPassword("password")
			.bitly()
			.shorten("longUrl");
		System.out.println(url);
	}
}
