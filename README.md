# Simple java client for Bitly

A very simple URL shortener and expander for Java using Bitly API.

## Prerequisite

* You need to have an account on bitly. If you don't, you can create an account [here](https://bitly.com/a/sign_up).

* Then you need to generate a access token [there](https://bitly.com/a/oauth_apps). You will need this token to use this lib. 

## Usage

You need the access token to get an instance of Bitly object.
```
String access_token = "…"
Bitly bitly = Bitly.of(access_token).bitly();

```
Once you have an instance of Bitly, you can easily shorten or expand an URL:
```
String shortUrl = bitly.shorten("https://github.com/romain-warnan/simple-java-bitly");
String longUrl = bitly.expand("http://bit.ly/2cNk0Gp");
```
> Note that the long URL doesn't has to be URL encoded to be shorten.

Of course if you don't need to use the instance later, there is a one-liner:
```
String shortUrl = Bitly
	.of(access_token)
	.bitly()
	.shorten("https://github.com/romain-warnan/simple-java-bitly");
```

## Usage with a proxy

Either:
```
Bitly.of(access_token)
	.proxyUri("http://proxy.host.com:port")
	.bitly()
	.shorten(longUrl);
```

Or:
```
Bitly.of(access_token)
	.proxyUri("http://proxy.host.com:port")
	.proxyUsername("username")
	.proxyPassword("password")
	.bitly()
	.shorten(longUrl);
```