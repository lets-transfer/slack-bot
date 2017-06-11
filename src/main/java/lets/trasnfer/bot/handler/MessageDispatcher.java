package lets.trasnfer.bot.handler;

import lets.trasnfer.bot.exception.DuplicateHandlerException;
import lets.trasnfer.bot.websocket.vo.ResponseMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MessageDispatcher {

	private final Map<String, MessageHandler> handlers = new HashMap<>();

	public void addHandler(String keyword, MessageHandler messageHandler) {
		if (handlers.containsKey(keyword)) {
			throw new DuplicateHandlerException("Keyword already registered: " + keyword);
		}
		handlers.put(keyword, messageHandler);
	}

	public ResponseMessage getHandleMessage(String text) {
		String[] split = text.split(" "); // 환율 USD, 날씨 서울
		MessageHandler handler = handlers.get(split[0]);

		if (handler == null) {
			log.info("handler not found for message : {}", text);
			return new ResponseMessage("핸들러를 찾을 수 없습니다 T.T");
		}

		return handler.handle(text);
	}

}
