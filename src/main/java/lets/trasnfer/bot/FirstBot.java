package lets.trasnfer.bot;

import lets.trasnfer.bot.handler.MessageDispatcher;
import lets.trasnfer.bot.handler.coin.CoinCurrencyHandler;
import lets.trasnfer.bot.handler.dust.DustHandler;
import lets.trasnfer.bot.handler.movie.MovieInfoHandler;
import lets.trasnfer.bot.websocket.SlackWebSocketConnector;

import java.io.IOException;

public class FirstBot {
	public static void main(String[] args) throws IOException {
		MessageDispatcher dispatcher = new MessageDispatcher();
		//dispatcher.addHandler("날씨", new WeatherHandler());
		dispatcher.addHandler("먼지" , new DustHandler());
		dispatcher.addHandler("코인", new CoinCurrencyHandler());
		dispatcher.addHandler("영화", new MovieInfoHandler());

		SlackWebSocketConnector connector = new SlackWebSocketConnector(dispatcher);
		connector.initialize();
		connector.connect();
	}
}
