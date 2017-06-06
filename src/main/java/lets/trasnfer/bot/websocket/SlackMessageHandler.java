package lets.trasnfer.bot.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lets.trasnfer.bot.websocket.vo.Message;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

class SlackMessageHandler implements WebSocketHandler {
	private final ObjectMapper objectMapper;

	SlackMessageHandler() {
		this.objectMapper = new ObjectMapper();
	}

	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("Established");
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> webSocketMessage) throws Exception {
		final String payload = webSocketMessage.getPayload().toString();
		final Message message = objectMapper.readValue(payload, Message.class);
		if (message.ofType("message")) {
			System.out.println(message.getText());
		} else {
			System.out.println(payload);
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
}
