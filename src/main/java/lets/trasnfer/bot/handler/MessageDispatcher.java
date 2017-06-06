package lets.trasnfer.bot.handler;

import lets.trasnfer.bot.exception.DuplicateHandlerException;

import java.util.HashMap;
import java.util.Map;

public class MessageDispatcher {

	private final Map<String, MessageHandler> handlers = new HashMap<>();

	public void addHandler(String keyword, MessageHandler messageHandler) {
		if (handlers.containsKey(keyword)) {
			throw new DuplicateHandlerException("Keyword already registered: " + keyword);
		}
		handlers.put(keyword, messageHandler);
	}

	public void getHandleMessage(String text) {
		String[] split = text.split(" "); // 환율 USD, 날씨 서울
		MessageHandler handler = handlers.get(split[0]);

		handler.handle(text);  //
	}

}
