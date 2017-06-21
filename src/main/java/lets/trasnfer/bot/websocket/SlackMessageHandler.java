package lets.trasnfer.bot.websocket;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lets.trasnfer.bot.handler.MessageDispatcher;
import lets.trasnfer.bot.handler.vo.RequestMessage;
import lets.trasnfer.bot.handler.vo.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
class SlackMessageHandler implements WebSocketHandler {

	private final ObjectMapper objectMapper;
	private final MessageDispatcher dispatcher;

	SlackMessageHandler(MessageDispatcher dispatcher) {
		this.objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		this.dispatcher = dispatcher;
	}

	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("Connection Established");
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> webSocketMessage) throws Exception {
		final String payload = webSocketMessage.getPayload().toString();
		log.debug("Payload: {}", payload);
		final RequestMessage message = objectMapper.readValue(payload, RequestMessage.class);
		log.debug("Message : {} ", message);

		if (message.ofType("request")) {
			ResponseMessage response = dispatcher.getHandleMessage(message);
			session.sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log.error("Transport Error : ", exception);

	}

	@Override
	public void afterConnectionClosed(
			WebSocketSession session, CloseStatus closeStatus) throws Exception {

	}

	@Override
	public boolean supportsPartialMessages() {
		return true;
	}
}
