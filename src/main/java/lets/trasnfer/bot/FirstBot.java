package lets.trasnfer.bot;

import lets.trasnfer.bot.handler.*;
import lets.trasnfer.bot.websocket.SlackWebSocketConnector;

import java.io.IOException;

public class FirstBot {
	public static void main(String[] args) throws IOException {
		MessageDispatcher dispatcher = new MessageDispatcher();
		//dispatcher.addHandler("날씨", new WeatherHandler());
		dispatcher.addHandler("먼지" , new DustHandler());
		dispatcher.addHandler("코인", new CoinCurrencyHandler());
		dispatcher.addHandler("영화", new MovieInfoHandler());

		SlackWebSocketConnector connector = new SlackWebSocketConnector();
		connector.initialize(dispatcher, "abcd");
		connector.connect();
	}
}
