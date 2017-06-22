package lets.trasnfer.bot.websocket;

import lets.trasnfer.bot.configuration.Configuration;

@Configuration(path = "config/base.json")
public interface BaseConfiguration {

	String token();

	String url();

}
