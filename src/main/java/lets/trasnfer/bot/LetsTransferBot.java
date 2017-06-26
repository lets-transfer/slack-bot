package lets.trasnfer.bot;

import lets.trasnfer.bot.handler.MessageDispatcher;
import lets.trasnfer.bot.handler.coin.CoinCurrencyHandler;
import lets.trasnfer.bot.handler.dust.DustHandler;
import lets.trasnfer.bot.handler.hello.HelloHandler;
import lets.trasnfer.bot.handler.movie.MovieInfoHandler;
import lets.trasnfer.bot.handler.shortenurl.ShortenUrlHandler;
import lets.trasnfer.bot.websocket.SlackWebSocketConnector;

import java.io.IOException;

public class LetsTransferBot {
	public static void main(String[] args) throws IOException {
		MessageDispatcher dispatcher = new MessageDispatcher();
		//dispatcher.addHandler("날씨", new WeatherHandler());
		dispatcher.addHandler("먼지", DustHandler::new);
		dispatcher.addHandler("코인", CoinCurrencyHandler::new);
		dispatcher.addHandler("영화", MovieInfoHandler::new);
		dispatcher.addHandler("안녕", HelloHandler::new);
		dispatcher.addHandler("단축", ShortenUrlHandler::new);

		SlackWebSocketConnector connector = new SlackWebSocketConnector(dispatcher);
		connector.initialize();
		connector.connect();
	}
}
