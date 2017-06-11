package lets.trasnfer.bot.handler;

import lets.trasnfer.bot.websocket.vo.ResponseMessage;

public interface MessageHandler {
	ResponseMessage handle(String message);
}
