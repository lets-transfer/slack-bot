package lets.trasnfer.bot.handler.dust;

import lets.trasnfer.bot.configuration.Configuration;

@Configuration(path = "config/handler/dust.json")
public interface DustApiConfiguration {

	String scheme();

	String dustUrl();

	String dustPath();

	String dustApiKey();

	String locUrl();

	String locPath();

	String locApiKey();
}
