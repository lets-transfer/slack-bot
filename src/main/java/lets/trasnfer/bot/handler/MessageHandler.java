package lets.trasnfer.bot.handler;

import lets.trasnfer.bot.websocket.vo.Message;
import lets.trasnfer.bot.websocket.vo.ResponseMessage;

public interface MessageHandler {
	ResponseMessage handle(Message message);
}
