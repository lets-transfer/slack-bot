package lets.trasnfer.bot.handler.shortenurl;

import lets.trasnfer.bot.configuration.Configuration;

@Configuration(path = "config/handler/shorten.json")
public interface GoogleApiConfiguration {

	String scheme();

	String url();

	String path();

	String apiKey();

}
