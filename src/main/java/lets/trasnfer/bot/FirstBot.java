package lets.trasnfer.bot;

import lets.trasnfer.bot.websocket.SlackWebSocketConnector;

import java.io.IOException;

public class FirstBot {
	public static void main(String[] args) throws IOException {
		SlackWebSocketConnector connector = new SlackWebSocketConnector();
		connector.initialize();
		connector.connect();
	}
}
