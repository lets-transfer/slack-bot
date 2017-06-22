package lets.trasnfer.bot.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lets.trasnfer.bot.configuration.ConfigurationLoader;
import lets.trasnfer.bot.handler.MessageDispatcher;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.message.BasicNameValuePair;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.jetty.JettyWebSocketClient;
import org.springframework.web.socket.handler.ExceptionWebSocketHandlerDecorator;

import java.io.IOException;
import java.util.HashMap;

public class SlackWebSocketConnector {

	private final BaseConfiguration baseConfiguration;
	private final MessageDispatcher dispatcher;

	private String url;

	public SlackWebSocketConnector(MessageDispatcher dispatcher) {
		this.dispatcher = dispatcher;
		this.baseConfiguration = ConfigurationLoader.load(BaseConfiguration.class);
	}

	public void initialize() throws IOException {
		// 1. 인증 받는다 https://slack.com/api/rtm.connect
		NameValuePair formParams = new BasicNameValuePair("token", baseConfiguration.token());
		Response response = Request.Post(baseConfiguration.url())
				.setHeader("Content-Type", "application/x-www-form-urlencoded")
				.bodyForm(formParams)
				.execute();

		// 결과 Json 파싱
		ObjectMapper om = new ObjectMapper();
		HashMap returnValue = om.readValue(response.returnContent().asBytes(), HashMap.class);
		this.url = (String) returnValue.get("url");
	}

	public void connect() {
		// 2. Web Socket 연결
		SslContextFactory sslContextFactory = new SslContextFactory();
		sslContextFactory.setTrustAll(true);
		org.eclipse.jetty.websocket.client.WebSocketClient webSocketClient = new org.eclipse.jetty.websocket.client.WebSocketClient(
				sslContextFactory);

		WebSocketClient client = new JettyWebSocketClient(webSocketClient);
		WebSocketHandler handler = new SlackMessageHandler(dispatcher);

		WebSocketConnectionManager connectionManager = new WebSocketConnectionManager(client, new ExceptionWebSocketHandlerDecorator(handler), url);
		connectionManager.start();
	}
}
