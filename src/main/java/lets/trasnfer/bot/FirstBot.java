package lets.trasnfer.bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.message.BasicNameValuePair;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.jetty.JettyWebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FirstBot {
	public static void main(String[] args) throws IOException {
		// 1. 인증 받는다 https://slack.com/api/rtm.connect
		// http client
		NameValuePair formParams = new BasicNameValuePair("token", "xxxx-xxxxxxx");

		Response response = Request.Post("https://slack.com/api/rtm.connect")
				.setHeader("Content-Type", "application/x-www-form-urlencoded")
				.bodyForm(formParams)
				.execute();


		// 결과 Json 파싱
		ObjectMapper om = new ObjectMapper();
		HashMap returnValue = om.readValue(response.returnContent().asBytes(), HashMap.class);
		String url = (String) returnValue.get("url");
		System.out.println(url);

		// 2. Web Socket 연결
		SslContextFactory sslContextFactory = new SslContextFactory();
		sslContextFactory.setTrustAll(true);
		org.eclipse.jetty.websocket.client.WebSocketClient webSocketClient = new org.eclipse.jetty.websocket.client.WebSocketClient(
				sslContextFactory);

		WebSocketClient client = new JettyWebSocketClient(webSocketClient);
		WebSocketHandler handler = new WebSocketHandler() {
			@Override
			public void afterConnectionEstablished(WebSocketSession session) throws Exception {
				System.out.println("Established");
			}

			@Override
			public void handleMessage(
					WebSocketSession session, WebSocketMessage<?> message) throws Exception {
				Map msg = om.readValue(message.getPayload().toString(), HashMap.class);
				if ( ((String) msg.get("type")).equals("message") ) {
					String text = (String) msg.get("text");
					System.out.println(text);
				} else {
					System.out.println(message.getPayload().toString());
				}

			}

			@Override
			public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
				System.out.println(exception.getMessage());

			}

			@Override
			public void afterConnectionClosed(
					WebSocketSession session, CloseStatus closeStatus) throws Exception {

			}

			@Override
			public boolean supportsPartialMessages() {
				return false;
			}
		};
		WebSocketConnectionManager connectionManager = new WebSocketConnectionManager(client, handler, url);
		connectionManager.start();


		// 3. 메시지 출력
	}
}
