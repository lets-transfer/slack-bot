package lets.trasnfer.bot.handler;

import lets.trasnfer.bot.websocket.vo.Message;

public interface MessageHandler {
	Message handle(Message message);
}
