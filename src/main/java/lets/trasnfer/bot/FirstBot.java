package lets.trasnfer.bot;

import lets.trasnfer.bot.handler.CoinCurrencyHandler;
import lets.trasnfer.bot.handler.MessageDispatcher;
import lets.trasnfer.bot.handler.MovieInfoHandler;
import lets.trasnfer.bot.handler.WeatherHandler;
import lets.trasnfer.bot.websocket.SlackWebSocketConnector;

import java.io.IOException;

public class FirstBot {
	public static void main(String[] args) throws IOException {

		MessageDispatcher dispatcher = new MessageDispatcher();
		dispatcher.addHandler("날씨", new WeatherHandler());
		dispatcher.addHandler("코인", new CoinCurrencyHandler());
		dispatcher.addHandler("영화", new MovieInfoHandler());

		SlackWebSocketConnector connector = new SlackWebSocketConnector();
		connector.initialize(dispatcher, "xoxb-196254644132-4P6cxcSCS1d7R8GpC3r6YlOz");
		connector.connect();
	}
}
