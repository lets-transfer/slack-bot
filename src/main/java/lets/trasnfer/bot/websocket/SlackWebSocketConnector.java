package lets.trasnfer.bot.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;
import java.util.HashMap;

public class SlackWebSocketConnector {

	private MessageDispatcher dispatcher;

	private String url;

	public void initialize(MessageDispatcher dispatcher, String token) throws IOException {
		this.dispatcher = dispatcher;

		// 1. 인증 받는다 https://slack.com/api/rtm.connect
		NameValuePair formParams = new BasicNameValuePair("token", token);
		Response response = Request.Post("https://slack.com/api/rtm.connect")
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
		WebSocketConnectionManager connectionManager = new WebSocketConnectionManager(client, handler, url);
		connectionManager.start();
	}
}
