package lets.trasnfer.bot.handler.movie;

import lets.trasnfer.bot.configuration.Configuration;

@Configuration(path = "config/handler/movie.json")
public interface MovieApiConfiguration {

	String host();

	String path();

	String apiKey();

}
