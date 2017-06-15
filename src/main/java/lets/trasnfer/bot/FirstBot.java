package lets.trasnfer.bot;

import lets.trasnfer.bot.handler.CoinCurrencyHandler;
import lets.trasnfer.bot.handler.DustHandler;
import lets.trasnfer.bot.handler.MessageDispatcher;
import lets.trasnfer.bot.handler.WeatherHandler;
import lets.trasnfer.bot.websocket.SlackWebSocketConnector;

import java.io.IOException;

public class FirstBot {
	public static void main(String[] args) throws IOException {
		MessageDispatcher dispatcher = new MessageDispatcher();
		//dispatcher.addHandler("날씨", new WeatherHandler());
		dispatcher.addHandler("먼지" , new DustHandler());
		dispatcher.addHandler("코인", new CoinCurrencyHandler());


		SlackWebSocketConnector connector = new SlackWebSocketConnector();
		//connector.initialize(dispatcher, "xoxb-195932843410-pRTxhV1GKPQ8M35x0s2HvcZU");
		connector.initialize(dispatcher, "xoxb-197500881319-vhXDtKjBszQvCnvyEH7f7y4E");
		connector.connect();
	}
}
