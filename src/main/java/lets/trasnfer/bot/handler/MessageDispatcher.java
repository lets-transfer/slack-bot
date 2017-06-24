package lets.trasnfer.bot.handler;

import lets.trasnfer.bot.exception.DuplicateHandlerException;
import lets.trasnfer.bot.handler.vo.RequestMessage;
import lets.trasnfer.bot.handler.vo.ResponseMessage;
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

	public ResponseMessage getHandleMessage(RequestMessage message) {
		String text = message.getText();
		String[] split = text.split(" "); // 환율 USD, 날씨 서울
		MessageHandler handler = handlers.get(split[0]);

		if (handler == null) {
			log.info("handler not found for message : {}", text);
			ResponseMessage response = new ResponseMessage();
			response.setChannel(message.getChannel());
			response.setType("message");
			response.setText("알 수 없는 명령어입니다.");

			return response;
		}

		return handler.handle(message);
	}

}
