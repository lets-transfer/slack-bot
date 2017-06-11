package lets.trasnfer.bot;

//import lets.trasnfer.bot.handler.CurrencyHandler;
import lets.trasnfer.bot.handler.MessageDispatcher;
import lets.trasnfer.bot.handler.WeatherHandler;
import lets.trasnfer.bot.websocket.SlackWebSocketConnector;

import java.io.IOException;

public class FirstBot {
	public static void main(String[] args) throws IOException {
		MessageDispatcher dispatcher = new MessageDispatcher();
		dispatcher.addHandler("날씨", new WeatherHandler());
//		dispatcher.addHandler("환율", new CurrencyHandler());

		SlackWebSocketConnector connector = new SlackWebSocketConnector();
		connector.initialize(dispatcher, "xoxb-189896543783-xWwIU4WOcxoZuUNHHB7Ja5uZ");
		connector.connect();
	}
}
